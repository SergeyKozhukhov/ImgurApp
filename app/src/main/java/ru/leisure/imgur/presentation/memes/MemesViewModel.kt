package ru.leisure.imgur.presentation.memes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.leisure.imgur.domain.ImgurRepository

class MemesViewModel(
    private val repository: ImgurRepository
) : ViewModel() {

    val uiState: StateFlow<MemesUiState> get() = _uiState.asStateFlow()
    private val _uiState: MutableStateFlow<MemesUiState> = MutableStateFlow(MemesUiState.Loading)

    private val exceptionHandler = CoroutineExceptionHandler { _, e ->
        _uiState.value = MemesUiState.Error(message = e.toString())
    }

    fun loadMemes() {
        viewModelScope.launch(exceptionHandler) {
            val memes = repository.getDefaultMemes()
            _uiState.value = MemesUiState.Success(memes = memes)
        }
    }


    companion object {

        @Suppress("UNCHECKED_CAST")
        fun provideFactory(repository: ImgurRepository) = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MemesViewModel(repository) as T
            }
        }
    }
}