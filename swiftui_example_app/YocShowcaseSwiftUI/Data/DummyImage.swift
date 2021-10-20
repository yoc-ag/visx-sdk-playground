//
//  DummyImage.swift
//  YocShowcaseSwiftUI
//
//  Created by Stefan Markovic on 27.7.21.
//

import SwiftUI

struct DummyImage: Identifiable {
    let id = UUID()
    let imageName: String
    let title: String
}

struct DummyImages {
    static let dummyImagesList = [
        DummyImage(imageName: "teaser_img_feed_motorsports", title: "Hidden champions: Cars to consider next"),
        DummyImage(imageName: "teaser_img_feed_sports", title: "How to improve your daily sport routine"),
        DummyImage(imageName: "teaser_img_feed_beauty", title: "This beauty blog is all you need to read"),
        DummyImage(imageName: "teaser_img_feed_beauty", title: "This beauty blog is all you need to read"),
        DummyImage(imageName: "teaser_img_feed_business", title: "How to turn business into success"),
        DummyImage(imageName: "teaser_img_feed_fashion", title: "Avoid these 10 Fashion mistakes"),
        DummyImage(imageName: "teaser_img_feed_sports", title: "How to improve your daily sport routine"),
        DummyImage(imageName: "teaser_img_feed_beauty", title: "This beauty blog is all you need to read"),
        DummyImage(imageName: "teaser_img_feed_beauty", title: "This beauty blog is all you need to read"),
        DummyImage(imageName: "teaser_img_feed_business", title: "How to turn business into success"),
        DummyImage(imageName: "teaser_img_feed_fashion", title: "Avoid these 10 Fashion mistakes"),
        DummyImage(imageName: "", title: ""),
        DummyImage(imageName: "teaser_img_feed_motorsports", title: "Hidden champions: Cars to consider next"),
        DummyImage(imageName: "teaser_img_feed_sports", title: "How to improve your daily sport routine"),
        DummyImage(imageName: "teaser_img_feed_beauty", title: "This beauty blog is all you need to read"),
        DummyImage(imageName: "teaser_img_feed_beauty", title: "This beauty blog is all you need to read"),
        DummyImage(imageName: "teaser_img_feed_business", title: "How to turn business into success"),
        DummyImage(imageName: "teaser_img_feed_fashion", title: "Avoid these 10 Fashion mistakes"),
        DummyImage(imageName: "teaser_img_feed_motorsports", title: "Hidden champions: Cars to consider next"),
        DummyImage(imageName: "teaser_img_feed_sports", title: "How to improve your daily sport routine"),
        DummyImage(imageName: "teaser_img_feed_beauty", title: "This beauty blog is all you need to read"),
        DummyImage(imageName: "teaser_img_feed_beauty", title: "This beauty blog is all you need to read"),
        DummyImage(imageName: "teaser_img_feed_business", title: "How to turn business into success"),
        DummyImage(imageName: "teaser_img_feed_fashion", title: "Avoid these 10 Fashion mistakes")
    ]
}
