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
@property (nonatomic, copy, nullable) RCTDirectEventBlock onScan;
@property (nonatomic, copy, nullable) RCTDirectEventBlock onBarcodeFrameAvailable;
@property (nonatomic, copy, nullable) RCTDirectEventBlock onRecognizeNewCodes;
@property (nonatomic, copy, nullable) RCTDirectEventBlock onChangeTrackedCodes;
@property (nonatomic, copy, nullable) RCTDirectEventBlock onTextRecognized;
@property (nonatomic, copy, nullable) RCTDirectEventBlock onSettingsApplied;
@property (nonatomic, copy, nullable) RCTDirectEventBlock onWarnings;
@property (nonatomic, copy, nullable) RCTDirectEventBlock onPropertyChanged;

- (void)finishOnScanCallbackShouldStop:(BOOL)shouldStop
                           shouldPause:(BOOL)shouldPause
                         codesToReject:(nullable NSArray<NSNumber *> *)codesToReject;

- (void)finishOnRecognizeNewCodesShouldStop:(BOOL)shouldStop
                                shouldPause:(BOOL)shouldPause
                        idsToVisuallyReject:(nullable NSArray<NSNumber *> *)idsToVisuallyReject;

- (void)finishOnChangeTrackedCodesShouldStop:(BOOL)shouldStop
                                 shouldPause:(BOOL)shouldPause
                         idsToVisuallyReject:(nullable NSArray<NSNumber *> *)idsToVisuallyReject;

- (void)finishOnTextRecognizedShouldStop:(BOOL)shouldStop
                             shouldPause:(BOOL)shouldPause
                            shouldReject:(BOOL)shouldReject;

@end
