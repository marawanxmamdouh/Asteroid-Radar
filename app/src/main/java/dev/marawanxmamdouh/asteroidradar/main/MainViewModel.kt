package dev.marawanxmamdouh.asteroidradar.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.marawanxmamdouh.asteroidradar.Constants.API_KEY
import dev.marawanxmamdouh.asteroidradar.api.AsteroidApi
import dev.marawanxmamdouh.asteroidradar.api.parseAsteroidsJsonResult
import dev.marawanxmamdouh.asteroidradar.model.Asteroid
import kotlinx.coroutines.launch
import org.json.JSONObject

private const val TAG = "MainViewModel"

class MainViewModel : ViewModel() {

    private val _asteroids = MutableLiveData<List<Asteroid>>()
    val asteroids: LiveData<List<Asteroid>>
        get() = _asteroids

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            try {
                val result = AsteroidApi.retrofitService.getProperties(API_KEY)
                _asteroids.value = parseAsteroidsJsonResult(JSONObject(result))
                Log.i(
                    TAG,
                    "fetchData (line 34):  ${_asteroids.value?.size} *** ${_asteroids.value?.get(0)}"
                )
            } catch (e: Exception) {
                Log.i(TAG, "getAsteroids (line 37): ${e.message}")
            }
        }
    }
}