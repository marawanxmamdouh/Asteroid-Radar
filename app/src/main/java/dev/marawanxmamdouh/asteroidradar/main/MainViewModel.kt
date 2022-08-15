package dev.marawanxmamdouh.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import dev.marawanxmamdouh.asteroidradar.Constants
import dev.marawanxmamdouh.asteroidradar.api.imageoftheday.IOTDApi
import dev.marawanxmamdouh.asteroidradar.api.imageoftheday.ImageOfTheDay
import dev.marawanxmamdouh.asteroidradar.database.getDatabase
import dev.marawanxmamdouh.asteroidradar.model.Asteroid
import dev.marawanxmamdouh.asteroidradar.repository.AsteroidRepository
import dev.marawanxmamdouh.asteroidradar.repository.Filter
import dev.marawanxmamdouh.asteroidradar.repository.currentDate
import dev.marawanxmamdouh.asteroidradar.repository.endDate
import kotlinx.coroutines.launch

private const val TAG = "MainViewModel"

class MainViewModel(application: Application) : ViewModel() {

    private val database = getDatabase(application)
    private val asteroidRepository = AsteroidRepository(database)

    init {
        viewModelScope.launch {
            asteroidRepository.refreshAsteroids(currentDate, endDate)
        }
        getImageOfTheDay()
    }

    val asteroids = asteroidRepository.asteroids

    /**
     * This part is to handle navigation from main fragment to detail fragment and back
     */
    private val _navigateToDetailFragment = MutableLiveData<Asteroid?>()
    val navigateToDetailFragment: LiveData<Asteroid?>
        get() = _navigateToDetailFragment

    fun navigateToDetailFragment(asteroid: Asteroid) {
        _navigateToDetailFragment.value = asteroid
    }

    fun onNavigateToDetailFragmentComplete() {
        _navigateToDetailFragment.value = null
    }

    /**
     * This part is to get image of the day from api
     */
    private val _imageOfTheDay = MutableLiveData<ImageOfTheDay?>()
    val imageOfTheDay: LiveData<ImageOfTheDay?>
        get() = _imageOfTheDay

    private fun getImageOfTheDay() {
        viewModelScope.launch {
            try {
                val result = IOTDApi.retrofitService.getProperties(Constants.API_KEY)
                _imageOfTheDay.value = result
            } catch (e: Exception) {
                Log.i(TAG, "getIOTD (line 51): ${e.message}")
            }
        }
    }

    fun refreshAsteroids(filter: Filter) {
        viewModelScope.launch {
            if (filter == Filter.TODAY) {
                asteroidRepository.refreshAsteroids(currentDate, currentDate)
            } else {
                asteroidRepository.refreshAsteroids(currentDate, endDate)
            }
        }
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct view model")
        }
    }
}