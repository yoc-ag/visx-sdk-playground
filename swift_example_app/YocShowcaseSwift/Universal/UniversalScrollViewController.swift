//
//  UniversalScrollViewController.swift
//  YocShowcaseSwift
//
//  Created by Stefan Markovic on 7.4.22.
//

import UIKit
import VisxSDK

class UniversalScrollViewController: UIViewController {

    @IBOutlet weak var adContainer: UIView!
    @IBOutlet weak var containerHeightConstraint: NSLayoutConstraint!
    
    var adView: VisxAdView?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        loadAd()
    }
    
    fileprivate func loadAd() {
        // Initializes visxAdView
        // @param adUnit creative ID
        // @param appDomain publisher domain
        // @param delegate adViewDelegate, viewcontroller which is implementing VisxAdViewDelegate protocol
        // @param size dimensions of the creative. see VisxAdSize.h
        // @param isUniversal Boolean flag which determines if the creative is universal or not
        // For fixed size creative, change universal flag to false
        adView = VisxAdView(adUnit: "912268",
                            appDomain: "yoc.com",
                            adViewDelegate: self,
                            adSize: .kMediumRectangle300x250,
                            universal: true)
        adView?.load()
    }
}

extension UniversalScrollViewController: VisxAdViewDelegate {
    // ViewController for presenting VisxAdView
    func viewControllerForPresentingVisxAdView() -> UIViewController {
        self
    }
    // Delegate method is called when the ad content is received for the first time and the VisxAdView has finished rendering the content.
    // @param visxAdView visxAdView with content
    // @param effect placement effect of the creative
    func visxAdViewDidInitialize(visxAdView: VisxAdView, effect: VisxPlacementEffect) {
        containerHeightConstraint.constant = visxAdView.frame.size.height
        adContainer.addSubview(visxAdView)
    }
    // Delegate method is called when the ad has been closed.
    // @param visxAdView the visxAdView which is closed.
    func visxAdViewClosed(visxAdView: VisxAdView) {
        containerHeightConstraint.constant = 0
    }
    // Delegate method is called when retrieving the ad content has failed for any reason and provides a detail error message.
    // @param visxAdView visxAdView the corresponding VisxAdView
    // @param message descriptive message about the error that occurred
    // @param code status code about the error that occurred
    func visxAdFailedWithError(visxAdView: VisxAdView, message: String, code: Int) {
        containerHeightConstraint.constant = 0
    }
    // Delegate method is called when dimensions of visxAdView are changed.
    // @param visxAdView visxAdView the corresponding VisxAdView
    // @param width new visxAdView width
    // @param height new visxAdView height
    func visxAdViewSizeChange(visxAdView: VisxAdView, width: CGFloat, height: CGFloat) {
        containerHeightConstraint.constant = height
    }
}
