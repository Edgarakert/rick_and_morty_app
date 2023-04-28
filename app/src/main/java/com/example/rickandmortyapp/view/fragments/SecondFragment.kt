package com.example.rickandmortyapp.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.rickandmortyapp.databinding.FragmentSecondBinding
import com.example.rickandmortyapp.model.entity.PersonModel
import com.example.rickandmortyapp.utils.Constants

@Suppress("DEPRECATION")
class SecondFragment : Fragment() {
    private lateinit var binding: FragmentSecondBinding
    lateinit var person: PersonModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        person = requireArguments().getSerializable(Constants.PERSON_MODEL_KEY) as PersonModel

        showPerson()
    }

    private fun showPerson() = with(binding) {
        Glide.with(this@SecondFragment)
            .load(person.image)
            .into(personImage)

        personStatus.text = person.status
        personLocation.text = person.location?.name ?: "unknown"
        personOrigin.text = person.origin?.name ?: "unknown"

    }
}