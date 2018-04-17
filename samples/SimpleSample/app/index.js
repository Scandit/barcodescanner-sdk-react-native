import React, { Component } from 'react';
import {
  AppRegistry,
  StyleSheet,
  Text,
  findNodeHandle,
  View
} from 'react-native';
import {
  BarcodePicker,
  ScanditModule,
  ScanSession,
  Barcode,
  SymbologySettings,
  ScanSettings
} from 'react-native-scandit';

ScanditModule.setAppKey('ATwr6w4fEzA1IyUL7gxW6Q8Db366LWoKjGbiKZ1OhtoAd2ODCXI343kt1dckVgSTphqDje50wGkIF015aGaH+Jx9rUp0UhNc0zo8EPZjI9nfQ/WxQEyJZYlFbIWVXU+ntytwRHIzYxVAVs17rnPHm19YCxN5KEyU1kv+0H9G7HOgcoP2x2kELp9DnQIhZiNt6XneecZu2b84eykJc1JWt0pTv0xuSwL0/32tVn1XKvWBYd5OJmo1nZp5XlpnL92THWuP9bEoQV61Xg3PFkbSOYhdv6mZd1uVLV0R0mUonykASDrN7HK4olJOL6O2ZrCY11jMiMFqCLe9bejk+DgUNRQtUyGR5WQqSw2TlJANpeTVYsjaUqVQHZMmNiBnmMz8cMPP+f2bNup44OTfsxtWsVMQFOIuVVQWxjbi/mxdI/Sm1Hm5Qw4hGsD6Wq/p6I8CdhTT4ogafwKw6pkVMlI8RxQ07K4z0ysXh5/d2XNZLuJlujTZq+96xjQR2OEM7mWhd/0xzW8UMPFigxAYgx+vGYyi2nZzt5+nW1cqyxco5atWoLiQW4y5E2f69vAF5O9bBf3HmVd5O75OaazeQdLcCfybRxuHr8iElFMGbNDAUQRA9S7dgWlp44pK/3EwnyDpPsPoKDBLhp+TXkUONm+2b9enytjj9YJ/o8x/uE/TKa0F3PNAqzs8P7P4IlDq0a4OYad3Y+egIm686ZY28bYp26aTbtMc4nq/7C2+C/ZrSVEnMNGo1Ku23448jDOmWNR8jxLqrKdaCI4t8UXbfLILi2M0v0pFqiRVqPmfoCSKm9iJCoccDutYgVIFXGcDtGGHzu8xvpkl8cMzQPVOwJxF52Srz5wKOYQl9T55Mgz2LXs/UKPaotbtP0qgdaXLzP8qfKXif/ypH2FDaECkL2Oyav86/V3QuhmrMRZSt4gKTNbN14S80p2CUhpjKBUs/pPznmbHeeDV78cxmnpk/cJQMWVx6gfcsOqmxEMbAkHke8dYaLbqU2saTOnE9BAjvQ==');

export default class SimpleSample extends Component {

  componentWillMount() {
    this.settings = new ScanSettings();
    this.settings.setSymbologyEnabled(Barcode.Symbology.EAN13, true);
    this.settings.setSymbologyEnabled(Barcode.Symbology.EAN8, true);
    this.settings.setSymbologyEnabled(Barcode.Symbology.UPCA, true);
    this.settings.setSymbologyEnabled(Barcode.Symbology.UPCE, true);
    this.settings.setSymbologyEnabled(Barcode.Symbology.CODE39, true);
    this.settings.setSymbologyEnabled(Barcode.Symbology.ITF, true);
    this.settings.setSymbologyEnabled(Barcode.Symbology.QR, true);
    this.settings.setSymbologyEnabled(Barcode.Symbology.DATA_MATRIX, true);
    this.settings.setSymbologyEnabled(Barcode.Symbology.CODE128, true);
    this.settings.setSymbologyEnabled(Barcode.Symbology.MICROQR, true);

    /* Some 1d barcode symbologies allow you to encode variable-length data. By default, the
       Scandit BarcodeScanner SDK only scans barcodes in a certain length range. If your
       application requires scanning of one of these symbologies, and the length is falling
       outside the default range, you may need to adjust the "active symbol counts" for this
       symbology. This is shown in the following few lines of code. */
    this.settings.getSymbologySettings(Barcode.Symbology.CODE39)
      .activeSymbolCounts = [7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20];
    /* For details on defaults and how to calculate the symbol counts for each symbology, take
       a look at http://docs.scandit.com/stable/c_api/symbologies.html. */
  }

  componentDidMount() {
    this.scanner.startScanning();
  }

  render() {
    return (
      <View style={{
			flex: 1,
			flexDirection: 'column'}}>
			<BarcodePicker
				onScan={(session) => { this.onScan(session) }}
				scanSettings= { this.settings }
        ref={(scan) => { this.scanner = scan }}
				style={{ flex: 1 }}/>
    </View>
    );
  }

  onScan(session) {
    alert(session.newlyRecognizedCodes[0].data + " " + session.newlyRecognizedCodes[0].symbology);
  }

}
