package com.example.rickandmortyapp.model.entity

import com.google.gson.annotations.SerializedName

data class PersonsModelList(
    @SerializedName("results")
    val result: List<PersonModel>
) : java.io.Serializable
