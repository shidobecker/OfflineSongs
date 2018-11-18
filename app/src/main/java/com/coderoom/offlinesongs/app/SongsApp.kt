package com.coderoom.offlinesongs.app

import android.app.Application
import android.content.Context
import com.coderoom.offlinesongs.BuildConfig
import com.coderoom.offlinesongs.schedule.SongsJobCreator
import com.evernote.android.job.JobManager
import io.realm.Realm
import io.realm.RealmConfiguration

class SongsApp : Application() {

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
        JobManager.create(this).addJobCreator(SongsJobCreator())
    }

    companion object {

        lateinit var appContext: Context
            private set

        operator fun get(context: Context): SongsApp {
            return context.applicationContext as SongsApp
        }
    }

}
