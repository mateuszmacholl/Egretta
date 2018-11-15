package mateuszmacholl.egretta.utils

// I have no idea in which package it should be
abstract class FactoryContext<type> {
    protected var list: MutableList<type> = mutableListOf()

    fun <conversionGoal  : type> get(typeClass: Class<conversionGoal>): conversionGoal{
        val goal = list.firstOrNull { typeClass.isInstance(it) } ?: throw IllegalArgumentException()
        @Suppress("UNCHECKED_CAST") // safety is implied by the high-level program logic
        return goal as conversionGoal
    }

    abstract fun set(list: MutableList<type>)
}