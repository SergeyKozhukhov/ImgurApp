package ru.leisure.imgur.core.parser.impl

import com.fasterxml.jackson.databind.ObjectMapper
import dagger.Module
import dagger.Provides
import ru.leisure.imgur.core.parser.api.JsonParser

@Module
object ParserModule {

    @Provides
    fun provideJsonParser(objectMapper: ObjectMapper): JsonParser {
        return JsonParserImpl(objectMapper)
    }

    @Provides
    fun provideObjectMapper(): ObjectMapper {
        return ObjectMapper()
    }
}