
/**
 * Module for all functionality of the barcode recognition plugin.
 *
 *
 */
public class Scandit {

	/**
	 * @brief Represents a recognized/localized barcode/2D code.
	 *
	 * The Barcode class represents a  barcode, or 2D code that has been localized or recognized
	 * by the barcode recognition engine.
	 *
	 *
	 */
	public class Barcode {

		/**
		*	@brief the data contained in the barcode/2D code, for example the 13 digit number of an EAN13 code.
		*/
		public String data;

		/**
		*	@brief the symbology of a recognized barcode.
		*/
		public String symbology;

	    /**
	     * @brief An enumeration of all supported barcode symbologies
	     */
		public enum Symbology  {
    		/**
    		 * @brief Sentinel value to represent an unknown symbology
    		 */
			UNKNOWN,
    		/**
     		 * @brief EAN13 1D barcode symbology.
    		 */
			EAN13,
    		/**
    		 * @brief EAN8 1D barcode symbology.
    		 */
			EAN8,
    		/**
    		 * @brief UPC12/UPCA 1D barcode symbology.
    		 */
			UPCA,
    		/**
    		 * @brief UPCE 1D barcode symbology.
    		 */
			UPCE,
    		/**
    		 * @brief Code 11 barcode symbology. Only available in the Professional and Enterprise Packages.
    		 */
			CODE11,
    		/**
    		 * @brief Code 128 1D barcode symbology, including GS1-Code128. Only available in the
    		 *    Professional and Enterprise Packages.
    		 */
			CODE128,
    		/**
    		 * @brief Code39 barcode symbology. Only available in the Professional and Enterprise Packages.
    		 */
			CODE39,
    		/**
    		 * @brief Code 93 barcode symbology. Only available in the Professional and Enterprise Packages.
    		 */
			CODE93,
    		/**
    		 * @brief Interleaved-Two-of-Five (ITF) 1D barcode symbology. Only available in the Professional and
    		 * Enterprise Packages.
    		 */
			ITF,
    		/**
    		 * @brief QR Code 2D barcode symbology.
    		 */
			QR,
    		/**
    		 * @brief Datamatrix 2D barcode symbology. Only available in the Professional and Enterprise Packages.
    		 */
			DATA_MATRIX,
    		/**
    		 * @brief PDF417 barcode symbology. Only available in the Professional and Enterprise Packages.
    		 */
			PDF417,
    		/**
    		 *	@brief MicroPDF417 barcode symbology. Only available in the Professional and Enterprise Packages.
    		 */
			MICRO_PDF417,
    		/**
    		 * @brief MSI Plessey 1D barcode symbology. Only available in the Professional and Enterprise Packages.
    		 */
			MSI_PLESSEY,
    		/**
    		 * @brief Databar 1D barcode symbology. Only available in the Professional and Enterprise Packages.
    		 */
			GS1_DATABAR,
    		/**
    		 * @brief Databar Limited 1D barcode symbology. Only available in the Professional and Enterprise Packages.
    		 */
			GS1_DATABAR_LIMITED,
    		/**
    		 * @brief Databar Expanded 1D barcode symbology. Only available in the Professional and Enterprise Packages.
    		 */
			GS1_DATABAR_EXPANDED,
    		/**
    		 * @brief Codabar 1D barcode symbology. Only available in the Professional and Enterprise Packages.
    		 */
			CODABAR,
    		/**
    		 * @brief Aztec 2D barcode symbology. Only available in the Professional and Enterprise Packages.
    		 */
			AZTEC,
    		/**
    		 * @brief DotCode 2D barcode symbology. Only available in the Professional and Enterprise Packages.
    		 */
			DOTCODE,
    		/**
    		 * @brief Maxicode 2D barcode symbology. Only available in the Professional and Enterprise Packages.
    		 */
			MAXICODE,
    		/**
    		 * @brief Five-digit add-on for UPC and EAN codes.
    		 *
    		 * In order to scan five-digit add-on codes, at least one of these symbologies must be activated
    		 * as well: \ref SYMBOLOGY_EAN13, \ref SYMBOLOGY_UPCA, \ref SYMBOLOGY_UPCE, or
    		 * \ref SYMBOLOGY_EAN8 and the maximum number of codes per frame has to be set to at least 2.
    		 *
    		 * Only available in the Professional and Enterprise Packages.
    		 */
			FIVE_DIGIT_ADD_ON,
    		/**
    		 * @brief Two-digit add-on for UPC and EAN codes.
    		 *
    		 * In order to scan two-digit add-on codes, at least one of these symbologies must be activated
    		 * as well: \ref SYMBOLOGY_EAN13, \ref SYMBOLOGY_UPCA, \ref SYMBOLOGY_UPCE, or
    		 * \ref SYMBOLOGY_EAN8 and the maximum number of codes per frame has to be set to at least 2.
    		 *
    		 * Only available in the Professional and Enterprise Packages.
    		 */
			TWO_DIGIT_ADD_ON,
    		/**
    		 * @brief Royal Dutch TPG Post KIX. Only available in Professional and Enterprise Packages.
    		 */
			KIX,
    		/**
    		 * @brief Royal Mail 4 State Customer Code (RM4SCC). Only available in Professional and Enterprise Packages.
    		 */
			RM4SCC
		}

