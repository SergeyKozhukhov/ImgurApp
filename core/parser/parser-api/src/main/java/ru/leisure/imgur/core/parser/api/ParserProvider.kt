package ru.leisure.imgur.core.parser.api

import com.fasterxml.jackson.databind.ObjectMapper

interface ParserProvider {

    val objectMapper: ObjectMapper
}