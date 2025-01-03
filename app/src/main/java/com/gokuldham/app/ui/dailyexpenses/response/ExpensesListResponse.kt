package com.gokuldham.app.ui.dailyexpenses.response

import Api
import android.os.Parcelable
import com.gokuldham.app.data.Parser
import com.gokuldham.app.data.remote.ApiFactory
import com.gokuldham.app.ui.base.BaseResponse
import com.gokuldham.app.util.Logger
import com.gokuldham.app.util.NetworkResponseCallback
import com.google.gson.annotations.SerializedName
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.parcel.Parcelize

class ExpensesListResponse : BaseResponse<ExpensesListResponse, String, Any>() {

    @SerializedName("ResponseMessage")
    var message: String = ""

    @SerializedName("OpeningBalance")
    var OpeningBalance: String = ""

    @SerializedName("ClosingBalance")
    var ClosingBalance: String = ""

    @SerializedName("CreditBalance")
    var CreditBalance: String = ""

    @SerializedName("DebitBalance")
    var DebitBalance: String = ""

    @SerializedName("DailyExpenseItem")
    var data = ArrayList<DailyExpenseItem>()


    override fun doNetworkRequest(
        requestParam: HashMap<String, Any>,
        vararg: Any,
        networkResponseCallback: NetworkResponseCallback<ExpensesListResponse>
    ): Disposable {

        val api = ApiFactory.clientWithHeader.create(Api::class.java)
        Logger.e("requestParam>>>>>", "" + requestParam.toString())

        return api.getExpenses(requestParam).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { networkResponseCallback.onResponse(it) },
                { throwable -> Parser.parseErrorResponse(throwable, networkResponseCallback) })
    }

    @Parcelize
    data class DailyExpenseItem(
        val Amount: Double,
        val Description: String,
        val Type: String
    ) : Parcelable


}

