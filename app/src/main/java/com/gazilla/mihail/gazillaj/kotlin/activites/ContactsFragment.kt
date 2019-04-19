package com.gazilla.mihail.gazillaj.kotlin.activites

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.gazilla.mihail.gazillaj.R
import kotlinx.android.synthetic.main.fragment_contacts.*

class ContactsFragment: MvpAppCompatFragment() {

    private val VK_APP_PACKAGE_ID = "com.vkontakte.android"
    private val FACEBOOK_APP_PACKAGE_ID = "com.facebook.katana"


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_contacts, null)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        clOpenVK.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://vk.com/gazilla_gz"))
            startActivity(intent)
        }

        clOpenFB.setOnClickListener {
            openLink(activity!!, "https://www.facebook.com/gazillalounge")
        }

        clOpenInsta.setOnClickListener { openLink(activity!!, "https://www.instagram.com/gazilla_gz") }

        clOpenMap.setOnClickListener {
            val adr = "Нижний Сусальный переулок 5с1"
            val uri1 = "geo:0,0?q=$adr"
            val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse(uri1))
            startActivity(mapIntent)
        }

        clOpenWWW.setOnClickListener {
            openLink(activity!!, "https://gazilla-lounge.ru")
        }

        clCallMe.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:89652662222")
            startActivity(intent)
        }
    }

    private fun openLink(activity: Activity, url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        val resInfo = activity.packageManager.queryIntentActivities(intent, 0)

        if (resInfo.isEmpty()) return

        for (info in resInfo) {
            if (info.activityInfo == null) continue
            if (VK_APP_PACKAGE_ID == info.activityInfo.packageName || FACEBOOK_APP_PACKAGE_ID == info.activityInfo.packageName) {
                intent.setPackage(info.activityInfo.packageName)
                break
            }
        }
        activity.startActivity(intent)
    }

}