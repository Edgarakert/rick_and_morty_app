package com.example.rickandmortyapp.view.recyclerview

import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.databinding.PersonItemBinding
import com.example.rickandmortyapp.model.entity.PersonModel
import com.example.rickandmortyapp.view.fragments.contract.FirstFragmentListener

class PersonsPagingAdapter(
    context: Context,
    private val listener: FirstFragmentListener
) : PagingDataAdapter<PersonModel, PersonsHolder>(PersonComparator) {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onBindViewHolder(holder: PersonsHolder, position: Int) {
        val item = getItem(position)

        holder.bind(item, layoutInflater.context, listener)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PersonsHolder {
        val inflater = layoutInflater.inflate(R.layout.person_item, parent, false)

        return PersonsHolder(inflater)
    }
}