//
//  MysteryView.swift
//  YocShowcaseSwiftUI
//
//  Created by Stefan Markovic on 28.7.21.
//

import SwiftUI
import VisxSDK
import Combine

struct MysteryView: View {

    @ObservedObject var mysteryAdView = MysteryAdManager()

    var body: some View {
        VStack {
            Button("Load Mystery Ad") {
                mysteryAdView.loadAd()
            }
            .frame(width: 170, height: 50)
            .background(Color.blue)
            .foregroundColor(.white)
            .cornerRadius(7)
            .padding()
        }
    }
}

struct MysteryView_Previews: PreviewProvider {
    static var previews: some View {
        MysteryView()
    }
}
