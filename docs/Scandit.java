/**
* Module for all functionality of the barcode recognition plugin
*/
public class Scandit {

  /**
  * @brief A convenience class for rectangles.
  */
  public class Rect {

    /**
    * @param x The x coordinate.
    * @param y The y coordinate.
    * @param width The rectangle's width.
    * @param height The rectangle's height.
    */
    public Rect(Number x, Number y, Number width, Number height);

  }

  /**
  * @brief A convenience class for quadrilaterals.
  */
  public class Quadrilateral {

    /**
    * @param topLeft The top left corner of the quadrilateral.
    * @param topRight The top right corner of the quadrilateral.
    * @param bottomLeft The bottom left corner of the quadrilateral.
    * @param bottomRight The top right corner of the quadrilateral.
    */
    public Quadrilateral(Point topLeft, Point topRight, Point bottomLeft, Point bottomRight);

  }

  /**
  * @brief A convenience class for points with an x and y coordinate.
  */
  public class Point {

    /**
    * @param x The x coordinate.
    * @param y The y coordinate.
    */
    public Point(Number x, Number y);

  }

  /**
  * @brief Represents a recognized/localized barcode/2D code.
  *
  * The Barcode class represents a  barcode, or 2D code that has been localized or recognized
  * by the barcode recognition engine.
  */
  public class Barcode {

    /**
    *  @brief The data contained in the barcode/2D code, for example the 13 digit number of an EAN13 code.
    *
    * For some types of barcodes/2D codes (for example DATAMATRIX, AZTEC, PDF417), the data string may contain non-printable characters and nul-bytes in the middle of the string. Use rawData if your application scans these types of codes and you are expecting binary/non-printable data.
    */
    public String data;

    /**
    * @brief The data contained in the barcode/2D code, for example the 13 digit number of an EAN13 code.
    *
    * The data is stored as an array of integers, where each item in the array corresponds to the char code. Use this property instead of data if your application expects to scan codes containing binary data that can not be represented as UTF-8 strings.
    */
    public int[] rawData;

    /**
    * @brief the symbology of a recognized barcode.
    */
    public Symbology symbology;

    /**
    * @brief The composite flag of the barcode.
    *
    * For codes that have been localized but not recognized, CompositeFlag.UNKNOWN is returned.
    */
    public CompositeFlag compositeFlag;

    /**
    * @brief Specifies if the barcode has been recognized or not.
    */
    public boolean isRecognized;

    /**
    * @brief The location of the barcode in the frame.
    */
    public Quadrilateral location;

    /**
    * @brief The predicted location of the barcode in the frame. (Only available for tracked codes if MatrixScan is enabled.)
    */
    public Quadrilateral predictedLocation;

    /**
    * @brief The converted predicted location of the barcode in the coordinate system of the picker. (Only available for tracked codes if MatrixScan is enabled.)
    */
    public Quadrilateral convertedPredictedLocation;

    /**
    * @brief The converted location of the barcode in the coordinate system of the picker. (Only available for tracked codes if MatrixScan is enabled.)
    */
    public Quadrilateral convertedLocation;

    /**
    * @brief If the barcode should animate to the next state, used for visualizing tracked codes. (Only available for tracked codes if MatrixScan is enabled.)
    */
    public boolean shouldAnimateFromPreviousToNextState;

    /**
    * @brief The predicted time until the predicted location, used for visualizing tracked codes. (Only available for tracked codes if MatrixScan is enabled.)
    */
    public boolean deltaTimeForPrediction;

    // Hack to force doxygen to generate a documentation page for this class.
    public void noop();

    /**
    * @brief An enumeration of all supported barcode symbologies
    */
    public enum Symbology {

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
      * @brief Code 25 barcode symbology. Only available in the Professional and Enterprise Packages.
      */
      CODE25,

      /**
      * @brief Code39 barcode symbology. Only available in the Professional and Enterprise Packages.
      */
      CODE39,

      /**
      * @brief Code 93 barcode symbology. Only available in the Professional and Enterprise Packages.
      */
      CODE93,

      /**
      * @brief Code 128 1D barcode symbology, including GS1-Code128. Only available in the
      *    Professional and Enterprise Packages.
      */
      CODE128,

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
        *  @brief MicroPDF417 barcode symbology. Only available in the Professional and Enterprise Packages.
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
      * @brief Micro QR 2D barcode symbology. Only available in the Professional and Enterprise Packages.
      */
      MICROQR,

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
      RM4SCC,

