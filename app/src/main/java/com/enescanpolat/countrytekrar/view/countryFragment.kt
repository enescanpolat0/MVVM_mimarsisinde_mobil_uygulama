package com.enescanpolat.countrytekrar.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.enescanpolat.countrytekrar.R
import com.enescanpolat.countrytekrar.databinding.FragmentCountryBinding
import com.enescanpolat.countrytekrar.util.downloadFromUrl
import com.enescanpolat.countrytekrar.util.placeholderproggresbar
import com.enescanpolat.countrytekrar.viewmodel.countryViewModel


class countryFragment : Fragment() {

    private var _binding : FragmentCountryBinding?=null
    private val binding get() = _binding!!

    private var  countryUuid = 0
    private lateinit var viewModel: countryViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding=FragmentCountryBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {

            countryUuid=countryFragmentArgs.fromBundle(it).countryUuid
        }

        viewModel = ViewModelProviders.of(this).get(countryViewModel::class.java)
        viewModel.getDatafromRoom(countryUuid)
        observeLiveData()
    }

    private fun observeLiveData(){

        viewModel.countryLiveData.observe(viewLifecycleOwner, Observer { country->
            country?.let {
                binding.countryName.text=country.countryName
                binding.countryRegion.text=country.countryRegion
                binding.countryCapital.text=country.countryCapital
                binding.countryCurrency.text=country.countryCurrency
                binding.countryLanguage.text=country.countryLanguage
                context?.let {
                    binding.countryImage.downloadFromUrl(country.imageUrl, placeholderproggresbar(it))
                }


            }
        })

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }


}