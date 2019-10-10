package com.scandit.reactnative

import android.graphics.*
import android.util.Base64
import com.facebook.react.bridge.*
import com.facebook.react.common.MapBuilder
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.annotations.ReactProp
import com.facebook.react.uimanager.events.RCTEventEmitter
import com.scandit.barcodepicker.*
import com.scandit.barcodepicker.BarcodePicker
import com.scandit.barcodepicker.ocr.RecognizedText
import com.scandit.barcodepicker.ocr.TextRecognitionListener
import com.scandit.recognition.Quadrilateral
import com.scandit.recognition.TrackedBarcode
import java.io.ByteArrayOutputStream
import java.lang.Thread
import java.util.ArrayList
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.CountDownLatch
import java.util.concurrent.ScheduledThreadPoolExecutor
import java.util.concurrent.TimeUnit
import java.util.HashSet


class BarcodePicker(
    val cameraApiSetting: CameraApiSetting
) : SimpleViewManager<BarcodePicker>(), OnScanListener, TextRecognitionListener,
        ProcessFrameListener, WarningsListener, PropertyChangeListener {

    private var picker: BarcodePicker? = null
    private var didScanLatch: CountDownLatch = CountDownLatch(1)
    private var didRecognizeTextLatch: CountDownLatch = CountDownLatch(1)
    private var didFinishOnRecognizeNewCodesLatch: CountDownLatch = CountDownLatch(1)
    private var didFinishOnChangeTrackedCodesLatch: CountDownLatch = CountDownLatch(1)
    private var lastFrameRecognizedIds = HashSet<Long>()
    private var isMatrixScanEnabled = false
    private var nextPickerState = NextPickerState.CONTINUE
    private var shouldPassBarcodeFrame = false
    private val codesToReject = ArrayList<Int>()
    private val idsToReject = ArrayList<String>()
    private var isTextRejected = false

    private val stopped = AtomicBoolean(false)

    override fun getName(): String = "BarcodePicker"

    override fun getCommandsMap(): MutableMap<String, Int> = MapBuilder.newHashMap<String, Int>().apply {
        put("startScanning", COMMAND_START_SCANNING)
        put("startScanningInPausedState", COMMAND_START_SCANNING_IN_PAUSED_STATE)
        put("stopScanning", COMMAND_STOP_SCANNING)
        put("resumeScanning", COMMAND_RESUME_SCANNING)
        put("pauseScanning", COMMAND_PAUSE_SCANNING)
        put("applySettings", COMMAND_APPLY_SETTINGS)
        put("setViewfinderDimension", COMMAND_VIEWFINDER_DIMENSION)
        put("switchTorchOn", COMMAND_SWITCH_TORCH_ON)
        put("setTorchEnabled", COMMAND_TORCH_ENABLED)
        put("setVibrateEnabled", COMMAND_VIBRATE_ENABLED)
        put("setBeepEnabled", COMMAND_BEEP_ENABLED)
        put("setTorchButtonMarginsAndSize", COMMAND_TORCH_BUTTON_MARGINS_AND_SIZE)
        put("setCameraSwitchVisibility", COMMAND_CAMERA_SWITCH_VISIBILITY)
        put("setCameraSwitchMarginsAndSize", COMMAND_CAMERA_SWITCH_MARGINS_AND_SIZE)
        put("setViewfinderColor", COMMAND_VIEWFINDER_COLOR)
        put("setViewfinderDecodedColor", COMMAND_VIEWFINDER_DECODED_COLOR)
        put("setMatrixScanHighlightingColor", COMMAND_MATRIX_HIGHLIGHT_COLOR)
        put("setOverlayProperty", COMMAND_SET_OVERLAY_PROPERTY)
        put("setGuiStyle", COMMAND_SET_GUI_STYLE)
        put("setTextRecognitionSwitchVisible", COMMAND_SET_TEXT_RECOGNITION_SWITCH_ENABLED)
        put("finishOnScanCallback", COMMAND_FINISH_ON_SCAN_CALLBACK)
        put("finishOnRecognizeNewCodes", COMMAND_FINISH_ON_RECOGNIZE_NEW_CODES_CALLBACK)
        put("finishOnChangeTrackedCodes", COMMAND_FINISH_ON_CHANGE_TRACKED_CODES_CALLBACK)
        put("finishOnTextRecognized", COMMAND_FINISH_ON_TEXT_RECOGNIZED_CALLBACK)
    }

    override fun receiveCommand(root: BarcodePicker, commandId: Int, args: ReadableArray?) {
        when (commandId) {
            COMMAND_START_SCANNING -> {
                stopped.set(false)
                root.startScanning()
            }
            COMMAND_START_SCANNING_IN_PAUSED_STATE -> {
                stopped.set(false)
                root.startScanning(true)
            }
            COMMAND_STOP_SCANNING -> {
                stopped.set(true)
                // Run stopping of the picker on a non-UI thread, to avoid a deadlock.
                Thread(object : Runnable {
                    override fun run() {
                        root.stopScanning()
                    }
                }).start()
            }
            COMMAND_RESUME_SCANNING -> root.resumeScanning()
            COMMAND_PAUSE_SCANNING -> root.pauseScanning()
            COMMAND_APPLY_SETTINGS -> setScanSettings(args)
            COMMAND_VIEWFINDER_DIMENSION -> setViewfinderDimension(args)
            COMMAND_SWITCH_TORCH_ON -> switchTorchOn(args)
            COMMAND_TORCH_ENABLED -> setTorchEnabled(args)
            COMMAND_VIBRATE_ENABLED -> setVibrateEnabled(args)
            COMMAND_BEEP_ENABLED -> setBeepEnabled(args)
            COMMAND_TORCH_BUTTON_MARGINS_AND_SIZE -> setTorchButtonMarginsSize(args)
            COMMAND_CAMERA_SWITCH_VISIBILITY -> setCameraSwitchVisibility(args)
            COMMAND_CAMERA_SWITCH_MARGINS_AND_SIZE -> setCameraSwitchMarginsSize(args)
            COMMAND_VIEWFINDER_COLOR -> setViewfinderColor(args)
            COMMAND_VIEWFINDER_DECODED_COLOR -> setViewfinderDecodedColor(args)
            COMMAND_MATRIX_HIGHLIGHT_COLOR -> setMatrixScanHighlightingColor(args)
            COMMAND_SET_OVERLAY_PROPERTY -> setOverlayProperty(args)
            COMMAND_SET_GUI_STYLE -> setGuiStyle(args)
            COMMAND_SET_TEXT_RECOGNITION_SWITCH_ENABLED -> setTextRecognitionSwitchVisible(args)
            COMMAND_FINISH_ON_SCAN_CALLBACK -> finishOnScan(args)
            COMMAND_FINISH_ON_RECOGNIZE_NEW_CODES_CALLBACK -> finishOnRecognizeNewCodes(args)
            COMMAND_FINISH_ON_CHANGE_TRACKED_CODES_CALLBACK -> finishOnChangeTrackedCodes(args)
            COMMAND_FINISH_ON_TEXT_RECOGNIZED_CALLBACK -> finishOnTextRecognized(args)
        }
    }

    override fun createViewInstance(reactContext: ThemedReactContext): BarcodePicker {
        var scanSettings = ScanSettings.create()
        if (cameraApiSetting.cameraApi == 2) {
            scanSettings.setProperty("enable_camera2_api", 1)
        }
        picker = BarcodePicker(reactContext, scanSettings)
        picker?.setOnScanListener(this)
        picker?.setTextRecognitionListener(this)
        picker?.setProcessFrameListener(this)
        picker?.addWarningsListener(this)
        picker?.setPropertyChangeListener(this)
        return picker as BarcodePicker
    }

    override fun getExportedCustomDirectEventTypeConstants(): MutableMap<String, Any> = MapBuilder.newHashMap<String, Any>().apply {
        put("onScan", MapBuilder.of("registrationName", "onScan"))
        put("onBarcodeFrameAvailable", MapBuilder.of("registrationName", "onBarcodeFrameAvailable"))
        put("onRecognizeNewCodes", MapBuilder.of("registrationName", "onRecognizeNewCodes"))
        put("onSettingsApplied", MapBuilder.of("registrationName", "onSettingsApplied"))
        put("onTextRecognized", MapBuilder.of("registrationName", "onTextRecognized"))
        put("onWarnings", MapBuilder.of("registrationName", "onWarnings"))
        put("onPropertyChanged", MapBuilder.of("registrationName", "onPropertyChanged"))
        put("onChangeTrackedCodes", MapBuilder.of("registrationName", "onChangeTrackedCodes"))
    }

    override fun didProcess(buffer: ByteArray?, width: Int, height: Int, scanSession: ScanSession?) {
        if (scanSession == null) {
            return
        }

        val context = picker?.context as ReactContext?

        // Call `onBarcodeFrameAvailable` only when new codes have been recognized.
        if (shouldPassBarcodeFrame && scanSession.newlyRecognizedCodes.size > 0) {
            val event = Arguments.createMap()
            event.putString("base64FrameString", base64StringFromByteArray(buffer, width, height))
            context?.getJSModule(RCTEventEmitter::class.java)?.receiveEvent(picker?.id ?: 0,
                    "onBarcodeFrameAvailable", event)
        }

        if (scanSession.trackedCodes.isEmpty()) {
            return
        }

        val trackedCodes: Map<Long, TrackedBarcode> = scanSession.trackedCodes
        val newlyTrackedCodes: MutableMap<Long, TrackedBarcode> = hashMapOf()
        val recognizedCodeIds: HashSet<Long> = hashSetOf()

        for (entry in trackedCodes.entries) {
            if (entry.value.isRecognized) {
                recognizedCodeIds.add(entry.key)
                if (!lastFrameRecognizedIds.contains(entry.key)) {
                    newlyTrackedCodes[entry.key] = entry.value
                }
            }
        }
        lastFrameRecognizedIds = recognizedCodeIds

        val matrixScanSessionMap =
                picker?.let {
                    matrixScanSessionCodesToMap(trackedCodes, newlyTrackedCodes, it)
                } ?: Arguments.createMap()
        val matrixScanSessionMapClone = matrixScanSessionMap.copy()

        if (isMatrixScanEnabled) {
            context?.getJSModule(RCTEventEmitter::class.java)?.receiveEvent(picker?.id ?: 0,
                    "onChangeTrackedCodes", matrixScanSessionMap)
            // Suspend the session thread, until finishOnChangeTrackedCodes is called from JS
            didFinishOnChangeTrackedCodesLatch.await()
            handleFinishingSemaphore(scanSession)
        }

        if (newlyTrackedCodes.isNotEmpty()) {
            context?.getJSModule(RCTEventEmitter::class.java)?.receiveEvent(picker?.id ?: 0,
                    "onRecognizeNewCodes", matrixScanSessionMapClone)
            // Suspend the session thread, until finishOnRecognizeNewCodes is called from JS
            didFinishOnRecognizeNewCodesLatch.await()
            handleFinishingSemaphore(scanSession)
        }
    }

    private fun handleFinishingSemaphore(scanSession: ScanSession) {
        for (id in idsToReject) {
            scanSession.rejectTrackedCode(scanSession.trackedCodes[id.toLong()])
        }
        idsToReject.clear()
        handleNextPickerState(scanSession)
    }

    override fun didScan(scanSession: ScanSession?) {
        if (isMatrixScanEnabled || scanSession == null) {
            return
        }

        // Don't forward the didScan callback to JS layer, as the picker has been already stopped.
        if (stopped.get()) return

        val context = picker?.context as ReactContext?

        // Very rarely it can happen that stopScanning is called in the middle of processing
        // a didScan callback. In that scenario, the JS BarcodePicker (for some inexplicable reason)
        // is not invoking its onScan method. As a result the engine thread is not released from
        // the didScanLatch. To prevent this deadlock, we try to release the engine thread in
        // a delayed runnable below.
        ScheduledThreadPoolExecutor(1).schedule({
            synchronized(didScanLatch) {
                didScanLatch.countDown()
                didScanLatch = CountDownLatch(1)
            }
        }, 400, TimeUnit.MILLISECONDS)
        context?.getJSModule(RCTEventEmitter::class.java)?.receiveEvent(picker?.id ?: 0,
                "onScan", sessionToMap(scanSession))
        didScanLatch.await()
        for (index in codesToReject) {
            scanSession.rejectCode(scanSession.newlyRecognizedCodes[index])
        }
        codesToReject.clear()
        handleNextPickerState(scanSession)
    }

    override fun didRecognizeText(text: RecognizedText): Int {
        val event = Arguments.createMap()
        val context = picker?.context as ReactContext?
        event.putString("text", text.text)
        event.putBoolean("rejected", text.isRejected())
        context?.getJSModule(RCTEventEmitter::class.java)?.receiveEvent(picker?.id ?: 0,
                "onTextRecognized", event)
        didRecognizeTextLatch.await()
        if (isTextRejected) {
            text.setRejected(true)
            isTextRejected = false
        }
        return getNextPickerState()
    }

    override fun onWarnings(warnings: Set<Int>) {
        if (warnings.isEmpty()) {
            return
        }
        val context = picker?.context as ReactContext?
        context?.getJSModule(RCTEventEmitter::class.java)?.receiveEvent(picker?.id ?: 0,
                "onWarnings", warningsToMap(warnings))
    }

    override fun onPropertyChange(name: Int, newState: Int) {
        val event = Arguments.createMap()
        val context = picker?.context as ReactContext?
        event.putString("name", propertyNameFromInt(name))
        event.putInt("newState", newState)
        context?.getJSModule(RCTEventEmitter::class.java)?.receiveEvent(picker?.id ?: 0,
                "onPropertyChanged", event)
    }

    private fun propertyNameFromInt(name: Int): String = when (name) {
        0 -> "torchOn"
        1 -> "switchCamera"
        2 -> "recognitionMode"
        3 -> "relativeZoom"
        else -> ""
    }

    @ReactProp(name = "scanSettings")
    fun setPropScanSettings(view: BarcodePicker, settingsJson: ReadableMap) {
        val settings = settingsFromMap(settingsJson)
        isMatrixScanEnabled = settings.isMatrixScanEnabled
        view.applyScanSettings(settings)
    }

    @Suppress("UNUSED_PARAMETER")
    @ReactProp(name = "shouldPassBarcodeFrame")
    fun setPropScanSettings(view: BarcodePicker, shouldPassBarcodeFrame: Boolean) {
        this.shouldPassBarcodeFrame = shouldPassBarcodeFrame
    }

    private fun base64StringFromByteArray(buffer: ByteArray?, width: Int, height: Int): String {
        if (buffer == null) {
            return ""
        }

        val jpegBitmap = getBitmapFromYuv(buffer, width, height)
        val outStream = ByteArrayOutputStream()
        jpegBitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream)

        return Base64.encodeToString(outStream.toByteArray(), Base64.DEFAULT)
    }

    private fun getBitmapFromYuv(bytes: ByteArray, width: Int, height: Int): Bitmap {
        val yuvImage = YuvImage(bytes, ImageFormat.NV21, width, height, null)
        val outputStream = ByteArrayOutputStream()
        yuvImage.compressToJpeg(Rect(0, 0, width, height), 100, outputStream)
        val jpegByteArray = outputStream.toByteArray()
        return BitmapFactory.decodeByteArray(jpegByteArray, 0, jpegByteArray.size)
    }

    private fun handleNextPickerState(scanSession: ScanSession) {
        when (nextPickerState) {
            NextPickerState.STOP -> scanSession.stopScanning()
            NextPickerState.PAUSE -> scanSession.pauseScanning()
            else -> return
        }
        nextPickerState = NextPickerState.CONTINUE
    }

    private fun getNextPickerState(): Int {
        val requestedState = nextPickerState
        nextPickerState = NextPickerState.CONTINUE
        return when (requestedState) {
            NextPickerState.STOP -> TextRecognitionListener.PICKER_STATE_STOPPED
            NextPickerState.PAUSE -> TextRecognitionListener.PICKER_STATE_PAUSED
            else -> TextRecognitionListener.PICKER_STATE_ACTIVE
        }
    }

    /**
     * Callback method that will be invoked by the JS side once the result of OnScan has been received and processed by JS layer.
     * Arguments in the args array are as follows:
     * 1. shouldStop flag
     * 2. shouldPause flag
     * 3. idsToVisuallyReject
     */
    private fun finishOnScan(args: ReadableArray?) {
        updateNextPickerState(args)

        args?.getArray(2)?.let { addIdsToReject(it) }

        synchronized(didScanLatch) {
            didScanLatch.countDown()
            didScanLatch = CountDownLatch(1)
        }
    }

    /**
     * Callback method that will be invoked by the JS side once the result of OnRecognizeNewCodes has been received and processed by JS layer.
     * Arguments in the args array are as follows:
     * 1. shouldStop flag
     * 2. shouldPause flag
     * 3. idsToVisuallyReject
     */
    private fun finishOnRecognizeNewCodes(args: ReadableArray?) {
        updateNextPickerState(args)

        args?.getArray(2)?.let { addIdsToReject(it) }

        synchronized(didFinishOnRecognizeNewCodesLatch) {
            didFinishOnRecognizeNewCodesLatch.countDown()
            didFinishOnRecognizeNewCodesLatch = CountDownLatch(1)
        }
    }

    /**
     * Callback method that will be invoked by the JS side once the result of OnChangeTrackedCodes has been received and processed by JS layer.
     * Arguments in the args array are as follows:
     * 1. shouldStop flag
     * 2. shouldPause flag
     * 3. idsToVisuallyReject
     */
    private fun finishOnChangeTrackedCodes(args: ReadableArray?) {
        updateNextPickerState(args)

        args?.getArray(2)?.let { addIdsToReject(it) }

        synchronized(didFinishOnChangeTrackedCodesLatch) {
            didFinishOnChangeTrackedCodesLatch.countDown()
            didFinishOnChangeTrackedCodesLatch = CountDownLatch(1)
        }
    }

    /**
     * Callback method that will be invoked by the JS side once the result of OnTextRecognized has been received and processed by JS layer.
     * Arguments in the args array are as follows:
     * 1. shouldStop flag
     * 2. shouldPause flag
     * 3. rejected text flag
     */
    private fun finishOnTextRecognized(args: ReadableArray?) {
        updateNextPickerState(args)

        isTextRejected = args?.getBoolean(2) ?: false

        synchronized(didRecognizeTextLatch) {
            didRecognizeTextLatch.countDown()
            didRecognizeTextLatch = CountDownLatch(1)
        }
    }

    private fun updateNextPickerState(args: ReadableArray?) {
        if (args?.getBoolean(0) == true)
            nextPickerState = NextPickerState.STOP
        if (args?.getBoolean(1) == true)
            nextPickerState = NextPickerState.PAUSE
    }

    private fun addIdsToReject(array: ReadableArray) {
        var index = 0
        while (index < array.size()) {
            idsToReject.add(array.getString(index++) ?: continue)
        }
    }

    private fun setScanSettings(args: ReadableArray?) {
        val settings = settingsFromMap(args?.getMap(0) ?: return)
        isMatrixScanEnabled = settings.isMatrixScanEnabled
        picker?.applyScanSettings(settings, {
            val context = picker?.context as ReactContext?
            context?.getJSModule(RCTEventEmitter::class.java)?.receiveEvent(picker?.id ?: 0,
                    "onSettingsApplied", Arguments.createMap())
        })
    }

    private fun setGuiStyle(args: ReadableArray?) {
        picker?.overlayView?.setGuiStyle(convertGuiStyle(args?.getString(0)))
    }

    private fun setViewfinderDimension(args: ReadableArray?) {
        picker?.overlayView?.setViewfinderDimension(
                args?.getDouble(0)?.toFloat() ?: 1f, args?.getDouble(1)?.toFloat() ?: 1f,
                args?.getDouble(2)?.toFloat() ?: 1f, args?.getDouble(3)?.toFloat() ?: 1f
        )
    }
    
    private fun switchTorchOn(args: ReadableArray?) {
        picker?.switchTorchOn(args?.getBoolean(0) ?: false)
    }

    private fun setTorchEnabled(args: ReadableArray?) {
        picker?.overlayView?.setTorchEnabled(args?.getBoolean(0) ?: false)
    }

    private fun setTorchButtonMarginsSize(args: ReadableArray?) {
        picker?.overlayView?.setTorchButtonMarginsAndSize(
                args?.getInt(0) ?: 0, args?.getInt(1) ?: 0, args?.getInt(2) ?: 0, args?.getInt(3) ?: 0
        )
    }

    private fun setVibrateEnabled(args: ReadableArray?) {
        picker?.overlayView?.setVibrateEnabled(args?.getBoolean(0) ?: false)
    }

    private fun setBeepEnabled(args: ReadableArray?) {
        picker?.overlayView?.setBeepEnabled(args?.getBoolean(0) ?: false)
    }

    private fun setCameraSwitchVisibility(args: ReadableArray?) {
        picker?.overlayView?.setCameraSwitchVisibility(convertCameraSwitchVisibility(args?.getString(0)))
    }

    private fun setCameraSwitchMarginsSize(args: ReadableArray?) {
        picker?.overlayView?.setCameraSwitchButtonMarginsAndSize(
                args?.getInt(0) ?: 0, args?.getInt(1) ?: 0, args?.getInt(2) ?: 0, args?.getInt(3) ?: 0
        )
    }

    private fun setViewfinderColor(args: ReadableArray?) {
        val colorInt = args?.getInt(0) ?: Color.WHITE
        picker?.overlayView?.setViewfinderColor(Color.red(colorInt) / 255f, Color.green(colorInt) / 255f, Color.blue(colorInt) / 255f)
    }

    private fun setViewfinderDecodedColor(args: ReadableArray?) {
        val colorInt = args?.getInt(0) ?: Color.GREEN
        picker?.overlayView?.setViewfinderDecodedColor(Color.red(colorInt) / 255f, Color.green(colorInt) / 255f, Color.blue(colorInt) / 255f)
    }

    private fun setMatrixScanHighlightingColor(args: ReadableArray?) {
        picker?.overlayView?.setMatrixScanHighlightingColor(
                convertMatrixScanState(args?.getString(0)), args?.getInt(1) ?: 0
        )
    }

    private fun setTextRecognitionSwitchVisible(args: ReadableArray?) {
        picker?.overlayView?.setTextRecognitionSwitchVisible(args?.getBoolean(0) ?: false)
    }

    private fun setOverlayProperty(args: ReadableArray?) {
        val propValue: Any? = when (args?.getType(1)) {
            ReadableType.Boolean -> args.getBoolean(1)
            ReadableType.String -> args.getString(1)
            ReadableType.Number -> args.getDouble(1)
            else -> null
        }
        picker?.overlayView?.setProperty(args?.getString(0), propValue)
    }

    private enum class NextPickerState {
        CONTINUE, PAUSE, STOP
    }
}
