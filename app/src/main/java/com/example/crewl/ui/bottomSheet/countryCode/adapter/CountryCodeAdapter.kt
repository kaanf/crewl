package com.example.crewl.ui.bottomSheet.countryCode.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.countryflags.getCountryFlagWithResID
import com.example.crewl.R
import com.example.crewl.core.BaseApplication
import com.example.crewl.data.models.Country

internal class CountryCodeAdapter(
    private val onCountryClicked: (Country) -> Unit
) : RecyclerView.Adapter<CountryCodeAdapter.CountryViewHolder>() {
    private var countries: List<Country> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    internal fun setCountries(countries: List<Country>) {
        this.countries = countries
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder =
        LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_country, parent, false)
            .let { CountryViewHolder(it) }
            .also { holder ->
                holder.itemView.setOnClickListener {
                    holder.item?.let { onCountryClicked(it) }
                }
            }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.item = countries[position]
    }

    override fun getItemCount() = countries.size

    class CountryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val applicationContext = BaseApplication.getContext()

        private val countryName = view.findViewById<TextView>(R.id.countryNameTextView)
        private val countryCode = view.findViewById<TextView>(R.id.countryCodeTextView)
        private val countryFlag = view.findViewById<ImageView>(R.id.flagImageView)

        var item: Country? = null
            @SuppressLint("SetTextI18n")
            set(value) {
                field = value

                value?.let {
                    countryName.text = it.countryName
                    countryCode.text = ("(" + it.phoneCode + ")")
                    countryFlag.setImageResource(applicationContext.getCountryFlagWithResID(it.isoCode))
                }
            }
    }
}
