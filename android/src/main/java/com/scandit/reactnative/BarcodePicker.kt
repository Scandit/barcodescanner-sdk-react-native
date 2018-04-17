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
import com.scandit.recognition.TrackedBarcode
import java.io.ByteArrayOutputStream
import java.util.ArrayList
import java.util.concurrent.CountDownLatch
import java.util.HashSet


class BarcodePicker : SimpleViewManager<BarcodePicker>(), OnScanListener, TextRecognitionListener, ProcessFrameListener {

    private var picker: BarcodePicker? = null
    private var didScanLatch: CountDownLatch = CountDownLatch(1)
    private var didProcessLatch: CountDownLatch = CountDownLatch(1)
    private var lastFrameRecognizedIds = HashSet<Long>()
    private var isMatrixScanEnabled = false
    private var nextPickerState = NextPickerState.CONTINUE
    private var shouldPassBarcodeFrame = false
    private val codesToReject = ArrayList<Int>()
    private val idsToReject = ArrayList<String>()

    override fun getName(): String = "BarcodePicker"

    override fun getCommandsMap(): MutableMap<String, Int> {
        val map = MapBuilder.newHashMap<String, Int>()
        map.put("startScanning", COMMAND_START_SCANNING)
        map.put("stopScanning", COMMAND_STOP_SCANNING)
        map.put("resumeScanning", COMMAND_RESUME_SCANNING)
        map.put("pauseScanning", COMMAND_PAUSE_SCANNING)
        map.put("applySettings", COMMAND_APPLY_SETTINGS)
        map.put("setViewfinderDimension", COMMAND_VIEWFINDER_DIMENSION)
        map.put("setTorchEnabled", COMMAND_TORCH_ENABLED)
        map.put("setVibrateEnabled", COMMAND_VIBRATE_ENABLED)
        map.put("setBeepEnabled", COMMAND_BEEP_ENABLED)
        map.put("setTorchButtonMarginsAndSize", COMMAND_TORCH_BUTTON_MARGINS_AND_SIZE)
        map.put("setCameraSwitchVisibility", COMMAND_CAMERA_SWITCH_VISIBILITY)
        map.put("setCameraSwitchMarginsAndSize", COMMAND_CAMERA_SWITCH_MARGINS_AND_SIZE)
        map.put("setViewfinderColor", COMMAND_VIEWFINDER_COLOR)
        map.put("setViewfinderDecodedColor", COMMAND_VIEWFINDER_DECODED_COLOR)
        map.put("setMatrixScanHighlightingColor", COMMAND_MATRIX_HIGHLIGHT_COLOR)
        map.put("setOverlayProperty", COMMAND_SET_OVERLAY_PROPERTY)
        map.put("setGuiStyle", COMMAND_SET_GUI_STYLE)
        map.put("setTextRecognitionSwitchVisible", COMMAND_SET_TEXT_RECOGNITION_SWITCH_ENABLED)
        map.put("finishOnScanCallback", COMMAND_FINISH_ON_SCAN_CALLBACK)
        map.put("finishOnRecognizeNewCodes", COMMAND_FINISH_ON_RECOGNIZE_NEW_CODES_CALLBACK)
        return map
    }

    override fun receiveCommand(root: BarcodePicker?, commandId: Int, args: ReadableArray?) {
        when (commandId) {
            COMMAND_START_SCANNING -> root?.startScanning()
            COMMAND_STOP_SCANNING -> root?.stopScanning()
            COMMAND_RESUME_SCANNING -> root?.resumeScanning()
            COMMAND_PAUSE_SCANNING -> root?.pauseScanning()
            COMMAND_APPLY_SETTINGS -> setScanSettings(args)
            COMMAND_VIEWFINDER_DIMENSION -> setViewfinderDimension(args)
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
            COMMAND_FINISH_ON_SCAN_CALLBACK -> finishOnScanCallback(args)
            COMMAND_FINISH_ON_RECOGNIZE_NEW_CODES_CALLBACK -> finishDidProcessCallback(args)
        }
    }

    override fun createViewInstance(reactContext: ThemedReactContext?): BarcodePicker {
        picker = BarcodePicker(reactContext, ScanSettings.create())
        picker?.setOnScanListener(this)
        picker?.setTextRecognitionListener(this)
        picker?.setProcessFrameListener(this)
        return picker as BarcodePicker
    }

    override fun getExportedCustomDirectEventTypeConstants(): MutableMap<String, Any> {
        return MapBuilder.of(
                "onScan", MapBuilder.of("registrationName", "onScan"),
                "onBarcodeFrameAvailable", MapBuilder.of("registrationName", "onBarcodeFrameAvailable"),
                "onRecognizeNewCodes", MapBuilder.of("registrationName", "onRecognizeNewCodes"),
                "onSettingsApplied", MapBuilder.of("registrationName", "onSettingsApplied"),
                "onTextRecognized", MapBuilder.of("registrationName", "onTextRecognized")
        )
    }

