//
//  VisxInterstitialModule.m
//  demoshowcasereact
//
//  Created by Marvin Zinowsky on 21.09.21.
//

#import <React/RCTViewManager.h>
#import <VisxSDK/VisxSDK.h>

/// Handling VIS.X Mystery (Interstitial) Ad
@interface VisxInterstitialModule : RCTViewManager <VisxAdViewDelegate>

/*!
 * VIS.X Mystery (Interstitial) Ad Instance
 */
@property (strong, nonatomic) VisxAdView *adView;

/*!
 * Ad Container used as wrapper for VIS.X Ad
 */
@property (strong, nonatomic) UIView *container;

@end

@implementation VisxInterstitialModule  {
  BOOL isLoaded;
}

/*!
 * Export module for React-Native App
 */
RCT_EXPORT_MODULE(VisxInterstitialModule);

/*!
 * Export method for React-Native App to call VIS.X Mystery (Interstitial) Ad
 */
RCT_EXPORT_METHOD(loadInterstitial) {
  NSLog(@"RN: Load Interstitial");
  [self loadAd];
};

/*!
 * Setting up UIView  wrapper where VIS.X Ad will be injected
 */
- (UIView *)view {
  [self loadAd];
  self.container = [UIView new];
  return self.container;
}

/*!
 * Initialize VIS.X Ad with required params like Ad Unit ID, Domain and Size
 */
- (void)loadAd {
  self.adView = [[VisxAdView alloc] initWithAdUnit:@"910968"
                                         appDomain:@"yoc.com"
                                    adViewDelegate:self
                                            adSize:[VisxAdSize kInterstitial1x1]
                                         universal:YES];
  [self.adView load];
}

/*!
 *  View controllel needed by VIS.X Ad to be rendered in React-Native App adContainer
 */
- (UIViewController *)viewControllerForPresentingVisxAdView {
  // We need to return the correct view controller that is presenting the view, otherwise interstitial ad will not be shown.
  return [self topMostController];
}

/*!
 *  Inject VIS.X Ad View to adContainer wrapper UIView
 */
- (void)visxAdViewDidInitializeWithVisxAdView:(VisxAdView * _Nonnull)visxAdView effect:(enum VisxPlacementEffect)effect {
  [self.adView showInterstitial];
}

- (dispatch_queue_t)methodQueue {
  return dispatch_get_main_queue();
}

/*!
 * Top controller in view hierarchy needed for displaying VIS.X Mystery (Interstitial) Ad
 */
- (UIViewController *) topMostController {
    UIViewController *topController = [UIApplication sharedApplication].keyWindow.rootViewController;
    while (topController.presentedViewController) {
        topController = topController.presentedViewController;
    }
    return topController;
}

@end
