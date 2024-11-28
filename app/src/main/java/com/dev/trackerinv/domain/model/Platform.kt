package com.dev.trackerinv.domain.model

enum class Platform(val displayName: String) {
    AMAZON("Amazon"),
    FLIPKART("Flipkart"),
    MYNTRA("Myntra"),
    AJIO("Ajio"),
    MEESHO("Meesho"),
    OFFLINE("Offline");

    override fun toString(): String {
        return displayName
    }
}
