package com.example.bleapplication.presentation.ui.details

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bleapplication.R
import com.example.bleapplication.databinding.ItemServiceBinding
import com.example.bleapplication.model.BleCharacteristic
import com.example.bleapplication.model.BleService
import com.example.bleapplication.model.BleState

class ServiceListAdapter(private val context: Context, private val bleState: BleState) :
    RecyclerView.Adapter<ServiceListAdapter.ViewHolder>() {

    private var services: MutableList<BleService> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemServiceBinding.inflate(LayoutInflater.from(context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(services[position])
    }

    override fun getItemCount(): Int = services.size

    fun addServices(bleServices: List<BleService>?) {
        if (!bleServices.isNullOrEmpty()) {
            services.addAll(bleServices)
        }
        notifyDataSetChanged()
    }

    inner class ViewHolder(item: ItemServiceBinding) : RecyclerView.ViewHolder(item.root) {

        private lateinit var charListAdapter: CharListAdapter
        private val viewBinding: ItemServiceBinding = item
        private var isExpanded: Boolean = false

        fun bind(service: BleService) {
            charListAdapter = CharListAdapter(bleState)
            viewBinding.apply {
                serviceName.text = service.name?.takeIf { it.isNotBlank() } ?: context.getString(R.string.unknown_service)
                serviceUuid.text = service.uuid?.toString()?.take(6)
                charList.apply {
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(context)
                    overScrollMode = RecyclerView.OVER_SCROLL_NEVER
                    adapter = charListAdapter
                    charListAdapter.addChars(service.characteristics)
                }
                root.setOnClickListener {
                    isExpanded = !isExpanded
                    charList.visibility =
                        if (isExpanded) View.VISIBLE else View.GONE
                }
            }
        }
    }
}