        /**
         * @brief Composite flags for barcodes/2D codes.
         */
        public enum CompositeFlag {
            /**
            * @brief Code is not part of a composite code.
            */
            NONE,
            /**
            * @brief Code could be part of a composite code.
            *
            * This flag is set by linear (1d) symbologies that have no composite flag support
            * but can be part of a composite code like the EAN/UPC symbology family.
            */
            UNKNOWN,
            /**
            * @brief Code is the linear component of a composite code.
            *
            * This flag can be set by GS1 DataBar or GS1-128 (Code 128).
            */
            LINKED,
            /**
            * @brief Code is a GS1 Composite Code Type A (CC-A).
            *
            * This flag can be set by MicroPDF417 codes.
            */
            GS1_TYPE_A,
            /**
             * @brief Code is a GS1 Composite Code Type B (CC-B).
             *
             * This flag can be set by MicroPDF417 codes.
             */
             GS1_TYPE_B,
             /**
             * @brief Code is a GS1 Composite Code Type C (CC-C).
             *
             * This flag can be set by PDF417 codes.
             */
             GS1_TYPE_C,
        }
	}

	/**
	 * @brief The main class for scanning barcodes with the Scandit Barcode Scanner.
	 *<p>
	 * This class sets up the recognition process, the preview view, controls the camera and provides
	 * a callback interface when barcodes are recognized.
	 * <p>
	 *
	 *
	 */
	public class BarcodePicker {

			/**
			*	@brief Prop used to pass the initial scan settings to the picker.
			*/
			public ScanSettings scanSettings;

			/**
			*	@brief Prop used to set the scan callback.
			*/
			public function onScan;

			/**
			*	@brief Prop used to set a callback called after new settings have been applied.
			*/
			public function onSettingsApplied;

			/**
			*	@brief Prop used to set the text recognition callback.
			*/
			public function onTextRecognized;

    	/**
    	 * @brief Reconfigure the barcode picker with new settings
    	 *
    	 * The settings are applied asynchronously. Once they have been applied, all new frames
    	 * will be processed with the new settings.
    	 *
    	 * @param settings the settings to apply.
    	 *
    	 *
    	 */
			 public void applySettings(ScanSettings settings);

    	/**
    	 * @brief Asynchronously pause the scanning process while keeping the camera preview running.
    	 *
    	 * Use this method when you plan to briefly pause the scanning process and intend to
    	 * resume it later. It is not recommended to use this method when you are interrupting the
    	 * scanning process for longer periods of time as the camera preview will use considerable
    	 * power. For such scenarios use {@link stopScanning} instead.
    	 *
    	 *
    	 */
    	public void pauseScanning();

