package br.fiap.rm82371.hardware.ong.terraplanetaagua.utils.slide

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.fiap.rm82371.hardware.ong.terraplanetaagua.R

class IntroSlideAdapter(private val introSlides: List<IntroSlide>): RecyclerView.Adapter<IntroSlideAdapter.IntroSlideViewHolder>() {

    inner class IntroSlideViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val txtTile = view.findViewById<TextView>(R.id.tvTitle)
        private val txtDescriptions = view.findViewById<TextView>(R.id.tvDescriptions)
        private val imgIcon = view.findViewById<ImageView>(R.id.ivSlideIcon)

        fun bind(introSlide: IntroSlide){
            txtTile.text = introSlide.title
            txtDescriptions.text = introSlide.description
            imgIcon.setImageResource(introSlide.icon)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntroSlideViewHolder {
       return IntroSlideViewHolder(
           LayoutInflater.from(parent.context).inflate(
               R.layout.slider_item_container,
               parent,
               false
           )
       )
    }

    override fun onBindViewHolder(holder: IntroSlideViewHolder, position: Int) {
        holder.bind(introSlides[position])
    }

    override fun getItemCount(): Int {
        return introSlides.size
    }
}