package mateuszmacholl.egretta.converter

import mateuszmacholl.egretta.utils.FactoryContext
import org.springframework.stereotype.Service

@Service //factory class
class ConverterContext: FactoryContext<Converter<*, *>>() {
}