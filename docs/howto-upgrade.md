
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

For Android, you have nothing to do. Only working with iOS requires the following steps:

First, you have to download the iOS SDK from Scandit's website. Then you need to copy the `ScanditBarcodeScanner.framework` file to the `project_directory/node_modules/scandit-react-native/ios/ScanditBarcodeScanner/Frameworks/` folder.

## How to use the Scandit Barcode Scanner with Expo, starting with version 34

For Android, you have nothing to do. Only working with iOS requires the following steps:

First, you have to download the iOS SDK from Scandit's website. Then you need to copy the `ScanditBarcodeScanner.framework` file to the `project_directory/node_modules/scandit-react-native/ios/ScanditBarcodeScanner/Frameworks/` folder.
Then, you have to link the plugin manually: `react-native link`.
After the previous step succeeded, go into the project's `ios` folder and run `pod install`.
