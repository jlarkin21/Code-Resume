//
//  MotionTestViewController.swift
//  MotionGraphs
//
//  Created by Charles Arvey on 3/7/17.
//  Copyright © 2017 Apple. All rights reserved.
//
import UIKit
import CoreMotion
import simd
import AVFoundation


class MotionTestViewController: UIViewController {
    // MARK: Properties
    
    var motionManager = CMMotionManager()
    
    var player: AVAudioPlayer?
    
    @IBOutlet weak var countdownTimer: UILabel!
    
    @IBOutlet weak var testLabel: UILabel!
    
    @IBOutlet var nextButton: UIBarButtonItem!
    
    var rangeData: [Double] = []
    var velocityData: [Double] = []
    var initialAttitude: CMAttitude = CMAttitude()
    
    var timerCount: Int = 40
    var preTestTimerCount: Int = 4
    var updateInterval = 0.1
    var timer: Timer = Timer()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        //hide next button
        self.navigationItem.rightBarButtonItem = nil
        
        //(will count down from 5, play beep to signal start of test, then begin main timer and collect data)
        startPreCountdown()
        
    }
    
    func startPreCountdown(){
        timer = Timer.scheduledTimer(timeInterval: 1.0, target: self, selector: #selector(updatePreTimer), userInfo: nil, repeats: true)
    }
    
    func startInitialCountdown(){
        timer = Timer.scheduledTimer(timeInterval: 1.0, target: self, selector: #selector(updateTimer), userInfo: nil, repeats: true)
    }
    
    func startUpdates() {

        if motionManager.isDeviceMotionAvailable {
            motionManager.deviceMotionUpdateInterval = updateInterval
            motionManager.showsDeviceMovementDisplay = true
            motionManager.startDeviceMotionUpdates(to: OperationQueue.current!, withHandler: { (deviceMotionData: CMDeviceMotion?, NSError) -> Void in
                self.handleIncomingData(deviceMotion: deviceMotionData!)
                if(NSError != nil){
                    print("\(String(describing: NSError))")
                }
            })
        }
        else{
            timer.invalidate()
            //create alert if no data collected
            let alert = UIAlertController(title: "Alert", message: "Device motion not available.", preferredStyle: UIAlertControllerStyle.alert)
            alert.addAction(UIAlertAction(title: "Ok", style: UIAlertActionStyle.default, handler: nil))
            self.present(alert, animated: true, completion: nil)
        }
    }
    
    func stopUpdates() {
        if motionManager.isDeviceMotionAvailable {
            motionManager.stopDeviceMotionUpdates()
        }
    }
    
    func updateTimer() {
        if(timer.isValid) {
            if (timerCount > 0) {
                timerCount -= 1
                countdownTimer.text = String(timerCount)
            } else {
                timer.invalidate()
                playSound()
                stopUpdates()
                performSegue(withIdentifier: "MotionTestResultsSegue", sender: nil)
                
            }
        }
    }
    
    //plays sound and starts the main timer when finished
    func updatePreTimer() {
        if(timer.isValid){
            if (preTestTimerCount > 0) {
                preTestTimerCount -= 1
                countdownTimer.text = String(preTestTimerCount)
            } else {
                timer.invalidate()
                testLabel.text = ""
                playSound()
                startInitialCountdown()
                startUpdates()
               
            }
        }
    }
    
    func handleIncomingData(deviceMotion: CMDeviceMotion){
        
        
        let currentAttitude = deviceMotion.attitude
        
        //if this is the first reading, set the initial attitude of the device
        if (rangeData.count == 0) {
            initialAttitude = currentAttitude
            self.rangeData.append(initialAttitude.roll)
            
        }
            

        else{
            
            //multiply the current reading by the inverse of the initial attitude at the beginning of the test (this will return the change in degrees from the starting position)
            currentAttitude.multiply(byInverseOf: initialAttitude)
            
            //Y-Axis Rotation Angle (degrees)
            var rollY = currentAttitude.roll * (180/Double.pi)
            
            //if range goes past 180, the values will turn negative, change this so that value will positively increase past 180 instead
            if(rollY < 0) {
                rollY = 360 + rollY
            }
            
            //if phone declines past the initial point values will go to 360 degrees and go down the further you go, change these values to the negative degree relative to the starting position
            if (rollY > 270){
                rollY = rollY - 360
            }
            
            
            //Y-Axis Rotation Rate (degrees per second)
            let rotationRateY = deviceMotion.rotationRate.y * (180/Double.pi)
            
            
            //add values to data matrix(first row - range of motion, second row - velocity)
            self.rangeData.append(rollY)
            self.velocityData.append(rotationRateY)
            
        }
        
    }
    
//    //check to see whether any data was collected before performing segue
//    override func shouldPerformSegue(withIdentifier identifier: String?, sender: Any?) -> Bool {
//        if let ident = identifier {
//            if ident == "MotionTestResultsSegue" {
//                if (velocityData.count <= 1 || rangeData.count <= 1) {
//                    
//                    //create alert if no data collected
//                    let alert = UIAlertController(title: "Alert", message: "An error occured and no data was collected from the test.", preferredStyle: UIAlertControllerStyle.alert)
//                    alert.addAction(UIAlertAction(title: "Ok", style: UIAlertActionStyle.default, handler: nil))
//                    self.present(alert, animated: true, completion: nil)
//                    
//                    return false
//                }
//            }
//        }
//        return true
//    }
    
    //pass results data to MotionTestResultsViewController
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "MotionTestResultsSegue" {
            if let resultsVC = segue.destination as? MotionTestResultsViewController {
                
                resultsVC.rangeData = rangeData
                resultsVC.velocityData = velocityData
            }
        }
    }
    
    //stop updates and timer when leaving view
    override func viewWillDisappear(_ animated: Bool) {
        timer.invalidate()
        stopUpdates()
        super.viewWillDisappear(animated)
    }
    
    //function to play beep
    func playSound() {
        let url = Bundle.main.url(forResource: "beep", withExtension: "mp3")!
        
        do {
            player = try AVAudioPlayer(contentsOf: url)
            guard let player = player else { return }
            
            player.prepareToPlay()
            player.play()
        } catch let error {
            print(error.localizedDescription)
        }
    }
    
    
}
