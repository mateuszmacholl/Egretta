package mateuszmacholl.egretta.converter

interface Converter<in from, out to>{
    fun convert(from: from): to
    fun convert(from: List<from>): List<to>
}