      /**
      * @brief Code 32 barcode symbology. Only available in Professional and Enterprise Packages.
      */
      CODE32,

      /**
      * @brief LAPA 4 State Code. Only available in Professional and Enterprise Packages.
      */
      LAPA4SC
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
  * <p>
  * This class sets up the recognition process, the preview view, controls the camera and provides
  * a callback interface when barcodes are recognized.
  * <p>
  */
  public class BarcodePicker {

    /**
    *  @brief Prop used to pass the initial scan settings to the picker.
    */
    public ScanSettings scanSettings;

    /**
     *  @brief Prop used to inform the picker whether it should make the frame data available
     *  via the {@link BarcodePicker.onBarcodeFrameAvailable} callback.
     */
    public boolean shouldPassBarcodeFrame;

    /**
    *  @brief Prop used to set the scan callback.
    */
    public function onScan;

    /**
    * @brief Prop used to set the matrix scan callback.
    */
    public function onRecognizeNewCodes;
  
    /**
    * @brief Prop used to set the matrix scan callback that's called every frame to get continuous updates about tracked codes.
    */
    public function onChangeTrackedCodes;

    /**
    *  @brief Prop used to set a callback called after new settings have been applied.
    */
    public function onSettingsApplied;

    /**
     *  @brief Prop used to set a callback that provides frame data in base64 string format.
     *
     *  The onBarcodeFrameAvailable method is executed after each frame is processed by the picker,
     *  but only if new codes have been recognized in that particular frame.
     *  Note that the callback will only be invoked
     *  if the {@link BarcodePicker.shouldPassBarcodeFrame} property is set to true.
     */
    public function onBarcodeFrameAvailable;

    /**
     *  @brief Prop used to set the text recognition callback.
     */
    public function onTextRecognized;

    /**
     *  @brief Prop used to set the warnings callback.
     *
     *  The onWarnings method will be executed after every time a warning is raised
     *  by the frame processing engine.
     */
    public function onWarnings;

    /**
     *  @brief Prop used to set the property changed callback.
     *
     *  The onPropertyChanged method is executed whenever the state of the barcode picker changes.
     *  Events that can invoke this method include changes to the torch, camera facing, recognition
     *  mode and relative zoom.
     *  The onPropertyChanged should accept two arguments.
     *  The first one is a string. It defines which property of the BarcodePicker has been changed
     *  and its value is one of {@link ChangedProperty} enum values.
     *  The second one is an int that represents the new state of the property.
     */
    public function onPropertyChanged;

    /**
    * @brief Reconfigure the barcode picker with new settings
    *
    * The settings are applied asynchronously. Once they have been applied, all new frames
    * will be processed with the new settings.
    *
    * @param settings the settings to apply.
    */
    public void applySettings(ScanSettings settings);

    /**
    * @brief Asynchronously pause the scanning process while keeping the camera preview running.
    *
    * Use this method when you plan to briefly pause the scanning process and intend to
    * resume it later. It is not recommended to use this method when you are interrupting the
    * scanning process for longer periods of time as the camera preview will use considerable
    * power. For such scenarios use {@link ScanSession.stopScanning} instead.
    */
    public void pauseScanning();

    /**
    * @brief Asynchronously resume a previously paused scanning process
    *
    * Use this method to resume scanning after {@link ScanSession.pauseScanning} or
    * {@link pauseScanning} was called. Calling resume on a picker that was not previously
    * started with startScanning() or startScanningInPausedState() is not possible.
    */
    public void resumeScanning();

    /**
    * @brief Asynchronously stop the scanning process and camera preview
    */
    public void stopScanning();

    /**
    * @brief Asynchronously start the camera preview and scanning process
    */
    public void startScanning();

    /**
    * @brief Asynchronously start the camera preview and initialize the scanning process in a paused state
    */
    public void startScanningInPausedState();

    /**
    * Set the barcode picker GUI style
    *
    * @param guiStyle the gui style to set, see {@link Scandit.ScanOverlay.GuiStyle ScanOverlay.GuiStyle}
    *
    */
    public void setGuiStyle(int guiStyle);