    	/**
    	 * @brief Asynchronously resume a previously paused scanning process
    	 *
    	 * Use this method to resume scanning after {@link ScanSession.pauseScanning} or
    	 * {@link pauseScanning} was called. Calling resume on a picker that was not previously
    	 * started with with startScanning() is undefined.
    	 *
    	 */
    	public void resumeScanning();

    	/**
    	 * @brief Asynchronously stop the scanning process and camera preview
    	 *
    	 */
    	public void stopScanning();

    	/**
    	 * @brief Asynchronously start the camera preview and scanning process
    	 *
    	 */
	    public void startScanning();

			/**
			* Set the camera switch visibility
			*
			* @param visibility the camera switch visibility, see {@link Scandit.BarcodePicker.Constants BarcodePicker.Constants}
			*/
			public void setCameraSwitchVisibility(int visibility);

			/**
			* Set the barcode picker GUI style
			*
			* @param guiStyle the gui style to set, see {@link Scandit.BarcodePicker.Constants BarcodePicker.Constants}
			*
			*/
			public void setGuiStyle(int guiStyle);

			/**
			* Set the matrix scanning highlighting color
			*
			* @param state the state for which you want to change the color, see {@link Scandit.BarcodePicker.Constants BarcodePicker.Constants}
			* @param color the color you want to set, this parameter can be a hex string or a color literal such as 'red' or 'yellow'
			*
			*/
			public void setMatrixScanHighlightingColor(int state, Object color);

			/** @name Sound Configuration
			 *  Customize the scan sound.
			 */
			///@{

			/**
			 * Enables (or disables) the sound when a barcode is recognized. If the phone's ring mode
			 * is set to muted or vibrate, no beep will be played regardless of the value.
			 *
			 * Enabled by default.
			 *
			 *
			 *
			 * @param enabled whether the beep is enabled.
			 */
			public void setBeepEnabled(boolean enabled);

			/**
			 * Enables or disables the vibration when a code was recognized. If the phone's ring mode
			 * is set to muted, no beep will be played regardless of the value.
			 *
			 * Enabled by default.
			 *
			 *
			 *
			 * @param enabled whether vibrate is enabled.
			 */
			public void setVibrateEnabled(boolean enabled);
			///@}

			/** @name Torch Configuration
			 *  Enable and customize appearance of the torch icon.
			 */
			///@{

			/**
			 * Enables or disables the torch toggle button for all devices/cameras that support a torch.
			 *
			 * By default it is enabled. The torch icon is never shown when the camera does not have a
			 * torch (most tablets, front cameras, etc).
			 *
			 *
			 *
			 * @param enabled whether the torch button should be shown.
			 */
			public void setTorchEnabled(boolean enabled);

			/**
			 * @brief Sets the position at which the button to enable the torch is drawn.
			 *
			 * By default the margins are 15 and width and height are 40.
			 *
			 *
			 *
			 * @param leftMargin Left margin in points.
			 * @param topMargin Top margin in points.
			 * @param width Width in points.
			 * @param height Height in points.
			 */
			public void setTorchButtonMarginsAndSize(int leftMargin, int topMargin, int width, int height);

			/** @name Viewfinder Configuration
			 *  Customize the viewfinder where the barcode location is highlighted.
			 */
			///@{

			/**
			 * Sets the size of the viewfinder relative to the size of the BarcodePicker's size.
			 *
			 * Changing this value does not(!) affect the area in which barcodes are successfully
			 * recognized. It only changes the size of the box drawn onto the scan screen.
			 *
			 * By default the width is 0.8, height is 0.4, landscapeWidth is 0.6, landscapeHeight is 0.4
			 *
			 * @param width Width of the viewfinder rectangle in portrait orientation.
			 * @param height Height of the viewfinder rectangle in portrait orientation.
			 * @param landscapeWidth Width of the viewfinder rectangle in landscape orientation.
			 * @param landscapeHeight Height of the viewfinder rectangle in landscape orientation.
			 */
			public void setViewfinderDimension(
					number width, number height, number landscapeWidth, number landscapeHeight);

