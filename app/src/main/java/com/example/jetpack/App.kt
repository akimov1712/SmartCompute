package com.example.jetpack

import android.app.Application
import android.util.Log
import com.yandex.mobile.ads.common.MobileAds


class App: Application() {

companion object{
    const val ADD_UNIT_ID_BANNER = "R-M-2428973-1"
    const val ADD_UNIT_ID_FULLSCREEN = "R-M-2428973-2"
}

    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(
            this
        ) { Log.d("App", "SDK initialized") }
    }


}