//
//  BannerView.swift
//  YocShowcaseSwiftUI
//
//  Created by Stefan Markovic on 28.7.21.
//

import SwiftUI
import VisxSDK

struct BannerView: UIViewRepresentable {

    typealias UIViewType = VisxAdView

    @Binding var bannerSize: CGSize

    func makeUIView(context: Context) -> VisxAdView {
        let myView = VisxAdView(adUnit: "912268", appDomain: "yoc.com", adViewDelegate: context.coordinator, adSize: .kUnderstitial300x250, universal: true)
        myView.load()
        return myView
    }

    func updateUIView(_ uiView: VisxAdView, context: Context) { }

    func makeCoordinator() -> Coordinator {
        Coordinator(self)
    }
}

extension BannerView {
    class Coordinator: NSObject, VisxAdViewDelegate {

        var parent: BannerView

        init(_ parent: BannerView) {
            self.parent = parent
        }

        func viewControllerForPresentingVisxAdView() -> UIViewController {
            (UIApplication.shared.windows.first?.rootViewController)!
        }

        func visxAdViewDidInitialize(visxAdView: VisxAdView, effect: VisxPlacementEffect) {
            self.parent.bannerSize = visxAdView.frame.size
        }

        func visxAdViewDidChangePlacementDimension(visxAdView: VisxAdView) {
            self.parent.bannerSize = visxAdView.frame.size
        }
    }
}
