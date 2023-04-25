package com.enescanpolat.countrytekrar.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.enescanpolat.countrytekrar.model.Country
import com.enescanpolat.countrytekrar.service.countryDatabase
import kotlinx.coroutines.launch

class countryViewModel(application: Application):baseViewModel(application) {

    val countryLiveData = MutableLiveData<Country>()

    fun getDatafromRoom(uuid : Int){

        launch {
            val dao = countryDatabase(getApplication()).countryDao()
            val country = dao.getCountry(uuid)
            countryLiveData.value= country
        }


    }
}