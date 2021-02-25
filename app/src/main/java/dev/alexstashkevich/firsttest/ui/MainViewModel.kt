package dev.alexstashkevich.firsttest.ui

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.alexstashkevich.firsttest.models.RequestItem
import dev.alexstashkevich.firsttest.repository.IRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: IRepository
) : ViewModel() {

    val requestList: Flow<List<RequestItem>> = repository.requestList

    fun askWolfram(question: String) {
        viewModelScope.launch {
            repository.askWolfram(question)
        }
    }
}