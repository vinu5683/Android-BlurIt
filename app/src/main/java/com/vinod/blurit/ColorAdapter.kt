package com.vinod.blurit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.vinod.blurit.databinding.ColorItemLayoutBinding

class ColorAdapter(private val list: List<Int>) :
    RecyclerView.Adapter<ColorAdapter.ColorViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        val binding: ColorItemLayoutBinding =
            ColorItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ColorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {

        Glide.with(holder.binding.ivColorImg).load(
            when ((list[position] % 6) + 1) {
                1 -> R.drawable.color1
                2 -> R.drawable.color2
                3 -> R.drawable.color3
                4 -> R.drawable.color4
                5 -> R.drawable.color5
                6 -> R.drawable.color6
                else -> R.drawable.color1
            }
        ).error(R.drawable.color3)
            .into(holder.binding.ivColorImg)

    }

    override fun getItemCount(): Int {
        return list.size
    }


    inner class ColorViewHolder(val binding: ColorItemLayoutBinding) : ViewHolder(binding.root)

}