package com.coderoom.offlinesongs.synchronization

import androidx.work.Worker
import com.coderoom.offlinesongs.app.SongsApp
import com.coderoom.offlinesongs.repository.ArtistRealm
import com.coderoom.offlinesongs.utils.NotificationCreator

class ArtistsWorker: Worker() {

    override fun doWork(): Result {
        //We are going to try sending our bookmarked artists to our backend server here
        val notSynchedArtists = ArtistRealm.fetchNotSynchedArtists()

        lateinit var result: Worker.Result

        ArtistRealm.syncArtistsToServer(notSynchedArtists, onSuccess = {
            //We only care if the request is complete, to send a simple notification.

            result = Result.SUCCESS
            notifySyncExecuted()
        }, onError = {
            result = Result.FAILURE
        })

        return result
    }

    private fun notifySyncExecuted() {
        val notificationUtil = NotificationCreator(SongsApp.appContext)
        val notification = notificationUtil.createSyncNotification("Offline Songs",
                "Your data has been sent to our servers :)")
        notificationUtil.notify(NotificationCreator.SYNC_NOTIFY_ID, notification)
    }


}