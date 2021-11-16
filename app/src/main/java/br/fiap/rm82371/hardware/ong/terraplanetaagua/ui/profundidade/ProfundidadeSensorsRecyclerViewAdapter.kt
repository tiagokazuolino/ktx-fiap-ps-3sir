package br.fiap.rm82371.hardware.ong.terraplanetaagua.ui.profundidade

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.fiap.rm82371.hardware.ong.terraplanetaagua.R
import br.fiap.rm82371.hardware.ong.terraplanetaagua.data.models.ProfundidadeSensorModel
import br.fiap.rm82371.hardware.ong.terraplanetaagua.databinding.ViewHolderProfundidadeSensorItemBinding
import java.lang.ref.WeakReference

class ProfundidadeSensorsRecyclerViewAdapter(
    private val callbackWeakRef: WeakReference<ProfundidadeSensorItemInterface>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface ProfundidadeSensorItemInterface {
        fun onProfundidadeSensorItemClick(item: ProfundidadeSensorModel)
    }

    private val profundidadeSensorItems = mutableListOf<ProfundidadeSensorModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ProfundidadeSensorItemViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ProfundidadeSensorItemViewHolder).onBind(profundidadeSensorItems[position]){
                item -> callbackWeakRef.get()?.onProfundidadeSensorItemClick(item)
        }
    }

    override fun getItemCount(): Int {
        return profundidadeSensorItems.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(profundidadeSensorItems: List<ProfundidadeSensorModel>) {
        this.profundidadeSensorItems.clear()
        this.profundidadeSensorItems.addAll(profundidadeSensorItems)
        notifyDataSetChanged()
    }

    inner class ProfundidadeSensorItemViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_profundidade_sensor_item, parent, false)
    ) {
        private val binding = ViewHolderProfundidadeSensorItemBinding.bind(itemView)
        fun onBind(item: ProfundidadeSensorModel, onClick: (ProfundidadeSensorModel) -> Unit){
            binding.tvProfundidadeViewHolderItemTitle.text = item.name
            binding.tvProfundidadeViewHolderItemDescription.text = item.status.toString()
            binding.pbProfundidadeViewHolderItem.progress = item.battery!!.toInt()
            binding.tvProfundidadeViewHolderItem.text = "${item.battery.toString()} %"
            binding.root.setOnClickListener {
                onClick(item)
            }
        }
    }
}