			/**
			 * Sets the color of the viewfinder before a bar code has been recognized
			 * <p>
			 * Note: This feature is only available with the Scandit SDK Enterprise Packages.
			 *
			 * By default the color is white
			 *
			 * @param color the color to set, this parameter can be a hex string or a color literal such as 'red' or 'yellow'
			 */
			public void setViewfinderColor(Object color);

			/**
			 * Sets the color of the viewfinder once the bar code has been recognized.
			 * <p>
			 * Note: This feature is only available with the Scandit SDK Enterprise Packages.
			 *
			 * By default the color is light blue
			 *
			 * @param color the color to set, this parameter can be a hex string or a color literal such as 'red' or 'yellow'
			 */
			public void setViewfinderDecodedColor(Object color);
			///@}

			/** @name Non-Official Methods
			 */
			///@{
			/**
			 * Set custom overlay property
			 *
			 * This function is for internal use/and or experimental features and any functionality that
			 * can be accessed through it can and will vanish without public notice from one version to
			 * the next. Do not use this method unless you specifically have to.
			 *
			 *
			 *
			 * @param key The name of the property
			 * @param value the value for the property.
			 */
			public void setOverlayProperty(String key, Object value);

	        /**
	         * Sets whether the button to switch between different recognition modes should be visible.
	         * If The scanner only supports one recognition mode the button is never shown.
	         *
	         *
	         *
	         * @param visible whether the switch should be visible.
	         */
	        public void setTextRecognitionSwitchVisible(boolean visible);
			///@}

			/**
			* @brief Object containing all constants necessary for setting up the picker.
			*/
			public class Constants {

				/**
				 * Used with {@link BarcodePicker.setCameraSwitchVisibility() Scandit.BarcodePicker.setCameraSwitchVisibility()} method.
				 * The camera switch button is always hidden.
				 */
				public int CAMERA_SWITCH_NEVER;
				/**
				 * Used with {@link BarcodePicker.setCameraSwitchVisibility() Scandit.BarcodePicker.setCameraSwitchVisibility()} method.
				 * The camera switch button is shown on tablet devices with front and back cameras.
				 */
				public int CAMERA_SWITCH_ON_TABLET;

				/**
				 * Used with {@link BarcodePicker.setCameraSwitchVisibility() Scandit.BarcodePicker.setCameraSwitchVisibility()} method.
				 * The camera switch button is shown on all devices that have front and back cameras.
				 */
				public int CAMERA_SWITCH_ALWAYS;

				/**
				 * Used with {@link BarcodePicker.setGuiStyle() Scandit.BarcodePicker.setGuiStyle()} method.
		     * A rectangular viewfinder with rounded corners is shown in the specified size. Recognized
		     * codes are marked with four corners.
		     */
				public int GUI_STYLE_DEFAULT;

		    /**
				 * Used with {@link BarcodePicker.setGuiStyle() Scandit.BarcodePicker.setGuiStyle()} method.
		     * A laser line is shown with the specified width while the height is not changeable. This mode
		     * should generally not be used if the recognition is running on the whole screen as it
		     * indicates that the code should be placed at the location of the laser line.
		     */
				public int GUI_STYLE_LASER;

		    /**
				 * Used with {@link BarcodePicker.setGuiStyle() Scandit.BarcodePicker.setGuiStyle()} method.
		     * No UI is shown to indicate where the barcode should be placed. Be aware that the Scandit
		     * logo continues to be displayed as showing it is part of the license agreement.
		     */
				public int GUI_STYLE_NONE;

				/**
				 * Used with {@link BarcodePicker.setGuiStyle() Scandit.BarcodePicker.setGuiStyle()} method.
         * Like {@link BarcodePicker.Constants.KEY_GUI_STYLE_NONE Scandit.BarcodePicker.Constants.KEY_GUI_STYLE_NONE}, but
         * barcode locations are highlighted in the UI.
         */
				public int GUI_STYLE_LOCATIONS_ONLY;