    /**
    * Set the matrix scanning highlighting color
    *
    * @param state the state for which you want to change the color, see {\link ScanOverlay.MatrixScanState Scandit.ScanOverlay.MatrixScanState}
    * @param color the color you want to set, this parameter can be a hex string or a color literal such as 'red' or 'yellow'
    *
    */
    public void setMatrixScanHighlightingColor(int state, Object color);
    
    /**
    * Switches the torch (if available) on or off programmatically
    * This method should only be called after scanning has been started. Otherwise, it will have no effect.
    * There is also a {@link BarcodePicker.setTorchEnabled method} to add a torch icon that the user can click to activate the torch.
    *
    * @param on true when the torch should be switched on, false if the torch should be turned off.
    *
    */
    public void switchTorchOn(boolean on);

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
    * @param enabled whether the beep is enabled.
    */
    public void setBeepEnabled(boolean enabled);

    /**
    * Enables or disables the vibration when a code was recognized. If the phone's ring mode
    * is set to muted, no beep will be played regardless of the value.
    *
    * Enabled by default.
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
     * \since 5.7.0
     *
     * \param enabled Whether the torch button should be shown.
     */
    public void setTorchEnabled(boolean enabled);

    /**
     * \brief Sets the position at which the button to enable the torch is drawn.
     *
     * By default the margins are 15 and width and height are 40.
     *
     * \since 5.7.0
     *
     * \param leftMargin Left margin in points.
     * \param topMargin Top margin in points.
     * \param width Width in points.
     * \param height Height in points.
     */
    public void setTorchButtonMarginsAndSize(int leftMargin, int topMargin, int width, int height);
    ///@}

    /** @name Camera Switch Configuration
     *  Enable camera switch and set icons
     */
    ///@{

    /**
     * Sets when the camera switch button is visible for devices that have more than one camera.
     *
     * By default it is {\link ScanOverlay.CameraSwitchVisibility.NEVER Scandit.ScanOverlay.CameraSwitchVisibility.NEVER}.
     *
     * \since 5.7.0
     *
     * \param visibility The visibility of the camera switch button
     *                   ({\link ScanOverlay.CameraSwitchVisibility.NEVER Scandit.ScanOverlay.CameraSwitchVisibility.NEVER},
     *                   {\link ScanOverlay.CameraSwitchVisibility.ON_TABLET Scandit.ScanOverlay.CameraSwitchVisibility.ON_TABLET},
     *                   {\link ScanOverlay.CameraSwitchVisibility.ALWAYS Scandit.ScanOverlay.CameraSwitchVisibility.ALWAYS})
     */
    public void setCameraSwitchVisibility(CameraSwitchVisibility visibility);

    /**
     * \brief Sets the position at which the button to switch the camera is drawn.
     *
     * By default the margins are 15 and width and height are 40.
     *
     * \since 5.7.0
     *
     * \param rightMargin Right margin in dp.
     * \param topMargin Top margin in dp.
     * \param width Width in dp.
     * \param height Height in dp.
     */
    public void setCameraSwitchMarginsAndSize(int rightMargin, int topMargin, int width, int height);
    ///@}

    /** @name Text Recognition Switch Configuration
     *  Sets whether the button to switch between different recognition modes should be visible.
     */
    ///@{

    /**
     * Sets whether the button to switch between different recognition modes should be visible. If
     * the scanner only supports one recognition mode the button is never shown.
     *
     * @param isVisible Whether the button should be visible.
     *
     * @since 5.7.0
     */
    public void setTextRecognitionSwitchVisible(boolean isVisible);
    ///@}

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
    public void setViewfinderDimension(number width, number height, number landscapeWidth, number landscapeHeight);

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
    * @param key The name of the property
    * @param value the value for the property.
    */
    public void setOverlayProperty(String key, Object value);
    ///@}

    /**
     * @brief Warnings that can be raised by the frame processing engine.
     */
    public enum Warning {

      /**
       * @brief Too much glare warning.
       */
      TOO_MUCH_GLARE_WARNING,
      /**
       * @brief Not enough contrast warning.
       */
      NOT_ENOUGH_CONTRAST_WARNING
    }

    public enum ChangedProperty {

