package com.enescanpolat.countrytekrar.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext


abstract class baseViewModel(application: Application): AndroidViewModel(application),CoroutineScope {

    //iş tanımlandı burada
    private val job = Job()

    override val coroutineContext: CoroutineContext
    //burada önce işini yap sonra main threade dön dedik
        get() = job + Dispatchers.Main

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}