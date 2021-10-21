//
//  VisxViewEmitter.h
//  demoshowcasereact
//
//  Created by Marvin Zinowsky on 21.09.21.
//

#import <React/RCTBridgeModule.h>
#import <React/RCTEventEmitter.h>
#import <VisxSDK/VisxSDK.h>

@interface VisxViewEmitter : RCTEventEmitter <RCTBridgeModule>

- (void)sendSizeUpdate:(VisxAdView*) visxAdView;

@end