      /**
       * @brief The torch has changed its state.
       *
       * New state will be either 0 (torch is unavailable) or 1 (torch is off) or 2 (torch is on).
       */
      TORCH,
      /**
       * @brief The camera has been switched from front to back-facing, or from back to front-facing.
       */
      SWITCH_CAMERA,
      /**
       * @brief The recognition mode has been changed.
       */
      RECOGNITION_MODE,
      /**
       * @brief The relative zoom factor times 1000.
       */
      RELATIVE_ZOOM
    }
  }

  /**
  * @brief Abstract scan UI class.
  *
  * The ScanOverlay implements the scan UI displayed on top of the video feed. It is responsible for highlighting barcodes and draw the viewfinder rectangle or laser UI.
  */
  public class ScanOverlay {

    // Hack to force doxygen to generate a documentation page for this class.
    public void noop();

    /**
    * @brief Scan UI style.
    */
    public enum GuiStyle {

      /**
      * Used with {@link BarcodePicker.setGuiStyle() Scandit.BarcodePicker.setGuiStyle()} method.
      * A rectangular viewfinder with rounded corners is shown in the specified size. Recognized
      * codes are marked with four corners.
      */
      GUI_STYLE_DEFAULT,

      /**
      * Used with {@link BarcodePicker.setGuiStyle() Scandit.BarcodePicker.setGuiStyle()} method.
      * A laser line is shown with the specified width while the height is not changeable. This mode
      * should generally not be used if the recognition is running on the whole screen as it
      * indicates that the code should be placed at the location of the laser line.
      */
      GUI_STYLE_LASER,

      /**
      * Used with {@link BarcodePicker.setGuiStyle() Scandit.BarcodePicker.setGuiStyle()} method.
      * No UI is shown to indicate where the barcode should be placed. Be aware that the Scandit
      * logo continues to be displayed as showing it is part of the license agreement.
      */
      GUI_STYLE_NONE,

      /**
      * Used with {@link BarcodePicker.setGuiStyle() Scandit.BarcodePicker.setGuiStyle()} method.
      * Like {@link ScanOverlay.GuiStyle.GUI_STYLE_NONE Scandit.ScanOverlay.GuiStyle.GUI_STYLE_NONE}, but
      * barcode locations are highlighted in the UI.
      */
      GUI_STYLE_LOCATIONS_ONLY,

      /**
      * Used with {@link BarcodePicker.setGuiStyle() Scandit.BarcodePicker.setGuiStyle()} method.
      * The MatrixScan UI is shown.
      * In order to use this UI, it is required to set {@link ScanSettings.matrixScanEnabled} to true.
      * Only available on Android version 11 and up.
      */
      GUI_STYLE_MATRIX_SCAN
    }

    /**
    * @brief Camera Switch Button Visibility.
    */
    public enum CameraSwitchVisibility {

      /**
      * @brief The camera switch button is always hidden.
      */
      NEVER,

      /**
      * @brief The camera switch button is shown on tablet devices with front and back cameras.
      */
      ON_TABLET,

      /**
      * @brief The camera switch button is shown on all devices that have front and back cameras.
      */
      ALWAYS
    }

    /**
     * @brief Matrix scan code states.
     */
    public enum MatrixScanState {

      /**
      * Used with {@link BarcodePicker.setMatrixScanHighlightingColor() Scandit.BarcodePicker.setMatrixScanHighlightingColor()} method.
      * State for tracked barcodes that have been localized, but not recognized yet.
      */
      MATRIX_SCAN_STATE_LOCALIZED,

      /**
      * Used with {@link BarcodePicker.setMatrixScanHighlightingColor() Scandit.BarcodePicker.setMatrixScanHighlightingColor()} method.
      * State for tracked barcodes that have been recognized.
      */
      MATRIX_SCAN_STATE_RECOGNIZED,

      /**
      * Used with {@link BarcodePicker.setMatrixScanHighlightingColor() Scandit.BarcodePicker.setMatrixScanHighlightingColor()} method.
      * State for tracked barcodes that have been rejected with {@link ScanSession.rejectCode() Scandit.ScanSession.rejectCode()}.
      */
      MATRIX_SCAN_STATE_REJECTED
    }
  }

  /**
  * @brief Settings to configure the decoding process
  */
  public class ScanSettings {

    /**
    * Possible recognition modes.
    */
    public enum RecognitionMode {

      /**
      * Text recognition.
      */
      TEXT,
      /**
      * Barcode/2d code recognition.
      */
      CODE,
      /**
      * Barcode/2d code recognition and text recognition.
      */
      CODE_AND_TEXT
    }

