package com.gazilla.mihail.gazillaj.kotlin.providers

import android.util.Log
import com.gazilla.mihail.gazillaj.kotlin.helps.App
import com.gazilla.mihail.gazillaj.kotlin.helps.BugReport.BugReport
import com.gazilla.mihail.gazillaj.kotlin.helps.response.callback.FailCallBack
import com.gazilla.mihail.gazillaj.kotlin.helps.response.callback.ReservesCallBack
import com.gazilla.mihail.gazillaj.kotlin.helps.response.callback.SuccessCallBack
import com.gazilla.mihail.gazillaj.kotlin.helps.signatur
import com.gazilla.mihail.gazillaj.kotlin.model.repository.RepositoryApi
import com.gazilla.mihail.gazillaj.kotlin.pojo.Reserve
import com.gazilla.mihail.gazillaj.kotlin.pojo.Success
import com.gazilla.mihail.gazillaj.kotlin.presenters.ReservePresenter
import javax.inject.Inject

class ReserveProvider(private val reservePresenter: ReservePresenter) {

    @Inject lateinit var repositoryApi: RepositoryApi

    init {
        App.appComponent.inject(this)
    }

    fun sendNewReserve(reserve: Reserve, preorder: Boolean){
        val data = "comment=${reserve.commentL}&" +
                "date=${reserve.date}&" +
                "hours=${reserve.hours}&" +
                "name=${reserve.name}&" +
                "phone=${reserve.phone}&" +
                "preorder=$preorder&" +
                "qty=${reserve.qty}"
        val signature = signatur(App.userWithKeys.privatekey, data)
       repositoryApi.reserving(reserve.qty, reserve.hours,reserve.date,reserve.phone, reserve.name,
               reserve.commentL, preorder,  App.userWithKeys.publickey, signature,
               object : SuccessCallBack{
                   override fun successResponse(success: Success?) {
                       reservePresenter.responseReserve(success!!.success)
                       if (!success.success)
                           BugReport().sendUserInfoForReserve("Проблема с резервом! ${reserve.phone} - ${reserve.name}")
                   }

                   override fun errorCallBack(error: String) {
                       reservePresenter.showErrorMessage(error)
                       val bugReport = BugReport()
                       bugReport.sendBugInfo(error, location = "ReserveProvider.sendNewReserve.errorCallBack")
                       bugReport.sendUserInfoForReserve("Проблема с резервом! ${reserve.phone} - ${reserve.name}")
                   }
               },
               object : FailCallBack{
                    override fun throwableCallBack(throwable: Throwable) {
                        val bugReport = BugReport()
                        reservePresenter.showErrorMessage(throwable.message.toString())
                        bugReport.sendUserInfoForReserve("Проблема с резервом! ${reserve.phone} - ${reserve.name}")
                        bugReport.sendBugInfo(throwable.message.toString(),"ReserveProvider.sendNewReserve.throwableCallBack" )
           }
       })
    }

    fun myReserve(){
        repositoryApi.myReserveOnServer(App.userWithKeys.publickey, signatur(App.userWithKeys.privatekey, ""),
                object : ReservesCallBack{
                    override fun myReserves(reserves: List<Reserve>?) {
                       reservePresenter.myReserves(reserves!!)
                    }

                    override fun errorCallBack(error: String) {
                        reservePresenter.showErrorMessage("Ошибка загрузки резерва")
                    }
                }, object : FailCallBack{
            override fun throwableCallBack(throwable: Throwable) {
                reservePresenter.showErrorMessage("Ошибка загрузки резерва")
                BugReport().sendBugInfo(throwable.message.toString(),"ReserveProvider.sendNewReserve.throwableCallBack" )
            }
        })
    }

    fun delReserve(id: Int){
        repositoryApi.dellMyReserve(App.userWithKeys.publickey,id,  signatur(App.userWithKeys.privatekey, "id=$id"),
                object : SuccessCallBack{
                    override fun successResponse(success: Success?) {
                        if (success!!.success){
                            reservePresenter.showErrorMessage("Бронь успешно отменена")
                            reservePresenter.checkMyReserve()
                        }
                        else{
                            reservePresenter.showErrorMessage("Ошибка отмены брони")
                            BugReport().sendUserInfoForReserve("Ошибка при отмене брони! ID брони - $id")
                        }

                    }

                    override fun errorCallBack(error: String) {
                        reservePresenter.showErrorMessage(error)
                    }
                }, object : FailCallBack{
            override fun throwableCallBack(throwable: Throwable) {
                BugReport().sendBugInfo(throwable.message.toString(),"ReserveProvider.delReserve.throwableCallBack" )
                reservePresenter.showErrorMessage(throwable.message.toString())
            }
        })
    }


}