package dev.marawanxmamdouh.asteroidradar.work


import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dev.marawanxmamdouh.asteroidradar.database.getDatabase
import dev.marawanxmamdouh.asteroidradar.repository.AsteroidRepository
import dev.marawanxmamdouh.asteroidradar.repository.currentDate
import dev.marawanxmamdouh.asteroidradar.repository.endDate
import retrofit2.HttpException

class RefreshDataWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        val database = getDatabase(applicationContext)
        val repository = AsteroidRepository(database)

        return try {
            repository.refreshAsteroids(currentDate, endDate)
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}