				/**
				 * Used with {@link BarcodePicker.setGuiStyle() Scandit.BarcodePicker.setGuiStyle()} method.
				 *	The MatrixScan UI is shown.
				 * In order to use this UI, it is required to set ScanSettings.matrixScanEnabled to true.
				 * Only available on Android version 11 and up.
				 */
				public int GUI_STYLE_MATRIX_SCAN;

				 /**
 				 * Used with {@link BarcodePicker.setMatrixScanHighlightingColor() Scandit.BarcodePicker.setMatrixScanHighlightingColor()} method.
				 * State for tracked barcodes that have been localized, but not recognized yet.
				 */
				public int MATRIX_SCAN_STATE_LOCALIZED;

				 /**
 				 * Used with {@link BarcodePicker.setMatrixScanHighlightingColor() Scandit.BarcodePicker.setMatrixScanHighlightingColor()} method.
				 * State for tracked barcodes that have been recognized.
				 */
				public int MATRIX_SCAN_STATE_RECOGNIZED;

				 /**
 				 * Used with {@link BarcodePicker.setMatrixScanHighlightingColor() Scandit.BarcodePicker.setMatrixScanHighlightingColor()} method.
				 * State for tracked barcodes that have been rejected with {@link ScanSession.rejectCode() Scandit.ScanSession.rejectCode()}.
				 */
				public int MATRIX_SCAN_STATE_REJECTED;
		}
	}


    /**
     * @brief Settings to configure the decoding process
     */
	public class ScanSettings {

		/**
		 * Possible recognition modes.
		 *
		 *
		 */
		public enum RecognitionMode {
			/**
			 * Text recognition
			 */
			TEXT,
			/**
			 * Barcode/2d code recognition
			 */
			CODE
		}

		/**
		 * Possible working ranges for the barcode picker
		 *
		 *
		 */
		public enum WorkingRange {
			/**
			 * The camera tries to focus on barcodes which are close to the camera. To scan far-
			 * away codes (30-40cm+), user must tap the screen. This is the default working range
			 * and works best for most use-cases. Only change the default value if you expect the
			 * users to often scan codes which are far away.
			 */
			STANDARD,
			/**
			 * The camera tries to focus on barcodes which are far from the camera. This will make
			 * it easier to scan codes that are far away but degrade performance for very close
			 * codes.
			 */
			LONG
		}

    	/**
    	 * Camera facing direction.
    	 */
		public enum CameraFacing {
			/**
			 * Facing away from the user.
			 */
			BACK,
			/**
			 * Facing towards the user (Facetime camera).
			 */
			FRONT
		}

  	    /** @name Working Range/Focus Control
  	     *
  	     */
  	    ///@{

	    /**
     	 * The working range tells the engine at which distance barcodes are to be
    	 * expected. When set to WorkingRange.WORKING_RANGE_STANDARD (the default), the focus is optimized
    	 * for barcodes close to the camera. When set to WorkingRange.WORKING_RANGE_LONG, the focus is
    	 * optimized for far-away codes.
    	 * <p>
    	 * When using non-standard working range, it is better to directly pass the
    	 * working range when constructing the barcode picker, because the camera
    	 * can already start to adjust the focus at an earlier point in time.
    	 * <p>
    	 * The working range hint is ignored on cameras with fixed-focus.
    	 */
    	public int workingRange;

   		///@}


    	/** @name Scan Session Configuration
    	 * @{
    	 */
    	/**
    	 * The duration (in milliseconds) for which barcodes are kept in the
    	 * session.
    	 * When set to values larger than zero, the value is
    	 * interpreted as milliseconds for which the barcodes should be kept in
    	 * the session. If set to zero, barcodes are discarded before processing
    	 * the next frame. Passing -1 will keep the codes for the duration of
    	 * the scan session.
    	 *
    	 * The default value is -1
    	 */
   		public int codeCachingDuration;

