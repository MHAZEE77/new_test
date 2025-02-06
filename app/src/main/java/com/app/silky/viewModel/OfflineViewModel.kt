package com.app.silky.viewModel

import androidx.lifecycle.*
import com.app.silky.di.DBRepository
import com.app.silky.di.Transformer
import com.app.silky.model.Users
import com.app.silky.utils.DataHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OfflineViewModel @Inject constructor(private val dbRepository: DBRepository) : ViewModel() {

    var userList = Transformations.map(dbRepository.getAllArticles()) { list ->

        val temp = list.map {
            Transformer.convertUserEntityToUserModelModel(it)
        }

        if (temp.isNullOrEmpty()) {
            DataHandler.ERROR(null, "LIST IS EMPTY OR NULL")
        } else {
            DataHandler.SUCCESS(temp)
        }
    }

    fun insertArticle(user: Users) {
        viewModelScope.launch {
            dbRepository.insertArticle(user)
        }
    }

}