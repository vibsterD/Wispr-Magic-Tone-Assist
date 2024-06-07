package com.example.wisprmagic

import android.util.Log
import android.view.inputmethod.ExtractedTextRequest
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import co.thingthing.fleksy.core.keyboard.KeyboardConfiguration
import co.thingthing.fleksy.core.keyboard.KeyboardService
import co.thingthing.fleksy.core.keyboard.PanelHelper
import co.thingthing.fleksy.core.personalisation.Button
import com.example.wisprmagic.ui.theme.WisprMagicTheme

class WisprKeyboardService: KeyboardService() {

    override fun createConfiguration(): KeyboardConfiguration {
        return KeyboardConfiguration(
            license = KeyboardConfiguration.LicenseConfiguration(
                licenseKey = "<your-license-key>",
                licenseSecret = "<your-license-secret>"
            ),
            customizationBundle = KeyboardConfiguration.CustomizationBundleConfiguration(
                /**
                 * The name of the customization bundle without the extension.
                 * In this case the bundle is called "custom-action.bundle"
                 * A sample bundle is included in the assets folder.
                 */
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
                ) {
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

                })
            )
        )
    }


}