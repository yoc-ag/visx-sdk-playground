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
        let myView = VisxAdView(adUnit: "910967", appDomain: "yoc.com", adViewDelegate: context.coordinator, adSize: .kUnderstitial300x250, universal: true)
        myView.load()
        return myView
    }

    func updateUIView(_ uiView: VisxAdView, context: Context) {
        bannerSize = uiView.frame.size
    }

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
            UIViewController()
        }

        func visxAdViewDidInitialize(visxAdView: VisxAdView, effect: VisxPlacementEffect) {
            visxAdView.setAnchorFrame(with: 0, 94, 375, 635)
            self.parent.bannerSize = visxAdView.frame.size
        }

        func visxAdViewSizeChange(visxAdView: VisxAdView, width: CGFloat, height: CGFloat) {
            self.parent.bannerSize = visxAdView.frame.size
        }
    }
}
