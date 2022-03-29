package com.example.androiddata.ui.main

import android.content.Context
import android.content.res.ColorStateList
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androiddata.R
import com.example.androiddata.data.IMonsterItemListener
import com.example.androiddata.data.Monster
import com.example.androiddata.databinding.MainFragmentBinding
import com.example.androiddata.databinding.MonsterGridItemBinding
import com.example.androiddata.utils.LOG_TAG
import java.text.DecimalFormat

class MonsterGridViewAdapter(val monsters: List<Monster>, val onclickListener: MonsterItemListener): RecyclerView.Adapter<MonsterGridViewAdapter.ViewHolder>() {

    inner class ViewHolder( val binding: MonsterGridItemBinding): RecyclerView.ViewHolder(binding.root)
    lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding = MonsterGridItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(this@MonsterGridViewAdapter.monsters[position]){
                holder.binding.root.setOnClickListener { onclickListener.onMonsterItemClick(this) }
                binding.nameText.text = name
                binding.ratingBar.numStars = this.scariness

//                val name = "monster${DecimalFormat("00").format(position+1)}"
//                Log.i(LOG_TAG, "onBindViewHolder: $name")
                //val imageId = context.resources.getIdentifier(imageFile, "drawable", context.packageName)
                //binding.monsterImage.setImageResource(imageId)

                Glide.with(context).load(thumbnailURL).into(binding.monsterImage)

            }
        }
    }

    override fun getItemCount(): Int  = monsters.count()
    interface MonsterItemListener {
        fun onMonsterItemClick(monster: Monster)
    }

}