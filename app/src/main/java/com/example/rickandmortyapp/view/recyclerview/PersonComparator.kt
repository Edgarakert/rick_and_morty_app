package com.example.rickandmortyapp.view.recyclerview

import androidx.recyclerview.widget.DiffUtil
import com.example.rickandmortyapp.model.entity.PersonModel

object PersonComparator : DiffUtil.ItemCallback<PersonModel>() {
    override fun areItemsTheSame(oldItem: PersonModel, newItem: PersonModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PersonModel, newItem: PersonModel): Boolean {
        return oldItem == newItem
    }

}