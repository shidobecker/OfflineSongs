package com.coderoom.offlinesongs.app

import android.content.Context
import androidx.multidex.MultiDexApplication
import com.coderoom.offlinesongs.BuildConfig
import com.coderoom.offlinesongs.schedule.ArtistJobCreator
import com.evernote.android.job.JobManager
import io.realm.Realm
import io.realm.RealmConfiguration

class SongsApp : MultiDexApplication() {

    private var realmConfiguration: RealmConfiguration? = null

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)

        appContext = applicationContext

        //As of now we are deleting our database in case some migration is needed
        realmConfiguration = RealmConfiguration.Builder()
                .schemaVersion(BuildConfig.REALM_SCHEMA_VERSION.toLong())
                .deleteRealmIfMigrationNeeded()
                .build()

        Realm.setDefaultConfiguration(realmConfiguration!!)

        //Initializing our JobCreator
        JobManager.create(this).addJobCreator(ArtistJobCreator())
    }

    companion object {

        lateinit var appContext: Context
            private set

        operator fun get(context: Context): SongsApp {
            return context.applicationContext as SongsApp
        }
    }

}
