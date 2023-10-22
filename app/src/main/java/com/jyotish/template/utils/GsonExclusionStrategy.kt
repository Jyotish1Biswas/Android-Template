package com.jyotish.template.utils

import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import kotlin.reflect.KClass

class GsonExclusionStrategy(
    vararg val classes: KClass<*>
): ExclusionStrategy {

    override fun shouldSkipField(f: FieldAttributes?): Boolean {
        return f?.getAnnotation(OverLook::class.java) != null
    }

    override fun shouldSkipClass(clazz: Class<*>?): Boolean {
        return classes.any { it == clazz }
    }

}

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class OverLook