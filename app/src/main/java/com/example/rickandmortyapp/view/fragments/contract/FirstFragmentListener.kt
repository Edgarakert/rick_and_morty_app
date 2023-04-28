package com.example.rickandmortyapp.view.fragments.contract

import com.example.rickandmortyapp.model.entity.PersonModel

interface FirstFragmentListener {
    fun onClickPerson(person: PersonModel)
}