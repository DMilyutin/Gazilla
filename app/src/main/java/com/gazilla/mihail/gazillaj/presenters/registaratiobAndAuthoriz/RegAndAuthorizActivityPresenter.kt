package com.gazilla.mihail.gazillaj.presenters.registaratiobAndAuthoriz

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.gazilla.mihail.gazillaj.activites.registrationAndAuthorization.AuthorizationFragment
import com.gazilla.mihail.gazillaj.activites.registrationAndAuthorization.RegistrationFragment
import com.gazilla.mihail.gazillaj.views.registaratiobAndAuthoriz.RegAndAuthorizView

@InjectViewState
class RegAndAuthorizActivityPresenter: MvpPresenter<RegAndAuthorizView>() {

    private val registrationFragment = RegistrationFragment()
    private val authorizationFragment = AuthorizationFragment()

    init {
        viewState.replaceFragment(registrationFragment,"Воостановить баллы")
    }

    fun pressReplaseFragment(btText: String){
        if (btText=="Воостановить баллы")
            viewState.replaceFragment(authorizationFragment, "Регистрация")
        else
            viewState.replaceFragment(registrationFragment,"Воостановить баллы")
    }
}