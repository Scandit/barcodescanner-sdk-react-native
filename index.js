import {
	NativeModules
} from 'react-native';
export { ScanSession } from './components/ScanSession';
export { BarcodePicker } from './components/BarcodePicker';
export { Barcode } from './components/Barcode';
export { ScanSettings } from './components/ScanSettings';
export { SymbologySettings } from './components/SymbologySettings';
export { ScanOverlay } from './components/ScanOverlay';
export { Rect } from './components/Rect';
export { Point } from './components/Point';
export { TextRecognitionSettings } from './components/TextRecognitionSettings';
export { RecognizedText } from './components/RecognizedText';

export const ScanditModule = NativeModules.ScanditModule;
