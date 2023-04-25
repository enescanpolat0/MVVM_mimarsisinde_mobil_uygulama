package com.enescanpolat.countrytekrar.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.enescanpolat.countrytekrar.R
import com.enescanpolat.countrytekrar.adapter.countryAdapter
import com.enescanpolat.countrytekrar.databinding.FragmentFeedBinding
import com.enescanpolat.countrytekrar.model.Country
import com.enescanpolat.countrytekrar.viewmodel.feedViewModel


class feedFragment : Fragment() {

    private var _binding : FragmentFeedBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewmodel : feedViewModel
    private val countryadapter =  countryAdapter(arrayListOf())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFeedBinding.inflate(inflater,container,false)
        val view = binding.root
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
/*
        binding.fragmentButton.setOnClickListener {
            val action = feedFragmentDirections.actionFeedFragmentToCountryFragment()
            Navigation.findNavController(it).navigate(action)
        }
 */

        viewmodel = ViewModelProviders.of(this).get(feedViewModel::class.java)
        viewmodel.refreshData()

        binding.countryList.layoutManager = LinearLayoutManager(context)
        binding.countryList.adapter=countryadapter



        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.countryList.visibility=View.GONE
            binding.countryError.visibility=View.GONE
            binding.countryLoading.visibility=View.VISIBLE
            viewmodel.refreshFromAPI()

            binding.swipeRefreshLayout.isRefreshing=false
        }


        observeLiveData()
    }



    private fun observeLiveData(){
        viewmodel.countries.observe(viewLifecycleOwner, Observer { countries ->

            countries?.let{
                binding.countryList.visibility=View.VISIBLE
                countryadapter.updateCountryList(countries)
            }

        })

        viewmodel.countryerror.observe(viewLifecycleOwner, Observer { error->
            error?.let{
                if(it){
                    binding.countryError.visibility=View.VISIBLE
                }else{
                    binding.countryError.visibility=View.GONE
                }
            }
        })

        viewmodel.countryloading.observe(viewLifecycleOwner, Observer { loading->
            loading?.let {
                if (it){
                    binding.countryLoading.visibility=View.VISIBLE
                    binding.countryList.visibility=View.GONE
                    binding.countryError.visibility=View.GONE
                }else{
                    binding.countryLoading.visibility=View.GONE
                }
            }

        })
    }






    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}