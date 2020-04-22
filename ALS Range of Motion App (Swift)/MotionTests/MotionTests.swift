//
//  MotionTests.swift
//  MotionTests
//
//  Created by Charles Arvey on 4/29/17.
//  Copyright © 2017 Apple. All rights reserved.
//
import XCTest
@testable import MotionGraphs

class MotionTests: XCTestCase {
    
    var vc: MotionTestResultsViewController!
    
    override func setUp() {
        super.setUp()
        
        let storyboard = UIStoryboard(name: "MotionTest", bundle: nil)
        vc = storyboard.instantiateViewController(withIdentifier: "motionTestResults") as! MotionTestResultsViewController
        
    }
    
    override func tearDown() {
        super.tearDown()
    }
    
    func testVelocity() {
        let vals: [Double] = [0, 1, 2, 3, 2, 1, 0, -1, -2, -3, 0, 1, 2, 3]
        vc.getVelocityValues(velocityData: vals)
        
        XCTAssert(vc.maxVelocity == 3)
        XCTAssertEqualWithAccuracy(vc.avgVelocity, 1.71428571428571, accuracy: 0.3)
    }
    
    func testRangePeaks()
    {
        vc.rangeData = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1]
        vc.getRangePeaks()
        print(vc.peaks)
        print(vc.numReps)
        // XCTAssert(vc.numReps == 2)
    }
    
    
    
    
}
