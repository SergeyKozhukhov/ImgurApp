package ru.leisure.imgur.core.parser.impl

import com.fasterxml.jackson.databind.ObjectMapper
import dagger.Module
import dagger.Provides

@Module
object ParserModule {

    @Provides
    fun provideObjectMapper(): ObjectMapper {
        return ObjectMapper()
    }
}