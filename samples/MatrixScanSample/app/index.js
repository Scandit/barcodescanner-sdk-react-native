import React, { Component } from 'react';
import {
  AppRegistry,
  AppState,
  StyleSheet,
  Text,
  findNodeHandle,
  View,
  Platform,
  PermissionsAndroid,
  BackHandler
} from 'react-native';
import {
  BarcodePicker,
  ScanditModule,
  ScanSession,
  Barcode,
  SymbologySettings,
  ScanSettings,
  ScanOverlay
} from 'scandit-react-native';

ScanditModule.setAppKey('-- ENTER YOUR SCANDIT LICENSE KEY HERE --');

export default class MatrixScanSample extends Component {

  componentWillMount() {
    this.settings = new ScanSettings();
    this.settings.setSymbologyEnabled(Barcode.Symbology.EAN13, true);
    this.settings.setSymbologyEnabled(Barcode.Symbology.EAN8, true);
    this.settings.setSymbologyEnabled(Barcode.Symbology.UPCA, true);
    this.settings.setSymbologyEnabled(Barcode.Symbology.UPCE, true);
    
    this.settings.matrixScanEnabled = true;
    this.settings.codeRejectionEnabled = true;
    this.settings.highDensityModeEnabled = true;
    this.settings.maxNumberOfCodesPerFrame = 10;
  }

  isAndroidMarshmallowOrNewer() {
    return Platform.OS === 'android' && Platform.Version >= 23;
  }

  async hasCameraPermission() {
    if (this.isAndroidMarshmallowOrNewer()) {
      const granted = await PermissionsAndroid.check(PermissionsAndroid.PERMISSIONS.CAMERA);
      return granted;
    } else {
      return true;
    }
  }

  async requestCameraPermission() {
    if (this.isAndroidMarshmallowOrNewer()) {
      try {
        const granted = await PermissionsAndroid.request(PermissionsAndroid.PERMISSIONS.CAMERA);
        if (granted === PermissionsAndroid.RESULTS.GRANTED) {
          console.log("Android Camera Permission has been granted.");
          this.cameraPermissionGranted();
        } else {
          console.log("Android Camera Permission has been denied - the app will shut itself down.");
          this.cameraPermissionDenied();
        }
      } catch (err) {
        console.warn(err);
      }
    } else {
      this.cameraPermissionGranted();
    }
  }

  // This method should only be called if the Platform.OS is android.
  cameraPermissionDenied() {
    BackHandler.exitApp();
  }

  cameraPermissionGranted() {
    this.scanner.setGuiStyle(ScanOverlay.GuiStyle.MATRIX_SCAN);
    this.scanner.startScanning();
  }

  async componentDidMount() {
    AppState.addEventListener('change', this._handleAppStateChange);
    this.checkForCameraPermission();
  }

  componentWillUnmount() {
    AppState.removeEventListener('change', this._handleAppStateChange);
  }
  
  _handleAppStateChange = async (nextAppState) => {
    if (nextAppState.match(/inactive|background/)) {
      this.scanner.stopScanning();
    } else {
      this.checkForCameraPermission();
    }
  }

  async checkForCameraPermission() {
    const hasPermission = await this.hasCameraPermission();
    if (hasPermission) {
      this.cameraPermissionGranted();
    } else {
      await this.requestCameraPermission();
    }
  }

  render() {
    return (
      <View style={{
            flex: 1,
            flexDirection: 'column'}}>
            <BarcodePicker
                onRecognizeNewCodes={(session) => { this.onRecognizeNewCodes(session) }}
                scanSettings= { this.settings }
        ref={(scan) => { this.scanner = scan }}
                style={{ flex: 1 }}/>
    </View>
    );
  }

  onRecognizeNewCodes(session) {
    // If you want to visually reject a code you should use ScanSession's rejectCode.
    // For example, the following code will reject all EAN8 codes.
    session.newlyTrackedCodes.forEach(function(barcode) {
      if (barcode.symbology == Barcode.Symbology.EAN8) {
        session.rejectTrackedCode(barcode);
      }
    });
  }
}
