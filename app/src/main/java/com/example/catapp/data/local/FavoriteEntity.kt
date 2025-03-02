package com.example.catapp.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey

/**
 * Represents a favorite cat entry in the local database.
 *
 * - This entity stores the IDs of cats that have been marked as favorites.
 * - It establishes a foreign key relationship with `CatEntity`, ensuring that
 *   favorite entries are linked to existing cats.
 * - If a cat is deleted from the `cats` table, its corresponding favorite entry
 *   is also removed (`CASCADE` deletion).
 *
 * @property catId The unique identifier of the favorited cat (also serves as the primary key).
 */
@Entity(
    tableName = "favorites",
    foreignKeys = [ForeignKey(
        entity = CatEntity::class,
        parentColumns = ["id"],
        childColumns = ["catId"],
        onDelete = CASCADE
    )]
)
data class FavoriteEntity(
    @PrimaryKey val catId: String
)