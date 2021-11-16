package br.fiap.rm82371.hardware.ong.terraplanetaagua.ui.temperatura

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.fiap.rm82371.hardware.ong.terraplanetaagua.R
import br.fiap.rm82371.hardware.ong.terraplanetaagua.data.models.TemperaturaSensorModel
import br.fiap.rm82371.hardware.ong.terraplanetaagua.databinding.ViewHolderTemperaturaSensorItemBinding
import java.lang.ref.WeakReference

class TemperaturaSensorsRecyclerViewAdapter(
    private val callbackWeakRef: WeakReference<TemperaturaSensorItemInterface>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface TemperaturaSensorItemInterface {
        fun onTemperaturaSensorItemClick(item: TemperaturaSensorModel)
    }

    private val temperaturaSensorItems = mutableListOf<TemperaturaSensorModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TemperaturaSensorItemViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as TemperaturaSensorItemViewHolder).onBind(temperaturaSensorItems[position]){
                item -> callbackWeakRef.get()?.onTemperaturaSensorItemClick(item)
        }
    }

    override fun getItemCount(): Int {
        return temperaturaSensorItems.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(temperaturaSensorItems: List<TemperaturaSensorModel>) {
        this.temperaturaSensorItems.clear()
        this.temperaturaSensorItems.addAll(temperaturaSensorItems)
        notifyDataSetChanged()
    }

    inner class TemperaturaSensorItemViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_temperatura_sensor_item, parent, false)
    ) {
        private val binding = ViewHolderTemperaturaSensorItemBinding.bind(itemView)
        fun onBind(item: TemperaturaSensorModel, onClick: (TemperaturaSensorModel) -> Unit){
            binding.tvTemperaturaViewHolderItemTitle.text = item.name
            binding.tvTemperaturaViewHolderItemDescription.text = item.status.toString()
            binding.pbTemperaturaViewHolderItem.progress = item.battery!!.toInt()
            binding.tvTemperaturaViewHolderItem.text = "${item.battery.toString()} %"
            binding.root.setOnClickListener {
                onClick(item)
            }
        }
    }
}