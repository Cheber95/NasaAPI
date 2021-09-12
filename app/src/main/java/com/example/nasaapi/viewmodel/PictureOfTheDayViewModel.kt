package com.example.nasaapi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nasaapi.BuildConfig
import com.example.nasaapi.model.appstate.PODLiveData
import com.example.nasaapi.model.retrofit.PODServerResponseData
import com.example.nasaapi.model.retrofit.PictureRetrofit
import com.example.nasaapi.model.retrofit.PictureRetrofitImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val API_KEY = BuildConfig.NASA_API_KEY

class PictureOfTheDayViewModel(
    private val liveDataForViewToObserve: MutableLiveData<PODLiveData> = MutableLiveData(),
    private val retrofitImpl: PictureRetrofit = PictureRetrofitImpl()
) : ViewModel() {

    fun getData(): LiveData<PODLiveData> {
        sendServerRequest()
        return liveDataForViewToObserve
    }

    fun sendServerRequest() {
        liveDataForViewToObserve.value = PODLiveData.Loading(null)
        if (API_KEY.isBlank()) {
            PODLiveData.Error(Throwable("You API key is Empty"))
        } else {
            retrofitImpl.getRetrofitImpl().getPictureOfTheDay(API_KEY).enqueue(object :
                Callback<PODServerResponseData> {
                override fun onResponse(
                    call: Call<PODServerResponseData>,
                    response: Response<PODServerResponseData>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        liveDataForViewToObserve.value = PODLiveData.Success(response.body()!!)
                    } else {
                        val message = response.message()
                        if (message.isNullOrEmpty()) {
                            liveDataForViewToObserve.value = PODLiveData.Error(Throwable("error"))
                        }
                        else {
                            liveDataForViewToObserve.value = PODLiveData.Error(Throwable(message))
                        }
                    }
                }

                override fun onFailure(call: Call<PODServerResponseData>, t: Throwable) {
                    PODLiveData.Error(t)
                }

            })
        }
    }

}