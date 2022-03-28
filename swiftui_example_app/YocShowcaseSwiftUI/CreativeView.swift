//
//  CreativeView.swift
//  YocShowcaseSwiftUI
//
//  Created by Stefan Markovic on 27.7.21.
//

import SwiftUI
import VisxSDK

struct CreativeView: View {

    @State private var bannerSize = CGSize.zero
    var dummyItems = DummyImages.dummyImagesList
    var navTitle: String

    var body: some View {
        TabView {
            ScrollView {
                ForEach (0..<10) { index in
                    if index == 5 {
                        BannerView(bannerSize: $bannerSize)
                            .frame(width: $bannerSize.width.wrappedValue, height: $bannerSize.height.wrappedValue)
                            .background(Color.white)
                            .clipped()
                    } else {
                        Text("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.")
                            .font(.body)
                            .foregroundColor(Color.black)
                            .frame(maxWidth: .infinity)
                            .padding(.leading, 15)
                            .padding(.trailing, 15)
                            .padding(.bottom, 10)
                            .background(Color.white)
                            .zIndex(2)
                    }
                }
            }
            .background(
                GeometryReader { proxy in
                    Color.clear.onAppear {
                        print(proxy.frame(in: .global))
                    }
                }
            )
        }
        .navigationBarTitleDisplayMode(.inline)
        .navigationTitle(navTitle)
    }
}
