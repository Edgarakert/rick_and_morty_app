package com.example.rickandmortyapp.view.fragments

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmortyapp.R
//import com.example.rickandmortyapp.appComponent
import com.example.rickandmortyapp.databinding.FragmentFirstBinding
import com.example.rickandmortyapp.model.entity.PersonModel
import com.example.rickandmortyapp.utils.Constants
import com.example.rickandmortyapp.utils.appComponent
import com.example.rickandmortyapp.utils.error.CacheException
import com.example.rickandmortyapp.utils.error.ServerException
import com.example.rickandmortyapp.utils.toast
import com.example.rickandmortyapp.view.fragments.contract.FirstFragmentListener
import com.example.rickandmortyapp.view.recyclerview.PersonsPagingAdapter
import com.example.rickandmortyapp.viewmodel.FirstViewModel
import javax.inject.Inject

@Suppress("DEPRECATION")
class FirstFragment : Fragment(), FirstFragmentListener {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var firstViewModel: FirstViewModel

    private lateinit var binding: FragmentFirstBinding
    private var isNotCalled: Boolean = true

    private val pagingAdapter by lazy(LazyThreadSafetyMode.NONE) {
        PersonsPagingAdapter(requireContext(), this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.injectFirstFragment(this)
        firstViewModel =
            ViewModelProviders.of(this@FirstFragment, viewModelFactory)[FirstViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        showPersons()
    }

    private fun setRecyclerView() = with(binding) {
        rcView.layoutManager = LinearLayoutManager(requireContext())
        rcView.adapter = pagingAdapter
    }

    private fun showPersons(query: String? = null) {
        try {
            firstViewModel.getPersonsList(query, pagingAdapter)
            showData()
        } catch (e: ServerException) {
            showException(Constants.NO_INTERNET_CONNECTION)
        } catch (e: CacheException) {
            showException(Constants.CACHE_IS_EMPTY)
        }
    }

    private fun showException(message: String) {
        binding.progressBar.visibility = View.GONE
        requireContext().toast(message)
    }

    private fun showData() = with(binding) {
        var duration: Long
        if (isNotCalled) {
            isNotCalled = false
            duration = 1200L
        } else
            duration = 0L

        Handler().postDelayed({
            progressBar.visibility = View.GONE
            rcView.visibility = View.VISIBLE
        }, duration)
    }

    override fun onClickPerson(person: PersonModel) {
        findNavController().navigate(
            R.id.action_firstFragment_to_secondFragment,
            bundleOf(Constants.PERSON_MODEL_KEY to person, Constants.PERSON_NAME to person.name)
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.first_frag_toolbar, menu)

        val searchView =
            SearchView((requireContext() as AppCompatActivity).supportActionBar?.themedContext!!)

        // Set searchView to toolBar
        menu.findItem(R.id.search_view).actionView = searchView


        // Implement listener for searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = true

            override fun onQueryTextChange(newText: String?): Boolean {
                showPersons(newText)
                return true
            }
        })
    }
}