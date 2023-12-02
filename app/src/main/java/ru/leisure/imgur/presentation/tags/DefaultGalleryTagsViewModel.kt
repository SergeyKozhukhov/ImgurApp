package ru.leisure.imgur.presentation.tags

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

class DefaultGalleryTagsViewModel(
    private val interactor: ImgurInteractor
) : ViewModel() {

    val uiState: StateFlow<DefaultGalleryTagsUiState> get() = _uiState.asStateFlow()
    private val _uiState: MutableStateFlow<DefaultGalleryTagsUiState> = MutableStateFlow(DefaultGalleryTagsUiState.Loading)

    fun loadGalleryTags() {
        viewModelScope.launch {
            try {
                val tags = interactor.getDefaultGalleryTags()
                _uiState.value = DefaultGalleryTagsUiState.Success(tags = tags)
            } catch (e: DataLoadingException) {
                _uiState.value = DefaultGalleryTagsUiState.Error(message = e.toString())
            }
        }
    }


    companion object {

        val Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                val appComponent = MyApplication.appComponent(application)
                return DefaultGalleryTagsViewModel(interactor = appComponent.imgurInteractor) as T
            }
        }
    }
}