    override fun didProcess(buffer: ByteArray?, width: Int, height: Int, scanSession: ScanSession?) {
        if (scanSession == null || scanSession.trackedCodes.isEmpty()) {
            return
        }

        val context = picker?.context as ReactContext?

        if (shouldPassBarcodeFrame && scanSession.newlyRecognizedCodes.size > 0) {
            val event = Arguments.createMap()
            event.put("base64FrameString", base64StringFromByteArray(buffer, width, height))
            context?.getJSModule(RCTEventEmitter::class.java)?.receiveEvent(picker?.id ?: 0,
                    "onBarcodeFrameAvailable", event)
        }

        val trackedCodes = scanSession.trackedCodes
        val newlyTrackedCodes = ArrayList<TrackedBarcode>()
        val recognizedCodeIds = HashSet<Long>()

        for (entry in trackedCodes.entries) {
            if (entry.value.isRecognized) {
                recognizedCodeIds.add(entry.key)
                if (!lastFrameRecognizedIds.contains(entry.key)) {
                    newlyTrackedCodes.add(entry.value)
                }
            }
        }
        lastFrameRecognizedIds = recognizedCodeIds
        if (newlyTrackedCodes.isEmpty()) {
            return
        }

        context?.getJSModule(RCTEventEmitter::class.java)?.receiveEvent(picker?.id ?: 0,
                "onRecognizeNewCodes", (newlyTrackedCodesToMap(newlyTrackedCodes)))
        didProcessLatch.await()
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
        val context = picker?.context as ReactContext?
        context?.getJSModule(RCTEventEmitter::class.java)?.receiveEvent(picker?.id ?: 0,
                "onScan", sessionToMap(scanSession))
        didScanLatch.await()
        for (index in codesToReject) {
            scanSession.rejectCode(scanSession.newlyRecognizedCodes[index])
        }
        codesToReject.clear()
        handleNextPickerState(scanSession)
    }

    override fun didRecognizeText(text: RecognizedText?): Int {
        val event = Arguments.createMap()
        val context = picker?.context as ReactContext?
        event.putString("text", text?.text)
        context?.getJSModule(RCTEventEmitter::class.java)?.receiveEvent(picker?.id
                ?: 0, "onTextRecognized", event)
        return TextRecognitionListener.PICKER_STATE_ACTIVE
    }

    @ReactProp(name = "scanSettings")
    fun setPropScanSettings(view: BarcodePicker, settingsJson: ReadableMap) {
        val settings = settingsFromMap(settingsJson)
        isMatrixScanEnabled = settings.isMatrixScanEnabled
        view.applyScanSettings(settings)
    }

    @ReactProp(name = "shouldPassBarcodeFrame")
    fun setPropScanSettings(view: BarcodePicker, shouldPassBarcodeFrame: Boolean) {
        this.shouldPassBarcodeFrame = shouldPassBarcodeFrame
    }

    private fun base64StringFromByteArray(buffer: ByteArray?, width: Int, height: Int): String {
        if (buffer == null) {
            return ""
        }

        val jpegBitmap = getRotatedBitmapFromYuv(buffer, width, height)
        val outStream = ByteArrayOutputStream()
        jpegBitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream)

        return Base64.encodeToString(outStream.toByteArray(), Base64.DEFAULT)
    }

    private fun getRotatedBitmapFromYuv(bytes: ByteArray, width: Int, height: Int): Bitmap {
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

    private fun finishOnScanCallback(args: ReadableArray?) {
        if (args?.getBoolean(0) == true)
            nextPickerState = NextPickerState.STOP
        if (args?.getBoolean(1) == true)
            nextPickerState = NextPickerState.PAUSE
        var index = 0
        val array = args?.getArray(2)
        while (index < array?.size() ?: 0) {
            codesToReject.add(array?.getInt(index++) ?: continue)
        }
        didScanLatch.countDown()
        didScanLatch = CountDownLatch(1)
    }

    private fun finishDidProcessCallback(args: ReadableArray?) {
        if (args?.getBoolean(0) == true)
            nextPickerState = NextPickerState.STOP
        if (args?.getBoolean(1) == true)
            nextPickerState = NextPickerState.PAUSE
        var index = 0
        val array = args?.getArray(2)
        while (index < array?.size() ?: 0) {
            idsToReject.add(array?.getString(index++) ?: continue)
        }
        didProcessLatch.countDown()
        didProcessLatch = CountDownLatch(1)
    }

    private fun setScanSettings(args: ReadableArray?) {
        val settings = settingsFromMap(args?.getMap(0) ?: return)
        isMatrixScanEnabled = settings.isMatrixScanEnabled
        picker?.applyScanSettings(settings, {
            val context = picker?.context as ReactContext?
            context?.getJSModule(RCTEventEmitter::class.java)?.receiveEvent(picker?.id
                    ?: 0, "onSettingsApplied", Arguments.createMap())
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

    private fun setTorchEnabled(args: ReadableArray?) {
        picker?.overlayView?.setTorchEnabled(args?.getBoolean(0) ?: false)
    }

    private fun setTorchButtonMarginsSize(args: ReadableArray?) {
        picker?.overlayView?.setTorchButtonMarginsAndSize(
                args?.getInt(0) ?: 0, args?.getInt(1) ?: 0, args?.getInt(2) ?: 0, args?.getInt(3)
                ?: 0
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
                args?.getInt(0) ?: 0, args?.getInt(1) ?: 0, args?.getInt(2) ?: 0, args?.getInt(3)
                ?: 0
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