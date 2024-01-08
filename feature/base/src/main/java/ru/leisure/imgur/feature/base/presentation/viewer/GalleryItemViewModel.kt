package ru.leisure.imgur.feature.base.presentation.viewer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.leisure.imgur.feature.base.di.ImgurComponent
import ru.leisure.imgur.feature.base.domain.ImgurInteractor
import ru.leisure.imgur.feature.base.domain.models.DataLoadingException
import ru.leisure.imgur.feature.base.domain.models.GalleryAlbum
import ru.leisure.imgur.feature.base.domain.models.GalleryItem
import ru.leisure.imgur.feature.base.domain.models.GalleryMedia

class GalleryItemViewModel(
    private val interactor: ImgurInteractor
) : ViewModel() {

    val uiState: StateFlow<GalleryItemUiState> get() = _uiState.asStateFlow()
    private val _uiState: MutableStateFlow<GalleryItemUiState> =
        MutableStateFlow(GalleryItemUiState.Idle)

    fun loadItem(item: GalleryItem) {
        viewModelScope.launch {
            try {
                _uiState.value = GalleryItemUiState.Loading
                _uiState.value = load(item)
            } catch (e: DataLoadingException) {
                _uiState.value = GalleryItemUiState.Error(message = e.toString())
            }
        }
    }

    private suspend fun load(item: GalleryItem) = coroutineScope {
        val galleryItem = async {
            when (item) {
                is GalleryAlbum -> interactor.getGalleryAlbum(item.id)
                is GalleryMedia -> interactor.getGalleryMedia(item.id)
            }
        }
        val comments = async { interactor.getComments(item.id) }
        GalleryItemUiState.Success(
            galleryItem = galleryItem.await(),
            comments = comments.await()
        )
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