    	/**
    	 * The duration of the duplicate filter in milliseconds.
    	 * When set to values larger than zero, barcodes with the same symbology
    	 * and data are filtered out if they are decoded less than the specified
    	 * milliseconds apart. Set this value to zero, if you do not want to
    	 * filter duplicates. When set to -1, barcodes are filtered as duplicates
    	 * if they match an already decoded barcode in the session.
    	 *
    	 * By default, the duplicate filter is set to 500ms
    	 */
    	public int codeDuplicateFilter;

    	/**
    	 * @}
    	 */

 	    /**
      	 * The recognition mode to use for the barcode picker.
         * <p>
         * Use this function to programmatically switch between text and barcode recognition.
         * By default, barcode recognition is on RecognitionMode.CODE
     	 */
     	public RecognitionMode recognitionMode;

    	/**
    	 * High density mode enables phones to work at higher camera resolution,
    	 * provided they support it. When enabled, phones that are able to run the
    	 * video preview at 1080p (1920x1080) will use 1080p and not just 720p
    	 * (1280x720). High density mode gives better decode ranges at the
    	 * expense of processing speed and allows to decode smaller code in the near
    	 * range, or codes that further away.
    	 * <p>
    	 * By default, high density mode is disabled.
    	 */
    	public boolean highDensityModeEnabled;

    	/**
    	 * Sets the device name to identify the current device when looking at analytics tools. Sends a request to
    	 * the server to set this as soon as a connection is available.
    	 */
    	public String deviceName;

    	/**
    	 * Hash containing the symbology settings for each available symbology.
    	 */
    	public HashMap<Barcode.Symbology, SymbologySettings> symbologies;

    	/**
    	 * @brief Retrieve symbology specific-settings
    	 *
    	 * @param symbology the symbology settings to retrieve
    	 * @return the symbology settings object, or null, if symbology is an invalid symbology.
    	 *
    	 *
    	 */
    	public SymbologySettings getSymbologySettings(Barcode.Symbology symbology);

    	/**
    	 * @brief Enable/disable decoding of a certain symbology.
    	 *
    	 * This function provides a convenient shortcut to enabling/disabling decoding of a
    	 * particular symbology without having to go through SymbologySettings.
    	 *
    	 * Some 1d barcode symbologies allow you to encode variable-length data. By default, the
    	 * Scandit BarcodeScanner SDK only scans barcodes in a certain length range. If your
    	 * application requires scanning of one of these symbologies, and the length is falling
    	 * outside the default range, you may need to adjust the \ref SymbologySettings.activeSymbolCounts
    	 * for this symbology. For details on defaults and how to calculate the symbol counts for
    	 * each symbology, take a look at <a href="../react-native/react-native-active-symbols-counts.html">the barcode length page</a>.
    	 *
    	 * @param symbology The symbology to be enabled.
    	 * @param enabled true when decoding of the symbology should be enabled, false if not.
    	 *
    	 *
    	 */
    	public void setSymbologyEnabled(int symbology, boolean enabled);

    	/**
    	 * The picker first gives preference to cameras of the specified direction. When
    	 * the device has no such camera, cameras of the opposite face are tried as
    	 * well.
    	 * <p>
    	 * By default, the back-facing camera is preferred.
    	 *
    	 *
    	 */
    	public CameraFacing cameraFacingPreference;

    	/**
    	 * The percentage of the max zoom (between 0 and 1).
    	 */
    	public number relativeZoom;

    	/**
    	 * Maximum number of codes to be decoded every frame.
    	 * The value is set to 1 if a negative value is supplied.
    	 */
		public int maxNumberOfCodesPerFrame;

        /**
         * @brief Whether code rejection is enabled
         *
         * Property indicating whether code rejection is enabled. This feature
         * allows you to implement custom integrity checks for recognized barcodes by
         * rejecting codes that don't satisfy additional criteria. By default, code
         * rejection is disabled.
         *
         * @see {@link ScanSession.rejectCode(Barcode)}
         *
         *
         */
    	public boolean codeRejectionEnabled;
    }

    /**
     * @brief Holds settings specific to a particular symbology (1d, 2d)
     */
    public class SymbologySettings {

