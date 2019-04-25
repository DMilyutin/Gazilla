package com.gazilla.mihail.gazillaj.activites.FirstTipsApp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gazilla.mihail.gazillaj.R

class FirstAdTipFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_first_ad_tip, null)
    }
}