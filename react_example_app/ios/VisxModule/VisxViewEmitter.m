//
//  VisxViewEmitter.m
//  demoshowcasereact
//
//  Created by Marvin Zinowsky on 21.09.21.
//

#import "VisxViewEmitter.h"

/*!
 * Handling data sent to React-Native App
 */
@implementation VisxViewEmitter {
  BOOL hasListeners;
}

/*!
 * Export module for React-Native App
 */
RCT_EXPORT_MODULE();

/*!
 * Init Emitter instance
 */
+ (id)allocWithZone:(NSZone *)zone {
    static VisxViewEmitter *sharedInstance = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        sharedInstance = [super allocWithZone:zone];
    });
    return sharedInstance;
}

- (NSArray<NSString *> *)supportedEvents {
    return @[@"onSessionDidConnect", @"sizeChange"];
}

/*!
 * Emitting size updates for React-Native adContainer
 */
- (void)sendSizeUpdate:(VisxAdView*) visxAdView {
  NSLog(@"Frame: %f", visxAdView.frame.size.height);
  NSString *height = [NSString stringWithFormat:@"%f", visxAdView.frame.size.height];
  [self sendEventWithName:@"sizeChange" body:height];
}

/*!
 * Start observing and subscribe emitter
 * Use if needed
 */
//- (void)startObserving {
//    hasListeners = YES;
//    for (NSString *eventName in [self supportedEvents]) {
//        [[NSNotificationCenter defaultCenter]
//         addObserver:self
//         selector:@selector(handleNotification:)
//         name:eventName
//         object:nil];
//    }
//}

/*!
 * Stop observing and remove emitter changes
 * Use if needed
 */
//- (void)stopObserving {
//    hasListeners = NO;
//    [[NSNotificationCenter defaultCenter] removeObserver:self];
//}

/*!
 * Handle notification for emitted events
 * Use if needed
 */
//- (void)handleNotification:(NSNotification *)notification {
//    if (hasListeners)
//      [self sendEventWithName:notification.name body:notification.userInfo];
//}

/*!
 * Send notification React-Native App
 * Use if needed
 */
//- (void)sendNotificationToReactNative {
//    [self sendEventWithName:@"EventReminder" body:@{@"name": @"name"}];
//    //[self sendEventWithName:@"sizeChange" body:@[NSStringFromCGSize(visxAdView.frame.size)]];
//}

@end
