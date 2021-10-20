//
//  YocShowcaseSwiftUIApp.swift
//  YocShowcaseSwiftUI
//
//  Created by Stefan Markovic on 27.7.21.
//

import SwiftUI
import VisxSDK

@main
struct YocShowcaseSwiftUIApp: App {
    init() {
        VisxSDKManager.sharedInstance().initializeSDK()
    }
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
