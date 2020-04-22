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


class MotionTestViewControllerLateral: UIViewController {
    // MARK: Properties
    
    var motionManager = CMMotionManager()
    
    var player: AVAudioPlayer?
    
    @IBOutlet weak var countdownTimer: UILabel!
    
    @IBOutlet weak var testLabel: UILabel!
    
    var rangeData: [Double] = []
    var velocityData: [Double] = []
    var initialAttitude: CMAttitude = CMAttitude()
    
    
    //var count: Int = 40
    
    //10 seconds for testing purposes
    var count: Int = 10
    
    var preCount: Int = 4
    
    var updateInterval = 0.1
    
    var timer: Timer = Timer()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        
        //(will count down from 5, play beep to signal start of test, then begin main timer and collect data)
        startPreCountdown()
        
        startUpdates()
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
            print("motion not available")
        }
    }
    
    func stopUpdates() {
        if motionManager.isDeviceMotionAvailable {
            motionManager.stopDeviceMotionUpdates()
        }
    }
    
    func updateTimer() {
        if (count > 0) {
            count -= 1
            countdownTimer.text = String(count)
        } else {
            timer.invalidate()
            stopUpdates()
            performSegue(withIdentifier: "MotionTestResultsSegue", sender: nil)
        }
    }
    
    //plays sound and starts the main timer when finished
    func updatePreTimer() {
        if (preCount > 0) {
            preCount -= 1
            countdownTimer.text = String(preCount)
        } else {
            timer.invalidate()
            testLabel.text = ""
            playSound()
            startInitialCountdown()
        }
    }
    
    func handleIncomingData(deviceMotion: CMDeviceMotion){
        
        
        let currentAttitude = deviceMotion.attitude
        
        //if this is the first reading, set the initial attitude of the device
        if (rangeData.count == 0) {
            initialAttitude = currentAttitude
            self.rangeData.append(initialAttitude.roll)
            
        }
            //multiply the current reading by the inverse of the initial attitude at the beginning of the test (this will return the change in degrees from the starting position)
        else{
            
            currentAttitude.multiply(byInverseOf: initialAttitude)
            
            //Y-Axis Rotation Angle (degrees)
            let rollY = fabs(currentAttitude.roll * (180/Double.pi))
            
            //Y-Axis Rotation Rate (degrees per second)
            let rotationRateY = deviceMotion.rotationRate.y * (180/Double.pi)
            
            
            //add values to data matrix(first row - range of motion, second row - velocity)
            self.rangeData.append(rollY)
            self.velocityData.append(rotationRateY)
            
        }
        
    }
    
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "MotionTestResultsSegue" {
            if let resultsVC = segue.destination as? MotionTestResultsViewController {
                resultsVC.rangeData = rangeData
                resultsVC.velocityData = velocityData
            }
        }
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
