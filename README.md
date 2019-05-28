# Networking Sample

Sample demonstration of making network calls using retrofit, coroutines and moshi.

## Handling Success, Error and Loading state in Activity
```kotlin
viewModel.fetchPosts().observe(this, Observer {

    when (it?.status) {
        Status.SUCCESS -> {
            Log.d("MainActivity", "---> Success in fetching data")
        }
        Status.ERROR -> {
            Log.d("MainActivity", "---> Error while making network call")
        }
        Status.LOADING -> {
            Log.d("MainActivity", "---> Loading...")
        }
    }
})
```

## Making network call in ViewModel
```kotlin
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
```

## Response Data Class
```kotlin
data class Post (
    var userId: Int,
    var id: Int,
    var title: String,
    var body: String
)
```

## For syncing remote and local data using room and retrofit
Thinking of implementing [Single Source Of Truth](https://en.wikipedia.org/wiki/Single_source_of_truth) 

## Articles that I'm referring
[Oversimplified network call using Retrofit, LiveData, Kotlin Coroutines and DSL](https://proandroiddev.com/oversimplified-network-call-using-retrofit-livedata-kotlin-coroutines-and-dsl-512d08eadc16)
