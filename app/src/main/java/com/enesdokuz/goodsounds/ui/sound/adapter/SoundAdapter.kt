package com.enesdokuz.goodsounds.ui.sound.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.enesdokuz.goodsounds.R
import com.enesdokuz.goodsounds.model.Sound
import com.enesdokuz.goodsounds.ui.sound.listener.SoundListener
import kotlinx.android.synthetic.main.item_sound.view.*

class SoundAdapter(private val list: ArrayList<Sound>, val listener: SoundListener) :
    RecyclerView.Adapter<SoundAdapter.Holder>() {

    class Holder(var view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_sound, parent, false)
        return Holder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val context = holder.view.context
        holder.view.txtNameSoundItem.text = list[position].name

        holder.view.seekSoundItem.progress = (list[position].volume * 100).toInt()
        if (list[position].isFavorite) {
            Glide.with(context).load(R.drawable.ic_favorite).into(holder.view.imgFavSoundItem)
        } else {
            Glide.with(context).load(R.drawable.ic_favorite_no).into(holder.view.imgFavSoundItem)
        }
        if (list[position].isPlaying) {
            Glide.with(context).load(R.drawable.ic_pause).into(holder.view.imgPlaySoundItem)
            holder.view.seekSoundItem.isEnabled = true
        } else {
            Glide.with(context).load(R.drawable.ic_play).into(holder.view.imgPlaySoundItem)
            holder.view.seekSoundItem.isEnabled = false
        }

        holder.view.imgFavSoundItem.setOnClickListener {
            list[position].isFavorite = !list[position].isFavorite
            listener.onClickedFav(list[position].id, list[position].isFavorite)
            notifyItemChanged(position)
        }

        holder.view.imgPlaySoundItem.setOnClickListener {
            listener.onClickedPlay(list[position].id, list[position].isPlaying, list[position].link)
            list[position].isPlaying = !list[position].isPlaying
            notifyItemChanged(position)
        }

        holder.view.seekSoundItem?.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                p0?.progress?.toFloat()?.let { listener.onChangedVolume(list[position].id, it/100) }
                println(p0?.progress?.toFloat())
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        }
        )
    }

    fun update(newList: List<Sound>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

}