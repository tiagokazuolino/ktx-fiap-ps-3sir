package br.fiap.rm82371.hardware.ong.terraplanetaagua.ui.oxigenio.detail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.fiap.rm82371.hardware.ong.terraplanetaagua.R
import br.fiap.rm82371.hardware.ong.terraplanetaagua.data.utils.DataSensor
import br.fiap.rm82371.hardware.ong.terraplanetaagua.databinding.ViewHolderOxigenioDetailItemBinding
import java.lang.ref.WeakReference
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class OxigenioDetailRecyclerViewAdapter(
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val oxigenioSensorItems = mutableListOf<DataSensor>()
    var pattern = "dd/MM/yyyy"
    var simpleDateFormat = SimpleDateFormat(pattern)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return OxigenioDetailItemViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as OxigenioDetailItemViewHolder).onBind(oxigenioSensorItems[position])
    }

    override fun getItemCount(): Int {
        return oxigenioSensorItems.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(oxigenioSensorItems: List<DataSensor>) {
        this.oxigenioSensorItems.clear()
        this.oxigenioSensorItems.addAll(oxigenioSensorItems)
        notifyDataSetChanged()
    }

    inner class OxigenioDetailItemViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_oxigenio_detail_item, parent, false)
    ) {
        private val binding = ViewHolderOxigenioDetailItemBinding.bind(itemView)
        fun onBind(item: DataSensor){
            binding.apply {
                tvMin.text = String.format("%.3f",item.min)
                tvMax.text = String.format("%.3f",item.max)
                tvDate.text = simpleDateFormat.format(item.eventAt!!)
            }
        }
    }
}