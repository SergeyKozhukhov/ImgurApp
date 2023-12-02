package ru.leisure.imgur.presentation.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.leisure.imgur.MyApplication
import ru.leisure.imgur.domain.ImgurInteractor
import ru.leisure.imgur.domain.models.DataLoadingException

class GalleryViewModel(
    private val interactor: ImgurInteractor
) : ViewModel() {

    val uiState: StateFlow<GalleryUiState> get() = _uiState.asStateFlow()
    private val _uiState: MutableStateFlow<GalleryUiState> =
        MutableStateFlow(GalleryUiState.Idle)


    fun loadGallery() {
        viewModelScope.launch {
            try {
                _uiState.value = GalleryUiState.Loading
                val gallery = interactor.getGallery()
                _uiState.value = GalleryUiState.Success(gallery = gallery)
            } catch (e: DataLoadingException) {
                _uiState.value = GalleryUiState.Error(message = e.toString())
            }
        }
    }

    fun searchGallery(query: String) {
        viewModelScope.launch {
            try {
                _uiState.value = GalleryUiState.Loading
                val gallery = interactor.searchGallery(query)
                _uiState.value = GalleryUiState.Success(gallery = gallery)
            } catch (e: DataLoadingException) {
                _uiState.value = GalleryUiState.Error(message = e.toString())
            }
        }
    }

    companion object {

        val Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                val appComponent = MyApplication.appComponent(application)
                return GalleryViewModel(interactor = appComponent.imgurInteractor) as T
            }
        }
    }
}