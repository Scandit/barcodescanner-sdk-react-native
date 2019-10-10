import { Rect } from './Rect';

export class TextRecognitionSettings {

  constructor() {
    this.areaPortrait = new Rect(0, 0.375, 1, 0.25);
    this.areaLandscape = new Rect(0, 0.375, 1, 0.25);
    this.regex = null;
    this.characterWhitelist = null;
  }

}
