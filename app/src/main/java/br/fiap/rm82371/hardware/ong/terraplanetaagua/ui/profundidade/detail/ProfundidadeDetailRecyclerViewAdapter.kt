package br.fiap.rm82371.hardware.ong.terraplanetaagua.ui.profundidade.detail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.fiap.rm82371.hardware.ong.terraplanetaagua.R
import br.fiap.rm82371.hardware.ong.terraplanetaagua.data.utils.DataSensor
import br.fiap.rm82371.hardware.ong.terraplanetaagua.databinding.ViewHolderProfundidadeDetailItemBinding
import java.text.SimpleDateFormat

class ProfundidadeDetailRecyclerViewAdapter(
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val profundidadeSensorItems = mutableListOf<DataSensor>()
    var pattern = "dd/MM/yyyy"
    var simpleDateFormat = SimpleDateFormat(pattern)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ProfundidadeDetailItemViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ProfundidadeDetailItemViewHolder).onBind(profundidadeSensorItems[position])
    }

    override fun getItemCount(): Int {
        return profundidadeSensorItems.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(profundidadeSensorItems: List<DataSensor>) {
        this.profundidadeSensorItems.clear()
        this.profundidadeSensorItems.addAll(profundidadeSensorItems)
        notifyDataSetChanged()
    }

    inner class ProfundidadeDetailItemViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_profundidade_detail_item, parent, false)
    ) {
        private val binding = ViewHolderProfundidadeDetailItemBinding.bind(itemView)
        fun onBind(item: DataSensor){
            binding.apply {
                tvMin.text = String.format("%.3f",item.min)
                tvMax.text = String.format("%.3f",item.max)
                tvDate.text = simpleDateFormat.format(item.eventAt!!)
            }
        }
    }
}