package ru.leisure.imgur.feature.base.presentation.tags

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.leisure.imgur.feature.base.di.ImgurComponent
import ru.leisure.imgur.feature.base.domain.ImgurInteractor
import ru.leisure.imgur.feature.base.domain.models.DataLoadingException

class DefaultGalleryTagsViewModel(
    private val interactor: ImgurInteractor
) : ViewModel() {

    val uiState: StateFlow<DefaultGalleryTagsUiState> get() = _uiState.asStateFlow()
    private val _uiState = MutableStateFlow(DefaultGalleryTagsUiState())

    fun loadGalleryTags() {
        if (uiState.value.defaultTags != null) return
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isDefaultTagsLoading = true) }
                val tags = interactor.getDefaultGalleryTags()
                _uiState.value = DefaultGalleryTagsUiState(defaultTags = tags)
                tags.tags.firstOrNull()?.let { onTagClick(it.name) }
            } catch (e: DataLoadingException) {
                _uiState.value = DefaultGalleryTagsUiState(defaultTagsError = e.toString())
            }
        }
    }

    fun onTagClick(tag: String) {
        if (uiState.value.mediaTag?.name == tag) return
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isMediaTagLoading = true, mediaTag = null) }
                val mediaTag = interactor.getMediaTag(tag)
                _uiState.update { it.copy(isMediaTagLoading = false, mediaTag = mediaTag) }
            } catch (e: DataLoadingException) {
                _uiState.update { it.copy(isMediaTagLoading = false, mediaTagError = e.toString()) }
            }
        }
    }

    companion object {

        val Factory = object : ViewModelProvider.Factory {

            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val interactor = ImgurComponent.create().imgurInteractor
                return DefaultGalleryTagsViewModel(interactor = interactor) as T
            }
        }
    }
}