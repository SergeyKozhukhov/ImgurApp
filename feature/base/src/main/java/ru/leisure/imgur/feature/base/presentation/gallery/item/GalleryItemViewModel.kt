package ru.leisure.imgur.feature.base.presentation.gallery.item

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.leisure.imgur.feature.base.di.ImgurComponent
import ru.leisure.imgur.feature.base.domain.ImgurInteractor
import ru.leisure.imgur.feature.base.domain.models.DataLoadingException

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
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val interactor = ImgurComponent.create().imgurInteractor
                return GalleryItemViewModel(interactor = interactor) as T
            }
        }
    }
}