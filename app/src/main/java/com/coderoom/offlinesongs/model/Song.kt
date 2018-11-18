package com.coderoom.offlinesongs.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Song(
        @PrimaryKey
        var id: String,
        var name: String,
        var artistId: String,
        var synchedWithServer: Boolean
) : RealmObject()