package ru.leisure.imgur.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.OkHttpClient
import ru.leisure.imgur.data.ImgurRepositoryImpl
import ru.leisure.imgur.data.converters.ImageConverter
import ru.leisure.imgur.data.datasources.ImgurDataSourceImpl
import ru.leisure.imgur.presentation.memes.MemesScreen
import ru.leisure.imgur.presentation.memes.MemesViewModel
import ru.leisure.imgur.presentation.ui.theme.ImgurAppTheme
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = provideViewModel()

        setContent {
            ImgurAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MemesScreen(viewModel = viewModel)
                }
            }
        }
    }

    private fun provideViewModel(): MemesViewModel {
        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(3000, TimeUnit.MILLISECONDS)
            .writeTimeout(3000, TimeUnit.MILLISECONDS)
            .build()
        val objectMapper = ObjectMapper()
        val dataSource = ImgurDataSourceImpl(okHttpClient, objectMapper)
        val repository = ImgurRepositoryImpl(dataSource, ImageConverter())
        return ViewModelProvider(
            this,
            MemesViewModel.provideFactory(repository)
        )[MemesViewModel::class.java]
    }
}
