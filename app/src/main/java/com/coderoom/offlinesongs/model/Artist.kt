package com.coderoom.offlinesongs.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class Artist(
        @PrimaryKey
        var id: String = UUID.randomUUID().toString(),

        var name: String = "",

        var songs: RealmList<Song> = RealmList(),

        //We are going to use this to control with that artist is already synched to avoid duplicate data
        var synchedWithServer: Boolean = false
) : RealmObject()