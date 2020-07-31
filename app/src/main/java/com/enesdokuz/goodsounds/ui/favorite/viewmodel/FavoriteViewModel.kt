package com.enesdokuz.goodsounds.ui.favorite.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.enesdokuz.goodsounds.model.Sound
import com.enesdokuz.goodsounds.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application) : BaseViewModel(application) {

    val favorites = MutableLiveData<List<Sound>>()
    val isLoading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()

    fun getFavorites() {
        showLoading()
        launch {
            favorites.value = dao.getMyFavorites()
        }
        hideLoading()
    }

    fun setFavorite(soundId: String, isFavorite: Boolean) {
        showLoading()
        launch {
            dao.updateSoundFav(id = soundId, isFavorite = isFavorite)
        }
        hideLoading()
    }

    fun setSoundVolume(soundId: String, volume: Float) {
        showLoading()
        launch {
            dao.updateSoundVolume(id = soundId, volume = volume)
        }
        hideLoading()
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