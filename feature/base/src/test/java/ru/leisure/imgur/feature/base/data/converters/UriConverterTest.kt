package ru.leisure.imgur.feature.base.data.converters

import com.google.common.truth.Truth
import org.junit.Test
import java.net.URI

internal class UriConverterTest {

    private val converter = UriConverter()

    @Test
    fun `convert if source is null`() {
        val output = converter.convert(null)
        Truth.assertThat(output).isNull()
    }

    @Test
    fun `convert if source is incorrect`() {
        val output = converter.convert("//https:/url?{{incorrect}}")
        Truth.assertThat(output).isNull()
    }

    @Test
    fun convert() {
        val input = "https://kotlinlang.org/docs/coroutines-overview.html"
        val output = converter.convert(input)
        Truth.assertThat(output).isEqualTo(URI(input))
    }
}