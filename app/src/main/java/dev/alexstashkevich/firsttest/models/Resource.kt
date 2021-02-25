package dev.alexstashkevich.firsttest.models

sealed class Resource {
    data class Success(val answers: List<String>): Resource()
    data class Failure(val message: String): Resource()
    object IDontUnderstand: Resource()
}