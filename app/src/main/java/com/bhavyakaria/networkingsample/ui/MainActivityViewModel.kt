package com.bhavyakaria.networkingsample.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.bhavyakaria.networkingsample.data.Post
import com.bhavyakaria.networkingsample.service.ApiInterface
import kotlinx.coroutines.*

class MainActivityViewModel: ViewModel() {

    private val service: ApiInterface = ApiInterface.create()

    private val parentJob = Job()

    private val scope = CoroutineScope(Dispatchers.Main + parentJob)

    fun fetchPosts(): List<Post>? {

        var postList: List<Post>? = null

        scope.launch {
            val postResponse = service.getPostsAsync().await()

            if (postResponse.isSuccessful) {
                postList = postResponse.body()
                Log.d("Parzival", postList?.size.toString())
            }
        }
        return postList
    }
}