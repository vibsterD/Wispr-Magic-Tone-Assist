package com.example.wisprmagic

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.util.Log
import android.view.View
import android.view.inputmethod.ExtractedTextRequest
import android.widget.LinearLayout
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.core.view.marginRight
import co.thingthing.fleksy.core.keyboard.KeyboardConfiguration
import co.thingthing.fleksy.core.keyboard.KeyboardService
import co.thingthing.fleksy.core.keyboard.PanelHelper
import co.thingthing.fleksy.core.personalisation.Button
import com.example.wisprmagic.data.ToneAssistRequest
import com.example.wisprmagic.data.ToneAssistResponse
import com.example.wisprmagic.databinding.ToneAssistBinding
import com.example.wisprmagic.network.ToneAssistApi
import com.example.wisprmagic.ui.theme.WisprMagicTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WisprKeyboardService: KeyboardService() {
    private lateinit var toneAssistView: ToneAssistBinding
    private lateinit var toneAssistResponse: ToneAssistResponse

    @SuppressLint("SetTextI18n")
    override fun createConfiguration(): KeyboardConfiguration {
        return KeyboardConfiguration(
            license = KeyboardConfiguration.LicenseConfiguration(
                licenseKey = BuildConfig.FLEKSY_LICENSE_KEY,
                licenseSecret = BuildConfig.FLEKSY_LICENSE_SECRET
            ),
            customizationBundle = KeyboardConfiguration.CustomizationBundleConfiguration(
                // corresponds to the name of the customization bundle without the extension
                bundleFileName = "custom-action",

                /**
                 * List of custom buttons. These must be previously defined in the customization
                 * bundle's layout json file: keyboard-global.json
                 */
                buttons = listOf(Button(
                    /**
                     * Label by which to match with the defined button in the custom layout file.
                     */
                    /**
                     * Label by which to match with the defined button in the custom layout file.
                     */
                    label = "custom-action",

                    /**
                     * Image which will be shown in the custom button's keycap.
                     */

                    /**
                     * Image which will be shown in the custom button's keycap.
                     */
                    image = R.drawable.wispr_icon_dark,

                    /**
                     * The scale mode for the image inside the custom button's keycap. In this
                     * case it will center the button inside the keycap and its dimensions will be
                     * equal to or less than the dimensions of the keycap.
                     */

                    /**
                     * The scale mode for the image inside the custom button's keycap. In this
                     * case it will center the button inside the keycap and its dimensions will be
                     * equal to or less than the dimensions of the keycap.
                     */
                    scaleMode = Button.ScaleMode.CENTER_INSIDE,
                ) { it ->
                    /**
                     * On click action for the custom button.
                     */
                    Log.e("ActionButton", "Action button clicked! " + it)

                    val currentInputConnection = currentInputConnection

                    Log.d("WisprKeyboardService", "currentInputConnection: $currentInputConnection")
                    Log.d("WisprKeyboardService", "Extracted Text: " + currentInputConnection.getExtractedText(
                        ExtractedTextRequest(), 0
                    ).text.toString())
                    Log.d("WisprKeyboardService", "Selected Text: " + currentInputConnection.getSelectedText(0))

                    toneAssistView = ToneAssistBinding.inflate(this.layoutInflater)
//                    add more emojis to the text below
                    toneAssistView.textView.text = "Tone assist is analysing your text 😊🌟"

                    toneAssistView.closeButton.setOnClickListener {
                        PanelHelper.hideFullView()
                    }

                    toneAssistView.insertButton.setOnClickListener {
                        currentInputConnection.deleteSurroundingText(1e7.toInt(), 1e7.toInt())
                        currentInputConnection.commitText(toneAssistView.textView.text, 1)
                    }

                    toneAssistView.copyButton.setOnClickListener {
                        val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                        val clip = ClipData.newPlainText("Tone Assist", toneAssistView.textView.text)
                        clipboard.setPrimaryClip(clip)
                    }

                    buildToneAssistView(currentInputConnection.getExtractedText(
                        ExtractedTextRequest(), 0
                    ).text.toString())






                    PanelHelper.showFullView(toneAssistView.root)

                })
            )
        )
    }

    // Network request to get and set tone assist data
    private fun buildToneAssistView(editorText: String) {

        toneAssistView.progressBar.visibility = View.VISIBLE
        toneAssistView.linearLayout.isClickable = false
        toneAssistView.linearLayout.isEnabled = false

        toneAssistView.insertButton.isEnabled = false
        toneAssistView.copyButton.isEnabled = false


        CoroutineScope(Dispatchers.Main).launch {
            try {
                toneAssistResponse =
                    ToneAssistApi.retrofitService.getToneAssist(
                        ToneAssistRequest(
                            editorText
                        )
                    )
                Log.d("WisprKeyboardService", "Response: $toneAssistResponse")

                toneAssistView.linearLayout.removeAllViews()

                val buttonData = listOf("Social", "Professional", "Polite", "Emojify")

                for (data in buttonData) {
                    val button = android.widget.Button(this@WisprKeyboardService)
                    button.text = data
                    button.layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )

                    button.setOnClickListener{
                        Log.d("WisprKeyboardService", "Button Clicked: $data")
                        toneAssistView.textView.text =  toneAssistResponse[data]
                    }

                    toneAssistView.linearLayout.addView(button)

                }

                toneAssistView.textView.text = toneAssistResponse["Social"]

            } catch (e: Exception) {
                Log.e("WisprKeyboardService", "Error: $e")
                toneAssistView.textView.text = "Oops! Something went wrong. Please try again."
            } finally {
                toneAssistView.progressBar.visibility = View.GONE
                toneAssistView.linearLayout.isClickable = true
                toneAssistView.linearLayout.isEnabled = true
                toneAssistView.insertButton.isEnabled = true
                toneAssistView.copyButton.isEnabled = true
            }
        }
    }


}