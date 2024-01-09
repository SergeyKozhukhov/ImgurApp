package ru.leisure.imgur.feature.base.presentation.utils

import androidx.compose.foundation.lazy.LazyListState
import kotlin.math.abs

fun LazyListState.findCentralItemIndex(): Int? {
    val layoutInfo = layoutInfo
    val visibleItems = layoutInfo.visibleItemsInfo
    val visibleIndexes = visibleItems.map { it.index }
    return if (visibleIndexes.size == 1) {
        visibleIndexes.first()
    } else {
        val midPoint = (layoutInfo.viewportStartOffset + layoutInfo.viewportEndOffset) / 2
        val itemsFromCenter = visibleItems.sortedBy { abs((it.offset + it.size / 2) - midPoint) }
        itemsFromCenter.firstOrNull()?.index
    }
}