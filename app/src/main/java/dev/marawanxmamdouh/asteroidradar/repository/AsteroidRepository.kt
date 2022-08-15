package dev.marawanxmamdouh.asteroidradar.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import dev.marawanxmamdouh.asteroidradar.Constants
import dev.marawanxmamdouh.asteroidradar.api.AsteroidApi
import dev.marawanxmamdouh.asteroidradar.api.asDatabaseModel
import dev.marawanxmamdouh.asteroidradar.api.parseAsteroidsJsonResult
import dev.marawanxmamdouh.asteroidradar.database.AsteroidDatabase
import dev.marawanxmamdouh.asteroidradar.database.asDomainModel
import dev.marawanxmamdouh.asteroidradar.model.Asteroid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.SocketTimeoutException

enum class Filter { TODAY, WEEK, SAVED }

class AsteroidRepository(private val database: AsteroidDatabase) {

    val asteroids: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidDao.getAsteroids()) {
            it.asDomainModel()
        }

    suspend fun refreshAsteroids(currentDate: String, endDate: String) {
        withContext(Dispatchers.IO) {
            try {
                val result = AsteroidApi.retrofitService.getProperties(
                    Constants.API_KEY,
                    currentDate,
                    endDate
                )
                val asteroids = parseAsteroidsJsonResult(JSONObject(result))
                database.asteroidDao.deleteAsteroids()
                database.asteroidDao.insertAll(*asteroids.asDatabaseModel())
                Log.i("AsteroidRepository", "refreshAsteroids (line 34): $currentDate --- $endDate")
            } catch (e: SocketTimeoutException) {
                Log.i("AsteroidRepository", "refreshAsteroids (line 37): $e")
            } catch (e: Exception) {
                Log.i("AsteroidRepository", "refreshAsteroids (line 39): $e")
            }
        }
    }
}