    /**
    * Possible working ranges for the barcode picker
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
    */
    public SymbologySettings getSymbologySettings(Scandit.Barcode.Symbology symbology);

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
    */
    public void setSymbologyEnabled(int symbology, boolean enabled);

    /**
    * The picker first gives preference to cameras of the specified direction. When
    * the device has no such camera, cameras of the opposite face are tried as
    * well.
    * <p>
    * By default, the back-facing camera is preferred.
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
    */
    public boolean codeRejectionEnabled;

    /**
    * @brief Sets the active scan area for portrait mode scanning.
    *
    * By default, the barcode recognition engine searches the whole image for barcodes. Use this method to define the area in which barcodes are to be searched.
    * <p>
    * Rectangle coordinates run from 0 to 1. The coordinates are relative to the whole camera preview (even if the BarcodePicker view is cropped).
    * Invoking this method with invalid rectangles, e.g. rectangles whose top, left, right, or bottom attributes are outside the allowed range of 0.0-1.0, or rectangles with negative width/height will have no effect.
    */
    public Rect activeScanningAreaPortrait;

    /**
    * @brief Sets the active scan area for landscape mode scanning.
    *
    * By default, the barcode recognition engine searches the whole image for barcodes. Use this method to define the area in which barcodes are to be searched.
    * <p>
    * Rectangle coordinates run from 0 to 1. The coordinates are relative to the whole camera preview (even if the BarcodePicker view is cropped).
    * Invoking this method with invalid rectangles, e.g. rectangles whose top, left, right, or bottom attributes are outside the allowed range of 0.0-1.0, or rectangles with negative width/height will have no effect.
    */
    public Rect activeScanningAreaLandscape;

    /**
    * @brief The location in the image where barcodes are decoded with the highest priority.
    *
    * This variable shows a slightly different behavior depending on whether the full screen scanning is active or not. In Full screen scanning mode:
    * <p>
    * Sets the location in the image which is decoded with the highest priority when multiple barcodes are present in the image.
    * <p>
    * In restrictActiveScanningArea mode (activated with setting either {@link ScanSettings.activeScanningAreaPortrait activeScanningAreaPortrait} or {@link ScanSettings.activeScanningAreaLandscape activeScanningAreaLandscape}):
    * <p>
    * Changes the location of the spot where the barcode decoder actively scans for barcodes.
    * <p>
    * X and Y can be between 0 and 1, where 0/0 corresponds to the top left corner and 1/1 to the bottom right corner of the screen.
    * <p>
    * The default hotspot is centered on the image (0.5, 0.5).
    * When setting the hot spot to values outside the allowed range, the hot spot value is ignored.
    */
    public Point scanningHotSpot;

    /**
    * @brief Whether MatrixScan should be enabled.
    *
    * MatrixScan allows you to show the locations of all localized codes.
    * In order to get MatrixScan, it is recommended to implement the didRecognizeNewCodes
    * callback and to use newlyTrackedCodes. To use the default MatrixScan UI,
    * it is necessary to set {@link BarcodePicker.setGuiStyle() setGuiStyle()} to
    * {@link ScanOverlay.GuiStyle.GUI_STYLE_MATRIX_SCAN}.
    * Implementing a custom MatrixScan UI is not possible.
    */
    public boolean matrixScanEnabled;

    /**
     * @brief Settings related to to text recognition.
     */
    public TextRecognitionSettings textRecognition;
  }

  /**
  * @brief Settings related to to text recognition.
  */
  public class TextRecognitionSettings {

    /**
    * @brief The area (in relative coordinates) in which text is to be recognized.
    *
    * While it's possible to set this area to the whole image, it is not recommended to do so for
    * speed reasons. For best performance, set this to the smallest possible area. By default,
    * the recognition area is set to 1/4 of the image height.
    *
    * This value is only used when scanning in portrait orientation.
    */
    public Rect areaPortrait;

    /**
    * @brief The area (in relative coordinates) in which text is to be recognized.
    *
    * While it's possible to set this area to the whole image, it is not recommended to do so for
    * speed reasons. For best performance, set this to the smallest possible area. By default,
    * the recognition area is set to 1/4 of the image height.
    *
    * This value is only used when scanning in landscape orientation.
    */
    public Rect areaLandscape;

