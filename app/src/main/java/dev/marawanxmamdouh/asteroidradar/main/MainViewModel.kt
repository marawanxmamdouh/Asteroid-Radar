package dev.marawanxmamdouh.asteroidradar.main

import android.app.Application
import androidx.lifecycle.*
import dev.marawanxmamdouh.asteroidradar.database.getDatabase
import dev.marawanxmamdouh.asteroidradar.model.Asteroid
import dev.marawanxmamdouh.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.launch

private const val TAG = "MainViewModel"

class MainViewModel(application: Application) : ViewModel() {

    private val database = getDatabase(application)
    private val asteroidRepository = AsteroidRepository(database)

    init {
        viewModelScope.launch {
            asteroidRepository.refreshAsteroids()
        }
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