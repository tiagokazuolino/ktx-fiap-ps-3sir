package br.fiap.rm82371.hardware.ong.terraplanetaagua.ui.temperatura.detail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.fiap.rm82371.hardware.ong.terraplanetaagua.R
import br.fiap.rm82371.hardware.ong.terraplanetaagua.data.utils.DataSensor
import br.fiap.rm82371.hardware.ong.terraplanetaagua.databinding.ViewHolderTemperaturaDetailItemBinding
import java.text.SimpleDateFormat

class TemperaturaDetailRecyclerViewAdapter(
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val temperaturaSensorItems = mutableListOf<DataSensor>()
    var pattern = "dd/MM/yyyy"
    var simpleDateFormat = SimpleDateFormat(pattern)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TemperaturaDetailItemViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as TemperaturaDetailItemViewHolder).onBind(temperaturaSensorItems[position])
    }

    override fun getItemCount(): Int {
        return temperaturaSensorItems.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(temperaturaSensorItems: List<DataSensor>) {
        this.temperaturaSensorItems.clear()
        this.temperaturaSensorItems.addAll(temperaturaSensorItems)
        notifyDataSetChanged()
    }

    inner class TemperaturaDetailItemViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_temperatura_detail_item, parent, false)
    ) {
        private val binding = ViewHolderTemperaturaDetailItemBinding.bind(itemView)
        fun onBind(item: DataSensor){
            binding.apply {
                tvMin.text = String.format("%.3f",item.min)
                tvMax.text = String.format("%.3f",item.max)
                tvDate.text = simpleDateFormat.format(item.eventAt!!)
            }
        }
    }
}