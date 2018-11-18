package com.coderoom.offlinesongs.schedule

import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.blankj.utilcode.util.NetworkUtils
import com.coderoom.offlinesongs.BuildConfig
import com.coderoom.offlinesongs.synchronization.BookmarkWorker
import com.evernote.android.job.Job
import com.evernote.android.job.JobRequest
import java.util.concurrent.TimeUnit

class BookmarkJob : Job() {

    companion object {
        const val TAG: String = "BOOKMARK_JOB"

        fun scheduleJob() {

            /**
             * This will be the interval that this job will be running
             * The minimum value must be 15 minutes, which is the value we are working with
             */
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
            val bookMarkWorker = OneTimeWorkRequest.Builder(BookmarkWorker::class.java)
                    .build()

            val continuation = workManager.beginUniqueWork(BookmarkJob.TAG,
                    ExistingWorkPolicy.REPLACE,
                    bookMarkWorker)
            /**
             * You can append workers using:
             * continuation.then(another_worker)
             */

            continuation.enqueue()
            Result.SUCCESS
        } else {
            Result.RESCHEDULE
        }
    }


}



