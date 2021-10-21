//
//  VisxUniversalView.m
//  demoshowcasereact
//
//  Created by Marvin Zinowsky on 20.09.21.
//

#import <React/RCTViewManager.h>
#import <VisxSDK/VisxSDK.h>
#import "VisxViewEmitter.h"

/// Handling VIS.X Universal Ad
@interface VisxUniversalView: RCTViewManager <VisxAdViewDelegate>

/*!
 * VIS.X Universal Ad Instance
 */
@property (strong, nonatomic) VisxAdView *adView;

/*!
 * Ad Container used as wrapper for VIS.X Ad
 */
@property (strong, nonatomic) UIView *container;

/*!
 * Emitting Ad size changes to React-Native App adContainer
 */
@property (strong, nonatomic) VisxViewEmitter *viewEmitter;

@end

@implementation VisxUniversalView {
  BOOL isLoaded;
}

/*!
 * Export module for React-Native App
 */
RCT_EXPORT_MODULE(VisxUniversalView)

/*!
 * Setting up UIView  wrapper where VIS.X Ad will be injected
 */
- (UIView *)view {
  [self loadAd];
  self.container = [UIView new];
  self.container.clipsToBounds = YES;
  return self.container;
}

/// Initialize VIS.X Ad with required params like Ad Unit ID, Domain and Size
- (void)loadAd {
  self.viewEmitter = [VisxViewEmitter allocWithZone:nil];
  self.adView = [[VisxAdView alloc] initWithAdUnit:@"910967"
                                         appDomain:@"yoc.com"
                                    adViewDelegate:self
                                            adSize:[VisxAdSize kUnderstitial300x250]
                                         universal:YES];
  [self.adView load];
}

/*!
 *  View controllel needed by VIS.X Ad to be rendered in React-Native App adContainer
 */
- (UIViewController *)viewControllerForPresentingVisxAdView {
  return [UIViewController new];
}

/*!
 *  Inject VIS.X Ad View to adContainer wrapper UIView
 */
- (void)visxAdViewDidInitializeWithVisxAdView:(VisxAdView * _Nonnull)visxAdView effect:(enum VisxPlacementEffect)effect {
  self.adView = visxAdView;
  [self.container addSubview:self.adView];
}

/*!
 * Send update size to the React-Native App adContainer
 */
- (void)visxAdViewDidChangePlacementDimensionWithVisxAdView:(VisxAdView *)visxAdView {
  [self.viewEmitter sendSizeUpdate:visxAdView];
}

/*!
 * Send update size to the React-Native App adContainer
 */
- (void)visxAdViewDidChangePlacementEffectWithVisxAdView:(VisxAdView *)visxAdView effect:(enum VisxPlacementEffect)effect {
  [self.viewEmitter sendSizeUpdate:visxAdView];
}

- (dispatch_queue_t)methodQueue {
  return dispatch_get_main_queue();
}

@end
