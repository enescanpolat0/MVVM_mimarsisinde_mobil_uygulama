package com.enescanpolat.countrytekrar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.enescanpolat.countrytekrar.databinding.ItemCountryBinding
import com.enescanpolat.countrytekrar.model.Country
import com.enescanpolat.countrytekrar.util.downloadFromUrl
import com.enescanpolat.countrytekrar.util.placeholderproggresbar
import com.enescanpolat.countrytekrar.view.feedFragment
import com.enescanpolat.countrytekrar.view.feedFragmentDirections

class countryAdapter(val countryList : ArrayList<Country>):RecyclerView.Adapter<countryAdapter.Countryviewholder>() {


    class Countryviewholder(val binding : ItemCountryBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Countryviewholder {
        val binding = ItemCountryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Countryviewholder(binding)
    }

    override fun onBindViewHolder(holder: Countryviewholder, position: Int) {
        holder.binding.name.text=countryList[position].countryName
        holder.binding.region.text=countryList[position].countryRegion
        holder.itemView.setOnClickListener {
            val action = feedFragmentDirections.actionFeedFragmentToCountryFragment(countryList[position].uuid)
           // action.countryUuid
            Navigation.findNavController(it).navigate(action)
        }
        holder.binding.imageview.downloadFromUrl(countryList[position].imageUrl,
            placeholderproggresbar(holder.itemView.context))
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    fun updateCountryList(newCountryList :List<Country>){
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }


}