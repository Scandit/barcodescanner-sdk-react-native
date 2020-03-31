import React, { Component } from 'react';

import {
  AppRegistry,
  AppState,
  StyleSheet,
  Text,
  View,
  Button,
  ActivityIndicator,
  ScrollView,
  Switch,
  Picker,
  Alert,
  TouchableWithoutFeedback,
  Image
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

import Events from 'react-native-simple-events';
import CustomClickableElement from '../components/CustomClickableElement';
import StatusBar from '../components/StatusBar';
import SplitView from '../components/SplitView';

export default class PickersTab extends Component {

  static navigationOptions = {
    title: 'Pickers',
    tabBarIcon: ({ tintColor }) => (
      <Image
        source={require('../assets/scan_icon.png')}
        style={{resizeMode:'contain', height:26, width:26, tintColor: tintColor}}
      />
    ),
    tabBarOnPress: (event) => {
      Events.trigger('fetchForPickersTab', null);
      Events.trigger('pickersTabOpened', null);
      event.jumpToIndex(event.scene.index);
    }
  };

  state = {
    isScaledPickerVisible: false,
    isCroppedPickerVisible: false,
    isFullscreenPickerVisible: false,
    isSplitViewVisible: false
  }

  showScaledPicker = () => {
    this.setState({
      isScaledPickerVisible: true
    });
  }

  showCroppedPicker = () => {
    this.setState({
      isCroppedPickerVisible: true
    });
  }

  showFullscreenPicker = () => {
    this.setState({
      isFullscreenPickerVisible: true
    });
  }
  
  showSplitView = () => {
    this.setState({
      isSplitViewVisible: true
    });
  }

  hidePicker = () => {
    this.setState({
      isScaledPickerVisible: false,
      isCroppedPickerVisible: false,
      isFullscreenPickerVisible: false,
      isSplitViewVisible: false
    });
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
  }

  applySettings() {
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
      this.scanner.setTorchButtonMarginsAndSize(this.scanSpecs.overlaySettings.torchOffset.left,
      this.scanSpecs.overlaySettings.torchOffset.top, 40, 40);
      this.scanner.setCameraSwitchVisibility(this.scanSpecs.overlaySettings.cameraSwitchVisibility);
      this.scanner.setCameraSwitchMarginsAndSize(this.scanSpecs.overlaySettings.cameraSwitchOffset.right,
      this.scanSpecs.overlaySettings.cameraSwitchOffset.top, 40, 40);
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

  componentDidMount() {
    this.props.navigation.addListener('didFocus', this._onFocus);
    this.props.navigation.addListener('didBlur', this._onBlur);
    AppState.addEventListener('change', this._handleAppStateChange);
  }

  componentWillMount() {
    Events.on('fetchForPickersTab', 'pickersTab', () => { this.fetchSettings() });
    Events.on('scanTabOpened', 'pickersTab', () => { this.stopScanning() });
    Events.on('settingsTabOpened', 'pickersTab', () => { this.stopScanning() })
    this.fetchSettings();
  }

  componentWillUnmount() {
    this.props.navigation.removeListener('didFocus', this._onBlur);
    this.props.navigation.removeListener('didBlur', this._onFocus);
    AppState.removeEventListener('change', this._handleAppStateChange);
    Events.rm('fetchForPickersTab', 'pickersTab');
    Events.rm('scanTabOpened', 'pickersTab');
    Events.rm('settingsTabOpened', 'pickersTab');
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
      <View style={{flex:1}}>
        <StatusBar style={{ backgroundColor: 'white' }}/>
        <TouchableWithoutFeedback onPress={() => {this.hidePicker()}} underlayColor="white">
          <View style={{width: '100%',
          height: '100%',
          alignItems: 'center',
          justifyContent: 'center'}}>
            <ScrollView style={{width: '100%',
            height: '100%',
            backgroundColor: 'white'}}>
              <Text style={{fontWeight: 'bold',
                margin: 10}}>
                Pickers
              </Text>
              <CustomClickableElement
                onPress={() => {this.showScaledPicker()}}
                mainLabel="Scaled Picker"
              />
              <CustomClickableElement
                onPress={() => {this.showCroppedPicker()}}
                mainLabel="Cropped Picker"
              />
              <CustomClickableElement
                onPress={() => {this.showFullscreenPicker()}}
                mainLabel="Fullscreen Picker"
              />
              <CustomClickableElement
                onPress={() => {this.showSplitView()}}
                mainLabel="Split View"
              />
            </ScrollView>
              {this.state.isScaledPickerVisible && (
                <View style={{width: '100%',
                height: '100%',
                position: 'absolute',
                alignItems: 'center',
                justifyContent: 'center',
                justifyContent: 'center',
                backgroundColor: 'rgba(52, 52, 52, 0.8)'}}>
                  <View style={{width: '75%',
                  height: '75%',
                  alignItems: 'center',
                  justifyContent: 'center'}}>
                    <BarcodePicker
                      style={{ width: '100%', height: '100%' }}
                      onScan={ (session) => { this.onScan(session) }}
                      scanSettings= { new ScanSettings() }
                      ref={(scan) => {
                        this.scanner = scan
                        if (this.scanner) {
                          this.applySettings()
                          this.scanner.startScanning()
                        }
                      }}/>
                  </View>
                </View>
              )}
              {this.state.isCroppedPickerVisible && (
                <View style={{width: '100%',
                height: '100%',
                position: 'absolute',
                alignItems: 'center',
                justifyContent: 'center',
                justifyContent: 'center',
                backgroundColor: 'rgba(52, 52, 52, 0.8)'}}>
                  <View style={{width: '100%',
                  height: '25%',
                  alignItems: 'center',
                  justifyContent: 'center'}}>
                    <BarcodePicker
                      style={{ width: '100%', height: '100%' }}
                      onScan={ (session) => { this.onScan(session) }}
                      scanSettings= { new ScanSettings() }
                      ref={(scan) => {
                        this.scanner = scan
                        if (this.scanner) {
                          this.applySettings()
                          this.scanner.startScanning()
                        }
                      }}/>
                  </View>
                </View>
              )}
              {this.state.isFullscreenPickerVisible && (
                <View style={{width: '100%',
                height: '100%',
                position: 'absolute',
                alignItems: 'center',
                justifyContent: 'center',
                justifyContent: 'center',
                backgroundColor: 'rgba(52, 52, 52, 0.8)'}}>
                  <View style={{width: '100%',
                  height: '100%',
                  alignItems: 'center',
                  justifyContent: 'center'}}>
                    <TouchableWithoutFeedback onPress={() => {this.hidePicker()}} >
                      <BarcodePicker
                        style={{ width: '100%', height: '100%' }}
                        onScan={ (session) => { this.onScan(session) }}
                        scanSettings= { new ScanSettings() }
                        ref={(scan) => {
                          this.scanner = scan
                          if (this.scanner) {
                            this.applySettings()
                            this.scanner.startScanning()
                          }
                        }}/>
                    </TouchableWithoutFeedback>
                  </View>
                </View>
              )}
              {this.state.isSplitViewVisible && (
                <View style={{width: '100%',
                height: '100%',
                position: 'absolute',
                alignItems: 'stretch',
                justifyContent: 'center',
                backgroundColor: 'rgba(52, 52, 52, 0.8)'}}>
                  <View style={{width: '100%',
                  height: '100%',
                  alignItems: 'stretch',
                  justifyContent: 'center' }}>
                    <TouchableWithoutFeedback onPress={() => {this.hidePicker()}} >
                      <SplitView
                        style={{ width: '100%', height: '100%' }}
                        ref={(scan) => {
                          this.scanner = scan
                        }}/>
                    </TouchableWithoutFeedback>
                  </View>
                </View>
              )}
          </View>
        </TouchableWithoutFeedback>
      </View>
    );
  }

  onScan(session) {
    var code = this.firstElementOf(session.newlyRecognizedCodes)
    if (code && code.data) {
      Alert.alert("Scanned code:" + code.data);
    }
  }

  stopScanning() {
    if (this.scanner) {
      this.scanner.stopScanning();
    }
    this.hidePicker()
  }

  startScanning() {
    this.forceUpdate()
    if (this.scanner) {
      this.scanner.startScanning()
    }
  }

  firstElementOf(p) {
    for(var i in p) return p[i];
  }

}
