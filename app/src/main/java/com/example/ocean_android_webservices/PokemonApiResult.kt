package com.example.ocean_android_webservices

data class PokemonApiResult(
    val count: Int,
    val next: String?,  // ? pode retornar null
    val previous: String?,
    val results: List<Pokemon>
)

data class Pokemon(
    val name: String,
    val url: String
)