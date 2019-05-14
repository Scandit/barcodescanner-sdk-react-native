//
//  SCNBarcodePickerManager.m
//  ScanditBarcodeScanner
//
//  Created by Luca Torella on 08.08.17.
//  Copyright © 2017 Scandit. All rights reserved.
//

#import "SCNBarcodePickerManager.h"
#import "SCNBarcodePicker.h"
#import "SBSOverlayController+Properties.h"
#import <React/RCTUIManager.h>

@import ScanditBarcodeScanner;

@interface SCNBarcodePickerManager ()

@end

@implementation SCNBarcodePickerManager

RCT_EXPORT_MODULE(BarcodePicker)

-(dispatch_queue_t)methodQueue {
    return dispatch_get_main_queue();
}

- (UIView *)view {
    return [[SCNBarcodePicker alloc] init];
}

RCT_EXPORT_VIEW_PROPERTY(scanSettings, NSDictionary)
RCT_EXPORT_VIEW_PROPERTY(shouldPassBarcodeFrame, BOOL)
RCT_EXPORT_VIEW_PROPERTY(onScan, RCTDirectEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onRecognizeNewCodes, RCTDirectEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onChangeTrackedCodes, RCTDirectEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onBarcodeFrameAvailable, RCTDirectEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onTextRecognized, RCTDirectEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onSettingsApplied, RCTDirectEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onWarnings, RCTDirectEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onPropertyChanged, RCTDirectEventBlock)

RCT_EXPORT_METHOD(startScanning:(nonnull NSNumber *)reactTag) {
    [self.bridge.uiManager addUIBlock:
     ^(__unused RCTUIManager *uiManager, NSDictionary<NSNumber *, UIView *> *viewRegistry) {
         id view = viewRegistry[reactTag];
         if (![view isKindOfClass:[SCNBarcodePicker class]]) {
             RCTLogError(@"Invalid view returned from registry, expecting SCNBarcodePicker, got: %@", view);
         } else {
             [((SCNBarcodePicker *)view).picker startScanning];
         }
     }];
}

RCT_EXPORT_METHOD(stopScanning:(nonnull NSNumber *)reactTag) {
    [self.bridge.uiManager addUIBlock:
     ^(__unused RCTUIManager *uiManager, NSDictionary<NSNumber *, UIView *> *viewRegistry) {
         id view = viewRegistry[reactTag];
         if (![view isKindOfClass:[SCNBarcodePicker class]]) {
             RCTLogError(@"Invalid view returned from registry, expecting SCNBarcodePicker, got: %@", view);
         } else {
             [((SCNBarcodePicker *)view).picker stopScanning];
         }
     }];
}

RCT_EXPORT_METHOD(pauseScanning:(nonnull NSNumber *)reactTag) {
    [self.bridge.uiManager addUIBlock:
     ^(__unused RCTUIManager *uiManager, NSDictionary<NSNumber *, UIView *> *viewRegistry) {
         id view = viewRegistry[reactTag];
         if (![view isKindOfClass:[SCNBarcodePicker class]]) {
             RCTLogError(@"Invalid view returned from registry, expecting SCNBarcodePicker, got: %@", view);
         } else {
             [((SCNBarcodePicker *)view).picker pauseScanning];
         }
     }];
}

RCT_EXPORT_METHOD(resumeScanning:(nonnull NSNumber *)reactTag) {
    [self.bridge.uiManager addUIBlock:
     ^(__unused RCTUIManager *uiManager, NSDictionary<NSNumber *, UIView *> *viewRegistry) {
         id view = viewRegistry[reactTag];
         if (![view isKindOfClass:[SCNBarcodePicker class]]) {
             RCTLogError(@"Invalid view returned from registry, expecting SCNBarcodePicker, got: %@", view);
         } else {
             [((SCNBarcodePicker *)view).picker resumeScanning];
         }
     }];
}

RCT_EXPORT_METHOD(applySettings:(nonnull NSNumber *)reactTag
                  settings:(NSDictionary *)scanSettings) {
    [self.bridge.uiManager addUIBlock:
     ^(__unused RCTUIManager *uiManager, NSDictionary<NSNumber *, UIView *> *viewRegistry) {
         id view = viewRegistry[reactTag];
         if (![view isKindOfClass:[SCNBarcodePicker class]]) {
             RCTLogError(@"Invalid view returned from registry, expecting SCNBarcodePicker, got: %@", view);
         } else {
             SCNBarcodePicker *pickerView = (SCNBarcodePicker *)view;
             pickerView.scanSettings = scanSettings;
         }
     }];
}

