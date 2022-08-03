package com.zeira.fuelua.utils

object CustomKt {
    inline fun <T> forThem(vararg objs: T, block: T.() -> Unit) {
        for (obj in objs)
            obj.block()
    }

}