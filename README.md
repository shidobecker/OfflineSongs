### Intro:
Offline apps works even when your net connection is lost and you can resume the app as if nothing ever happened. And there's huge benefits to this approach:
 - Build an app that works offline allows your users to get an flexible experience and still keeps their data secure. 
 - Your users will be able to use your app without any particular condition, except having it installed.
 - Users from areas with poor or no internet connection at all (like rural areas or during a travel) can still use your app and enjoy it as much as a normal connection area user
 - Users will see this as competitive advantage between you and your competitors
 - Quicker loading times

### Purpose :
This article's purpose is to create an Android "offline-first" application that is heavily tied to it's behavior
To achieve this result, we are going to need a help of some libraries, each one has a role in our flow
 - [Realm](https://realm.io/docs/java/latest/) as one of the best mobile database as of now, specially if you are already a iOS developer, this might give you a good grasp of classes and repositories' flow we are going to use
 - [Workmanager](https://developer.android.com/topic/libraries/architecture/workmanager/) together with [JobScheduler](https://github.com/evernote/android-job) to synchronize all our data with a backend server (which won't be covered in this article)

### App Theme:
Our app theme will be a simple music bookmark. User can type and save it's favorite artists and songs, all in a offline context and will be sent to the backend server by scheduling a task to do it so.

### Let's start:
First of all let's create our Realm Model

```kotlin
  open class Artist(
        @PrimaryKey
        var id: String = UUID.randomUUID().toString(),

        var name: String = "",

        var synchedWithServer: Boolean = false
) : RealmObject()
```

Notice the property `synchedWithServer` this will help us control which object will be synched further. As long as this project evolves, new ways to control it will be shown  

Now let's create some CRUD functions to insert and retrieve our artists from our realm database

```kotlin

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

```

Our function `syncArtistsToServer` will be responsible for sending our local data to the backend server. We are not going to work on it right now, it will just print a message and return success from it.  

### Workers + JobScheduler
The fun part begins now: We are going to use WorkManager in conjuction with JobSchedule to periodically send any non-synched data if there's a connection avaliable.  
Let's build our worker first:

```kotlin

class ArtistsWorker: Worker() {

    override fun doWork(): Result {
    
        lateinit var result: Worker.Result

        ArtistRealm.syncArtistsToServer(notSynchedArtists, onSuccess = {
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


```
The `notifySyncExecuted` function will help us have an idea when our Artist are synched  

Now let's create our Job and JobCreator classes



```kotlin

class ArtistsJob : Job() {

    companion object {
        const val TAG: String = "ARTIST_JOB"

        fun scheduleJob() {
            val periodicInterval = BuildConfig.JOB_PERIODIC_INTERVAL

            val periodicFlex = BuildConfig.JOB_FLEX_INTERVAL

            JobRequest.Builder(TAG)
                    .setPeriodic(TimeUnit.MINUTES.toMillis(periodicInterval),
                            TimeUnit.MINUTES.toMillis(periodicFlex))
                    .setUpdateCurrent(true)
                    .build()
                    .schedule()
        }
    }

    override fun onRunJob(params: Params): Result {
        return if (NetworkUtils.isConnected()) {

            val workManager: WorkManager = WorkManager.getInstance()
            val bookMarkWorker = OneTimeWorkRequest.Builder(ArtistsWorker::class.java)
                    .build()

            val continuation = workManager.beginUniqueWork(ArtistsJob.TAG,
                    ExistingWorkPolicy.REPLACE,
                    bookMarkWorker)
    
            continuation.enqueue()
            Result.SUCCESS
        } else {
            Result.RESCHEDULE
        }
    }


}


class ArtistJobCreator : JobCreator {

    override fun create(tag: String): Job? {
        return ArtistsJob()
    }
}


```
Notice the field: `BuildConfig.JOB_PERIODIC_INTERVAL` this is set as a buildConfigField on our build.gradle(app) file.  
You can change it to any value you want as long as it respect the 15 minutes mininum value.  
You can also create any number of jobs, and return them based on their tag, for example:  

```kotlin
  override fun create(tag: String): Job? = when (tag) {
        ArtistJob.TAG -> ArtistJob()
        SongsJob.TAG -> SongsJob()
        LyricsJobJob.TAG -> LyricsJob()
        else -> null
    }

```

### Initializing everything
We need to extend our Application class to be able to start our JobScheduler and Realm as well:

```kotlin

class SongsApp : Application() {
 
 private var realmConfiguration: RealmConfiguration? = null

    override fun onCreate() {
        super.onCreate()
        Utils.init(this)
        Realm.init(this)

        appContext = applicationContext

        realmConfiguration = RealmConfiguration.Builder()
                .schemaVersion(BuildConfig.REALM_SCHEMA_VERSION.toLong())
                .deleteRealmIfMigrationNeeded()
                .build()

        Realm.setDefaultConfiguration(realmConfiguration!!)

        JobManager.create(this).addJobCreator(ArtistJobCreator())
    }

}

```



