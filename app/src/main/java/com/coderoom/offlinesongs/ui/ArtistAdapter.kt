package com.coderoom.offlinesongs.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.coderoom.offlinesongs.R
import com.coderoom.offlinesongs.model.Artist
import kotlinx.android.synthetic.main.artist_card.view.*

class ArtistAdapter : RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder>() {

    var artistList: List<Artist> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.artist_card, parent, false)
        return ArtistViewHolder(view)
    }

    override fun getItemCount(): Int = artistList.size

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        holder.bind(artistList[position])
    }

    inner class ArtistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(artist: Artist) {
            itemView.artist_name.text = artist.name
            itemView.artist_synched.visibility = if (artist.synchedWithServer) View.VISIBLE
            else View.GONE
        }
    }
}