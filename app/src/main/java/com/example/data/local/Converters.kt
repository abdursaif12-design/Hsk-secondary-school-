package com.example.data.local

import androidx.room.TypeConverter
import com.example.data.model.CardDifficulty
import com.example.data.model.SubjectType

class Converters {
    @TypeConverter
    fun fromSubjectType(value: SubjectType): String = value.name

    @TypeConverter
    fun toSubjectType(value: String): SubjectType = try {
        SubjectType.valueOf(value)
    } catch (e: Exception) {
        SubjectType.CUSTOM
    }

    @TypeConverter
    fun fromCardDifficulty(value: CardDifficulty): String = value.name

    @TypeConverter
    fun toCardDifficulty(value: String): CardDifficulty = try {
        CardDifficulty.valueOf(value)
    } catch (e: Exception) {
        CardDifficulty.NEW
    }
}
