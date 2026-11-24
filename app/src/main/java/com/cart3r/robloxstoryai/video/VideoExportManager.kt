package com.cart3r.robloxstoryai.video

import android.content.Context
import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.transformer.Composition
import androidx.media3.transformer.EditedMediaItem
import androidx.media3.transformer.ExportException
import androidx.media3.transformer.ExportResult
import androidx.media3.transformer.Transformer
import java.io.File

/**
 * Small Media3 export adapter.
 *
 * This exports/transcodes one existing video URI to an H.264/AAC MP4 file.
 * It is not yet the full multi-scene Unity render/composition pipeline.
 * Call all methods from the main application thread, as required by Media3.
 */
class VideoExportManager(context: Context) {
    private val appContext = context.applicationContext
    private var activeTransformer: Transformer? = null

    fun exportSingleClip(
        inputUri: Uri,
        outputFile: File,
        onCompleted: (File) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        require(outputFile.parentFile?.exists() == true) {
            "Output directory must exist before export."
        }
        require(inputUri != Uri.EMPTY) { "Input video URI is empty." }

        activeTransformer?.cancel()
        if (outputFile.exists()) outputFile.delete()

        val editedItem = EditedMediaItem.Builder(MediaItem.fromUri(inputUri)).build()
        val listener = object : Transformer.Listener {
            override fun onCompleted(composition: Composition, exportResult: ExportResult) {
                activeTransformer = null
                onCompleted(outputFile)
            }

            override fun onError(
                composition: Composition,
                exportResult: ExportResult,
                exportException: ExportException
            ) {
                activeTransformer = null
                onError(exportException)
            }
        }

        val transformer = Transformer.Builder(appContext)
            .setVideoMimeType(MimeTypes.VIDEO_H264)
            .setAudioMimeType(MimeTypes.AUDIO_AAC)
            .addListener(listener)
            .build()

        activeTransformer = transformer
        transformer.start(editedItem, outputFile.absolutePath)
    }

    fun cancel() {
        activeTransformer?.cancel()
        activeTransformer = null
    }
}
