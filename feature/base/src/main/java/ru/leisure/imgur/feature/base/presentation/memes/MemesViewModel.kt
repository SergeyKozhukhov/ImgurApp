package ru.leisure.imgur.feature.base.presentation.memes

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

class MemesViewModel(
    private val interactor: ImgurInteractor
) : ViewModel() {

    val uiState: StateFlow<MemesUiState> get() = _uiState.asStateFlow()
    private val _uiState: MutableStateFlow<MemesUiState> = MutableStateFlow(MemesUiState.Loading)


    fun loadMemes() {
        viewModelScope.launch {
            try {
                val memes = interactor.getDefaultMemes()
                _uiState.value = MemesUiState.Success(memes = memes)
            } catch (e: DataLoadingException) {
                _uiState.value = MemesUiState.Error(message = e.toString())
            }
        }
    }


    companion object {

        val Factory = object : ViewModelProvider.Factory {

            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val interactor = ImgurComponent.create().imgurInteractor
                return MemesViewModel(interactor = interactor) as T
            }
        }
    }
}