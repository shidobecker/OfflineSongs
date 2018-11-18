package com.coderoom.offlinesongs.repository

import android.annotation.SuppressLint
import android.util.Log
import com.coderoom.offlinesongs.model.Artist
import com.vicpin.krealmextensions.query
import com.vicpin.krealmextensions.queryAll
import com.vicpin.krealmextensions.save


object ArtistRealm : ArtistRepository {

    override fun saveArtist(artist: Artist) {
        artist.save() //Realm extensions make saving and fetching really simple
    }

    override fun fetchNotSynchedArtists(): List<Artist> {
        return Artist().query { equalTo("synchedWithServer", false) }
    }

    override fun fetchArtists(): List<Artist> {
        return Artist().queryAll()
    }

    @SuppressLint("LogNotTimber")
    override fun syncArtistsToServer(artists: List<Artist>, onSuccess: () -> Unit, onError: () -> Unit) {
        try{
            Log.i("Artist Realm", "Sending data to backend...")

            //Set artists are synched so we won't try to send them again
            setArtistsAreSynched(artists)
            onSuccess()
        }catch (exception : Exception){
            onError()
        }
    }

    private fun setArtistsAreSynched(artists: List<Artist>){
        artists.forEach {artist ->
            artist.synchedWithServer = true
            artist.save()
        }
    }

}