RCT_EXPORT_METHOD(setViewfinderDimension:(nonnull NSNumber *)reactTag
                  width:(float)width
                  height:(float)height
                  landscapeWidth:(float)landscapeWidth
                  landscapeHeight:(float)landscapeHeight) {
    [self.bridge.uiManager addUIBlock:
     ^(__unused RCTUIManager *uiManager, NSDictionary<NSNumber *, UIView *> *viewRegistry) {
         id view = viewRegistry[reactTag];
         if (![view isKindOfClass:[SCNBarcodePicker class]]) {
             RCTLogError(@"Invalid view returned from registry, expecting SCNBarcodePicker, got: %@", view);
         } else {
             SBSOverlayController *overlayController = ((SCNBarcodePicker *)view).picker.overlayController;
             [overlayController setViewfinderWidth:width
                                            height:height
                                    landscapeWidth:landscapeWidth
                                   landscapeHeight:landscapeHeight];
         }
     }];
}

RCT_EXPORT_METHOD(setTorchEnabled:(nonnull NSNumber *)reactTag
                  enabled:(BOOL)enabled) {
    [self.bridge.uiManager addUIBlock:
     ^(__unused RCTUIManager *uiManager, NSDictionary<NSNumber *, UIView *> *viewRegistry) {
         id view = viewRegistry[reactTag];
         if (![view isKindOfClass:[SCNBarcodePicker class]]) {
             RCTLogError(@"Invalid view returned from registry, expecting SCNBarcodePicker, got: %@", view);
         } else {
             [((SCNBarcodePicker *)view).picker.overlayController setTorchEnabled:enabled];
         }
     }];
}

RCT_EXPORT_METHOD(switchTorchOn:(nonnull NSNumber *)reactTag
                  enabled:(BOOL)enabled) {
    [self.bridge.uiManager addUIBlock:
     ^(__unused RCTUIManager *uiManager, NSDictionary<NSNumber *, UIView *> *viewRegistry) {
         id view = viewRegistry[reactTag];
         if (![view isKindOfClass:[SCNBarcodePicker class]]) {
             RCTLogError(@"Invalid view returned from registry, expecting SCNBarcodePicker, got: %@", view);
         } else {
             [((SCNBarcodePicker *)view).picker switchTorchOn:enabled];
         }
     }];
}

RCT_EXPORT_METHOD(setVibrateEnabled:(nonnull NSNumber *)reactTag
                  enabled:(BOOL)enabled) {
    [self.bridge.uiManager addUIBlock:
     ^(__unused RCTUIManager *uiManager, NSDictionary<NSNumber *, UIView *> *viewRegistry) {
         id view = viewRegistry[reactTag];
         if (![view isKindOfClass:[SCNBarcodePicker class]]) {
             RCTLogError(@"Invalid view returned from registry, expecting SCNBarcodePicker, got: %@", view);
         } else {
             [((SCNBarcodePicker *)view).picker.overlayController setVibrateEnabled:enabled];
         }
     }];
}

RCT_EXPORT_METHOD(setBeepEnabled:(nonnull NSNumber *)reactTag
                  enabled:(BOOL)enabled) {
    [self.bridge.uiManager addUIBlock:
     ^(__unused RCTUIManager *uiManager, NSDictionary<NSNumber *, UIView *> *viewRegistry) {
         id view = viewRegistry[reactTag];
         if (![view isKindOfClass:[SCNBarcodePicker class]]) {
             RCTLogError(@"Invalid view returned from registry, expecting SCNBarcodePicker, got: %@", view);
         } else {
             [((SCNBarcodePicker *)view).picker.overlayController setBeepEnabled:enabled];
         }
     }];
}

RCT_EXPORT_METHOD(setTorchButtonMarginsAndSize:(nonnull NSNumber *)reactTag
                  leftMargin:(float)leftMargin
                  topMargin:(float)topMargin
                  width:(float)width
                  height:(float)height) {
    [self.bridge.uiManager addUIBlock:
     ^(__unused RCTUIManager *uiManager, NSDictionary<NSNumber *, UIView *> *viewRegistry) {
         id view = viewRegistry[reactTag];
         if (![view isKindOfClass:[SCNBarcodePicker class]]) {
             RCTLogError(@"Invalid view returned from registry, expecting SCNBarcodePicker, got: %@", view);
         } else {
             SBSOverlayController *overlayController = ((SCNBarcodePicker *)view).picker.overlayController;
             [overlayController setTorchButtonLeftMargin:leftMargin
                                               topMargin:topMargin
                                                   width:width
                                                  height:height];
         }
     }];
}

