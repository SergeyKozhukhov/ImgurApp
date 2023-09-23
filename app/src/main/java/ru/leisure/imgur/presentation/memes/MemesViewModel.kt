package ru.leisure.imgur.presentation.memes

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
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                val diContainer = MyApplication.diContainer(application)
                return MemesViewModel(interactor = diContainer.imgurInteractor) as T
            }
        }
    }
}