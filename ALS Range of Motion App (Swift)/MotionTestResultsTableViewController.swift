//
//  MotionTestResultsTableViewController.swift
//  MotionGraphs
//
//  Created by Justin Larkin on 4/27/17.
//  Copyright © 2017 Apple. All rights reserved.
//

import Foundation
import UIKit

class MotionTestResultsTableViewController: UITableViewController  {
    
    var strings : [String] = ["Max Range:", "Avg. Range", "Max Velocity(degrees/sec):", "Avg. Velocity(degrees/sec):", "Number of Reps:"]
    
    var values = [String]()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        tableView.estimatedRowHeight = 45.0
        tableView.rowHeight = UITableViewAutomaticDimension
        
        tableView.reloadData()
    }
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return strings.count
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "TestResultsCell", for: indexPath) as! MotionTestResultsTableCell
        
        
        let resultString = strings[indexPath.row]
        let resultValue = values[indexPath.row]
        
        cell.stringLabel?.text = resultString
        cell.valueLabel?.text = resultValue
        
        
        
        return cell
    }
}
