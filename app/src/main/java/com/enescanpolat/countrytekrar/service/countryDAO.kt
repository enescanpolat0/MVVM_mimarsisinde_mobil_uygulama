package com.enescanpolat.countrytekrar.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.enescanpolat.countrytekrar.model.Country

@Dao
interface countryDAO {


    @Insert
    suspend fun insertAll(vararg countries : Country):List<Long>

    //ınsert -> insert into işlemi
    //suspend -> durdurulup devam ettirlieblir fonksiyon couruitnelerde kullanılabilir
    //vararg -> istediğimiz kadar obje eklemeye yarar
    //list long -> primary keyi döndürüyor


    @Query("SELECT * FROM Country")
    suspend fun getAllcountries():List<Country>

    @Query("SELECT * FROM Country WHERE uuid = :countryId")
    suspend fun getCountry(countryId : Int):Country

    @Query("DELETE FROM Country")
    suspend fun deleteAllCountries()

}