//
//  SCNBarcodePicker.h
//  SCNScanditBarcodeScanner
//
//  Created by Luca Torella on 13.08.17.
//  Copyright © 2017 Scandit. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <React/RCTComponent.h>

@class SBSBarcodePicker;

@interface SCNBarcodePicker : UIView

@property (nonatomic, strong, nonnull) SBSBarcodePicker *picker;

@property (nonatomic, strong, nullable) NSDictionary *scanSettings;
@property (nonatomic) BOOL shouldPassBarcodeFrame;
@property (nonatomic, copy, nullable) RCTBubblingEventBlock onScan;
@property (nonatomic, copy, nullable) RCTBubblingEventBlock onBarcodeFrameAvailable;
@property (nonatomic, copy, nullable) RCTBubblingEventBlock onRecognizeNewCodes;
@property (nonatomic, copy, nullable) RCTBubblingEventBlock onTextRecognized;
@property (nonatomic, copy, nullable) RCTBubblingEventBlock onSettingsApplied;
@property (nonatomic, copy, nullable) RCTBubblingEventBlock onWarnings;

- (void)finishOnScanCallbackShouldStop:(BOOL)shouldStop
                           shouldPause:(BOOL)shouldPause
                         codesToReject:(nullable NSArray<NSNumber *> *)codesToReject;

- (void)finishOnRecognizeNewCodesShouldStop:(BOOL)shouldStop
                                shouldPause:(BOOL)shouldPause
                        idsToVisuallyReject:(nullable NSArray<NSNumber *> *)idsToVisuallyReject;

@end
