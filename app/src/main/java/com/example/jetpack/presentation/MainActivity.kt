package com.example.jetpack.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.jetpack.App
import com.example.jetpack.databinding.ActivityMainBinding
import com.yandex.mobile.ads.banner.AdSize
import com.yandex.mobile.ads.common.AdRequest
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.ImpressionData
import com.yandex.mobile.ads.interstitial.InterstitialAd
import com.yandex.mobile.ads.interstitial.InterstitialAdEventListener

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adRequestBanner by lazy{
        AdRequest.Builder().build()
    }
    private val bannerAdView by lazy {
        binding.bannerView
    }

    private val interstitialAd by lazy {
        InterstitialAd(this)
    }
    private val adRequestFullscreen by lazy{
        AdRequest.Builder().build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(bannerAdView){
            setAdUnitId(App.ADD_UNIT_ID_BANNER)
            setAdSize(AdSize.BANNER_320x50)
            loadAd(adRequestBanner)
        }
        with(interstitialAd){
            setAdUnitId(App.ADD_UNIT_ID_FULLSCREEN)
            setInterstitialAdEventListener(object: InterstitialAdEventListener {
                override fun onAdLoaded() {
                    show()
                }

                override fun onAdFailedToLoad(p0: AdRequestError) {
                    interstitialAd.loadAd(adRequestFullscreen)
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
        interstitialAd.loadAd(adRequestFullscreen)
    }
}