RCT_EXPORT_METHOD(setCameraSwitchVisibility:(nonnull NSNumber *)reactTag
                  visibility:(SBSCameraSwitchVisibility)visibility) {
    [self.bridge.uiManager addUIBlock:
     ^(__unused RCTUIManager *uiManager, NSDictionary<NSNumber *, UIView *> *viewRegistry) {
         id view = viewRegistry[reactTag];
         if (![view isKindOfClass:[SCNBarcodePicker class]]) {
             RCTLogError(@"Invalid view returned from registry, expecting SCNBarcodePicker, got: %@", view);
         } else {
             SBSOverlayController *overlayController = ((SCNBarcodePicker *)view).picker.overlayController;
             [overlayController setCameraSwitchVisibility:visibility];
         }
     }];
}

RCT_EXPORT_METHOD(setCameraSwitchMarginsAndSize:(nonnull NSNumber *)reactTag
                  rightMargin:(float)rightMargin
                  topMargin:(float)topMargin
                  width:(float)width
                  height:(float)height) {
    [self.bridge.uiManager addUIBlock:
     ^(__unused RCTUIManager *uiManager, NSDictionary<NSNumber *, UIView *> *viewRegistry) {
         id view = viewRegistry[reactTag];
         if (![view isKindOfClass:[SCNBarcodePicker class]]) {
             RCTLogError(@"Invalid view returned from registry, expecting SCNBarcodePicker, got: %@", view);
         } else {
             SBSOverlayController *overlayController = ((SCNBarcodePicker *)view).picker.overlayController;
             [overlayController setCameraSwitchButtonRightMargin:rightMargin
                                                       topMargin:topMargin
                                                           width:width
                                                          height:height];
         }
     }];
}

RCT_EXPORT_METHOD(setViewfinderColor:(nonnull NSNumber *)reactTag
                  color:(UIColor *)color) {
    [self.bridge.uiManager addUIBlock:
     ^(__unused RCTUIManager *uiManager, NSDictionary<NSNumber *, UIView *> *viewRegistry) {
         id view = viewRegistry[reactTag];
         if (![view isKindOfClass:[SCNBarcodePicker class]]) {
             RCTLogError(@"Invalid view returned from registry, expecting SCNBarcodePicker, got: %@", view);
         } else {
             CGFloat red = 0, green = 0, blue = 0, alpha = 0;
             [color getRed:&red green:&green blue:&blue alpha:&alpha];
             SBSOverlayController *overlayController = ((SCNBarcodePicker *)view).picker.overlayController;
             [overlayController setViewfinderColor:red green:green blue:blue];
         }
     }];
}

RCT_EXPORT_METHOD(setViewfinderDecodedColor:(nonnull NSNumber *)reactTag
                  color:(UIColor *)color) {
    [self.bridge.uiManager addUIBlock:
     ^(__unused RCTUIManager *uiManager, NSDictionary<NSNumber *, UIView *> *viewRegistry) {
         id view = viewRegistry[reactTag];
         if (![view isKindOfClass:[SCNBarcodePicker class]]) {
             RCTLogError(@"Invalid view returned from registry, expecting SCNBarcodePicker, got: %@", view);
         } else {
             CGFloat red = 0, green = 0, blue = 0, alpha = 0;
             [color getRed:&red green:&green blue:&blue alpha:&alpha];
             SBSOverlayController *overlayController = ((SCNBarcodePicker *)view).picker.overlayController;
             [overlayController setViewfinderDecodedColor:red green:green blue:blue];
         }
     }];
}

RCT_EXPORT_METHOD(setMatrixScanColor:(nonnull NSNumber *)reactTag
                  color:(UIColor *)color
                  state:(SBSMatrixScanHighlightingState)state) {
    [self.bridge.uiManager addUIBlock:
     ^(__unused RCTUIManager *uiManager, NSDictionary<NSNumber *, UIView *> *viewRegistry) {
         id view = viewRegistry[reactTag];
         if (![view isKindOfClass:[SCNBarcodePicker class]]) {
             RCTLogError(@"Invalid view returned from registry, expecting SCNBarcodePicker, got: %@", view);
         } else {
             SBSOverlayController *overlayController = ((SCNBarcodePicker *)view).picker.overlayController;
             [overlayController setMatrixScanHighlightingColor:color forState:state];
         }
     }];
}

RCT_EXPORT_METHOD(setGuiStyle:(nonnull NSNumber *)reactTag
                  guiStyle:(SBSGuiStyle)guiStyle) {
    [self.bridge.uiManager addUIBlock:
     ^(__unused RCTUIManager *uiManager, NSDictionary<NSNumber *, UIView *> *viewRegistry) {
         id view = viewRegistry[reactTag];
         if (![view isKindOfClass:[SCNBarcodePicker class]]) {
             RCTLogError(@"Invalid view returned from registry, expecting SCNBarcodePicker, got: %@", view);
         } else {
             SBSOverlayController *overlayController = ((SCNBarcodePicker *)view).picker.overlayController;
             [overlayController setGuiStyle:guiStyle];
         }
     }];
}

