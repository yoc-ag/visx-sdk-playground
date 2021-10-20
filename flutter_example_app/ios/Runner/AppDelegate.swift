import UIKit
import Flutter
import VisxSDK

/// VIS.X Ad View Delegate for environment and ad changes
@UIApplicationMain
@objc class AppDelegate: FlutterAppDelegate, VisxAdViewDelegate {
    
    /// VIS.X Ad instance
    private var adView: VisxAdView?
    
    override func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?
    ) -> Bool {
        
        /// Initializing VIS.X SDK
        VisxSDKManager.sharedInstance().initializeSDK()
        
        /// Controller needed for initializing VIS.X Ad
        guard let controller = window?.rootViewController as? FlutterViewController else {
          fatalError("rootViewController is not type FlutterViewController")
        }
    
        initVisxView(controller: controller)
        initVisxInterstitial()
        
        return super.application(application, didFinishLaunchingWithOptions: launchOptions)
    }
    
    /// Create and display native iOS Rendered View to display VIS.X Ad Inline Banner/Video or Universal Ad
    func initVisxView(controller: FlutterViewController) {
        weak var registrar = self.registrar(forPlugin: "plugin-name")
        
        let factory = FLNativeViewFactory(messenger: registrar!.messenger(), controller: controller)
        self.registrar(forPlugin: "<plugin-name>")!.register(
            factory,
            withId: "ios_view_type")
    }
    
    /// Method Channel for calling native iOS VIS.X Mystery (Interstitial) Ad from the Flutter App
    func initVisxInterstitial() {
        let controller : FlutterViewController = window?.rootViewController as! FlutterViewController
        let visxIosNative = FlutterMethodChannel(name: "visx.sdk",
                                                 binaryMessenger: controller.binaryMessenger)
        visxIosNative.setMethodCallHandler({
            [weak self] (call: FlutterMethodCall, result: FlutterResult) -> Void in
            
            if(call.method == "interstitial.ios") {
                self?.displayInterstitial(result: result)
            } else {
                result(FlutterMethodNotImplemented)
                return
            }
        })
    }
    
    /// Load and display native iOS VIS.X Mystery (Interstitial) Ad
    private func displayInterstitial(result: FlutterResult) {
        loadInterstitial()
    }
    
    fileprivate func loadInterstitial() {
        adView = VisxAdView(adUnit: "912263", appDomain: "yoc.com", adViewDelegate: self, adSize: .kInterstitial1x1, universal: true)
        adView!.load()
    }
    
    /// Check for Top Controller in the View hierarchy needed for VIS.X Mystery (Interstitial) Ad where should be displayed
    func viewControllerForPresentingVisxAdView() -> UIViewController {
        if(topMostController() != nil) {
            return topMostController()!
        } else {
            return UIViewController()
        }
    }
    
    /// Display VIS.X Mystery (Interstitial) Ad
    func visxAdViewDidInitialize(visxAdView: VisxAdView, effect: VisxPlacementEffect) {
        adView!.showInterstitial()
    }
    
    /// Top controller in view hierarchy needed for displaying VIS.X Mystery (Interstitial) Ad
    func topMostController() -> UIViewController? {
        var topController = UIApplication.shared.keyWindow?.rootViewController
        while ((topController?.presentedViewController) != nil) {
            topController = topController?.presentedViewController
        }
        return topController
    }
}
