package com.enescanpolat.countrytekrar.viewmodel


import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.enescanpolat.countrytekrar.model.Country
import com.enescanpolat.countrytekrar.service.countryAPIService
import com.enescanpolat.countrytekrar.service.countryDatabase
import com.enescanpolat.countrytekrar.util.customSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class feedViewModel(application: Application):baseViewModel(application) {

    val countries = MutableLiveData<List<Country>>()
    val countryerror = MutableLiveData<Boolean>()
    val countryloading = MutableLiveData<Boolean>()
    private val countryapiservice = countryAPIService()
    private val disposable = CompositeDisposable()
    private var customPreferences = customSharedPreferences(getApplication())
    private var refreshTime = 10 * 60 * 1000 * 1000 * 1000L


    fun refreshData(){

        val updateTime = customPreferences.getTime()
        if (updateTime!=null && updateTime!=0L && System.nanoTime() - updateTime<refreshTime){
            getDataFromSQLite()
        }else{
            getDataFromAPI()
        }


    }


    private fun getDataFromSQLite(){
        countryloading.value==true
        launch {
            val countries = countryDatabase(getApplication()).countryDao().getAllcountries()
            showCountries(countries)
            Toast.makeText(getApplication(),"SQLiteden veri cektik",Toast.LENGTH_LONG).show()
        }
    }

    fun refreshFromAPI(){
        getDataFromAPI()
    }

    private fun getDataFromAPI(){

        countryloading.value=true
        disposable.add(
            countryapiservice.getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Country>>(){
                    override fun onSuccess(t: List<Country>) {
                        storeinSQLite(t)
                        Toast.makeText(getApplication(),"API'dan veri cektik",Toast.LENGTH_LONG).show()
                    }

                    override fun onError(e: Throwable) {
                        countryloading.value=false
                        countryerror.value=true
                        e.printStackTrace()


                    }

                })
        )


    }

    private fun showCountries(countylist : List<Country>){

        countries.value=countylist
        countryerror.value=false
        countryloading.value=false

    }


    private fun storeinSQLite(list : List<Country>){

        launch {
            val dao = countryDatabase(getApplication()).countryDao()
            dao.deleteAllCountries()
            val listLong = dao.insertAll(*list.toTypedArray())//listeyi tek tek haline getiriyor
            var i =0
            while (i<list.size){
                list[i].uuid=listLong[i].toInt()
                i=i+1
            }

            showCountries(list)
        }

        customPreferences.saveTime(System.nanoTime())


    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}