package ru.leisure.imgur.core.parser.api

import com.fasterxml.jackson.core.type.TypeReference

interface JsonParser {

    @Throws(ParserException::class)
    fun <T> parse(json: String, reference: TypeReference<T>): T
}