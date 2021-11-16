package br.fiap.rm82371.hardware.ong.terraplanetaagua.ui.oxigenio

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import br.fiap.rm82371.hardware.ong.terraplanetaagua.R
import br.fiap.rm82371.hardware.ong.terraplanetaagua.data.models.OxigenioSensorModel
import br.fiap.rm82371.hardware.ong.terraplanetaagua.databinding.ViewHolderOxigenioSensorItemBinding
import java.lang.ref.WeakReference

class OxigenioSensorsRecyclerViewAdapter(
    private val callbackWeakRef: WeakReference<OxigenioSensorItemInterface>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface OxigenioSensorItemInterface {
        fun onOxigenioSensorItemClick(item: OxigenioSensorModel)
    }

    private val oxigenioSensorItems = mutableListOf<OxigenioSensorModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return OxigenioSensorItemViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as OxigenioSensorItemViewHolder).onBind(oxigenioSensorItems[position]){
            item -> callbackWeakRef.get()?.onOxigenioSensorItemClick(item)
        }
    }

    override fun getItemCount(): Int {
        return oxigenioSensorItems.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(oxigenioSensorItems: List<OxigenioSensorModel>) {
        this.oxigenioSensorItems.clear()
        this.oxigenioSensorItems.addAll(oxigenioSensorItems)
        notifyDataSetChanged()
    }

    inner class OxigenioSensorItemViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_oxigenio_sensor_item, parent, false)
    ) {
        private val binding = ViewHolderOxigenioSensorItemBinding.bind(itemView)
        fun onBind(item: OxigenioSensorModel, onClick: (OxigenioSensorModel) -> Unit){

            binding.tvOxigenioViewHolderItemTitle.text = item.name
            binding.tvOxigenioViewHolderItemDescription.text = item.status.toString()
            binding.pbOxigenioViewHolderItem.progress = item.battery!!.toInt()
            binding.tvOxigenioViewHolderItem.text = "${item.battery.toString()} %"
            binding.root.setOnClickListener {
                onClick(item)
            }
        }
    }
}