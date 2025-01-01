package com.gokuldham.app.ui.exchange.exchangelist

import com.gokuldham.app.data.ListData
import com.gokuldham.app.data.local.AppPreference
import com.gokuldham.app.databinding.DialogAddExchangeBinding
import com.gokuldham.app.ui.base.BaseViewModel
import com.gokuldham.app.ui.exchange.response.AddExchangeResponse
import com.gokuldham.app.ui.exchange.response.ExchangeListResponse
import com.gokuldham.app.util.NetworkResponseCallback
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ExchangeListViewModel : BaseViewModel<ExchangeListNavigator>() {

    var businessmanData: ListData? = null

    fun villageListAPI(searchKey: String) {

        val requestMap: HashMap<String, Any> = HashMap()
        requestMap["businessManid"] = businessmanData?.Id.toString()

        navigator!!.showProgress()


        disposable.add(
            ExchangeListResponse().doNetworkRequest(
                requestMap, AppPreference,
                object : NetworkResponseCallback<ExchangeListResponse> {

                    override fun onResponse(data: ExchangeListResponse) {
                        navigator!!.hideProgress()
                        navigator!!.exchangeListResponse(data)
                    }

                    override fun onFailure(message: String) {
                        navigator!!.hideProgress()
                        navigator!!.showNetworkError(message)
                    }

                    override fun onServerError(error: String) {
                        navigator!!.hideProgress()
                        navigator!!.showNetworkError(error)
                    }

                    override fun onErrorMessage(errorMessage: String) {
                        navigator!!.hideProgress()
                        super.onErrorMessage(errorMessage)
                        navigator!!.showNetworkError(errorMessage, false)
                    }

                    override fun onSessionExpire(error: String) {
                        navigator!!.hideProgress()
                        navigator!!.showSessionExpireAlert(error)
                    }

                    override fun onAppVersionUpdate(msg: String) {
                        navigator!!.hideProgress()
                        navigator!!.showNetworkError(msg)
                    }

                })
        )
    }

    fun checkValidation(viewDataBinding: DialogAddExchangeBinding): Boolean {

        if (viewDataBinding.editItemAmount.text!!.trim().isEmpty()) {
            navigator!!.showValidationError("कृपया राशि दर्ज करें")
            viewDataBinding.editItemAmount.requestFocus()
            return false
        }
        if (viewDataBinding.editInterestRate.text!!.trim().isEmpty()) {
            navigator!!.showValidationError("कृपया ब्याज दर दर्ज करें")
            viewDataBinding.editInterestRate.requestFocus()
            return false
        }

        return true
    }

    private var isUpdate = false
    fun addVillageApi(viewDataBinding: DialogAddExchangeBinding?) {

        val currentDate = Date()
        val dateFormat = SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault())
        val formattedDate = dateFormat.format(currentDate)

        navigator!!.showProgress()
        val requestMap: HashMap<String, Any> = HashMap()
        requestMap["BusinessManId"] = businessmanData?.Id.toString()
        requestMap["Amount"] = viewDataBinding?.editItemAmount?.text.toString()
        requestMap["InterestRate"] = viewDataBinding?.editInterestRate?.text.toString()
        requestMap["ExchangeDate"] = formattedDate

        disposable.add(
            AddExchangeResponse().doNetworkRequest(requestMap, isUpdate,
                object : NetworkResponseCallback<AddExchangeResponse> {

                    override fun onResponse(data: AddExchangeResponse) {
                        navigator!!.hideProgress()
                        navigator!!.addExchangeResponse(data)
                    }

                    override fun onFailure(message: String) {
                        navigator!!.hideProgress()
                        navigator!!.showNetworkError(message)
                    }

                    override fun onServerError(error: String) {
                        navigator!!.hideProgress()
                        navigator!!.showNetworkError(error)
                    }

                    override fun onErrorMessage(errorMessage: String) {
                        navigator!!.hideProgress()
                        super.onErrorMessage(errorMessage)
                        navigator!!.showNetworkError(errorMessage, false)
                    }

                    override fun onSessionExpire(error: String) {
                        navigator!!.hideProgress()
                        navigator!!.showSessionExpireAlert(error)
                    }

                    override fun onAppVersionUpdate(msg: String) {
                        navigator!!.hideProgress()
                        navigator!!.showNetworkError(msg)
                    }

                })
        )
    }

}