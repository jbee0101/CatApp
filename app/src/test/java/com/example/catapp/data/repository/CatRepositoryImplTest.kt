package com.example.catapp.data.repository

import android.util.Log
import com.example.catapp.data.local.CatDao
import com.example.catapp.data.local.CatEntity
import com.example.catapp.data.local.FavoriteEntity
import com.example.catapp.data.mapper.toCat
import com.example.catapp.data.mapper.toCatEntity
import com.example.catapp.data.mapper.toSearchCatEntity
import com.example.catapp.data.model.CatBreedsResponse
import com.example.catapp.data.model.CatWithFavorite
import com.example.catapp.data.model.ImageResponse
import com.example.catapp.data.remote.CatApiService
import com.example.catapp.domain.repository.CatRepository
import io.mockk.every
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.kotlin.mock

@ExperimentalCoroutinesApi
class CatRepositoryImplTest {

    private lateinit var catRepository: CatRepository
    private val catApiService: CatApiService = mock()
    private val catDao: CatDao = mock()

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
        every { Log.e(any(), any()) } returns 0

        Dispatchers.setMain(testDispatcher)
        catRepository = CatRepositoryImpl(catApiService, catDao)
    }

    /**
     * Test for getAllCats() - Verify if the repository correctly fetches all cats from the database
     */
    @Test
    fun `getAllCats returns cats from the database`() = runTest {
        val catsFromDb = listOf(
            CatWithFavorite(
                CatEntity(
                    id = "1",
                    name = "Catty",
                    url = "url",
                    breedDescription = "breedDescription",
                    breedLifeSpan = "breedLifeSpan",
                    breedOrigin = "breedOrigin",
                    breedTemperament = "breedTemperament",
                    breedUrl = "breedUrl",
                    isFavorite = false
                ),
                favorite = null
            ),
            CatWithFavorite(
                CatEntity(
                    id = "1",
                    name = "Catty",
                    url = "url",
                    breedDescription = "breedDescription",
                    breedLifeSpan = "breedLifeSpan",
                    breedOrigin = "breedOrigin",
                    breedTemperament = "breedTemperament",
                    breedUrl = "breedUrl",
                    isFavorite = false
                ),
                favorite = null
            )
        )

        `when`(catDao.getAllCatsWithFavorites()).thenReturn(flowOf(catsFromDb))

        val result = catRepository.getAllCats()

        result.collect { cats ->
            assert(cats.size == catsFromDb.size)
            verify(catDao).getAllCatsWithFavorites()
        }
    }

    /**
     * Test for getFavoriteCats() - Verify if the repository correctly fetches favorite cats
     */
    @Test
    fun `getFavoriteCats returns favorite cats from the database`() = runTest {
        val favoriteCatsFromDb = listOf(
            CatWithFavorite(
                CatEntity(
                    id = "1",
                    name = "Catty",
                    url = "url",
                    breedDescription = "breedDescription",
                    breedLifeSpan = "breedLifeSpan",
                    breedOrigin = "breedOrigin",
                    breedTemperament = "breedTemperament",
                    breedUrl = "breedUrl",
                    isFavorite = true
                ),
                favorite = FavoriteEntity("1")
            )
        )

        `when`(catDao.getFavoriteCats()).thenReturn(flowOf(favoriteCatsFromDb))

        val result = catRepository.getFavoriteCats()

        result.collect { favoriteCats ->
            assert(favoriteCats.size == favoriteCatsFromDb.size)
            verify(catDao).getFavoriteCats()
        }
    }

    /**
     * Test for fetchCats() - Verify if fetchCats correctly interacts with the API and saves data
     */
    @Test
    fun `fetchCats fetches and saves cats data to the database`() = runTest {
        // Arrange: Mock API response
        val catApiResponse = listOf(
            // Assuming CatResponse is a data class, mock its data
            mock<CatBreedsResponse>().apply {
                `when`(this.id).thenReturn("1")
                `when`(this.name).thenReturn("Catty")
                `when`(this.temperament).thenReturn("Angry")
                `when`(this.origin).thenReturn("EU")
                `when`(this.description).thenReturn("Angry Cat")
                `when`(this.lifeSpan).thenReturn("13")
                `when`(this.wikipediaUrl).thenReturn("url")
                `when`(this.referenceImageId).thenReturn("imageId")
            }
        )

        `when`(catApiService.getCatBreeds()).thenReturn(catApiResponse)
        `when`(catApiService.getCatImage("imageId")).thenReturn(ImageResponse(url = "https://mtek3d.com/wp-content/uploads/2018/01/image-placeholder-500x500.jpg"))

        catRepository.fetchCats()

        // Assert: Verify that the API service methods are called and data is inserted into the database
        verify(catApiService).getCatBreeds()
        verify(catApiService).getCatImage("imageId")
        verify(catDao).insertCats(catApiResponse.map { it.toCat().toCatEntity() })
    }

    /**
     * Test for toggleFavorite() - Verify if toggleFavorite correctly updates the favorite status in the database
     */
    @Test
    fun `toggleFavorite adds or removes a cat to-from favorites`() = runTest {
        val catId = "1"
        val isFavorite = true

        catRepository.toggleFavorite(catId, isFavorite)

        verify(catDao).addToFavorites(FavoriteEntity(catId))
    }

    /**
     * Test for searchCats() - Verify if the repository correctly searches cats from API and saves them
     */
    @Test
    fun `searchCats searches and saves cats data to the database`() = runTest {
        val query = "persian"
        val catApiResponse = listOf(
            mock<CatBreedsResponse>().apply {
                `when`(this.id).thenReturn("1")
                `when`(this.name).thenReturn("Catty")
                `when`(this.temperament).thenReturn("Angry")
                `when`(this.origin).thenReturn("EU")
                `when`(this.description).thenReturn("Angry Cat")
                `when`(this.lifeSpan).thenReturn("13")
                `when`(this.wikipediaUrl).thenReturn("url")
                `when`(this.referenceImageId).thenReturn("imageId")
            }
        )

        `when`(catApiService.searchCats(query)).thenReturn(catApiResponse)
        `when`(catApiService.getCatImage("imageId")).thenReturn(ImageResponse(url = "https://mtek3d.com/wp-content/uploads/2018/01/image-placeholder-500x500.jpg"))

        catRepository.searchCats(query)

        // Assert: Verify that the API methods are called and the data is saved in the database
        verify(catApiService).searchCats(query)
        verify(catApiService).getCatImage("imageId")
        verify(catDao).clearSearchCats()
        verify(catDao).insertSearchCats(catApiResponse.map { it.toCat().toSearchCatEntity() })
    }
}
