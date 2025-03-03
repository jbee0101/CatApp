package com.example.catapp.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CatDaoTest {

    private lateinit var catDao: CatDao
    private lateinit var db: CatDatabase

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            CatDatabase::class.java
        ).allowMainThreadQueries()
            .build()
        catDao = db.catDao()
    }

    /**
     * Insert records in cats table and verify it after retrieving
     */
    @Test
    fun test_insert_and_retrieve_cats() = runBlocking {
        val catEntities = listOf(
            CatEntity(
                id = "1",
                name = "Persian",
                url = "http://persian.com",
                breedDescription = "Friendly",
                breedLifeSpan = "12-16",
                breedOrigin = "Iran",
                breedTemperament = "Calm",
                breedUrl = "http://persian.com",
                isFavorite = false
            ),
            CatEntity(
                id = "2",
                name = "Siamese",
                url = "http://siamese.com",
                breedDescription = "Energetic",
                breedLifeSpan = "10-15",
                breedOrigin = "Thailand",
                breedTemperament = "Playful",
                breedUrl = "http://siamese.com",
                isFavorite = false
            )
        )

        catDao.insertCats(catEntities)

        val cats = catDao.getAllCatsWithFavorites().first()

        assertEquals(2, cats.size)
        assertEquals("Persian", cats[0].cat.name)
        assertEquals("Siamese", cats[1].cat.name)
    }

    /**
     * Insert record in cats table and then add/remove it to favorite table and verify
     */
    @Test
    fun test_add_and_remove_from_favorites() = runBlocking {
        val cat = CatEntity(
            id = "1",
            name = "Persian",
            url = "http://persian.com",
            breedDescription = "Friendly",
            breedLifeSpan = "12-16",
            breedOrigin = "Iran",
            breedTemperament = "Calm",
            breedUrl = "http://persian.com",
            isFavorite = false
        )


        catDao.insertCats(listOf(cat))

        val favorite = FavoriteEntity(catId = "1")
        catDao.addToFavorites(favorite)

        val favoriteCats = catDao.getFavoriteCats().first()

        assertEquals(1, favoriteCats.size)
        assertTrue(favoriteCats[0].favorite != null)

        catDao.removeFromFavorites("1")

        val emptyFavoriteCats = catDao.getFavoriteCats().first()

        assertEquals(0, emptyFavoriteCats.size)
    }

    /**
     * Insert records in search table and verify it. Then clear search table and verify again.
     */
    @Test
    fun test_insert_and_clear_search_cats() = runBlocking {
        val searchCats = listOf(
            SearchCatEntity(
                id = "1",
                name = "Bengal",
                url = "http://bengal.com",
                breedDescription = "Energetic",
                breedLifeSpan = "12-14",
                breedOrigin = "India",
                breedTemperament = "Active",
                breedUrl = "http://bengal.com",
                isFavorite = false
            ),
            SearchCatEntity(
                id = "2",
                name = "Maine Coon",
                url = "http://mainecoon.com",
                breedDescription = "Gentle Giant",
                breedLifeSpan = "10-15",
                breedOrigin = "USA",
                breedTemperament = "Gentle",
                breedUrl = "http://mainecoon.com",
                isFavorite = false
            )
        )

        catDao.insertSearchCats(searchCats)

        val catsFromSearch = catDao.getSearchCatsData().first()

        assertEquals(2, catsFromSearch.size)
        assertEquals("Bengal", catsFromSearch[0].name)

        catDao.clearSearchCats()

        val clearedSearchCats = catDao.getSearchCatsData().first()

        assertEquals(0, clearedSearchCats.size)
    }
}
