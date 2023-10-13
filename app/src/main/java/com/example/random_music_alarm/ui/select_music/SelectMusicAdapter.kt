package com.example.random_music_alarm.ui.select_music

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.random_music_alarm.R

data class Selected(
    val selectedIndex: Int? = null,
    val playedIndex: Int? = null
)

class SelectMusicAdapter(
    private var data: List<String>,
    val onClickSelect: (index: Int) -> Unit,
    val onClickPlay : (index : Int) -> Unit
) : RecyclerView.Adapter<SelectMusicAdapter.MyViewHolder>() {

    private var selected =Selected()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val musicTitleText: TextView = itemView.findViewById(R.id.musicTitleText)
        val isSelectedButton  = itemView.findViewById<ImageButton>(R.id.isSelectButton)
        val playPauseButton = itemView.findViewById<ImageButton>(R.id.playPauseButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.custom_music_view, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = data[position]
        holder.musicTitleText.text = item
        if(position == selected.selectedIndex) holder.isSelectedButton.setImageResource(R.drawable.selected_button)
        else holder.isSelectedButton.setImageResource(R.drawable.unselected_button)

        if(position == selected.playedIndex) holder.playPauseButton.setImageResource(R.drawable.pause)
        else holder.playPauseButton.setImageResource(R.drawable.play)

        holder.isSelectedButton.setOnClickListener {
            onClickSelect(position)
        }
        holder.playPauseButton.setOnClickListener {
            onClickPlay(position)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(selectedIndex: Int?, playedIndex: Int?) {
        selected = Selected(selectedIndex,playedIndex)
        notifyDataSetChanged()
        Log.d("RTTT",selected.toString())
    }


}