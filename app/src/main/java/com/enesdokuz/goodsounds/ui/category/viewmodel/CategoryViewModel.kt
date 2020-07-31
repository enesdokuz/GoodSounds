package com.enesdokuz.goodsounds.ui.category.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.enesdokuz.goodsounds.model.Category
import com.enesdokuz.goodsounds.repository.retrofit.Service
import com.enesdokuz.goodsounds.repository.room.MyDatabase
import com.enesdokuz.goodsounds.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class CategoryViewModel(application: Application) : BaseViewModel(application) {

    val categories = MutableLiveData<List<Category>>()
    val isLoading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    private val service = Service()
    private val disposable = CompositeDisposable()
    private val dao = MyDatabase(getApplication()).dao()

    fun getCategories() {
        showLoading()
        getCategoriesFromRoom()
        getCategoriesFromRetrofit()
    }

    private fun getCategoriesFromRoom() {
        launch {
            categories.value = dao.getCategories()
            hideLoading()
        }
    }

    private fun getCategoriesFromRetrofit() {
        disposable.add(
            service.getCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    result?.let {
                        categories.value = it
                        saveCategoriesToRoom(it)
                        hideLoading()
                    }
                }, { error ->
                    error?.let {
                        showError(it.localizedMessage)
                        println(error)
                    }

                })
        )
    }

    private fun saveCategoriesToRoom(values: List<Category>) {
        launch {
            dao.deleteCategories()
        }
        launch {
            dao.insertCategories(*values.toTypedArray())
        }
    }


    private fun showError(errorMessage: String) {
        isLoading.value = false
        this.errorMessage.value = errorMessage
    }

    private fun showLoading() {
        isLoading.value = true
    }

    private fun hideLoading() {
        isLoading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}
