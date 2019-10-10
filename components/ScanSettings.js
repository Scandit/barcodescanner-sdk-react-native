import { SymbologySettings } from './SymbologySettings';

export class ScanSettings {

  constructor() {
    this.symbologies = {};
  }

  getSymbologySettings(symbology) {
    return this.symbologies[symbology];
  }

  setSymbologyEnabled(symbology, enabled) {
    var symbologySettings = this.getSymbologySettings(symbology);
    if (!symbologySettings) {
      symbologySettings = new SymbologySettings();
      this.symbologies[symbology] = symbologySettings;
    }
    symbologySettings.enabled = enabled;
  }
}

ScanSettings.RecognitionMode = {
  TEXT: "text",
  CODE: "code",
  CODE_AND_TEXT: "code_and_text"
}

ScanSettings.CameraFacing = {
  BACK: "back",
  FRONT: "front"
}

ScanSettings.WorkingRange = {
  STANDARD: "standard",
  LONG: "long"
}
