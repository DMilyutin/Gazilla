package com.gazilla.mihail.gazillaj.di

import com.gazilla.mihail.gazillaj.activites.Proms.PromoSmokerpassActivity
import com.gazilla.mihail.gazillaj.helps.BugReport.BugReport
import com.gazilla.mihail.gazillaj.helps.MenuFromServer
import com.gazilla.mihail.gazillaj.helps.PromoFromServer
import com.gazilla.mihail.gazillaj.presenters.ProductsFreeFragmentPresenter
import com.gazilla.mihail.gazillaj.presenters.ProductsMenuAdapterPresenter
import com.gazilla.mihail.gazillaj.presenters.registaratiobAndAuthoriz.RegistrationFragmentPresenter
import com.gazilla.mihail.gazillaj.providers.*
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
    fun inject(provider: MenuFromServer)
    fun inject(provider: PromoFromServer)
    fun inject(provider: PromoCocktailProvider)

    fun inject(presenter: RegistrationFragmentPresenter)
    fun inject(presenter: ProductsMenuAdapterPresenter)
    fun inject(presenter: ProductsFreeFragmentPresenter)
    fun inject(presenter: PromoSmokerpassActivity)

    fun inject(bugReport: BugReport)

}