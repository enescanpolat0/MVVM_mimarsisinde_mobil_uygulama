package com.enescanpolat.countrytekrar.service

import com.enescanpolat.countrytekrar.model.Country
import io.reactivex.Single
import retrofit2.http.GET

interface countryAPI {

    //https://raw.githubusercontent.com/  base url
    //atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json  ext url

    @GET("atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json")
    fun getCountries():Single<List<Country>>

}