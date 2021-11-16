package br.fiap.rm82371.hardware.ong.terraplanetaagua.ui.ph.detail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.fiap.rm82371.hardware.ong.terraplanetaagua.R
import br.fiap.rm82371.hardware.ong.terraplanetaagua.data.utils.DataSensor
import br.fiap.rm82371.hardware.ong.terraplanetaagua.databinding.ViewHolderPHDetailItemBinding
import java.text.SimpleDateFormat

class PHDetailRecyclerViewAdapter(
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val phSensorItems = mutableListOf<DataSensor>()
    var pattern = "dd/MM/yyyy"
    var simpleDateFormat = SimpleDateFormat(pattern)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PHDetailItemViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PHDetailItemViewHolder).onBind(phSensorItems[position])
    }

    override fun getItemCount(): Int {
        return phSensorItems.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(phSensorItems: List<DataSensor>) {
        this.phSensorItems.clear()
        this.phSensorItems.addAll(phSensorItems)
        notifyDataSetChanged()
    }

    inner class PHDetailItemViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_p_h_detail_item, parent, false)
    ) {
        private val binding = ViewHolderPHDetailItemBinding.bind(itemView)
        fun onBind(item: DataSensor){
            binding.apply {
                tvMin.text = String.format("%.3f",item.min)
                tvMax.text = String.format("%.3f",item.max)
                tvDate.text = simpleDateFormat.format(item.eventAt!!)
            }
        }
    }
}