RCT_EXPORT_METHOD(setTextRecognitionSwitchVisible:(nonnull NSNumber *)reactTag
                  visible:(BOOL)visible) {
    [self.bridge.uiManager addUIBlock:
     ^(__unused RCTUIManager *uiManager, NSDictionary<NSNumber *, UIView *> *viewRegistry) {
         id view = viewRegistry[reactTag];
         if (![view isKindOfClass:[SCNBarcodePicker class]]) {
             RCTLogError(@"Invalid view returned from registry, expecting SCNBarcodePicker, got: %@", view);
         } else {
             [((SCNBarcodePicker *)view).picker.overlayController setTextRecognitionSwitchVisible:visible];
         }
     }];
}

RCT_EXPORT_METHOD(setOverlayProperty:(nonnull NSNumber *)reactTag
                  key:(NSString *)key
                  value:(id)value) {
    [self.bridge.uiManager addUIBlock:
     ^(__unused RCTUIManager *uiManager, NSDictionary<NSNumber *, UIView *> *viewRegistry) {
         id view = viewRegistry[reactTag];
         if (![view isKindOfClass:[SCNBarcodePicker class]]) {
             RCTLogError(@"Invalid view returned from registry, expecting SCNBarcodePicker, got: %@", view);
         } else if ([value isKindOfClass:[NSNumber class]]) {
             [((SCNBarcodePicker *)view).picker.overlayController setProperty:key toValue:[value boolValue]];
         }
     }];
}

RCT_EXPORT_METHOD(finishOnScanCallback:(nonnull NSNumber *)reactTag
                  shouldStop:(BOOL)shouldStop
                  shouldPause:(BOOL)shouldPause
                  codesToReject:(NSArray<NSNumber *> *)codesToReject) {
    [self.bridge.uiManager addUIBlock:
     ^(__unused RCTUIManager *uiManager, NSDictionary<NSNumber *, UIView *> *viewRegistry) {
         id view = viewRegistry[reactTag];
         if (![view isKindOfClass:[SCNBarcodePicker class]]) {
             RCTLogError(@"Invalid view returned from registry, expecting SCNBarcodePicker, got: %@", view);
         } else {
             SCNBarcodePicker *barcodePicker = (SCNBarcodePicker *)view;
             [barcodePicker finishOnScanCallbackShouldStop:shouldStop
                                               shouldPause:shouldPause
                                             codesToReject:codesToReject];
         }
     }];
}

RCT_EXPORT_METHOD(finishOnRecognizeNewCodes:(nonnull NSNumber *)reactTag
                  shouldStop:(BOOL)shouldStop
                  shouldPause:(BOOL)shouldPause
                  idsToVisuallyReject:(NSArray<NSNumber *> *)idsToVisuallyReject) {
    [self.bridge.uiManager addUIBlock:
     ^(__unused RCTUIManager *uiManager, NSDictionary<NSNumber *, UIView *> *viewRegistry) {
         id view = viewRegistry[reactTag];
         if (![view isKindOfClass:[SCNBarcodePicker class]]) {
             RCTLogError(@"Invalid view returned from registry, expecting SCNBarcodePicker, got: %@", view);
         } else {
             SCNBarcodePicker *barcodePicker = (SCNBarcodePicker *)view;
             [barcodePicker finishOnRecognizeNewCodesShouldStop:shouldStop
                                                    shouldPause:shouldPause
                                            idsToVisuallyReject:idsToVisuallyReject];
         }
     }];
}

RCT_EXPORT_METHOD(finishOnChangeTrackedCodes:(nonnull NSNumber *)reactTag
                  shouldStop:(BOOL)shouldStop
                  shouldPause:(BOOL)shouldPause
                  idsToVisuallyReject:(NSArray<NSNumber *> *)idsToVisuallyReject) {
    [self.bridge.uiManager addUIBlock:
     ^(__unused RCTUIManager *uiManager, NSDictionary<NSNumber *, UIView *> *viewRegistry) {
         id view = viewRegistry[reactTag];
         if (![view isKindOfClass:[SCNBarcodePicker class]]) {
             RCTLogError(@"Invalid view returned from registry, expecting SCNBarcodePicker, got: %@", view);
         } else {
             SCNBarcodePicker *barcodePicker = (SCNBarcodePicker *)view;
             [barcodePicker finishOnChangeTrackedCodesShouldStop:shouldStop
                                                     shouldPause:shouldPause
                                             idsToVisuallyReject:idsToVisuallyReject];
         }
     }];
}

@end
