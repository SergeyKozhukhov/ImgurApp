package ru.leisure.imgur.domain

import ru.leisure.imgur.domain.models.DataLoadingException
import ru.leisure.imgur.domain.models.Image
import kotlin.jvm.Throws

interface ImgurInteractor {

    @Throws(DataLoadingException::class)
    suspend fun getDefaultMemes(): List<Image>
}