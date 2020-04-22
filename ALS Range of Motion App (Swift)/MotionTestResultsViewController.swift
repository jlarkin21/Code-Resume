//
//  MotionTestResultsViewController.swift
//  MotionGraphs
//
//  Created by Charles Arvey on 3/7/17.
//  Copyright © 2017 Apple. All rights reserved.
//
import UIKit
import Foundation

class MotionTestResultsViewController: UIViewController {
    
    
    var rangeData : [Double] = []
    var velocityData : [Double] = []
    
    
    var maxRange = 0.0
    var avgRange = 0.0
    var maxVelocity = 0.0
    var avgVelocity : Double = 0.0
    var numReps = 0
    var peaks: [Double] = [];
    
    @IBOutlet weak var graphView: GraphView!
    
    
    override func viewDidLoad() {
        super.viewDidLoad()

        //after view loads, prepare() for results table segue is called
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "ResultsTableSegue" {
            if let resultsVC = segue.destination as? MotionTestResultsTableViewController {
                
                //Range
                if(rangeData.count <= 1) {
                    let alert = UIAlertController(title: "Alert", message: "An error occured and no range of motion data was collected from the test.", preferredStyle: UIAlertControllerStyle.alert)
                    alert.addAction(UIAlertAction(title: "Ok", style: UIAlertActionStyle.default, handler: nil))
                    self.present(alert, animated: true, completion: nil)
                }else{
                    populateGraph()
                
                    //get peak values for range of motion
                    getRangePeaks()
                    
                }
                
                //Velocity
                if(velocityData.count <= 1) {
                    let alert = UIAlertController(title: "Alert", message: "An error occured and no velocity data was collected from the test.", preferredStyle: UIAlertControllerStyle.alert)
                    alert.addAction(UIAlertAction(title: "Ok", style: UIAlertActionStyle.default, handler: nil))
                    self.present(alert, animated: true, completion: nil)
                    
                } else {
                    //get peak values and max for velocity
                    getVelocityValues(velocityData: self.velocityData)
                
                    //send data to results table view
                    resultsVC.values.append(String(Int(maxRange)) + "\u{00B0}")
                    resultsVC.values.append(String(Int(avgRange)) + "\u{00B0}")
                    resultsVC.values.append(String(Int(maxVelocity)))
                    resultsVC.values.append(String(Int(avgVelocity)))
                    resultsVC.values.append(String(numReps))
                }
            }
        }
    }

    
    func populateGraph(){
        
        // add all data to the results graph in reverse to show time as start -> end
        // as opposed to most recent -> oldest
        for rangeValue in rangeData.reversed() {
            //log max point
            if rangeValue > maxRange{
                maxRange = rangeValue
            }
            self.graphView.add(rangeValue)
        }
        
    }
    
    func getRangePeaks(){
        var left = 0
        var right = 0
        
        //set range to how long incline and decline intervals have to be in order to be classified as a peak 25->2.5 seconds
        let range = 25
        
        //look for peaks
        for var i in 0..<rangeData.count {
            var isPeak = true;
            // Check from left to right
            left = max(0, i - range);
            right = min(rangeData.count - 1, i + range);
            for j in stride(from: left, through: right, by: 1) {
                
                // Skip if we are on current
                if (i == j) {
                    continue;
                }
                if (rangeData[i] < rangeData[j]) {
                    isPeak = false;
                    break;
                }
            }
            
            if (isPeak) {
                //peak has to be greater than 20 degrees in order to qualify, eliminates some false peak values at the beginning of the test
                if(rangeData[i] > 20){
                    peaks += [rangeData[i]]
                    numReps += 1;
                }
                i += range;
            }
        }
        getPeakAverage()
        
    }
    
    //calculates the average of the peaks array
    func getPeakAverage(){
        var sum = 0.0
        if peaks.count != 0{
            for data in peaks {
                sum += data
            }
            avgRange = sum/Double(peaks.count)
        }
    }
    
    func getVelocityValues(velocityData: [Double]){
        
        
        //only get velocity values from incline
        var sortedData: [Double] = []
        for data in velocityData.sorted(){
            
            if data > 2 {
                sortedData += [fabs(data)]
            }
        }
        
        //take trimmed mean (avg of middle 70% of the data)

        
        //number of values trimmed off each side (15%)
        let numTrimmedAvg = Int(floor(Double(sortedData.count) * 0.15))
    
        //find average of trimmed data
        var sum = 0.0
        var count = 0
        for i in stride(from: numTrimmedAvg - 1, to: (sortedData.count - numTrimmedAvg), by: 1) {
            count+=1
            
            sum += sortedData[i]
            
        }
        avgVelocity = sum/Double(count)
        
        
        //get max from middle 95% of data
        
        //number of values trimmed off each side (2.5%)
        let numTrimmedMax = Int(ceil(Double(sortedData.count) * 0.025))
        
        //find max of trimmed data
        for i in stride(from: numTrimmedMax - 1, to: (sortedData.count - numTrimmedMax), by: 1) {
            count+=1
  
            //log max velocity
            if sortedData[i] > maxVelocity{
                maxVelocity = sortedData[i]
            }
        }
        
    }
    
}
