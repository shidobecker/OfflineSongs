package com.coderoom.offlinesongs.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.coderoom.offlinesongs.model.Artist
import com.coderoom.offlinesongs.repository.ArtistRealm
import com.coderoom.offlinesongs.schedule.ArtistsJob
import com.evernote.android.job.JobManager

class MainActivityViewModel : ViewModel() {

    init {
        scheduleJob()
    }

    var artistList = MutableLiveData<List<Artist>>()

    fun fetchAllArtists() {
        artistList.value = ArtistRealm.fetchArtists()
    }

    fun filterBySearch(query: String) {
        artistList.value?.let { list ->
            artistList.value = list.filter { it.name.startsWith(query) }
        }
    }

    fun saveNewArtist(name: String){
        val artist = Artist(name = name)
        ArtistRealm.saveArtist(artist)
        fetchAllArtists()
    }


    private fun scheduleJob() {
        if (JobManager.instance().getAllJobRequestsForTag(ArtistsJob.TAG).isEmpty())
            ArtistsJob.scheduleJob()
    }



}