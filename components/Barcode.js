export class Barcode {

}

Barcode.CompositeFlag = {
  NONE: 0,
  UNKNOWN: 1,
  LINKED: 2,
  GS1_TYPE_A: 3,
  GS1_TYPE_B: 4,
  GS1_TYPE_C: 5
}

Barcode.Symbology = {
  UNKNOWN: "unknown",
  EAN13: "ean13",
  EAN8: "ean8",
  UPCA: "upca",
  UPCE: "upce",
  CODE11: "code11",
  CODE128: "code128",
  CODE39: "code39",
  CODE93: "code93",
  CODE25: "code25",
  ITF: "itf",
  QR: "qr",
  DATA_MATRIX: "data-matrix",
  PDF417: "pdf417",
  MICRO_PDF417: "micropdf417",
  MSI_PLESSEY: "msi-plessey",
  GS1_DATABAR: "databar",
  GS1_DATABAR_LIMITED: "databar-limited",
  GS1_DATABAR_EXPANDED: "databar-expanded",
  CODABAR: "codabar",
  AZTEC: "aztec",
  MAXICODE: "maxicode",
  FIVE_DIGIT_ADD_ON: "five-digit-add-on",
  TWO_DIGIT_ADD_ON: "two-digit-add-on",
  KIX: "kix",
  RM4SCC: "rm4scc",
  DOTCODE: "dotcode",
  MICROQR: "microQR",
  CODE32: "code32",
  LAPA4SC: "lapa4sc",
  IATA_TWO_OF_FIVE: "iata2of5"
}
