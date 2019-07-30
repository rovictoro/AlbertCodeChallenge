package com.albert.codechallenge.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class WishViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: WishRepository
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allWishes: LiveData<List<Wish>>

    init {
        val wishesDao = WishRoomDatabase.getDatabase(application, viewModelScope).wishDao()
        repository = WishRepository(wishesDao)
        allWishes = repository.allWishes
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(wish: Wish) = viewModelScope.launch {
        repository.insert(wish)
    }

    fun delete(id: Long) = viewModelScope.launch {
        repository.delete(id)
    }
}