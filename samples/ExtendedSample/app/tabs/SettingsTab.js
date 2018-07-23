import React, { Component } from 'react';

import {
  AppRegistry,
  StyleSheet,
  Text,
  View,
  Button,
  ActivityIndicator,
  AsyncStorage,
  ScrollView,
  Switch,
  Slider,
  Picker,
  Image
} from 'react-native';

import {
  Barcode,
  SymbologySettings,
  ScanSettings,
  ScanOverlay
} from 'scandit-react-native';

import {
  TabNavigator
} from 'react-navigation';

import Events from 'react-native-simple-events';
import LabeledSwitch from '../components/LabeledSwitch'
import LabeledSlider from '../components/LabeledSlider'
import StatusBar from '../components/StatusBar'

export default class SettingsTab extends Component {

  constructor() {
    super();
  }

  static navigationOptions = {
    title: 'Settings',
    tabBarIcon: ({ tintColor }) => (
      <Image
        source={require('../assets/settings_icon.png')}
        style={{resizeMode:'contain', height:26, width:26, tintColor: tintColor}}
      />
    ),
    tabBarOnPress: (event) => {
      Events.trigger('settingsTabOpened', null);
      event.jumpToIndex(event.scene.index);
    }
  };

  componentWillMount() {
    this.fetchSettings();
  }

  async fetchSettings() {
    try {
      var storedSettings = await AsyncStorage.getItem('@MySuperStore:settings');
      super.setState(JSON.parse(storedSettings));
    } catch (error) {
      console.error(error);
    }
  }

  async saveSettings() {
    try {
      if (this.state.scanSettings.symbologies[Barcode.Symbology.TWO_DIGIT_ADD_ON].enabled ||
        this.state.scanSettings.symbologies[Barcode.Symbology.FIVE_DIGIT_ADD_ON].enabled) {
          this.state.scanSettings.maxNumberOfCodesPerFrame = 2;
        } else {
          this.state.scanSettings.maxNumberOfCodesPerFrame = 1;
        }
      await AsyncStorage.setItem('@MySuperStore:settings', JSON.stringify(this.state));
    } catch (error) {
      console.error(error);
    }
  }

  setState(state) {
    super.setState(state);
    this.saveSettings();
  }

