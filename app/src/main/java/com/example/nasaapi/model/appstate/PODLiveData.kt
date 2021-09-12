package com.example.nasaapi.model.appstate

import com.example.nasaapi.model.retrofit.PODServerResponseData

sealed class PODLiveData {
    data class Success(val serverResponseData: PODServerResponseData) : PODLiveData()
    data class Error(val error: Throwable) : PODLiveData()
    data class Loading(val progress: Int?) : PODLiveData()
}
