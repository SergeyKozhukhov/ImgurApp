package ru.leisure.imgur.presentation.gallery.item

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.leisure.imgur.MyApplication
import ru.leisure.imgur.domain.ImgurInteractor
import ru.leisure.imgur.domain.models.DataLoadingException

class GalleryItemViewModel(
    private val interactor: ImgurInteractor
) : ViewModel() {

    val uiState: StateFlow<GalleryItemUiState> get() = _uiState.asStateFlow()
    private val _uiState: MutableStateFlow<GalleryItemUiState> =
        MutableStateFlow(GalleryItemUiState.Idle)

    fun loadComments(id: String) {
        viewModelScope.launch {
            try {
                _uiState.value = GalleryItemUiState.Loading
                val comments = interactor.getComments(id)
                _uiState.value = GalleryItemUiState.Success(comments = comments)
            } catch (e: DataLoadingException) {
                _uiState.value = GalleryItemUiState.Error(message = e.toString())
            }
        }
    }

    companion object {

        val Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application =
                    checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                val appComponent = MyApplication.appComponent(application)
                return GalleryItemViewModel(interactor = appComponent.imgurInteractor) as T
            }
        }
    }
}