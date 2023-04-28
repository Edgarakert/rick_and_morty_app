package com.example.rickandmortyapp.view.recyclerview

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rickandmortyapp.databinding.PersonItemBinding
import com.example.rickandmortyapp.model.entity.PersonModel
import com.example.rickandmortyapp.view.fragments.contract.FirstFragmentListener

class PersonsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding = PersonItemBinding.bind(itemView)

    fun bind(person: PersonModel?, context: Context, listener: FirstFragmentListener) {

        Glide.with(context).load(person?.image).into(binding.personImage)

        binding.apply {
            personName.text = person?.name ?: ""
            personStatus.text = person?.status ?: ""
            personLocation.text = person?.location?.name ?: "unknown"
            personOrigin.text = person?.origin?.name ?: "unknown"

            cardView.setOnClickListener {
                if (person != null)
                    listener.onClickPerson(person)
            }
        }
    }
}