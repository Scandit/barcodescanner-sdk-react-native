import { BarcodeFrame } from './BarcodeFrame';
import { MatrixScanSession } from './MatrixScanSession';
import { ScanSession } from './ScanSession';
import { RecognizedText } from './RecognizedText';

export class SerializationHelper {

  static serializeScanSession(scanSession) {
    return [scanSession.shouldStop, scanSession.shouldPause, scanSession.rejectedCodes];
  }

  static deserializeScanSession(map) {
    return new ScanSession(
      map.allRecognizedCodes,
      map.newlyRecognizedCodes,
      map.newlyLocalizedCodes);
  }

  static deserializeMatrixScanSession(map) {
    return new MatrixScanSession(map.newlyTrackedCodes, map.allTrackedCodes);
  }

  static serializeRecognizedText(recognizedText) {
    return [recognizedText.shouldStop, recognizedText.shouldPause, recognizedText.rejected];
  }

  static deserializeRecognizedText(map) {
    return new RecognizedText(map.text, map.rejected);
  }

  static deserializeFrame(frame) {
    return new BarcodeFrame(frame);
  }

}
