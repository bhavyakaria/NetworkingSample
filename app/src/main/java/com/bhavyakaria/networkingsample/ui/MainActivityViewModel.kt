package com.bhavyakaria.networkingsample.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bhavyakaria.networkingsample.data.Post
import com.bhavyakaria.networkingsample.service.ApiInterface
import com.bhavyakaria.networkingsample.utils.Resource
import kotlinx.coroutines.*

class MainActivityViewModel: ViewModel() {

    private val service: ApiInterface = ApiInterface.create()

    private val parentJob = Job()

    private val scope = CoroutineScope(Dispatchers.Main + parentJob)

    fun fetchPosts(): MutableLiveData<Resource<List<Post>>> {

        val result = MutableLiveData<Resource<List<Post>>>()
        result.value = Resource.loading(null)

        scope.launch {
            val postResponse = service.getPostsAsync().await()
            if (postResponse.isSuccessful) {
                withContext(Dispatchers.Main) {
                    result.value = Resource.success(postResponse.body())
                }
            } else {
                withContext(Dispatchers.Main) {
                    result.value = Resource.error("Unable to get posts", null)
                }
            }
        }
        return result
    }
}