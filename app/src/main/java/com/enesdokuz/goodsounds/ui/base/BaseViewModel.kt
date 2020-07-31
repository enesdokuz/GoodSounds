package com.enesdokuz.goodsounds.ui.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.enesdokuz.goodsounds.repository.retrofit.Service
import com.enesdokuz.goodsounds.repository.room.MyDatabase
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel(application: Application): AndroidViewModel(application),CoroutineScope {

    private val job = Job()
    val service = Service()
    val disposable = CompositeDisposable()
    val dao = MyDatabase(getApplication()).dao()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}