    	/**
    	 * Checksums for the symbology.
    	 */
    	public enum Checksum {
    		/**
			 * Modulo 10 checksum.
			 */
    		MOD_10,
    		/**
			 * Modulo 11 checksum.
			 */
    		MOD_11,
    		/**
			 * Modulo 47 checksum.
			 */
    		MOD_47,
    		/**
			 * Modulo 43 checksum.
			 */
    		MOD_43,
    		/**
			 * Modulo 103 checksum.
			 */
    		MOD_103,
    		/**
			 * Two modulo 10 checksums.
			 */
    		MOD_1010,
    		/**
			 * A modulo 11 and a modulo 10 checksum.
			 */
    		MOD_1110,
		}

		/**
		 * Extensions for the symbology.
		 */
		public enum Extension {
			/**
			 * Interprets the Code39 code data using two symbols per output character to encode all ASCII characters.
			 */
			FULL_ASCII,
			/**
			 * Removes the leading zero digit from the result.
			 */
			REMOVE_LEADING_ZERO,
			/**
			 * Enables scanning codes that have quiet zones (white area before and after the code) that are significantly 
			 * smaller than allowed by the symbology specification. Use this extension if you are having difficulties to 
			 * scan codes due to quiet zone violations. However, enabling it may come at the cost of more false positives 
			 * under certain circumstances.
			 */
			RELAXED_SHARP_QUIET_ZONE_CHECK,
			/**
			 * Transforms the UPCE result into its UPCA representation.
			 */
	    RETURN_AS_UPCA,
			/**
			 * Removes the leading zero digit from the result if the UPCA representation extension 'return_as_upca' is enabled.
			 */
	    REMOVE_LEADING_UPCA_ZERO,
			/**
			 * Removes the leading FNC1 character that indicates a GS1 code.
			 */
	    STRIP_LEADING_FNC1
		}


    	/**
    	 * Enables/disables decoding of dark codes on bright background only. If color-
    	 * inverted (bright on dark) codes for this symbology are required, enable them through the
    	 * colorInvertedEnabled property. By default decoding of all symbologies is disabled.
    	 *
    	 * It is advised to only enable symbologies that are required by the application as every enabled
    	 * symbology adds processing overhead.
    	 *
    	 *
    	 */
    	public boolean enabled;

    	/**
    	 * Enables/disables decoding of bright codes on dark background only. By default
    	 * color-inverted decoding of all symbologies is disabled.
    	 *
    	 * It is advised to only enable symbologies that are required by the application as every enabled
    	 * symbology adds processing overhead.
    	 *
    	 *
    	 */
    	public boolean colorInvertedEnabled;

    	/**
    	 * An array of all active custom extensions for the symbology.
    	 *
    	 * Extensions are custom features that are only supported by a small number of
    	 * symbologies. For a list of supported extensions, consult the constants of this class.
    	 *
    	 *
    	 */
    	public Extension[] extensions;

    	/**
    	 * Array of additional checksums for this symbology. When a barcode has been
    	 * decoded, the checksums contained are evaluated in addition to any mandatory checksum defined by
    	 * the symbology specification. If any of the checksums matches, the code is returned as
    	 * recognized, otherwise it is discarded.
    	 *
    	 *
    	 */
    	public Checksum[] checksums;

    	/**
    	 * Array containing the length of barcodes to be decoded for this symbology. Change
    	 * this property to enable decoding of long codes which can not be decoded with the default
    	 * settings, or to optimize decoder performance for codes of certain lengths. This is useful when
    	 * it is known that the application only requires scanning of particular barcode lengths.
    	 *
    	 * The mapping from characters to symbols is symbology-specific. For some symbologies, the start
    	 * and end characters are included, others include checksums characters in the symbol counts.
    	 *
    	 * The active symbol count setting is ignored for fixed-size barcodes (the EAN and UPC family of
    	 * codes) as well as 2d codes. For other symbologies, see <a href="../c_api/symbologies.html">
    	 * Calculating symbol counts for variable-length symbologies</a>.
    	 *
    	 *
    	 */
    	public int[] activeSymbolCounts;
    }


