package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class SubjectType(val displayNameBn: String, val displayNameEn: String) {
    PHYSICS("পদার্থবিজ্ঞান", "Physics"),
    CHEMISTRY("রসায়ন", "Chemistry"),
    MATH("গণিত", "Mathematics"),
    CUSTOM("কাস্টম", "Custom")
}

enum class CardDifficulty(val labelBn: String, val reviewIntervalMinutes: Long) {
    HARD("কঠিন (১ মিনিট)", 1L),
    MEDIUM("মাঝারি (১ দিন)", 24 * 60L),
    EASY("সহজ (৪ দিন)", 4 * 24 * 60L),
    NEW("নতুন", 0L)
}

@Entity(tableName = "flashcards")
data class FlashcardEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val subject: SubjectType,
    val topic: String,
    val question: String,
    val answer: String,
    val formulaOrNote: String = "",
    val grade: String = "নবম-দশম শ্রেণি",
    val difficulty: CardDifficulty = CardDifficulty.NEW,
    val lastReviewedAt: Long = 0L,
    val nextReviewAt: Long = 0L,
    val reviewCount: Int = 0,
    val isCustom: Boolean = false,
    val isBookmarked: Boolean = false
)
