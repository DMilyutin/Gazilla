package com.gazilla.mihail.gazillaj.activites.Proms

import android.os.Bundle
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.gazilla.mihail.gazillaj.R
import com.gazilla.mihail.gazillaj.activites.Proms.viewForPagerCoctailPromo.PromoCoctailAdapter
import com.gazilla.mihail.gazillaj.helps.AppDialogs
import com.gazilla.mihail.gazillaj.presenters.PromoCocktailPresenter
import com.gazilla.mihail.gazillaj.views.PromoCocktailView
import kotlinx.android.synthetic.main.activity_promo_cocktail.*

class PromoCocktailActivity: MvpAppCompatActivity(), PromoCocktailView {


    @InjectPresenter
    lateinit var promoCocktailPresenter: PromoCocktailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_promo_cocktail)

        promoCocktailPresenter.myStars()

        viewPagerPromoCoctail.offscreenPageLimit = 3
        viewPagerPromoCoctail.adapter = PromoCoctailAdapter(supportFragmentManager, 3)

        viewPagerIndicatorCoctailPromo.initWithViewPager(viewPagerPromoCoctail)

        btSendPromocodeCoctail.setOnClickListener {
            promoCocktailPresenter.sendCodeCoctailPromo(etPromocodeCoctailPromo.text.toString(), false)
        }

        tvHowTakeStars.setOnClickListener {
            AppDialogs().messageDialog(this, "Узнайте как получить звезды в нашей группе вконтакте https://vk.com/gazilla_gz")
        }
    }


    override fun setMyStars(stars: Int) {
        rbPromoCoctail.rating = stars.toFloat()
    }

    override fun accessSendCode() {
        etPromocodeCoctailPromo.setText("")
        promoCocktailPresenter.myStars()
    }

    override fun accessDeleteOldCoctail() {
        promoCocktailPresenter.sendCodeCoctailPromo(etPromocodeCoctailPromo.text.toString(), true)
    }

    override fun showDialogAccessDeleteOldCoctail(message: String) {
        AppDialogs().dialogAccessDeleteOldCoctailPromo(this, this, message)
    }

    override fun showMessage(message: String) {
        AppDialogs().messageDialog(this, message)
    }

}