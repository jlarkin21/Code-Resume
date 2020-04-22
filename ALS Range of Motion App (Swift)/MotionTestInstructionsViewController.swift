//
//  MotionTestInstructionsViewController.swift
//  MotionGraphs
//
//  Created by Charles Arvey on 3/7/17.
//  Copyright © 2017 Apple. All rights reserved.
//

import UIKit

class MotionTestInstructionsViewController: UIViewController {
    
    
    @IBOutlet weak var webView: UIWebView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let url = URL(string: "http://alsneversurrender.org/videos-how-to-motion/")
        let request = URLRequest(url: url!)
        webView.loadRequest(request)
        
        self.webView.frame = self.view.bounds
        self.webView.scalesPageToFit = true
    }

    
    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
        
    }
    
    
}
