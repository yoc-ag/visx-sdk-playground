//
//  InterstitialViewController.swift
//  YocShowcaseSwift
//
//  Created by Stefan Markovic on 7.4.22.
//

import UIKit
import VisxSDK

class InterstitialViewController: UIViewController {

    @IBOutlet weak var showAd: UIButton!

    var adView: VisxAdView?
    var isAdReady = false

    override func viewDidLoad() {
        super.viewDidLoad()
    }

    @IBAction func loadAd(_ sender: UIButton) {
        // Initializes visxAdView
        // @param adUnit creative ID
        // @param appDomain publisher domain
        // @param delegate adViewDelegate, viewcontroller which is implementing VisxAdViewDelegate protocol
        // @param size dimensions of the creative. see VisxAdSize.h
        // @param isUniversal Boolean flag which determines if the creative is universal or not
        // For fixed size creative, change universal flag to false
        isAdReady = false
        adView = VisxAdView(adUnit: "912263",
                            appDomain: "yoc.com",
                            adViewDelegate: self,
                            adSize: .kInterstitial1x1,
                            universal: true)
        adView?.load()
        adView?.setInterstitialBackgroundColor(color: .black)
    }

    @IBAction func showAd(_ sender: UIButton) {
        if isAdReady {
            adView?.showInterstitial()
        }
    }
}

extension InterstitialViewController: VisxAdViewDelegate {
    // ViewController for presenting VisxAdView
    func viewControllerForPresentingVisxAdView() -> UIViewController {
        self
    }
    // Delegate method is called when the ad content is received for the first time and the VisxAdView has finished rendering the content.
    // @param visxAdView visxAdView with content
    // @param effect placement effect of the creative
    func visxAdViewDidInitialize(visxAdView: VisxAdView, effect: VisxPlacementEffect) {
        showAd.isEnabled = true
        isAdReady = true
    }
    // Delegate method is called when the ad has been closed.
    // @param visxAdView the visxAdView which is closed.
    func visxAdInterstitialClosed(visxAdView: VisxAdView) {
        showAd.isEnabled = false
        isAdReady = false
    }
    // Delegate method is called when retrieving the ad content has failed for any reason and provides a detail error message.
    // @param visxAdView visxAdView the corresponding VisxAdView
    // @param message descriptive message about the error that occurred
    // @param code status code about the error that occurred
    func visxAdFailedWithError(visxAdView: VisxAdView, message: String, code: Int) {
        isAdReady = false
    }
}
