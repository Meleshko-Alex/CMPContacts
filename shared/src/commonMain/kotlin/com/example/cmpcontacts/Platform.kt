package com.example.cmpcontacts

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform