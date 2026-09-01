package com.example.data.repository

import com.example.data.initial.InitialFlashcards
import com.example.data.local.FlashcardDao
import com.example.data.model.CardDifficulty
import com.example.data.model.FlashcardEntity
import com.example.data.model.SubjectType
import kotlinx.coroutines.flow.Flow

class FlashcardRepository(private val dao: FlashcardDao) {

    val allCards: Flow<List<FlashcardEntity>> = dao.getAllCards()

    suspend fun initializeIfNeeded() {
        if (dao.getCardCount() == 0) {
            dao.insertAll(InitialFlashcards.getPreloadedCards())
        }
    }

    suspend fun addCard(
        subject: SubjectType,
        topic: String,
        question: String,
        answer: String,
        formulaOrNote: String = "",
        grade: String = "নবম-দশম শ্রেণি"
    ): Long {
        val newCard = FlashcardEntity(
            subject = subject,
            topic = topic.ifBlank { "ব্যবহারকারী কাস্টম নোট" },
            question = question.trim(),
            answer = answer.trim(),
            formulaOrNote = formulaOrNote.trim(),
            grade = grade,
            difficulty = CardDifficulty.NEW,
            isCustom = true
        )
        return dao.insertCard(newCard)
    }

    suspend fun rateCard(card: FlashcardEntity, difficulty: CardDifficulty) {
        val now = System.currentTimeMillis()
        val nextReview = when (difficulty) {
            CardDifficulty.HARD -> now + (1 * 60 * 1000L) // 1 minute
            CardDifficulty.MEDIUM -> now + (24 * 60 * 60 * 1000L) // 1 day (tomorrow)
            CardDifficulty.EASY -> now + (4 * 24 * 60 * 60 * 1000L) // 4 days
            CardDifficulty.NEW -> now
        }
        val updated = card.copy(
            difficulty = difficulty,
            lastReviewedAt = now,
            nextReviewAt = nextReview,
            reviewCount = card.reviewCount + 1
        )
        dao.updateCard(updated)
    }

    suspend fun toggleBookmark(card: FlashcardEntity) {
        dao.updateCard(card.copy(isBookmarked = !card.isBookmarked))
    }

    suspend fun deleteCard(card: FlashcardEntity) {
        dao.deleteCard(card)
    }

    suspend fun resetAllProgress() {
        dao.deleteAll()
        dao.insertAll(InitialFlashcards.getPreloadedCards())
    }
}
