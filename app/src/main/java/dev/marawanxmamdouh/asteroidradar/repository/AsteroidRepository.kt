package dev.marawanxmamdouh.asteroidradar.repository

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

class AsteroidRepository(private val database: AsteroidDatabase) {

    val asteroids: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidDao.getAsteroids()) {
            it.asDomainModel()
        }

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            val result = AsteroidApi.retrofitService.getProperties(Constants.API_KEY)
            val asteroids = parseAsteroidsJsonResult(JSONObject(result))
            database.asteroidDao.insertAll(*asteroids.asDatabaseModel())
        }
    }
}