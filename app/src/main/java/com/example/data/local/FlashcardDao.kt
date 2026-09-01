package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.FlashcardEntity
import com.example.data.model.SubjectType
import kotlinx.coroutines.flow.Flow

@Dao
interface FlashcardDao {
    @Query("SELECT * FROM flashcards ORDER BY id ASC")
    fun getAllCards(): Flow<List<FlashcardEntity>>

    @Query("SELECT * FROM flashcards WHERE subject = :subject ORDER BY id ASC")
    fun getCardsBySubject(subject: SubjectType): Flow<List<FlashcardEntity>>

    @Query("SELECT * FROM flashcards WHERE isCustom = 1 ORDER BY id DESC")
    fun getCustomCards(): Flow<List<FlashcardEntity>>

    @Query("SELECT * FROM flashcards WHERE id = :id LIMIT 1")
    suspend fun getCardById(id: Long): FlashcardEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCard(card: FlashcardEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cards: List<FlashcardEntity>)

    @Update
    suspend fun updateCard(card: FlashcardEntity)

    @Delete
    suspend fun deleteCard(card: FlashcardEntity)

    @Query("DELETE FROM flashcards")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM flashcards")
    suspend fun getCardCount(): Int
}
