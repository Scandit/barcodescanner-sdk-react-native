import PropTypes from 'prop-types';
import React from 'react';
import { findNodeHandle, processColor, requireNativeComponent, View } from 'react-native';

import { CommandDispatcher } from './CommandDispatcher';
import { SerializationHelper } from './SerializationHelper';

var iface = {
  name: 'BarcodePicker',
  propTypes: {
        scanSettings: PropTypes.object,
        shouldPassBarcodeFrame: PropTypes.bool,
        onScan: PropTypes.func,
        onRecognizeNewCodes: PropTypes.func,
        onChangeTrackedCodes: PropTypes.func,
        onBarcodeFrameAvailable: PropTypes.func,
        onSettingsApplied: PropTypes.func,
        onTextRecognized: PropTypes.func,
        onWarnings: PropTypes.func,
        onPropertyChanged: PropTypes.func,
        ...View.propTypes
  }
};

var ReactBarcodePicker = requireNativeComponent('BarcodePicker', iface);

export class BarcodePicker extends React.Component {

    constructor(props) {
        super(props);
        this.onScan = this.onScan.bind(this);
        this.onRecognizeNewCodes = this.onRecognizeNewCodes.bind(this);
        this.onChangeTrackedCodes = this.onChangeTrackedCodes.bind(this);
        this.onBarcodeFrameAvailable = this.onBarcodeFrameAvailable.bind(this);
        this.onSettingsApplied = this.onSettingsApplied.bind(this);
        this.onTextRecognized = this.onTextRecognized.bind(this);
        this.onWarnings = this.onWarnings.bind(this);
        this.onPropertyChanged = this.onPropertyChanged.bind(this);
    }

    componentDidMount() {
        this.dispatcher = new CommandDispatcher(findNodeHandle(this.reference));
    }

    onScan(event: Event) {
        if (!this.props.onScan) {
            return;
        }
        var session = SerializationHelper.deserializeScanSession(event.nativeEvent);
        this.props.onScan(session);
        this.dispatcher.finishOnScanCallback(SerializationHelper.serializeScanSession(session));
    }

    onRecognizeNewCodes(event: Event) {
        if (!this.props.onRecognizeNewCodes) {
            return;
        }
        var session = SerializationHelper.deserializeMatrixScanSession(event.nativeEvent);
        this.props.onRecognizeNewCodes(session);
        this.dispatcher.finishOnRecognizeNewCodes(SerializationHelper.serializeScanSession(session));
    }

    onChangeTrackedCodes(event: Event) {
        if (!this.props.onChangeTrackedCodes) {
            return;
        }
        var session = SerializationHelper.deserializeMatrixScanSession(event.nativeEvent);
        this.props.onChangeTrackedCodes(session);
        this.dispatcher.finishOnChangeTrackedCodes(SerializationHelper.serializeScanSession(session));
    }

    onBarcodeFrameAvailable(event: Event) {
        if (!this.props.onBarcodeFrameAvailable) {
            return;
        }
        var frame = SerializationHelper.deserializeFrame(event.nativeEvent);
        this.props.onBarcodeFrameAvailable(frame);
    }

    onSettingsApplied(event: Event) {
        if (!this.props.onSettingsApplied) {
            return;
        }
        this.props.onSettingsApplied(event.nativeEvent);
    }

    onTextRecognized(event: Event) {
        if (!this.props.onTextRecognized) {
            return;
        }
        this.props.onTextRecognized(event.nativeEvent.text);
    }

    onWarnings(event: Event) {
        if (!this.props.onWarnings) {
            return
        }
        this.props.onWarnings(event.nativeEvent);
    }

    onPropertyChanged(event: Event) {
        if (!this.props.onPropertyChanged) {
            return
        }
        this.props.onPropertyChanged(event.nativeEvent.name, event.nativeEvent.newState);
    }

    render() {
        return <ReactBarcodePicker
            {...this.props}
            onScan = {this.onScan}
            onRecognizeNewCodes = {this.onRecognizeNewCodes}
            onChangeTrackedCodes = {this.onChangeTrackedCodes}
            onBarcodeFrameAvailable = {this.onBarcodeFrameAvailable}
            onSettingsApplied = {this.onSettingsApplied}
            onTextRecognized = {this.onTextRecognized}
            onWarnings = {this.onWarnings}
            onPropertyChanged = {this.onPropertyChanged}
            ref = {(scan) => {this.reference = scan}} />;
    }

    startScanning() {
        this.dispatcher.startScanning();
    }

    stopScanning() {
        this.dispatcher.stopScanning();
    }

    resumeScanning() {
        this.dispatcher.resumeScanning();
    }

    pauseScanning() {
        this.dispatcher.pauseScanning();
    }

    setBeepEnabled(isEnabled) {
        this.dispatcher.setBeepEnabled(isEnabled);
    }

    applySettings(settingsJSON) {
        this.dispatcher.applySettings(settingsJSON);
    }

    setVibrateEnabled(isEnabled) {
        this.dispatcher.setVibrateEnabled(isEnabled);
    }
    
    switchTorchOn(on) {
        this.dispatcher.switchTorchOn(on);
    }

    setTorchEnabled(isEnabled) {
        this.dispatcher.setTorchEnabled(isEnabled);
    }

    setCameraSwitchVisibility(visibility) {
        this.dispatcher.setCameraSwitchVisibility(visibility);
    }

    setTextRecognitionSwitchVisible(isVisible) {
        this.dispatcher.setTextRecognitionSwitchVisible(isVisible);
    }

    setViewfinderDimension(x, y, width, height) {
        this.dispatcher.setViewfinderDimension(x, y, width, height);
    }

    setTorchButtonMarginsAndSize(leftMargin, topMargin, width, height) {
        this.dispatcher.setTorchButtonMarginsAndSize(leftMargin, topMargin, width, height);
    }

    setCameraSwitchMarginsAndSize(leftMargin, topMargin, width, height) {
        this.dispatcher.setCameraSwitchMarginsAndSize(leftMargin, topMargin, width, height);
    }

    setViewfinderColor(color) {
        this.dispatcher.setViewfinderColor(processColor(color));
    }

    setViewfinderDecodedColor(color) {
        this.dispatcher.setViewfinderDecodedColor(processColor(color));
    }

    setMatrixScanHighlightingColor(state, color) {
        this.dispatcher.setMatrixScanHighlightingColor(state, processColor(color));
    }

    setOverlayProperty(propName, propValue) {
        this.dispatcher.setOverlayProperty(propName, propValue);
    }

    setGuiStyle(style) {
        this.dispatcher.setGuiStyle(style);
    }
}

BarcodePicker.Warning = {
    TOO_MUCH_GLARE_WARNING: 3,
    NOT_ENOUGH_CONTRAST_WARNING: 4
}

BarcodePicker.ChangedProperty = {
    TORCH: 'torchOn',
    SWITCH_CAMERA: 'switchCamera',
    RECOGNITION_MODE: 'recognitionMode',
    RELATIVE_ZOOM: 'relativeZoom'
}
