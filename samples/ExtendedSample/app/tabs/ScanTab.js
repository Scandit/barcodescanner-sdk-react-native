import React, { Component } from 'react';

import {
  AppRegistry,
  AppState,
  StyleSheet,
  Text,
  findNodeHandle,
  View,
  Button,
  Image,
  Platform,
  PermissionsAndroid,
  BackHandler
} from 'react-native';

import AsyncStorage from '@react-native-community/async-storage';

import {
  BarcodePicker,
  ScanditModule,
  ScanSession,
  Barcode,
  SymbologySettings,
  ScanSettings,
  Rect,
  ScanOverlay
} from 'scandit-react-native';

import {
  TabNavigator
} from 'react-navigation';

import Events from 'react-native-simple-events';
import StatusBar from '../components/StatusBar'

export default class ScanScreen extends Component {

  constructor(props) {
    super(props);
    this.state = {
      isPickerVisible: true,
      buttonDisabled: true,
      text: 'Scan a code'
    }
  }

  static navigationOptions = {
    title: 'Scan',
    tabBarIcon: ({ tintColor }) => (
      <Image
        source={require('../assets/scan_icon.png')}
        style={{resizeMode:'contain', height:26, width:26, tintColor: tintColor}}
      />
    ),
    tabBarOnPress: (event) => {
      Events.trigger('fetch', null);
      Events.trigger('scanTabOpened', null);
      event.jumpToIndex(event.scene.index);
    }
  }

  async fetchSettings() {
    try {
      var storedSettings = await AsyncStorage.getItem('@MySuperStore:settings');
      this.scanSpecs = JSON.parse(storedSettings);
    } catch (error) {
      console.error(error);
    }
    if (!this.scanSpecs) {
      this.initAndStoreSettings();
    }
    if (this.scanner) {
      this.scanner.applySettings(this.scanSpecs.scanSettings);
      this.scanner.setGuiStyle(this.scanSpecs.overlaySettings.guiStyle);
      this.scanner.setViewfinderDimension(
        this.scanSpecs.overlaySettings.viewfinderSize.width,
        this.scanSpecs.overlaySettings.viewfinderSize.height,
        this.scanSpecs.overlaySettings.viewfinderSize.landscapeWidth,
        this.scanSpecs.overlaySettings.viewfinderSize.landscapeHeight);
      this.scanner.setBeepEnabled(this.scanSpecs.overlaySettings.beep);
      this.scanner.setVibrateEnabled(this.scanSpecs.overlaySettings.vibrate);
      this.scanner.setTorchEnabled(this.scanSpecs.overlaySettings.torchVisible);
      this.scanner.setTorchButtonMarginsAndSize(
        this.scanSpecs.overlaySettings.torchOffset.left,
        this.scanSpecs.overlaySettings.torchOffset.top, 40, 40);
      this.scanner.setCameraSwitchVisibility(this.scanSpecs.overlaySettings.cameraSwitchVisibility);
      this.scanner.setCameraSwitchMarginsAndSize(
        this.scanSpecs.overlaySettings.cameraSwitchOffset.right,
        this.scanSpecs.overlaySettings.cameraSwitchOffset.top,
        40,
        40);
    }
  }

