//
//  YocImage.swift
//  YocShowcaseSwiftUI
//
//  Created by Stefan Markovic on 27.7.21.
//

import SwiftUI

struct YocImage: Identifiable {
    let id = UUID()
    let imageName: String
    let title: String
    let description: String
}

struct YocImages {
    static let yocImagesList = [
        YocImage(imageName: "Banner", title: "YOC Banner Classic", description: "The YOC Banner Classic is integrated directly into the editorial content of a website and generates awareness using images and videos."),
        YocImage(imageName: "Video", title: "YOC Inline Video Ad", description: "The YOC Inline Video Ad is an innovative outstream video product, guaranteeing your audience’s attention. YOC’s proprietary video technology (IVA) transcodes any video asset to auto-play across all devices."),
        YocImage(imageName: "Mystery", title: "YOC Mystery Ad", description: "Fullscreen HTML5 format with unlimited creative possibilities. Through the intelligent use of smartphone sensor technology (e.g. shaking, turning, wiping) unique brand experiences can be created."),
        YocImage(imageName: "Understitial", title: "YOC Understitial Ad", description: "The YOC Understitial Ad is a native and responsive format that is embedded underneath the content of a page. This non-intrusive full screen format is delivered without interrupting the users reading flow."),
        YocImage(imageName: "Universal", title: "YOC Universal Ad", description: "The Universal Ad Unit of the VIS.X SDK offers a new and smart approach for in-app monetization. All our inline advertising formats in just one ad unit. Use the reload button to see it in action.")]
}