  render() {
    if (!this.state) {
      return (
        <ActivityIndicator
          style={{flex:1}}
          size='large'/>
      );
    }
    return (
        <View style={{flex:1}}>
          <StatusBar style={{ backgroundColor: 'white' }}/>
          <ScrollView style={{flex:1}}>
            <Text style={{fontWeight: 'bold',
              margin: 10}}>
              Symbologies
            </Text>
            <LabeledSwitch
              label='EAN-13 & UPC-A'
              value={this.state.scanSettings.symbologies[Barcode.Symbology.EAN13].enabled}
              listener={(value) => {
                this.state.scanSettings.symbologies[Barcode.Symbology.EAN13].enabled = value;
                this.setState(this.state);
              }}
            />
            <LabeledSwitch
              label='EAN-8'
              value={this.state.scanSettings.symbologies[Barcode.Symbology.EAN8].enabled}
              listener={(value) => {
                this.state.scanSettings.symbologies[Barcode.Symbology.EAN8].enabled = value;
                this.setState(this.state);
              }}
            />
            <LabeledSwitch
              label='UPC-E'
              value={this.state.scanSettings.symbologies[Barcode.Symbology.UPCE].enabled}
              listener={(value) => {
                this.state.scanSettings.symbologies[Barcode.Symbology.UPCE].enabled = value;
                this.setState(this.state);
              }}
            />
            <LabeledSwitch
              label='2-Digit Add-On'
              value={this.state.scanSettings.symbologies[Barcode.Symbology.TWO_DIGIT_ADD_ON].enabled}
              listener={(value) => {
                this.state.scanSettings.symbologies[Barcode.Symbology.TWO_DIGIT_ADD_ON].enabled = value;
                this.setState(this.state);
              }}
            />
            <LabeledSwitch
              label='5-Digit Add-On'
              value={this.state.scanSettings.symbologies[Barcode.Symbology.FIVE_DIGIT_ADD_ON].enabled}
              listener={(value) => {
                this.state.scanSettings.symbologies[Barcode.Symbology.FIVE_DIGIT_ADD_ON].enabled = value;
                this.setState(this.state);
              }}
            />
            <LabeledSwitch
              label='Code 11'
              value={this.state.scanSettings.symbologies[Barcode.Symbology.CODE11].enabled}
              listener={(value) => {
                this.state.scanSettings.symbologies[Barcode.Symbology.CODE11].enabled = value;
                this.setState(this.state);
              }}
            />
            <LabeledSwitch
              label='Code 25'
              value={this.state.scanSettings.symbologies[Barcode.Symbology.CODE25].enabled}
              listener={(value) => {
                this.state.scanSettings.symbologies[Barcode.Symbology.CODE25].enabled = value;
                this.setState(this.state);
              }}
            />
            <LabeledSwitch
              label='Code 39'
              value={this.state.scanSettings.symbologies[Barcode.Symbology.CODE39].enabled}
              listener={(value) => {
                this.state.scanSettings.symbologies[Barcode.Symbology.CODE39].enabled = value;
                this.setState(this.state);
              }}
            />
            <LabeledSwitch
              label='Code 93'
              value={this.state.scanSettings.symbologies[Barcode.Symbology.CODE93].enabled}
              listener={(value) => {
                this.state.scanSettings.symbologies[Barcode.Symbology.CODE93].enabled = value;
                this.setState(this.state);
              }}
            />
            <LabeledSwitch
              label='Code 128'
              value={this.state.scanSettings.symbologies[Barcode.Symbology.CODE128].enabled}
              listener={(value) => {
                this.state.scanSettings.symbologies[Barcode.Symbology.CODE128].enabled = value;
                this.setState(this.state);
              }}
            />
            <LabeledSwitch
              label='Interleaved-Two-Five (ITF)'
              value={this.state.scanSettings.symbologies[Barcode.Symbology.ITF].enabled}
              listener={(value) => {
                this.state.scanSettings.symbologies[Barcode.Symbology.ITF].enabled = value;
                this.setState(this.state);
              }}
            />
            <LabeledSwitch
              label='MSI Plessey'
              value={this.state.scanSettings.symbologies[Barcode.Symbology.MSI_PLESSEY].enabled}
              listener={(value) => {
                this.state.scanSettings.symbologies[Barcode.Symbology.MSI_PLESSEY].enabled = value;
                this.setState(this.state);
              }}
            />
            <LabeledSwitch
              label='GS1 DataBar'
              value={this.state.scanSettings.symbologies[Barcode.Symbology.GS1_DATABAR].enabled}
              listener={(value) => {
                this.state.scanSettings.symbologies[Barcode.Symbology.GS1_DATABAR].enabled = value;
                this.setState(this.state);
              }}
            />
            <LabeledSwitch
              label='GS1 DataBar Limited'
              value={this.state.scanSettings.symbologies[Barcode.Symbology.GS1_DATABAR_LIMITED].enabled}
              listener={(value) => {
                this.state.scanSettings.symbologies[Barcode.Symbology.GS1_DATABAR_LIMITED].enabled = value;
                this.setState(this.state);
              }}
            />
            <LabeledSwitch
              label='GS1 DataBar Expanded'
              value={this.state.scanSettings.symbologies[Barcode.Symbology.GS1_DATABAR_EXPANDED].enabled}
              listener={(value) => {
                this.state.scanSettings.symbologies[Barcode.Symbology.GS1_DATABAR_EXPANDED].enabled = value;
                this.setState(this.state);
              }}
            />
            <LabeledSwitch
              label='Codabar'
              value={this.state.scanSettings.symbologies[Barcode.Symbology.CODABAR].enabled}
              listener={(value) => {
                this.state.scanSettings.symbologies[Barcode.Symbology.CODABAR].enabled = value;
                this.setState(this.state);
              }}
            />
            <LabeledSwitch
              label='QR'
              value={this.state.scanSettings.symbologies[Barcode.Symbology.QR].enabled}
              listener={(value) => {
                this.state.scanSettings.symbologies[Barcode.Symbology.QR].enabled = value;
                this.setState(this.state);
              }}
            />
            <LabeledSwitch
              label='Color Inverted QR'
              disabled={!this.state.scanSettings.symbologies[Barcode.Symbology.QR].enabled}
              value={this.state.scanSettings.symbologies[Barcode.Symbology.QR].colorInvertedEnabled}
              listener={(value) => {
                this.state.scanSettings.symbologies[Barcode.Symbology.QR].colorInvertedEnabled = value;
                this.setState(this.state);
              }}
            />
            <LabeledSwitch
              label='Data Matrix'
              value={this.state.scanSettings.symbologies[Barcode.Symbology.DATA_MATRIX].enabled}
              listener={(value) => {
                this.state.scanSettings.symbologies[Barcode.Symbology.DATA_MATRIX].enabled = value;
                this.setState(this.state);
              }}
            />
            <LabeledSwitch
              label='Color Inverted Data Matrix'
              disabled={!this.state.scanSettings.symbologies[Barcode.Symbology.DATA_MATRIX].enabled}
              value={this.state.scanSettings.symbologies[Barcode.Symbology.DATA_MATRIX].colorInvertedEnabled}
              listener={(value) => {
                this.state.scanSettings.symbologies[Barcode.Symbology.DATA_MATRIX].colorInvertedEnabled = value;
                this.setState(this.state);
              }}
            />
            <LabeledSwitch
              label='PDF417'
              value={this.state.scanSettings.symbologies[Barcode.Symbology.PDF417].enabled}
              listener={(value) => {
                this.state.scanSettings.symbologies[Barcode.Symbology.PDF417].enabled = value;
                this.setState(this.state);
              }}
            />
            <LabeledSwitch
              label='MicroPDF417'
              value={this.state.scanSettings.symbologies[Barcode.Symbology.MICRO_PDF417].enabled}
              listener={(value) => {
                this.state.scanSettings.symbologies[Barcode.Symbology.MICRO_PDF417].enabled = value;
                this.setState(this.state);
              }}
            />
            <LabeledSwitch
              label='Aztec'
              value={this.state.scanSettings.symbologies[Barcode.Symbology.AZTEC].enabled}
              listener={(value) => {
                this.state.scanSettings.symbologies[Barcode.Symbology.AZTEC].enabled = value;
                this.setState(this.state);
              }}
            />
            <LabeledSwitch
              label='MaxiCode'
              value={this.state.scanSettings.symbologies[Barcode.Symbology.MAXICODE].enabled}
              listener={(value) => {
                this.state.scanSettings.symbologies[Barcode.Symbology.MAXICODE].enabled = value;
                this.setState(this.state);
              }}
            />
            <LabeledSwitch
              label='RM4SCC'
              value={this.state.scanSettings.symbologies[Barcode.Symbology.RM4SCC].enabled}
              listener={(value) => {
                this.state.scanSettings.symbologies[Barcode.Symbology.RM4SCC].enabled = value;
                this.setState(this.state);
              }}
            />
            <LabeledSwitch
              label='KIX'
              value={this.state.scanSettings.symbologies[Barcode.Symbology.KIX].enabled}
              listener={(value) => {
                this.state.scanSettings.symbologies[Barcode.Symbology.KIX].enabled = value;
                this.setState(this.state);
              }}
            />
            <LabeledSwitch
              label='DotCode'
              value={this.state.scanSettings.symbologies[Barcode.Symbology.DOTCODE].enabled}
              listener={(value) => {
                this.state.scanSettings.symbologies[Barcode.Symbology.DOTCODE].enabled = value;
                this.setState(this.state);
              }}
            />
            <LabeledSwitch
              label='Micro QR'
              value={this.state.scanSettings.symbologies[Barcode.Symbology.MICROQR].enabled}
              listener={(value) => {
                this.state.scanSettings.symbologies[Barcode.Symbology.MICROQR].enabled = value;
                this.setState(this.state);
              }}
            />
            <LabeledSwitch
              label='Code 32'
              value={this.state.scanSettings.symbologies[Barcode.Symbology.CODE32].enabled}
              listener={(value) => {
                this.state.scanSettings.symbologies[Barcode.Symbology.CODE32].enabled = value;
                this.setState(this.state);
              }}
            />
            <Text style={{fontWeight: 'bold',
              margin: 10}}>
              Scanning Area
            </Text>
            <LabeledSwitch
              label='Restrict Scanning Area'
              value={ this.state.scanSettings.restrictedAreaScanningEnabled }
              listener={(value) => {
                if (value) {
                  this.state.scanSettings.activeScanningAreaLandscape = this.state.scanSettings.activeScanningArea;
                  this.state.scanSettings.activeScanningAreaPortrait = this.state.scanSettings.activeScanningArea;
                } else {
                  delete this.state.scanSettings.activeScanningAreaLandscape;
                  delete this.state.scanSettings.activeScanningAreaPortrait;
                }
                this.state.scanSettings.restrictedAreaScanningEnabled = value;
                this.setState(this.state);
            }}/>
            <LabeledSlider
              label='Hot Spot Width'
              disabled={ !this.state.scanSettings.restrictedAreaScanningEnabled }
              initialValue={ this.state.scanSettings.activeScanningArea.width }
              step={ 0.01 }
              decimals={ 2 }
              minValue={ 0.1 }
              maxValue={ 1.0 }
              listener={(value) => {
                this.state.scanSettings.activeScanningArea.width = value;
                value = Number((0.5 - (value / 2)).toFixed(2));
                this.state.scanSettings.activeScanningArea.x = value;
                this.setState(this.state);
            }}/>
            <LabeledSlider
              label='Hot Spot Height'
              disabled={ !this.state.scanSettings.restrictedAreaScanningEnabled }
              initialValue={ this.state.scanSettings.activeScanningArea.height }
              step={ 0.01 }
              decimals={ 2 }
              minValue={ 0.05 }
              maxValue={ 0.95 }
              listener={(value) => {
                this.state.scanSettings.activeScanningArea.height = value;
                value = Number((0.5 - (value / 2)).toFixed(2));
                this.state.scanSettings.activeScanningArea.y = value;
                this.setState(this.state);
            }}/>
            <LabeledSlider
              label='Hot Spot Y'
              disabled={ !this.state.scanSettings.restrictedAreaScanningEnabled }
              initialValue={ this.state.scanSettings.activeScanningAreaCenterY }
              step={ 0.01 }
              decimals={ 2 }
              minValue={ 0.1 }
              maxValue={ 0.9 }
              listener={(value) => {
                var height = this.state.scanSettings.activeScanningArea.height;
                this.state.scanSettings.activeScanningAreaCenterY = value;
                value = Number((value - (height / 2)).toFixed(2));
                this.state.scanSettings.scanningHotSpot = {"x": 0.5, "y": value};
                this.state.scanSettings.activeScanningArea.y = value;
                this.setState(this.state);
            }}/>
            <Text style={{ fontWeight: 'bold',
              margin: 10}}>
              HD Mode
            </Text>
            <LabeledSwitch
              label='Enable HD'
              value={this.state.scanSettings.highDensityModeEnabled}
              listener={(value) => {
                this.state.scanSettings.highDensityModeEnabled = value;
                this.setState(this.state);
              }}
            />
            <Text style={{ fontWeight: 'bold',
              margin: 10}}>
              Viewfinder
            </Text>
            <Text style={{ margin: 10}}>
              GUI Style
            </Text>
            <Picker
              style={{
                margin: 10
              }}
              selectedValue={ this.state.overlaySettings.guiStyle }
              onValueChange={(itemValue, itemIndex) => {
                this.state.overlaySettings.guiStyle = itemValue;
                this.setState(this.state);
              }}>
              <Picker.Item label='Frame' value={ ScanOverlay.GuiStyle.DEFAULT }/>
              <Picker.Item label='Laser' value={ ScanOverlay.GuiStyle.LASER }/>
              <Picker.Item label='None' value={ ScanOverlay.GuiStyle.NONE }/>
            </Picker>
            <LabeledSlider
              label='Width (Portrait)'
              disabled={ this.state.overlaySettings.guiStyle == ScanOverlay.GuiStyle.NONE }
              initialValue={ this.state.overlaySettings.viewfinderSize.width }
              step={ 0.01 }
              decimals={ 2 }
              minValue={ 0.1 }
              maxValue={ 0.95 }
              listener={(value) => {
                this.state.overlaySettings.viewfinderSize.width = value;
                this.setState(this.state);
            }}/>
            <LabeledSlider
              label='Height (Portrait)'
              disabled={ this.state.overlaySettings.guiStyle != ScanOverlay.GuiStyle.DEFAULT }
              initialValue={ this.state.overlaySettings.viewfinderSize.height }
              step={ 0.01 }
              decimals={ 2 }
              minValue={ 0.05 }
              maxValue={ 0.8 }
              listener={(value) => {
                this.state.overlaySettings.viewfinderSize.height = value;
                this.setState(this.state);
            }}/>
            <LabeledSlider
              label='Width (Landscape)'
              disabled={ this.state.overlaySettings.guiStyle == ScanOverlay.GuiStyle.NONE }
              initialValue={ this.state.overlaySettings.viewfinderSize.landscapeWidth }
              step={ 0.01 }
              decimals={ 2 }
              minValue={ 0.1 }
              maxValue={ 0.95 }
              listener={(value) => {
                this.state.overlaySettings.viewfinderSize.landscapeWidth = value;
                this.setState(this.state);
            }}/>
            <LabeledSlider
              label='Height (Landscape)'
              disabled={ this.state.overlaySettings.guiStyle != ScanOverlay.GuiStyle.DEFAULT }
              initialValue={ this.state.overlaySettings.viewfinderSize.landscapeHeight }
              step={ 0.01 }
              decimals={ 2 }
              minValue={ 0.05 }
              maxValue={ 0.8 }
              listener={(value) => {
                this.state.overlaySettings.viewfinderSize.landscapeHeight = value;
                this.setState(this.state);
            }}/>
            <Text style={{ fontWeight: 'bold',
              margin: 10}}>
              Feedback
            </Text>
            <LabeledSwitch
              label='Beep'
              value={this.state.overlaySettings.beep}
              listener={(value) => {
                this.state.overlaySettings.beep = value;
                this.setState(this.state);
              }}
            />
            <LabeledSwitch
              label='Vibrate'
              value={this.state.overlaySettings.vibrate}
              listener={(value) => {
                this.state.overlaySettings.vibrate = value;
                this.setState(this.state);
              }}
            />
            <Text style={{ fontWeight: 'bold',
              margin: 10}}>
              Torch Button
            </Text>
            <LabeledSwitch
              label='Visible'
              value={this.state.overlaySettings.torchVisible}
              listener={(value) => {
                this.state.overlaySettings.torchVisible = value;
                this.setState(this.state);
              }}
            />
            <LabeledSlider
              label='Torch Button Left Offset'
              disabled={ !this.state.overlaySettings.torchVisible }
              initialValue={ this.state.overlaySettings.torchOffset.left }
              step={ 1 }
              decimals={ 0 }
              minValue={ 0 }
              maxValue={ 50 }
              listener={(value) => {
                this.state.overlaySettings.torchOffset.left = value;
                this.setState(this.state);
            }}/>
            <LabeledSlider
              label='Torch Button Top Offset'
              disabled={ !this.state.overlaySettings.torchVisible }
              initialValue={ this.state.overlaySettings.torchOffset.top }
              step={ 1 }
              decimals={ 0 }
              minValue={ 0 }
              maxValue={ 50 }
              listener={(value) => {
                this.state.overlaySettings.torchOffset.top = value;
                this.setState(this.state);
            }}/>
            <Text style={{ fontWeight: 'bold',
              margin: 10}}>
              Camera Switch Button
            </Text>
            <Text style={{ margin: 10}}>
              Visibility
            </Text>
            <Picker
              style={{
                margin: 10
              }}
              selectedValue={ this.state.overlaySettings.cameraSwitchVisibility }
              onValueChange={(itemValue, itemIndex) => {
                this.state.overlaySettings.cameraSwitchVisibility = itemValue;
                this.setState(this.state);
              }}>
              <Picker.Item label='Always' value={ ScanOverlay.CameraSwitchVisibility.ALWAYS }/>
              <Picker.Item label='On Tablet' value={ ScanOverlay.CameraSwitchVisibility.ON_TABLET }/>
              <Picker.Item label='Never' value={ ScanOverlay.CameraSwitchVisibility.NEVER }/>
            </Picker>
            <LabeledSlider
              label='Camera Switch Right Offset'
              disabled={ this.state.overlaySettings.cameraSwitchVisibility == ScanOverlay.CameraSwitchVisibility.NEVER }
              initialValue={ this.state.overlaySettings.cameraSwitchOffset.right }
              step={ 1 }
              decimals={ 0 }
              minValue={ 0 }
              maxValue={ 50 }
              listener={(value) => {
                this.state.overlaySettings.cameraSwitchOffset.right = value;
                this.setState(this.state);
            }}/>
            <LabeledSlider
              label='Camera Switch Top Offset'
              disabled={ this.state.overlaySettings.cameraSwitchVisibility == ScanOverlay.CameraSwitchVisibility.NEVER }
              initialValue={ this.state.overlaySettings.cameraSwitchOffset.top }
              step={ 1 }
              decimals={ 0 }
              minValue={ 0 }
              maxValue={ 50 }
              listener={(value) => {
                this.state.overlaySettings.cameraSwitchOffset.top = value;
                this.setState(this.state);
            }}/>
          </ScrollView>
        </View>
    );
  }
}