    /**
    * @brief Regular expression for filtering the recognized characters. Text that does not match the
    * regular expression is ignored.
    *
    * By default, the regex is set to null. You must explicitly initialize the regex in order for
    * text recognition to work.
    */
    public String regex;

    /**
    * @brief White list of recognizable characters. If the white list is non-null, a recognition result
    * will never contain characters that are not contained in it.
    *
    * By default the white list is null and all characters will be recognized.
    */
    public String characterWhitelist;
  }

  /**
  * @brief Holds settings specific to a particular symbology (1d, 2d)
  */
  public class SymbologySettings {

    // Hack to force doxygen to generate a documentation page for this class.
    public void noop();

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
    * Enables/disables decoding of dark codes on bright background only. If color-
    * inverted (bright on dark) codes for this symbology are required, enable them through the
    * colorInvertedEnabled property. By default decoding of all symbologies is disabled.
    *
    * It is advised to only enable symbologies that are required by the application as every enabled
    * symbology adds processing overhead.
    */
    public boolean enabled;

    /**
    * Enables/disables decoding of bright codes on dark background only. By default
    * color-inverted decoding of all symbologies is disabled.
    *
    * It is advised to only enable symbologies that are required by the application as every enabled
    * symbology adds processing overhead.
    */
    public boolean colorInvertedEnabled;

    /**
    * An array of all active custom extensions for the symbology.
    *
    * Extensions are custom features that are only supported by a small number of
    * symbologies. For a list of supported extensions, consult <a href="../c_api/symbologies.html">.
    */
    public String[] extensions;

    /**
    * Array of additional checksums for this symbology. When a barcode has been
    * decoded, the checksums contained are evaluated in addition to any mandatory checksum defined by
    * the symbology specification. If any of the checksums matches, the code is returned as
    * recognized, otherwise it is discarded.
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
  */
  public class ScanSession {

    /**
    * List of barcodes that have been successfully recognized in the last frame.
    */
    public Barcode[] newlyRecognizedCodes;

    /**
    * List of barcodes that have been localized in the last frame. This list does not include
    * barcodes that have been successfully recognized.
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
    * @return a new copy of the list of barcodes that have been successfully decoded in this session
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
    */
    public void pauseScanning();

    /**
    * Immediately stops the scanning and clears the scan session
    * <p>
    * Calling stop will release the camera, so that other applications can use
    * it.
    *
    * @see BarcodePicker.stopScanning(), pauseScanning()
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
    */
    public void rejectCode(Barcode code);
  }

  public class MatrixScanSession {

    /**
    * List of barcodes that are newly tracked in the last frame.
    */
    public Barcode[] newlyTrackedCodes;

    /**
    * @brief Returns the list of barcodes (data, symbology) that are currently tracked.
    *
    * Depending on the code caching and duplicate filtering behaviour, different
    * sets of codes are returned by this method.
    *
    * @see ScanSettings.codeCachingDuration
    * @see ScanSettings.codeDuplicateFilter
    *
    * @return a new copy of the list of barcodes that have been successfully decoded in this session
    */
    public Barcode[] allTrackedCodes;

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
    */
    public void pauseScanning();

    /**
    * Immediately stops the scanning and clears the scan session.
    * <p>
    * Calling stop will release the camera, so that other applications can use
    * it.
    *
    * @see BarcodePicker.stopScanning(), pauseScanning()
    */
    public void stopScanning();

    /**
    * @brief The codes that should be visualized as rejected in the tracking view.
    *
    * Use this method to visually reject a certain code in the tracking API.
    * In order to use this feature it is necessary to {@link ScanSettings.matrixScanEnabled} to true.
    *
    * @param code The code to reject.
    */
    public void rejectTrackedCode(Barcode code);
  }

  /**
  * @brief Holds the text recognition result as identified in a frame.
  */
  public class RecognizedText {

    /**
    * @brief The identified text in the frame.
    */
    public String text;

    /**
    * @brief Whether this code is rejected or not.
    *
    * If beeping/vibration is enabled, the device will beep and vibrate whenever text has been
    * recognized. Set this property to true to suppress beeping/vibration. Use this functionality
    * if you want to perform additional checks on the recognized text that cannot be expressed
    * through a regular expression.
    */
    public boolean rejected;
  }
}
