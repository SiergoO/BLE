package com.example.bleapplication.presentation.ui.details.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bleapplication.R
import com.example.bleapplication.databinding.ItemServiceBinding
import com.example.bleapplication.domain.ble.ConnectionStatus
import com.example.bleapplication.model.ble.BleService
import com.example.bleapplication.model.ble.BleState
import com.example.bleapplication.presentation.utils.shorten

class ServiceListAdapter(private val context: Context, private val bleState: BleState, private val connectionStatus: ConnectionStatus) :
    RecyclerView.Adapter<ServiceListAdapter.ViewHolder>() {

    companion object {
        private const val ALPHA_STATUS_CONNECTED = 1.0f
        private const val ALPHA_STATUS_DISCONNECTED = 0.4f
        val presenter = ServiceListAdapterPresenter()
    }

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
            presenter.fillExpandStatusList(services.size)
        }
        notifyDataSetChanged()
    }

    fun clearServices() {
        services.clear()
        presenter.clearExpandStatusList()
    }

    inner class ViewHolder(item: ItemServiceBinding) : RecyclerView.ViewHolder(item.root) {

        private lateinit var charListAdapter: CharListAdapter
        private val viewBinding: ItemServiceBinding = item
        private var isExpanded: Boolean = false

        fun bind(service: BleService) {
            charListAdapter = CharListAdapter(bleState, connectionStatus)
            isExpanded = presenter.getExpandStatus(adapterPosition)
            viewBinding.apply {
                serviceName.text = service.name?.takeIf { it.isNotBlank() } ?: context.getString(R.string.unknown_service)
                serviceUuid.text = service.uuid?.shorten()
                charList.apply {
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(context)
                    overScrollMode = RecyclerView.OVER_SCROLL_NEVER
                    adapter = charListAdapter
                    charListAdapter.addChars(service.characteristics)
                    visibility = if (isExpanded) View.VISIBLE else View.GONE
                }
                root.setOnClickListener {
                    presenter.serviceClicked(adapterPosition)
                    isExpanded = presenter.getExpandStatus(adapterPosition)
                    charList.visibility = if (isExpanded) View.VISIBLE else View.GONE
                }
                root.alpha = if (bleState.connectionStatus) ALPHA_STATUS_CONNECTED else ALPHA_STATUS_DISCONNECTED
                connectionStatus.observeStatus {
                    root.alpha = if (it) ALPHA_STATUS_CONNECTED else ALPHA_STATUS_DISCONNECTED
                }
            }
        }
    }
}