package com.gazilla.mihail.gazillaj.activites.FirstTipsApp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.gazilla.mihail.gazillaj.R
import com.gazilla.mihail.gazillaj.adapters.FirstAdTipFragmentAdapter
import kotlinx.android.synthetic.main.activity_first_tips.*

class FirstTipsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_tips)

        viewpagerFirstAdTips.offscreenPageLimit = 3

        viewpagerFirstAdTips.adapter = FirstAdTipFragmentAdapter(supportFragmentManager, 3)

        viewPagerIndicatorCoctailPromo.initWithViewPager(viewpagerFirstAdTips)
    }
}
