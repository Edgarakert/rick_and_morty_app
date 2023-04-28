package com.example.rickandmortyapp.model.entity

data class PersonModel(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: Location?,
    val location: Location?,
    val image: String,
    val episode: List<String>,
    val created: String
) : java.io.Serializable