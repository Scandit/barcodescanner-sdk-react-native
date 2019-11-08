
Upgrade the Scandit Barcode Scanner SDK {#react-native-howto-upgrade}
===================================


## How to upgrade from a test to a production Scandit Barcode Scanner SDK edition

If you upgrade from the test to one of the enterprise or professional editions you only need to replace the license key to enterprise/professional edition. Please contact us for more details.

## How to upgrade to a new version of the Scandit Barcode Scanner

Whenever you update to the newest version you simply need to remove the already installed plugin and install the new version.

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
> cd <directory of your project>
> rm -rf node_modules/scandit-react-native/
> yarn install
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

## How to use the Scandit Barcode Scanner with React Native newer than 0.60.0

For Android, you have nothing to do. On iOS, follow the following steps:
- Download the iOS SDK from Scandit's website. 
- Copy the `ScanditBarcodeScanner.framework` file to the `<directory_of_your_project>/node_modules/scandit-react-native/ios/ScanditBarcodeScanner/Frameworks/` folder (you probably need to create the `Frameworks` folder).
- From the Finder, go to `ScanditBarcodeScanner.framework/Resources` and drag and drop `ScanditBarcodeScanner.bundle` inside the Frameworks folder (the one in the main project, not the one inside the Pods project) in Xcode (choose **not** to copy items).

## How to use the Scandit Barcode Scanner with Expo, starting with version 34

For Android, you have nothing to do. On iOS, follow the following steps:
- Download the iOS SDK from Scandit's website. 
- Copy the `ScanditBarcodeScanner.framework` file to the `<directory_of_your_project>/node_modules/scandit-react-native/ios/ScanditBarcodeScanner/Frameworks/` folder (you probably need to create the `Frameworks` folder).
- Link the plugin manually: `react-native link`.
- Go into the project's `ios` folder and run `pod install`.
- From the Finder, go to `ScanditBarcodeScanner.framework/Resources` and drag and drop `ScanditBarcodeScanner.bundle` inside the Frameworks folder (the one in the main project, not the one inside the Pods project) in Xcode (choose **not** to copy items).
