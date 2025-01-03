package com.gokuldham.app.ui.login.response

import Api
import com.gokuldham.app.data.Parser
import com.gokuldham.app.data.remote.ApiFactory
import com.gokuldham.app.ui.base.BaseResponse
import com.gokuldham.app.util.Logger
import com.gokuldham.app.util.NetworkResponseCallback

import com.google.gson.annotations.SerializedName
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class LoginResponse : BaseResponse<LoginResponse, String, Any>() {

    @SerializedName("ResponseMessage")
    var message: String = ""

    @SerializedName("AuthToken")
    lateinit var authToken: String

    override fun doNetworkRequest(
        requestParam: HashMap<String, Any>,
        vararg: Any,
        networkResponseCallback: NetworkResponseCallback<LoginResponse>
    ): Disposable {

        val api = ApiFactory.getClientWithoutHeader().create(Api::class.java)
        Logger.e("requestParam>>>>>", "" + requestParam.toString())

        return api.loginApi(requestParam)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { networkResponseCallback.onResponse(it) },
                { throwable -> Parser.parseErrorResponse(throwable, networkResponseCallback) })
    }

    data class UserData(
        val email: String,
        val firstName: String,
        val gender: String,
        val id: Int,
        val image: String,
        val lastName: String,
        val token: String,
        val username: String
    )


}