  async initAndStoreSettings() {
    this.scanSpecs = {};
    this.scanSpecs.overlaySettings = {};
    this.scanSpecs.scanSettings = new ScanSettings();
    this.scanSpecs.scanSettings.setSymbologyEnabled(Barcode.Symbology.EAN13, true);
    this.scanSpecs.scanSettings.setSymbologyEnabled(Barcode.Symbology.EAN8, true);
    this.scanSpecs.scanSettings.setSymbologyEnabled(Barcode.Symbology.UPCE, true);
    this.scanSpecs.scanSettings.setSymbologyEnabled(Barcode.Symbology.TWO_DIGIT_ADD_ON, false);
    this.scanSpecs.scanSettings.setSymbologyEnabled(Barcode.Symbology.FIVE_DIGIT_ADD_ON, false);
    this.scanSpecs.scanSettings.setSymbologyEnabled(Barcode.Symbology.CODE11, false);
    this.scanSpecs.scanSettings.setSymbologyEnabled(Barcode.Symbology.CODE25, false);
    this.scanSpecs.scanSettings.setSymbologyEnabled(Barcode.Symbology.CODE39, true);
    this.scanSpecs.scanSettings.setSymbologyEnabled(Barcode.Symbology.CODE93, false);
    this.scanSpecs.scanSettings.setSymbologyEnabled(Barcode.Symbology.CODE128, true);
    this.scanSpecs.scanSettings.setSymbologyEnabled(Barcode.Symbology.ITF, true);
    this.scanSpecs.scanSettings.setSymbologyEnabled(Barcode.Symbology.MSI_PLESSEY, false);
    this.scanSpecs.scanSettings.setSymbologyEnabled(Barcode.Symbology.GS1_DATABAR, false);
    this.scanSpecs.scanSettings.setSymbologyEnabled(Barcode.Symbology.GS1_DATABAR_LIMITED, false);
    this.scanSpecs.scanSettings.setSymbologyEnabled(Barcode.Symbology.GS1_DATABAR_EXPANDED, false);
    this.scanSpecs.scanSettings.setSymbologyEnabled(Barcode.Symbology.CODABAR, false);
    this.scanSpecs.scanSettings.setSymbologyEnabled(Barcode.Symbology.QR, true);
    this.scanSpecs.scanSettings.setSymbologyEnabled(Barcode.Symbology.DATA_MATRIX, true);
    this.scanSpecs.scanSettings.setSymbologyEnabled(Barcode.Symbology.PDF417, false);
    this.scanSpecs.scanSettings.setSymbologyEnabled(Barcode.Symbology.MICRO_PDF417, false);
    this.scanSpecs.scanSettings.setSymbologyEnabled(Barcode.Symbology.AZTEC, false);
    this.scanSpecs.scanSettings.setSymbologyEnabled(Barcode.Symbology.MAXICODE, false);
    this.scanSpecs.scanSettings.setSymbologyEnabled(Barcode.Symbology.RM4SCC, false);
    this.scanSpecs.scanSettings.setSymbologyEnabled(Barcode.Symbology.KIX, false);
    this.scanSpecs.scanSettings.setSymbologyEnabled(Barcode.Symbology.DOTCODE, false);
    this.scanSpecs.scanSettings.setSymbologyEnabled(Barcode.Symbology.MICROQR, false);
    this.scanSpecs.scanSettings.setSymbologyEnabled(Barcode.Symbology.CODE32, false);
    this.scanSpecs.scanSettings.setSymbologyEnabled(Barcode.Symbology.LAPA4SC, false);
    this.scanSpecs.scanSettings.setSymbologyEnabled(Barcode.Symbology.IATA_TWO_OF_FIVE, false);
    this.scanSpecs.scanSettings.symbologies[Barcode.Symbology.QR].colorInvertedEnabled = false;
    this.scanSpecs.scanSettings.symbologies[Barcode.Symbology.DATA_MATRIX].colorInvertedEnabled = false;
    this.scanSpecs.scanSettings.activeScanningAreaCenterY = 0.5;
    this.scanSpecs.scanSettings.scanningHotSpot = {"x": 0.5, "y": 0.5};
    this.scanSpecs.scanSettings.activeScanningArea = new Rect(0.25, 0.25, 0.5, 0.5);
    this.scanSpecs.scanSettings.highDensityModeEnabled = false;
    this.scanSpecs.overlaySettings.guiStyle = ScanOverlay.GuiStyle.DEFAULT;
    this.scanSpecs.overlaySettings.viewfinderSize = {
      width: 0.9,
      height: 0.4,
      landscapeWidth: 0.6,
      landscapeHeight: 0.4
    }
    this.scanSpecs.overlaySettings.torchOffset = {
      left: 15,
      top: 15
    }
    this.scanSpecs.overlaySettings.cameraSwitchOffset = {
      right: 15,
      top: 15
    }
    this.scanSpecs.overlaySettings.beep = true;
    this.scanSpecs.overlaySettings.vibrate = true;
    this.scanSpecs.overlaySettings.torchVisible = true;
    this.scanSpecs.overlaySettings.cameraSwitchVisibility = ScanOverlay.CameraSwitchVisibility.NEVER;
    try {
      await AsyncStorage.setItem('@MySuperStore:settings', JSON.stringify(this.scanSpecs));
    } catch (error) {
      console.error(error);
    }
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
    this.props.navigation.addListener('didFocus', this._onFocus);
    this.props.navigation.addListener('didBlur', this._onBlur);
    AppState.addEventListener('change', this._handleAppStateChange);
    this.scanner.startScanning();
  }

