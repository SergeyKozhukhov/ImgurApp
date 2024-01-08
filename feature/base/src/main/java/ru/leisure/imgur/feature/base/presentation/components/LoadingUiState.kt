package ru.leisure.imgur.feature.base.presentation.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun LoadingUiState() {
    ProgressBar(modifier = Modifier.fillMaxSize())
}