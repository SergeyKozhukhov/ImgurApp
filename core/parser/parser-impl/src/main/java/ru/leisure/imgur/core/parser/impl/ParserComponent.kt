package ru.leisure.imgur.core.parser.impl

import dagger.Component
import ru.leisure.imgur.core.parser.api.ParserProvider
import javax.inject.Singleton

@Singleton
@Component(modules = [ParserModule::class])
interface ParserComponent : ParserProvider