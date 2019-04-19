package com.gazilla.mihail.gazillaj.kotlin.activites

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.gazilla.mihail.gazillaj.R
import com.gazilla.mihail.gazillaj.kotlin.adapters.LvlDrakonCardFragmentAdapter
import com.gazilla.mihail.gazillaj.kotlin.helps.AppDialogs
import com.gazilla.mihail.gazillaj.kotlin.presenters.CardFragmentPresenter
import com.gazilla.mihail.gazillaj.kotlin.views.CardView
import kotlinx.android.synthetic.main.fragment_card.*

class CardFragment : MvpAppCompatFragment(), CardView{

    private lateinit var updateBalance: UpdateBalance
    private lateinit var lvlDrakonCardFragmentAdapter: LvlDrakonCardFragmentAdapter

    @InjectPresenter lateinit var cardFragmentPresenter: CardFragmentPresenter

    private lateinit var mContext : Context
    private val appDialogs = AppDialogs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_card, null)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        refresh.setOnRefreshListener {
            Log.i("Loog", "update refresh")
            refresh.isRefreshing = true
            cardFragmentPresenter.update()
            refresh.isRefreshing = false
        }

        btOpenReserve.setOnClickListener {
            val intent = Intent(mContext, ReserveActivity::class.java)
            startActivity(intent)
        }

        imgRuletka.setOnClickListener {
            cardFragmentPresenter.startWheeling()
        }

        miniProgressLayout.setOnClickListener {
            if (lvLvlDrakonProgressCardFragment.visibility == View.VISIBLE)
                lvLvlDrakonProgressCardFragment.visibility = View.GONE
            else
                lvLvlDrakonProgressCardFragment.visibility = View.VISIBLE
        }

        lvLvlDrakonProgressCardFragment.setOnItemClickListener { _, _, position, _ ->
            cardFragmentPresenter.getDecriptionLvlDracon(lvlDrakonCardFragmentAdapter.getItemId(position).toInt())
        }
    }

    override fun startWheeling() {
        imgRuletka.setColorFilter(Color.TRANSPARENT)
        val animation = AnimationUtils.loadAnimation(mContext, R.anim.rotate_ruletka)
        animation.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationRepeat(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                cardFragmentPresenter.showWin()
                imgRuletka.isClickable = true
            }

            override fun onAnimationStart(animation: Animation?) {
                imgRuletka.isClickable = false
            }
        })
        imgRuletka.startAnimation(animation)
    }

    override fun initWheelLvl(res: Int) {
        imgRuletka.setImageResource(res)
    }

    @SuppressLint("SetTextI18n")
    override fun setValueProgressBar(maxValue: Int, userValue: Int) {
        tvSum.text= "$userValue/$maxValue"
        pbCardFragment.max = maxValue
        pbCardFragment.progress = userValue
    }

    override fun setQrCode(bitmap: Bitmap) {
        imvIDclient.setImageBitmap(bitmap)
    }

    override fun setSpins(qty: Int) {
        tvSpins.text = qty.toString()
    }

    override fun setLvlUserDraconAdapter(levels: Map<Int, Int>) {
        lvlDrakonCardFragmentAdapter = LvlDrakonCardFragmentAdapter(mContext, levels)
        lvLvlDrakonProgressCardFragment.adapter = lvlDrakonCardFragmentAdapter
    }

    override fun showWhiteRoundDrakonTip(show: Boolean) {
        if (show){
            tvPresentCard.visibility = View.VISIBLE
            imgWhiteCircle.visibility = View.VISIBLE
            imgRuletka.setColorFilter(0x50000000)
        }else{
            tvPresentCard.visibility = View.GONE
            imgWhiteCircle.visibility = View.GONE
        }
    }

    override fun showMyWin(res: Int, description: String) {
        appDialogs.dialogWinWheel(mContext, res, description)
    }

    override fun showMessageDialog(message: String) {
        appDialogs.messageDialog(mContext, message)
    }

    override fun showDetailLvlDracon(myLvlDiscription: String, one: String, two: String, fri: String, four: String, fif: String) {
       appDialogs.dialogDetailLvlDracon(mContext, myLvlDiscription, one, two, fri, four, fif)
    }

    override fun openMessageWithReserve(message: String) {
        AppDialogs().dialogOpenReserve(mContext, message, this)
    }

    override fun openReserveActivity() {
        val intent = Intent(mContext, ReserveActivity::class.java)
        startActivity(intent)
    }

    interface UpdateBalance{
        fun updateMyBalance()
    }

    override fun updateBalance() {
        updateBalance.updateMyBalance()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        updateBalance = context as UpdateBalance
        mContext = context
    }
}