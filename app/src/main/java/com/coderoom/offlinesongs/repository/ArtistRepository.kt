package com.coderoom.offlinesongs.repository

import com.coderoom.offlinesongs.model.Artist

interface ArtistRepository {

    fun saveArtist(artist: Artist)

    fun fetchArtists(): List<Artist>

    fun fetchNotSynchedArtists(): List<Artist>

    fun syncArtistsToServer(artists: List<Artist>, onSuccess: () -> Unit, onError: () -> Unit)
}