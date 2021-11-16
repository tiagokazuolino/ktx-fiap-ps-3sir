package br.fiap.rm82371.hardware.ong.terraplanetaagua.ui.ph

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.fiap.rm82371.hardware.ong.terraplanetaagua.R
import br.fiap.rm82371.hardware.ong.terraplanetaagua.data.models.OxigenioSensorModel
import br.fiap.rm82371.hardware.ong.terraplanetaagua.data.models.PhSensorModel
import br.fiap.rm82371.hardware.ong.terraplanetaagua.databinding.ViewHolderPhSensorItemBinding
import java.lang.ref.WeakReference

class PHSensorsRecyclerViewAdapter(
    private val callbackWeakRef: WeakReference<PhSensorItemInterface>
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface PhSensorItemInterface {
        fun onPhSensorItemClick(item: PhSensorModel)
    }
    private val phSensorItems = mutableListOf<PhSensorModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PhSensorItemViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PhSensorItemViewHolder).onBind(phSensorItems[position]){
                item -> callbackWeakRef.get()?.onPhSensorItemClick(item)
        }
    }

    override fun getItemCount(): Int {
        return phSensorItems.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(phSensorItems: List<PhSensorModel>) {
        this.phSensorItems.clear()
        this.phSensorItems.addAll(phSensorItems)
        notifyDataSetChanged()
    }

    inner class PhSensorItemViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_ph_sensor_item, parent, false)
    ) {
        private val binding = ViewHolderPhSensorItemBinding.bind(itemView)
        fun onBind(item: PhSensorModel, onClick: (PhSensorModel) -> Unit){
            binding.tvPhViewHolderItemTitle.text = item.name
            binding.tvPhViewHolderItemDescription.text = item.status.toString()
            binding.pbPhViewHolderItem.progress = item.battery!!.toInt()
            binding.tvPhViewHolderItem.text = "${item.battery.toString()} %"
            binding.root.setOnClickListener {
                onClick(item)
            }
        }
    }
}