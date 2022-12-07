package com.ngemeal.ngemeal.ui.midtrans

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.midtrans.sdk.corekit.core.themes.CustomColorTheme
import com.midtrans.sdk.uikit.SdkUIFlowBuilder
import com.ngemeal.ngemeal.BuildConfig


class PaymentMidtransActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SdkUIFlowBuilder.init()
            .setClientKey(BuildConfig.MIDTRANS_CLIENT_KEY)
            .setContext(applicationContext)
            .setTransactionFinishedCallback ({
                result ->
            })
            .setMerchantBaseUrl(BuildConfig.BASE_URL)
            .enableLog(true)
            .setColorTheme(CustomColorTheme("#FFC700", "#E91E68", "#000"))
            .setLanguage("id")
            .buildSDK()
    }

}