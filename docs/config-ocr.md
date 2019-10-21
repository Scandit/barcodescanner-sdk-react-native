Configure OCR (text recognition) {#react-native-config-ocr}
=========================================================================

## Motivation

OCR (Optical Character Recognition) enables text recognition for your mobile scanning apps. This means you can add text recognition to your organization’s mobile data capture workflows. As a result, you can seamlessly switch between reading barcodes and text with the touch of a button.

## License Key

A dedicated OCR License key and SDK should be used. Please contact us (https://support.scandit.com/hc/en-us/requests/new) for more details.

### Add Android dependency

- Copy a file named ScanditOCR.aar from the archive downloaded from your Scandit Barcode Scanner SDK account at http://account.scandit.com to <directory_of_your_project>/android/libs directory.
- Make sure your main build.gradle file contains the path to that directory (you might have already done it if you carefully followed the \ref react-native-integrate) guide:

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

- Add the following dependency to your app's build.gradle to make sure the ScanditOCR library is accessible to use:

~~~~~~~~~~~~~~~~{.java}
api(name: "ScanditOCR", ext: "aar")
~~~~~~~~~~~~~~~~

## Implementing OCR

To integrate the OCR into your react-native app, follow the simple steps below:

- Create a ScanSettings instance and set the recognition mode to {@link Scandit.ScanSettings.RecognitionMode.TEXT Text} (or to {@link Scandit.ScanSettings.RecognitionMode.CODE_AND_TEXT Code and Text} if you want to read text and scan barcodes at the same time):

~~~~~~~~~~~~~~~~{.java}
this.settings = new ScanSettings();
this.settings.recognitionMode = ScanSettings.RecognitionMode.TEXT;
~~~~~~~~~~~~~~~~

- Create a TextRecognitionSettings instance, assign it to the settings from the previous step and adjust it according to your OCR requirements:

~~~~~~~~~~~~~~~~{.java}
this.settings.textRecognition = new TextRecognitionSettings();

// Set the area in which text is to be recognized:
this.settings.textRecognition.areaPortrait = new Rect(0, 0.4, 1, 0.2);
this.settings.textRecognition.areaLandscape = new Rect(0, 0.4, 1, 0.2);

// Set Regular Expression (i.e: VIN code):
this.settings.textRecognition.regex = "([A-Z0-9]{17})";

// Optionally set the character whitelist:
this.settings.textRecognition.characterWhitelist = { "characters": "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ<" };
~~~~~~~~~~~~~~~~

- Make use of the {@link Scandit.BarcodePicker.onTextRecognized BarcodePicker.onTextRecognized} prop to handle recognized text according to your needs: 

~~~~~~~~~~~~~~~~{.java}
render() {
  return (
    ...
    <BarcodePicker
      ref={(scan) => { this.scanner = scan }}
      scanSettings={ this.settings }
      onTextRecognized={(recognizedText) => { this.onTextRecognized(recognizedText) }}
      style={{ flex: 1 }}/>
  );
}

...

onTextRecognized(recognizedText) {
  // Reject some recognized texts based on your condition:
  if (recognizedText.text.startsWith("GB")) {
    recognizedText.reject()
  }
  
  // Do something with the unrejected text,
  // e.g. pause scanning and display it on the screen to the user.
  ...
}

~~~~~~~~~~~~~~~~
