Changing the Length of the Barcodes to Decode {#react-native-active-symbols-counts}
=========================================================================

By default the Scandit Barcode Scanner scans barcodes with a fixed symbol count range.
Under certain conditions it is helpful to modify the length of the barcode to be decoded, for example if

* you want to scan longer codes which can not be decoded with the default settings.
* to optimize decoder performance for codes of certain lengths; when it is known that the application only requires scanning of particular barcode lengths.

The mapping from characters to symbols is symbology-specific. For some symbologies, the start and end characters are included, others include checksums characters in the symbol counts.

The active symbol count setting is ignored for fixed-size barcodes (the EAN and UPC family of codes) as well as 2d codes. For other symbologies, see <a href="../c_api/symbologies.html">Calculating symbol counts for variable-length symbologies</a>.


## Change the barcode length

Changing the length of the barcodes is done through {@link Scandit.SymbologySettings.activeSymbolCounts activeSymbolCounts} on the symbology settings object.
Following is an example of changing the symbol count range (4-20) for Code 128 barcodes.


~~~~~~~~~~~~~~~~{.java}

var symbolCounts = [4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20];
settings.getSymbologySettings(Barcode.Symbology.CODE128).activeSymbolCounts = symbolCounts;

~~~~~~~~~~~~~~~~