  async componentDidMount() {
    const hasPermission = await this.hasCameraPermission();
    if (hasPermission) {
      this.cameraPermissionGranted();
    } else {
      await this.requestCameraPermission();
    }
  }

  componentWillMount() {
    Events.on('fetch', 'scanTab', () => { this.fetchSettings() });
    Events.on('pickersTabOpened', 'scanTab', () => { this.stopScanning() });
    Events.on('settingsTabOpened', 'scanTab', () => { this.stopScanning() })
    Events.on('scanTabOpened', 'scanTab', () => { this.startScanning() })
    this.fetchSettings();
  }

  componentWillUnmount() {
    this.props.navigation.removeListener('didFocus', this._onFocus);
    this.props.navigation.removeListener('didBlur', this._onBlur);
    AppState.removeEventListener('change', this._handleAppStateChange);
    Events.rm('fetch', 'scanTab');
    Events.rm('pickersTabOpened', 'scanTab');
    Events.rm('settingsTabOpened', 'scanTab');
    Events.rm('scanTabOpened', 'scanTab');
  }

  _onFocus = () => {
    this.setState({isFocused: true}, () => { this.setState(this.state)});
    this.startScanning()
  };

  _onBlur = () => {
    this.stopScanning();
    this.setState({isFocused: false});
  };
  
  _handleAppStateChange = (nextAppState) => {
    if (nextAppState.match(/inactive|background/)) {
      this.stopScanning();
    } else {
      this.fetchSettings();
      this.startScanning();
    }
  }

  render() {
    return (
      <View
        style={{ flex: 1 }}>
        <StatusBar style={{ backgroundColor: 'white' }}/>
        <View
          style={{ flex: 1 }}>
          {this.state.isPickerVisible && (
            <BarcodePicker
              style={{ flex: 1 }}
              onScan={ (session) => { this.onScan(session) }}
              scanSettings= { new ScanSettings() }
              ref={(scan) => { this.scanner = scan }}/>
          )}
        </View>
        <Text
          style={{
            flex: 1,
            textAlign: 'center',
            textAlignVertical: 'center'
          }}>
          { this.state.text }
        </Text>
        <View
          style={{ margin: 10 }}>
          <Button
            onPress={ () => { this.resumeScanning() }}
            title='Continue scanning'
            color='blue'
            disabled={ this.state.buttonDisabled }/>
        </View>
      </View>
    );
  }

  resumeScanning() {
    this.setState({ buttonDisabled: true });
    this.scanner.resumeScanning();
  }

  stopScanning() {
    if (this.scanner) {
      this.scanner.stopScanning();
      this.scanner = null
    }
    this.setState({
      isPickerVisible: false,
      buttonDisabled: false
    });
  }

  startScanning() {
    this.setState({
      isPickerVisible: true,
      buttonDisabled: true
    });
    if (this.scanner) {
      this.scanner.startScanning()
    }
  }

  onScan(session) {
    this.state.text = '';
    this.state.buttonDisabled = false;
    session.pauseScanning();
    session.newlyRecognizedCodes.forEach((barcode) =>
      this.state.text += '\n(' + barcode.symbology + ') ' + barcode.data);
    this.setState(this.state);
  }
}
