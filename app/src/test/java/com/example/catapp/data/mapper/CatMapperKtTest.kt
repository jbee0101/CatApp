package com.example.catapp.data.mapper

import com.example.catapp.data.local.CatEntity
import com.example.catapp.data.local.FavoriteEntity
import com.example.catapp.data.local.SearchCatEntity
import com.example.catapp.data.model.CatBreedsResponse
import com.example.catapp.data.model.CatWithFavorite
import com.example.catapp.domain.model.Cat
import com.example.catapp.utils.AppGlobal.DUMMY_URL
import org.junit.Assert.assertEquals
import org.junit.Test

class CatMapperTest {

    /**
     * Verifying CatBreedsResponse to Cat mapping
     */
    @Test
    fun `test CatBreedsResponse to Cat mapping`() {
        val catBreedsResponse = CatBreedsResponse(
            id = "1",
            name = "Persian",
            description = "A calm and friendly breed",
            lifeSpan = "12-16 years",
            origin = "Iran",
            temperament = "Affectionate",
            wikipediaUrl = "https://wikipedia.org/Persian_cat",
            referenceImageId = "ImageId"
        )

        val expectedCat = Cat(
            id = "1",
            name = "Persian",
            url = DUMMY_URL,
            breedDescription = "A calm and friendly breed",
            breedLifeSpan = "12-16 years",
            breedOrigin = "Iran",
            breedTemperament = "Affectionate",
            breedUrl = "https://wikipedia.org/Persian_cat",
            isFavorite = false
        )

        val result = catBreedsResponse.toCat()

        assertEquals(expectedCat, result)
    }

    /**
     * Verifying Cat to CatEntity mapping
     */
    @Test
    fun `test Cat to CatEntity mapping`() {
        val cat = Cat(
            id = "1",
            name = "Persian",
            url = "http://example.com",
            breedDescription = "Calm breed",
            breedLifeSpan = "15 years",
            breedOrigin = "Iran",
            breedTemperament = "Friendly",
            breedUrl = "https://example.com",
            isFavorite = true
        )

        val expectedCatEntity = CatEntity(
            id = "1",
            name = "Persian",
            url = "http://example.com",
            breedDescription = "Calm breed",
            breedLifeSpan = "15 years",
            breedOrigin = "Iran",
            breedTemperament = "Friendly",
            breedUrl = "https://example.com",
            isFavorite = true
        )

        val result = cat.toCatEntity()

        assertEquals(expectedCatEntity, result)
    }

    /**
     * Verifying Cat to SearchCatEntity mapping
     */
    @Test
    fun `test Cat to SearchCatEntity mapping`() {
        val cat = Cat(
            id = "1",
            name = "Siamese",
            url = "http://siamese.com",
            breedDescription = "Energetic and playful",
            breedLifeSpan = "12-20 years",
            breedOrigin = "Thailand",
            breedTemperament = "Playful",
            breedUrl = "https://siamese.com",
            isFavorite = true
        )

        val expectedSearchCatEntity = SearchCatEntity(
            id = "1",
            name = "Siamese",
            url = "http://siamese.com",
            breedDescription = "Energetic and playful",
            breedLifeSpan = "12-20 years",
            breedOrigin = "Thailand",
            breedTemperament = "Playful",
            breedUrl = "https://siamese.com",
            isFavorite = true
        )

        val result = cat.toSearchCatEntity()

        assertEquals(expectedSearchCatEntity, result)
    }

    /**
    CatWithFavorite     */
    @Test
    fun `test CatWithFavorite to Cat mapping`() {
        val catWithFavorite = CatWithFavorite(
            cat = CatEntity(
                id = "2",
                name = "Bengal",
                url = "http://bengal.com",
                breedDescription = "Adventurous and active",
                breedLifeSpan = "10-15 years",
                breedOrigin = "USA",
                breedTemperament = "Energetic",
                breedUrl = "https://bengal.com",
                isFavorite = true
            ),
            favorite = FavoriteEntity("2")
        )

        val expectedCat = Cat(
            id = "2",
            name = "Bengal",
            url = "http://bengal.com",
            breedDescription = "Adventurous and active",
            breedLifeSpan = "10-15 years",
            breedOrigin = "USA",
            breedTemperament = "Energetic",
            breedUrl = "https://bengal.com",
            isFavorite = true
        )

        val result = catWithFavorite.toCat()

        assertEquals(expectedCat, result)
    }

    /**
     * Verifying SearchCatEntity to Cat mapping
     */
    @Test
    fun `test SearchCatEntity to Cat mapping`() {
        val searchCatEntity = SearchCatEntity(
            id = "3",
            name = "Maine Coon",
            url = "http://mainecoon.com",
            breedDescription = "Gentle giant",
            breedLifeSpan = "12-15 years",
            breedOrigin = "USA",
            breedTemperament = "Gentle",
            breedUrl = "https://mainecoon.com",
            isFavorite = false
        )

        val expectedCat = Cat(
            id = "3",
            name = "Maine Coon",
            url = "http://mainecoon.com",
            breedDescription = "Gentle giant",
            breedLifeSpan = "12-15 years",
            breedOrigin = "USA",
            breedTemperament = "Gentle",
            breedUrl = "https://mainecoon.com",
            isFavorite = false
        )

        val result = searchCatEntity.toCat()

        assertEquals(expectedCat, result)
    }
}
