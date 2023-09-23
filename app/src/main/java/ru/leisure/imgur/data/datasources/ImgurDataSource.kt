package ru.leisure.imgur.data.datasources

import ru.leisure.imgur.data.models.BasicEntity
import ru.leisure.imgur.data.models.ImageEntity

interface ImgurDataSource {

    fun getDefaultMemes(): BasicEntity<List<ImageEntity>>
}