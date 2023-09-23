package ru.leisure.imgur.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.leisure.imgur.data.converters.ImageConverter
import ru.leisure.imgur.data.datasources.ImgurDataSource
import ru.leisure.imgur.domain.ImgurRepository

class ImgurRepositoryImpl(
    private val dataSource: ImgurDataSource,
    private val converter: ImageConverter
) : ImgurRepository {

    override suspend fun getDefaultMemes() = withContext(Dispatchers.IO) {
        val defaultMemes = dataSource.getDefaultMemes()
        converter.convert(defaultMemes.data)
    }
}