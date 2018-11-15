package mateuszmacholl.egretta.utils

import org.springframework.beans.factory.annotation.Autowired

// I have no idea in which package it should be
abstract class FactoryContext<type> {
    protected var list: MutableList<type> = mutableListOf()

    fun <conversionGoal  : type> get(typeClass: Class<conversionGoal>): conversionGoal{
        val goal = list.firstOrNull { typeClass.isInstance(it) } ?: throw IllegalArgumentException()
        @Suppress("UNCHECKED_CAST") // safety is implied by the high-level program logic
        return goal as conversionGoal
    }

    @Autowired
    fun set(list: MutableList<type>){
        this.list = list
    }
}