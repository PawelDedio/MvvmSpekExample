package com.dedio.spekexample.common.features

interface HasComponent<C> {

    fun getComponent(): C?
}