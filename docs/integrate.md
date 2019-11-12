Integrate the Scandit SDK into your app     {#react-native-integrate}
===================================

To integrate the Scandit Barcode Scanner into your React Native app, follow the simple steps below.

## Setup React Native

Instructions are at https://facebook.github.io/react-native/docs/getting-started.html
<p>
Make sure to follow the 'Building Projects with Native Code' guide!

## Create a new project

Make sure `react-native-cli` is installed

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~{.java}
> yarn global add react-native-cli
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

If you do not have a React Native project yet, you should create a new one.

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~{.java}
> react-native init helloworld
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

## Add the plugin to your project

Use the React Native CLI to add the plugin to your already existing project.

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~{.java}
> cd <directory of your project>
> yarn add scandit-react-native
> react-native link scandit-react-native
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

### Add Android dependencies

- Download the Barcode Scanner SDK for Android. It's available from your Scandit Barcode Scanner SDK account at http://account.scandit.com in the Downloads section.
- Inside the archive you will find a file named ScanditBarcodeScanner.aar .
Copy it to <directory_of_your_project>/android/libs, then in your main build.gradle file add

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~{.java}
  allprojects {
    repositories {
      ...
      flatDir {
        dirs "$rootDir/libs"
      }
    }
  }
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

### Add iOS dependencies

These instructions assume you have React Native 0.60 or greater. If you have a previous version go to https://docs.scandit.com/5.12/react_native/react-native-integrate.html.

- Download the Barcode Scanner SDK for iOS from your Scandit Barcode Scanner SDK account at http://account.scandit.com, Downloads section.
- In your existing ReactNative project, create a Frameworks folder inside the <directory_of_your_project>/node_modules/scandit-react-native/ios/ScanditBarcodeScanner and move the ScanditBarcodeScanner.framework from the Barcode Scanner for iOS package into the new folder.
- Open the Xcode project and add the required frameworks, see list in http://docs.scandit.com/stable/ios/ios-integrate.html.
- In the terminal, go to <directory_of_your_project>/ios and run `pod install`.
- From the Finder, go to ScanditBarcodeScanner.framework/Resources and drag and drop ScanditBarcodeScanner.bundle inside the Frameworks folder (the one in the main project, not the one inside the Pods project) in Xcode (choose **not** to copy items).
- In the Info.plist file add a new row where the key is "NSCameraUsageDescription" and the value is the message that will be shown to the user when camera access is requested.

## Instantiate and configure the barcode picker

The scanning process is managed by the {@link Scandit.BarcodePicker BarcodePicker}. Before instantiating the picker, you will have to set your Scandit Barcode Scanner license key. The key is available from your Scandit Barcode Scanner SDK account at http://account.scandit.com in the License Keys section. The barcode scanning is configured through an instance of scan settings that you pass to the BarcodePicker as props.

~~~~~~~~~~~~~~~~{.java}
import {
  BarcodePicker,
  ScanditModule,
  Barcode,
  ScanSettings
} from 'scandit-react-native';


// Set your license key.
ScanditModule.setAppKey('-- ENTER YOUR SCANDIT LICENSE KEY HERE --');

this.settings = new ScanSettings();
this.settings.setSymbologyEnabled(Barcode.Symbology.EAN13, true);
this.settings.setSymbologyEnabled(Barcode.Symbology.EAN8, true);
this.settings.setSymbologyEnabled(Barcode.Symbology.UPCA, true);

~~~~~~~~~~~~~~~~


## Render the BarcodePicker

Show the scanner to the user by adding it to the render() function.

~~~~~~~~~~~~~~~~{.java}

render() {
  return (
    ...
    <BarcodePicker
      ref={(scan) => { this.scanner = scan }}
      scanSettings={ this.settings }
      onScan={(session) => { this.onScan(session) }}
      style={{ flex: 1 }}/>
  );
}

~~~~~~~~~~~~~~~~

## Add callback to handle the scanning event

You now need to define the function that is referenced in the BarcodePicker props.

~~~~~~~~~~~~~~~~{.java}

onScan(session) {
  alert(session.newlyRecognizedCodes[0].data + " " + session.newlyRecognizedCodes[0].symbology);
}

~~~~~~~~~~~~~~~~

## Start the scanner

Start the actual scanning process to start the camera and look for codes.

~~~~~~~~~~~~~~~~{.java}

this.scanner.startScanning();

~~~~~~~~~~~~~~~~

<br/>

## Build and run the app

Compile your project. Attach a device and run the app on your desired plattform.

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~{.java}
    react-native run-android
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

## Next steps

* \ref react-native-examples
