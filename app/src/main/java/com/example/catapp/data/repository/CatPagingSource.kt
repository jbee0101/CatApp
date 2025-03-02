//package com.example.catapp.data.repository
//
//import android.util.Log
//import androidx.paging.PagingSource
//import androidx.paging.PagingState
//import com.example.catapp.data.local.CatDao
//import com.example.catapp.data.local.CatEntity
//import com.example.catapp.data.mapper.toCat
//import com.example.catapp.data.mapper.toCatEntity
//import com.example.catapp.data.remote.CatApiService
//
////TODO: Will handle this properly at the end
//class CatPagingSource(
//    private val catApiService: CatApiService,
//    private val catDao: CatDao
//) : PagingSource<Int, CatEntity>() {
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CatEntity> {
//        val page = params.key ?: 1 // Default to page 1 if null
//        return try {
//            // Fetch data from the API
//            val catsResponse = catApiService.getCatBreeds()
//
//            val catsWithImages = catsResponse.map { catResponse ->
//                val imageUrl =
//                    if (catResponse.referenceImageId.isNullOrEmpty()) "" else catApiService.getCatImage(
//                        catResponse.referenceImageId
//                    ).url
//                catResponse.toCat().copy(url = imageUrl)
//            }
//
//            Log.d("CatRepository", "Saving ${catsWithImages.size} cats to database")
//
//            catDao.insertCats(catsWithImages.map { it.toCatEntity() })
//
//            // Return the paginated data with pagination info
//            LoadResult.Page(
//                data = catsResponse.map { it.toCatEntity() },
//                prevKey = if (page == 1) null else page - 1, // Previous page key
//                nextKey = if (catsResponse.isEmpty()) null else page + 1 // Next page key
//            )
//        } catch (e: Exception) {
//            LoadResult.Error(e)
//        }
//    }
//
//    override fun getRefreshKey(state: PagingState<Int, CatEntity>): Int? {
//        TODO("Not yet implemented")
//    }
//}
