package com.zhogin.reciepeapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform