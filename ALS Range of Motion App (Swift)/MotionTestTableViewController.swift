//
//  MotionTestTableViewController.swift
//  MotionGraphs
//
//  Created by Christopher Anderson on 4/23/17.
//  Copyright © 2017 Apple. All rights reserved.
//

import Foundation
import UIKit

class MotionTestTableViewController: UITableViewController {
    
    var motionTests = [String]()
    var identities = [String]()

    
    override func viewDidLoad() {
        motionTests = ["Right Arm Front","Right Arm Side","Left Arm Front","Left Arm Side", "Right Leg Front", "Left Leg Front"];
        identities = ["RightArmFront","RightArmSide","LeftArmFront","LeftArmSide","RightLeg","LeftLeg"];
        
    }
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return motionTests.count
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "Cell")
        
        cell?.textLabel!.text = motionTests[indexPath.row]
        
        return cell!
    }
    
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        
        let vcName = identities[indexPath.row]
        let viewController = storyboard?.instantiateViewController(withIdentifier: vcName)
        self.navigationController?.pushViewController(viewController!, animated: true)
        
    }
    
}


