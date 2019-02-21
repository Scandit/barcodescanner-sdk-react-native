import {
  UIManager
} from 'react-native';

export class CommandDispatcher {

  constructor(viewHandle) {
    this.pickerViewHandle = viewHandle;
  }

  startScanning() {
    UIManager.dispatchViewManagerCommand(
      this.pickerViewHandle, UIManager.getViewManagerConfig('BarcodePicker').Commands.startScanning, null);
  }

  stopScanning() {
    UIManager.dispatchViewManagerCommand(
      this.pickerViewHandle, UIManager.getViewManagerConfig('BarcodePicker').Commands.stopScanning, null);
  }

  resumeScanning() {
    UIManager.dispatchViewManagerCommand(
      this.pickerViewHandle, UIManager.getViewManagerConfig('BarcodePicker').Commands.resumeScanning, null);
  }

  pauseScanning() {
    UIManager.dispatchViewManagerCommand(
      this.pickerViewHandle, UIManager.getViewManagerConfig('BarcodePicker').Commands.pauseScanning, null);
  }

  applySettings(scanSettings) {
    UIManager.dispatchViewManagerCommand(
      this.pickerViewHandle,
      UIManager.getViewManagerConfig('BarcodePicker').Commands.applySettings, [scanSettings]);
  }

  finishOnScanCallback(session) {
    UIManager.dispatchViewManagerCommand(
      this.pickerViewHandle,
      UIManager.getViewManagerConfig('BarcodePicker').Commands.finishOnScanCallback,
      session);
  }

  finishOnRecognizeNewCodes(session) {
    UIManager.dispatchViewManagerCommand(
      this.pickerViewHandle,
      UIManager.getViewManagerConfig('BarcodePicker').Commands.finishOnRecognizeNewCodes,
      session);
  }

  setBeepEnabled(isEnabled) {
    UIManager.dispatchViewManagerCommand(
      this.pickerViewHandle,
      UIManager.getViewManagerConfig('BarcodePicker').Commands.setBeepEnabled, [isEnabled]);
  }

  setVibrateEnabled(isEnabled) {
    UIManager.dispatchViewManagerCommand(
      this.pickerViewHandle,
      UIManager.getViewManagerConfig('BarcodePicker').Commands.setVibrateEnabled, [isEnabled]);
  }

  setTorchEnabled(isEnabled) {
    UIManager.dispatchViewManagerCommand(
      this.pickerViewHandle,
      UIManager.getViewManagerConfig('BarcodePicker').Commands.setTorchEnabled, [isEnabled]);
  }

  setCameraSwitchVisibility(visibility) {
    UIManager.dispatchViewManagerCommand(
      this.pickerViewHandle,
      UIManager.getViewManagerConfig('BarcodePicker').Commands.setCameraSwitchVisibility, [visibility]);
  }

  setTextRecognitionSwitchVisible(isVisible) {
    UIManager.dispatchViewManagerCommand(
      this.pickerViewHandle,
      UIManager.getViewManagerConfig('BarcodePicker').Commands.setTextRecognitionSwitchVisible, [isVisible]);
  }

  setViewfinderDimension(x, y, width, height) {
    UIManager.dispatchViewManagerCommand(
      this.pickerViewHandle,
      UIManager.getViewManagerConfig('BarcodePicker').Commands.setViewfinderDimension, [x, y, width, height]);
  }

  setTorchButtonMarginsAndSize(leftMargin, topMargin, width, height) {
    UIManager.dispatchViewManagerCommand(
      this.pickerViewHandle,
      UIManager.getViewManagerConfig('BarcodePicker').Commands.setTorchButtonMarginsAndSize, [leftMargin, topMargin, width, height]);
  }

  setCameraSwitchMarginsAndSize(leftMargin, topMargin, width, height) {
    UIManager.dispatchViewManagerCommand(
      this.pickerViewHandle,
      UIManager.getViewManagerConfig('BarcodePicker').Commands.setCameraSwitchMarginsAndSize, [leftMargin, topMargin, width, height]);
  }

  setViewfinderColor(color) {
    UIManager.dispatchViewManagerCommand(
      this.pickerViewHandle,
      UIManager.getViewManagerConfig('BarcodePicker').Commands.setViewfinderColor, [color]);
  }

  setViewfinderDecodedColor(color) {
    UIManager.dispatchViewManagerCommand(
      this.pickerViewHandle,
      UIManager.getViewManagerConfig('BarcodePicker').Commands.setViewfinderDecodedColor, [color]);
  }

  setMatrixScanHighlightingColor(state, color) {
    UIManager.dispatchViewManagerCommand(
      this.pickerViewHandle,
      UIManager.getViewManagerConfig('BarcodePicker').Commands.setMatrixScanHighlightingColor, [state, color]);
  }

  setOverlayProperty(propName, propValue) {
    UIManager.dispatchViewManagerCommand(
      this.pickerViewHandle,
      UIManager.getViewManagerConfig('BarcodePicker').Commands.setOverlayProperty, [propName, propValue]);
  }

  setGuiStyle(style) {
    UIManager.dispatchViewManagerCommand(
      this.pickerViewHandle,
      UIManager.getViewManagerConfig('BarcodePicker').Commands.setGuiStyle, [style]);
  }

}