	/**
	 * The scan session holds all barcodes that were decoded in the current
	 * session. These codes are available as {@link allRecognizedCodes}.
	 *
	 * <h2>Configuring Session Behaviour</h2>
	 *
	 * The scan session is responsible for determining the list of "relevant" barcodes
	 * by filtering out duplicates. Depending on your app, different duplicate removal
	 * is required. For some applications, only one barcode is required. The scanning
	 * process is stopped as soon as one code is decoded. For other applications,
	 * multiple codes are scanned after another. For example, a scanner at the cash
	 * desk may need to scan multiple products. To avoid duplicates, the same barcode
	 * should not be scanned in short succession. The same barcode (data, symbology)
	 * should not count as a duplicate if encountered again after a few seconds.
	 * <p>
	 * By default, if a barcode has the same symbology and data as code that was
	 * decoded less than 500ms ago, it is filtered out as a duplicate. The exact
	 * filtering behaviour can be changed by setting the "code duplicate filter", see
	 * {@link ScanSettings.codeDuplicateFilter} for details.
	 *
	 * <h2>Session Lifetime</h2>
	 *
	 * The session is cleared when either {@link BarcodePicker.startScanning} or ,
	 * {@link BarcodePicker.stopScanning} is called.
	 *
	 * @see ScanSettings.codeCachingDuration
	 * @see ScanSettings.codeDuplicateFilter
	 *
	 *
	 *
	 */
	public class ScanSession {
		/**
		 * List of barcodes that have been successfully recognized in the last frame.
		 *
		 *
		 */
		public Barcode[] newlyRecognizedCodes;

		/**
		 * List of barcodes that have been localized in the last frame. This list does not include
		 * barcodes that have been successfully recognized.
		 *
		 *
		 */
		public Barcode[] newlyLocalizedCodes;

		/**
		 * @brief Returns the list of barcodes (data, symbology) that have been recognized
		 *     in this session.
		 *
		 * Depending on the code caching and duplicate filtering behaviour, different
		 * sets of codes are returned by this method.
		 *
		 * @see ScanSettings.codeCachingDuration
		 * @see ScanSettings.codeDuplicateFilter
		 *
		 * @return a new copy of the list of barcodes that have been successfully
		 *    decoded in this session
		 *
		 *
		 */
		public Barcode[] allRecognizedCodes;

		/**
		 * @brief Immediately Pauses barcode recognition, but keeps camera preview open.
		 *
		 * This is useful for briefly pausing the barcode recognition to show the
		 * recognized code in an overlay and then resume the scan process to scan
		 * more codes.
		 * <p>
		 * When only scanning one code and then returning to another part of the
		 * application, it is recommended to call {@link stopScanning()} instead.
		 * <p>
		 *
		 * @see BarcodePicker.resumeScanning()
		 *
		 *
		 */
		public void pauseScanning();

		/**
		 * Immediately stops the scanning and clears the scan session
		 * <p>
		 * Calling stop will release the camera, so that other applications can use
		 * it.
		 *
		 * @see BarcodePicker.stopScanning(), pauseScanning()
		 *
		 *
		 */
		public void stopScanning();

        /**
         * @brief Prevent beeping/vibrate and highlighting for a particular code.
         *
         * Use this method to reject a certain code if you have additional methods for verifying
         * the integrity of the code, e.g. with a custom checksum. Rejected
         * codes won't be highlighted in the scan UI. Additionally beep and vibration
         * will be surpressed.
         *
         * For code rejection to work, you must enabled it by setting
         * {@link ScanSettings.codeRejectionEnabled code rejection} to true.
         *
         * Rejected codes will be added to {@link allRecognizedCodes} like all other codes.
         *
         * Note that you should only pass codes returned by {@link newlyRecognizedCodes}
         * as passing any other code will have no effect. Additionally, you should only
         * calls this method from the scan callback.
         *
         * @param code The code to reject
         *
         *
         */
        public void rejectCode(Barcode code);
    }
}
