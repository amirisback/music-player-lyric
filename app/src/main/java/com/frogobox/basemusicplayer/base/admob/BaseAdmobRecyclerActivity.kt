package com.frogobox.basemusicplayer.base.admob

import com.frogobox.basemusicplayer.base.ui.BaseActivity
import com.google.android.gms.ads.AdView

/**
 * Created by Faisal Amir
 * FrogoBox Inc License
 * =========================================
 * ImplementationAdmob
 * Copyright (C) 29/11/2019.
 * All rights reserved
 * -----------------------------------------
 * Name     : Muhammad Faisal Amir
 * E-mail   : faisalamircs@gmail.com
 * Github   : github.com/amirisback
 * LinkedIn : linkedin.com/in/faisalamircs
 * -----------------------------------------
 * FrogoBox Software Industries
 * com.frogobox.basemusicplayer.base.admob
 *
 */
open class BaseAdmobRecyclerActivity : BaseActivity() {

    protected val arrayRecyclerView = mutableListOf<Any>()

    override fun onResume() {
        for (item in arrayRecyclerView) {
            if (item is AdView) {
                val adView = item as AdView
                adView.resume()
            }
        }
        super.onResume()
    }

    override fun onPause() {
        for (item in arrayRecyclerView) {
            if (item is AdView) {
                val adView = item as AdView
                adView.pause()
            }
        }
        super.onPause()
    }

    override fun onDestroy() {
        for (item in arrayRecyclerView) {
            if (item is AdView) {
                val adView = item as AdView
                adView.destroy()
            }
        }
        super.onDestroy()
    }

}