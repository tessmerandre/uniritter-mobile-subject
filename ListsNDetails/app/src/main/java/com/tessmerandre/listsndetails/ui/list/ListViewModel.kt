package com.tessmerandre.listsndetails.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tessmerandre.listsndetails.data.ListItem
import com.tessmerandre.listsndetails.data.list.ListRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class ListViewModel : ViewModel() {

    // this should be injected...
    private val repository = ListRepository()

    private val _uiState = MutableLiveData<ListUiState>(ListUiState.Loading)
    val uiState: LiveData<ListUiState>
        get() = _uiState

    private val handler = CoroutineExceptionHandler { _, _ -> _uiState.value = ListUiState.Error }

    fun getItems(type: ListType?) {
        if (type == null) return
        viewModelScope.launch(handler) {
            val items = when (type) {
                ListType.ALBUMS -> repository.getAlbums()
                ListType.COMMENTS -> repository.getComments()
                ListType.PHOTOS -> repository.getPhotos()
                ListType.POSTS -> repository.getPosts()
                ListType.TODOS -> repository.getTodos()
                ListType.USERS -> repository.getUsers()
            }

            _uiState.value = ListUiState.Idle(items = items)
        }
    }

}

sealed class ListUiState {
    object Loading : ListUiState()
    object Error : ListUiState()
    class Idle(val items: List<ListItem>) : ListUiState()
}