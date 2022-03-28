import Flutter
import UIKit
import Foundation
import VisxSDK

/// Class for handling native iOS VIS.X Ad - Inline Banner/Video or Universal Ad
class FLNativeViewFactory: NSObject, FlutterPlatformViewFactory, FlutterStreamHandler {
    
    private var messenger: FlutterBinaryMessenger
    private var controller: FlutterViewController
    
    init(messenger: FlutterBinaryMessenger, controller: FlutterViewController) {
        self.messenger = messenger
        self.controller = controller
        super.init()
        
        /// Creating an EventChannel for streaming data from iOS native platform to Flutter App
        /// name - should be the same on iOS native and Flutter App. It is key for subscribing to this eventChannel
        let eventChannel = FlutterEventChannel(name: "event.channel.ad.size.change", binaryMessenger: controller.binaryMessenger)
        eventChannel.setStreamHandler(self)
    }
    
    func create(withFrame frame: CGRect, viewIdentifier viewId: Int64, arguments args: Any?) -> FlutterPlatformView {
        
        /// Parameters for VIS.X Ad Ad Unit ID and Flag if the ad is Universal or Inline Banner/Video
        var auid = "912261"
        var isUniversal = false
        
        /// Creating VIS.X Ad Type and Setting Ad Unit ID
        /// args - Data payload for determining if VIS.X Ad is Inline Banner/Video or Universal
        /// note: can contain more data if needed
        if(args != nil) {
            let arguments = args as? Dictionary<String, String>
            if(arguments != nil && arguments!["ad_type"] == "banner") {
                /// BANNER auid
                auid = "912261"
                isUniversal = false
            } else {
                /// UNIVERSAL auid
                auid = "912265"
                isUniversal = true
            }
        }
        
        /// Return iOS native View containing VIS.X Ad to Flutter App
        return FLNativeView(frame: frame, viewIdentifier: viewId, arguments: args, binaryMessenger: messenger, controller :controller, flNativeViewFactory: self, auid: auid, isUniversal: isUniversal)
    }
    
    /// Function needed for coding/decoding arguments as data payload that is received from Flutter App
    /// for Banner or Universal showcase
    public func createArgsCodec() -> FlutterMessageCodec & NSObjectProtocol {
          return FlutterStandardMessageCodec.sharedInstance()
    }
    
    /// EventSink for sending data from iOS native to Flutter App needed for ad size
    /// changes so the Flutter App adContainer can be resized properly
    var eventSink: FlutterEventSink? = nil
    
    /// Register eventSink
    func onListen(withArguments arguments: Any?, eventSink events: @escaping FlutterEventSink) -> FlutterError? {
        eventSink = events

        /// In case of errors use this wherever needed
        //events(FlutterError(code: "ERROR_CODE", message: "Detailed message", details: nil))
        
        /// When stream finishes, use this wherever needed
        //events(FlutterEndOfEventStream)

        return nil
    }
    
    
    /// Unregister eventSink
    func onCancel(withArguments arguments: Any?) -> FlutterError? {
        nil
    }
    
    /// Sending values to Flutter App for adContainer to be updated
    public func updateAdContainer(width: Double, height: Double) {
        let adSize = [width, height]
        if(eventSink != nil) {
            
            /// Must be on the main thread for consistency
            DispatchQueue.main.async {
                self.eventSink!(adSize)
            }
        }
    }
}

/// Actual VIS.X Ad Handling
/// VisxAdViewDelegate for environment and ad changes
class FLNativeView: NSObject, FlutterPlatformView, VisxAdViewDelegate {

    /// Wrapper UIView for VIS.X Ad
    var _adContainer: UIView
    
    var adView: VisxAdView?
    private var auid: String
    
    private var controller: FlutterViewController
    private var flNativeViewFactory: FLNativeViewFactory

    init(frame: CGRect, viewIdentifier viewId: Int64, arguments args: Any?, binaryMessenger messenger: FlutterBinaryMessenger?, controller: FlutterViewController, flNativeViewFactory: FLNativeViewFactory, auid: String, isUniversal: Bool) {
        self.controller = controller
        self.auid = auid
        _adContainer = UIView()
        self.flNativeViewFactory = flNativeViewFactory
        super.init()
        createNativeView(view: _adContainer, isUniversal: isUniversal)
    }

    /// Initialize and display VIS.X Inline Banner/View or Universal Ad
    func createNativeView(view _view: UIView, isUniversal: Bool){
        adView = VisxAdView(adUnit: auid, appDomain: "yoc.com", adViewDelegate: self, adSize: .kUnderstitial300x250, universal: isUniversal)
        adView?.load()
        
        /// Function needed to initialize full Understitial effect of VIS.X Universal Ad
        _adContainer.clipsToBounds = true
    }

    /// Returns the iOS native UIView Wrapper with the VIS.X Ad to Flutter App for displaying
    func view() -> UIView {
        return _adContainer
    }

    /// Controller where should VIS.X Ad be displayed
    func viewControllerForPresentingVisxAdView() -> UIViewController {
        /// Note: When we cannot return current UIVIewController which is presenting the creative we can just return it this
        UIViewController()
    }

    /// Adding VIS.X Ad in to the UIView Wrapper needed for Flutter App displaying
    func visxAdViewDidInitialize(visxAdView: VisxAdView, effect: VisxPlacementEffect) {
        _adContainer.frame.size.height = visxAdView.frame.size.height
        _adContainer.addSubview(visxAdView)
    }
    
    func visxAdViewEffectChange(visxAdView: VisxAdView, effect: VisxPlacementEffect) {
        /// For resizing the adContainer in the Flutter App via eventSink
        flNativeViewFactory.updateAdContainer(width: Double(visxAdView.frame.size.width), height: Double(visxAdView.frame.size.height))
    }
    
    func visxAdViewSizeChange(visxAdView: VisxAdView, width: CGFloat, height: CGFloat) {
        flNativeViewFactory.updateAdContainer(width: Double(width), height: Double(height))
    }
}
