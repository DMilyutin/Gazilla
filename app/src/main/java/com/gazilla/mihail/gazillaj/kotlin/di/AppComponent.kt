package com.gazilla.mihail.gazillaj.kotlin.di

import android.app.Application
import com.gazilla.mihail.gazillaj.kotlin.activites.Proms.PromoSmokerpassActivity
import com.gazilla.mihail.gazillaj.kotlin.helps.BugReport.BugReport
import com.gazilla.mihail.gazillaj.kotlin.helps.MenuFromServer
import com.gazilla.mihail.gazillaj.kotlin.helps.PromoFromServer
import com.gazilla.mihail.gazillaj.kotlin.presenters.ProductsFreeFragmentPresenter
import com.gazilla.mihail.gazillaj.kotlin.presenters.ProductsMenuAdapterPresenter
import com.gazilla.mihail.gazillaj.kotlin.presenters.registaratiobAndAuthoriz.RegistrationFragmentPresenter
import com.gazilla.mihail.gazillaj.kotlin.providers.*
import dagger.Component
import javax.inject.Singleton

@Component(modules = [RemoteModule::class])

@Singleton
interface AppComponent  {

    fun inject(provider: AccountProvider)
    fun inject(provider: AuthorizationFragmentProvider)
    fun inject(provider: RegistrationFragmentProvider)
    fun inject(provider: ReserveProvider)
    fun inject(provider: ProductProvider)
    fun inject(provider: CardProvider)
    fun inject(provider: StartAppInitializationProviders)
    fun inject(provider: NotificationProvider)


    fun inject(presenter: RegistrationFragmentPresenter)
    fun inject(presenter: ProductsMenuAdapterPresenter)
    fun inject(presenter: ProductsFreeFragmentPresenter)
    fun inject(presenter: PromoSmokerpassActivity)

    fun inject(provider: MenuFromServer)
    fun inject(provider: PromoFromServer)

    fun inject(bugReport: BugReport)


}