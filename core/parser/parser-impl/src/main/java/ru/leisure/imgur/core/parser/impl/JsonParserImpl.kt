package ru.leisure.imgur.core.parser.impl

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.core.exc.StreamReadException
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DatabindException
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.ObjectMapper
import ru.leisure.imgur.core.parser.api.JsonParser
import ru.leisure.imgur.core.parser.api.ParserException

class JsonParserImpl(
    private val objectMapper: ObjectMapper
) : JsonParser {

    override fun <T> parse(json: String, reference: TypeReference<T>): T {
        return try {
            objectMapper.readValue(json, reference)
        } catch (e: JsonProcessingException) {
            throw ParserException(e)
        } catch (e: JsonMappingException) {
            throw ParserException(e)
        } catch (e: StreamReadException) {
            throw ParserException(e)
        } catch (e: DatabindException) {
            throw ParserException(e)
        }
    }
}