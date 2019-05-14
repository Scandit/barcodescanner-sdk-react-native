export class MatrixScanSession {

	constructor(newlyTrackedCodes, allTrackedCodes) {
		this.newlyTrackedCodes = newlyTrackedCodes;
		this.allTrackedCodes = allTrackedCodes;
		this.shouldPause = false;
		this.shouldStop = false;
		this.rejectedCodes = [];
	}

	pauseScanning() {
		this.shouldPause = true;
	}

	stopScanning() {
		this.shouldStop = true;
	}

	rejectTrackedCode(barcode) {
		this.rejectedCodes.push(barcode.id);
	}

}
