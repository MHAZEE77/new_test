package com.app.silky.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.silky.di.NetworkRepository
import com.app.silky.model.Users
import com.app.silky.utils.DataHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
 class OnlineViewModel @Inject constructor(private val networkRepository: NetworkRepository) : ViewModel() {

    private val _userList = MutableLiveData<DataHandler<List<Users>>>()
    val userList: LiveData<DataHandler<List<Users>>> = _userList

    fun getUserList() {
        _userList.postValue(DataHandler.LOADING())
        viewModelScope.launch {
            val response = networkRepository.getUserList()
            _userList.postValue(handleResponse(response))
        }
    }

    private fun handleResponse(response: Response<List<Users>>): DataHandler<List<Users>> {
        if (response.isSuccessful) {
            response.body()?.let { it ->
                return DataHandler.SUCCESS(it)
            }
        }
        return DataHandler.ERROR(message = response.errorBody().toString())
    }
}