Integrate the Scandit Barcode Scanner plugin into your app     {#react-native-integrate}
===================================

To integrate the Scandit Barcode Scanner into your React Native app, follow the simple steps below.

## Create a new project

If you do not have a React Native project yet, you should create a new one.

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~{.java}
    react-native init helloworld
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


## Add the plugin to your project

Use the React Native CLI to add the plugin to your already existing project.

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~{.java}
    cd <directory of your project>
    npm install react-native-scandit@https://github.com/Scandit/barcodescanner-sdk-react-native.git --save
    react-native link react-native-scandit
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


## Add Android dependencies

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

## Add iOS dependencies

- Download the Barcode Scanner SDK for iOS. It's available from your Scandit Barcode Scanner SDK account at http://account.scandit.com in the Downloads section.
- Create a ScanditSDK folder inside the iOS folder and move ScanditBarcodeScanner.framework inside the newly created folder.
- Add the required frameworks to your Xcode project (see http://docs.scandit.com/stable/ios/ios-integrate.html).
- Drag and drop ScanditBarcodeScanner.framework inside the Framework folder in Xcode (in the dialog, choose **not** to copy items).
- From the Finder, go to ScanditBarcodeScanner.framework/Resources and drag and drop ScanditBarcodeScanner.bundle inside the Framework folder in Xcode (again choose **not** to copy items).
- In Xcode, select your project in the Navigator, then select the app target and in the "Linked Frameworks and Libraries" section delete the greyed out libRCTScanditBarcodeScanner.a and link it again via the plus button.
- Go to "Build Settings", search "Framework Search Path" and add the following search path $(PROJECT_DIR)/ScanditSDK.

## Instantiate and configure the barcode picker

The scanning process is managed by the {@link Scandit.BarcodePicker BarcodePicker}. Before instantiating the picker, you will have to set your Scandit Barcode Scanner license key. The key is available from your Scandit Barcode Scanner SDK account at http://account.scandit.com in the License Keys section. The barcode scanning is configured through an instance of scan settings that you pass to the BarcodePicker as props.

~~~~~~~~~~~~~~~~{.java}

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
