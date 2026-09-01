package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.initial.InitialFlashcards
import com.example.data.model.SubjectType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("HSK Flashcards", appName)
  }

  @Test
  fun `verify preloaded flashcards contain Physics Chemistry and Math`() {
    val cards = InitialFlashcards.getPreloadedCards()
    assertTrue(cards.size >= 15)
    assertTrue(cards.any { it.subject == SubjectType.PHYSICS })
    assertTrue(cards.any { it.subject == SubjectType.CHEMISTRY })
    assertTrue(cards.any { it.subject == SubjectType.MATH })
  }
}

