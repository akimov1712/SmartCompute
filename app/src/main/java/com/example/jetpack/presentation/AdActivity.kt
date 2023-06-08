package com.example.jetpack.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.jetpack.App
import com.example.jetpack.R
import com.yandex.mobile.ads.common.AdRequest
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.ImpressionData
import com.yandex.mobile.ads.interstitial.InterstitialAd
import com.yandex.mobile.ads.interstitial.InterstitialAdEventListener

class AdActivity : AppCompatActivity() {

    private val interstitialAd by lazy {
        InterstitialAd(this)
    }
    private val adRequest by lazy{
        AdRequest.Builder().build()
    }

    private var state = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ad)
        with(interstitialAd){
            setAdUnitId(App.ADD_UNIT_ID_FULLSCREEN)
            setInterstitialAdEventListener(object: InterstitialAdEventListener {
                override fun onAdLoaded() {
                    show()
                    state = true
                }

                override fun onAdFailedToLoad(p0: AdRequestError) {
                    interstitialAd.loadAd(adRequest)
                }

                override fun onAdShown() {
                }

                override fun onAdDismissed() {

                }

                override fun onAdClicked() {

                }

                override fun onLeftApplication() {

                }

                override fun onReturnedToApplication() {

                }

                override fun onImpression(p0: ImpressionData?) {

                }
            })
        }
        interstitialAd.loadAd(adRequest)
    }

    override fun onResume() {
        super.onResume()
        if (state){
            finish()
        }
    }

    companion object{
        fun newIntent(context: Context): Intent{
            return Intent(context, AdActivity::class.java)
        }
    }
}