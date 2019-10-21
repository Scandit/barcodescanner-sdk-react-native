import { UIManager } from 'react-native';

export class CommandDispatcher {

  constructor(viewHandle) {
    this.pickerViewHandle = viewHandle;
  }

  getViewManagerConfig(viewManagerConfig) {
    if (UIManager.getViewManagerConfig) {
      return UIManager.getViewManagerConfig(viewManagerConfig);
    } else {
      return UIManager[viewManagerConfig];
    }
  }

  startScanning() {
    UIManager.dispatchViewManagerCommand(
      this.pickerViewHandle, this.getViewManagerConfig('BarcodePicker').Commands.startScanning, null);
  }

  startScanningInPausedState() {
    UIManager.dispatchViewManagerCommand(
      this.pickerViewHandle, this.getViewManagerConfig('BarcodePicker').Commands.startScanningInPausedState, null);
  }

  stopScanning() {
    UIManager.dispatchViewManagerCommand(
      this.pickerViewHandle, this.getViewManagerConfig('BarcodePicker').Commands.stopScanning, null);
  }

  resumeScanning() {
    UIManager.dispatchViewManagerCommand(
      this.pickerViewHandle, this.getViewManagerConfig('BarcodePicker').Commands.resumeScanning, null);
  }

  pauseScanning() {
    UIManager.dispatchViewManagerCommand(
      this.pickerViewHandle, this.getViewManagerConfig('BarcodePicker').Commands.pauseScanning, null);
  }

  applySettings(scanSettings) {
    UIManager.dispatchViewManagerCommand(
      this.pickerViewHandle,
      this.getViewManagerConfig('BarcodePicker').Commands.applySettings, [scanSettings]);
  }

  finishOnScanCallback(session) {
    UIManager.dispatchViewManagerCommand(
      this.pickerViewHandle,
      this.getViewManagerConfig('BarcodePicker').Commands.finishOnScanCallback,
      session);
  }

  finishOnRecognizeNewCodes(session) {
    UIManager.dispatchViewManagerCommand(
      this.pickerViewHandle,
      this.getViewManagerConfig('BarcodePicker').Commands.finishOnRecognizeNewCodes,
      session);
  }

  finishOnChangeTrackedCodes(session) {
    UIManager.dispatchViewManagerCommand(
      this.pickerViewHandle,
      this.getViewManagerConfig('BarcodePicker').Commands.finishOnChangeTrackedCodes,
      session);
  }

  finishOnTextRecognized(recognizedText) {
    UIManager.dispatchViewManagerCommand(
      this.pickerViewHandle,
      this.getViewManagerConfig('BarcodePicker').Commands.finishOnTextRecognized,
      recognizedText);
  }

  setBeepEnabled(isEnabled) {
    UIManager.dispatchViewManagerCommand(
      this.pickerViewHandle,
      this.getViewManagerConfig('BarcodePicker').Commands.setBeepEnabled, [isEnabled]);
  }

  setVibrateEnabled(isEnabled) {
    UIManager.dispatchViewManagerCommand(
      this.pickerViewHandle,
      this.getViewManagerConfig('BarcodePicker').Commands.setVibrateEnabled, [isEnabled]);
  }

  switchTorchOn(on) {
    UIManager.dispatchViewManagerCommand(
      this.pickerViewHandle,
      this.getViewManagerConfig('BarcodePicker').Commands.switchTorchOn, [on]);
  }

  setTorchEnabled(isEnabled) {
    UIManager.dispatchViewManagerCommand(
      this.pickerViewHandle,
      this.getViewManagerConfig('BarcodePicker').Commands.setTorchEnabled, [isEnabled]);
  }

  setCameraSwitchVisibility(visibility) {
    UIManager.dispatchViewManagerCommand(
      this.pickerViewHandle,
      this.getViewManagerConfig('BarcodePicker').Commands.setCameraSwitchVisibility, [visibility]);
  }

  setTextRecognitionSwitchVisible(isVisible) {
    UIManager.dispatchViewManagerCommand(
      this.pickerViewHandle,
      this.getViewManagerConfig('BarcodePicker').Commands.setTextRecognitionSwitchVisible, [isVisible]);
  }

  setViewfinderDimension(x, y, width, height) {
    UIManager.dispatchViewManagerCommand(
      this.pickerViewHandle,
      this.getViewManagerConfig('BarcodePicker').Commands.setViewfinderDimension, [x, y, width, height]);
  }

  setTorchButtonMarginsAndSize(leftMargin, topMargin, width, height) {
    UIManager.dispatchViewManagerCommand(
      this.pickerViewHandle,
      this.getViewManagerConfig('BarcodePicker').Commands.setTorchButtonMarginsAndSize, [leftMargin, topMargin, width, height]);
  }

  setCameraSwitchMarginsAndSize(leftMargin, topMargin, width, height) {
    UIManager.dispatchViewManagerCommand(
      this.pickerViewHandle,
      this.getViewManagerConfig('BarcodePicker').Commands.setCameraSwitchMarginsAndSize, [leftMargin, topMargin, width, height]);
  }

  setViewfinderColor(color) {
    UIManager.dispatchViewManagerCommand(
      this.pickerViewHandle,
      this.getViewManagerConfig('BarcodePicker').Commands.setViewfinderColor, [color]);
  }

  setViewfinderDecodedColor(color) {
    UIManager.dispatchViewManagerCommand(
      this.pickerViewHandle,
      this.getViewManagerConfig('BarcodePicker').Commands.setViewfinderDecodedColor, [color]);
  }

  setMatrixScanHighlightingColor(state, color) {
    UIManager.dispatchViewManagerCommand(
      this.pickerViewHandle,
      this.getViewManagerConfig('BarcodePicker').Commands.setMatrixScanHighlightingColor, [state, color]);
  }

  setOverlayProperty(propName, propValue) {
    UIManager.dispatchViewManagerCommand(
      this.pickerViewHandle,
      this.getViewManagerConfig('BarcodePicker').Commands.setOverlayProperty, [propName, propValue]);
  }

  setGuiStyle(style) {
    UIManager.dispatchViewManagerCommand(
      this.pickerViewHandle,
      this.getViewManagerConfig('BarcodePicker').Commands.setGuiStyle, [style]);
  }

}
