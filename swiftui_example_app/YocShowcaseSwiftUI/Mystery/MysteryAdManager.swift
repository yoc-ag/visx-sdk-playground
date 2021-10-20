//
//  MysteryAdManager.swift
//  YocShowcaseSwiftUI
//
//  Created by Stefan Markovic on 15.9.21.
//

import SwiftUI
import Combine
import VisxSDK

class MysteryAdManager: NSObject, ObservableObject, VisxAdViewDelegate {

    var objectWillChange = PassthroughSubject<Void, Never>()

    private lazy var mysteryAdView: VisxAdView = {
        let adView = VisxAdView(adUnit: "912263", appDomain: "yoc.com", adViewDelegate: self, adSize: .kInterstitial1x1, universal: true)
        return adView
    }()

    func loadAd() {
        mysteryAdView.load()
        objectWillChange.send()
    }

    func viewControllerForPresentingVisxAdView() -> UIViewController {
        let viewController = UIApplication.shared.windows.first?.rootViewController
        return viewController!
    }

    func visxAdViewDidInitialize(visxAdView: VisxAdView, effect: VisxPlacementEffect) {
        mysteryAdView.showInterstitial()
        objectWillChange.send()
        print("Print: visxAdViewDidInitialize")
    }

    func visxAdViewdidFailWithError(visxAdView: VisxAdView, error: NSError) {
        print("Print: didFailWithError")
    }

    func visxAdDidPresentScreen(visxAdView: VisxAdView) {
        objectWillChange.send()
        print("Print: visxAdDidPresentScreen")
    }
}
