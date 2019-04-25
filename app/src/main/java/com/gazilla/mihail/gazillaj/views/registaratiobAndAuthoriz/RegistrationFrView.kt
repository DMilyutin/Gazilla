package com.gazilla.mihail.gazillaj.views.registaratiobAndAuthoriz

import com.arellomobile.mvp.MvpView


interface RegistrationFrView : MvpView {

    fun showLoadDialog()
    fun closeLoadDialog()

    fun closeRegistration(success: Boolean)

    fun showMessageDialog(message: String)
}