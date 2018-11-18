package com.coderoom.offlinesongs.schedule

import com.evernote.android.job.Job
import com.evernote.android.job.JobCreator

class ArtistJobCreator : JobCreator {

    override fun create(tag: String): Job? {
        //Since we only have one job (:D) we are returning it
        return ArtistsJob()
    }
}