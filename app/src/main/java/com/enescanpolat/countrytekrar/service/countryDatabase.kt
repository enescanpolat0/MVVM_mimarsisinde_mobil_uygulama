package com.enescanpolat.countrytekrar.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.enescanpolat.countrytekrar.model.Country


@Database(entities = arrayOf(Country::class), version = 1)
abstract class countryDatabase : RoomDatabase() {


    abstract fun countryDao() : countryDAO


    //singleton mantığıyla database oluyştur yoksa çakışma olur

    companion object{

        //volatile -> farklı threadlerde görünür hale getirmeye yarar

        @Volatile private var instance : countryDatabase? = null

        private val lock = Any()

        operator fun invoke(context: Context)= instance?: synchronized(lock){

            instance?: makeDatabase(context).also {
                instance=it
            }

        }


        private fun makeDatabase(context : Context)= Room.databaseBuilder(
            context.applicationContext,countryDatabase::class.java, name = "countrydatabase"
        ).build()

    }


}