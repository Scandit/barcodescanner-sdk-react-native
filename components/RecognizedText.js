export class RecognizedText {

  constructor(text, rejected) {
    this.text = text;
    this.rejected = rejected;
    this.shouldPause = false;
    this.shouldStop = false;
  }

  pauseScanning() {
    this.shouldPause = true;
  }

  stopScanning() {
    this.shouldStop = true;
  }

  reject() {
    this.rejected = true;
  }

}
