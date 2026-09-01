package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material.icons.filled.Flip
import androidx.compose.material.icons.filled.Functions
import androidx.compose.material.icons.filled.NavigateBefore
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.SentimentDissatisfied
import androidx.compose.material.icons.filled.SentimentNeutral
import androidx.compose.material.icons.filled.SentimentSatisfiedAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CardDifficulty
import com.example.data.model.FlashcardEntity
import com.example.data.model.SubjectType
import com.example.ui.theme.AmberAccent
import com.example.ui.theme.AmberContainer
import com.example.ui.theme.AmberLight
import com.example.ui.theme.BlueSubject
import com.example.ui.theme.CrimsonAccent
import com.example.ui.theme.CrimsonContainer
import com.example.ui.theme.CrimsonLight
import com.example.ui.theme.EmeraldAccent
import com.example.ui.theme.EmeraldContainer
import com.example.ui.theme.EmeraldLight
import com.example.ui.theme.PurpleSubject

@Composable
fun FlashcardEngine(
    cards: List<FlashcardEntity>,
    currentIndex: Int,
    isFlipped: Boolean,
    onFlipCard: () -> Unit,
    onNextCard: () -> Unit,
    onPrevCard: () -> Unit,
    onRateCard: (CardDifficulty) -> Unit,
    onToggleBookmark: (FlashcardEntity) -> Unit,
    onResetSession: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (cards.isEmpty()) {
        EmptyFlashcardState(onResetSession = onResetSession, modifier = modifier)
        return
    }

    val safeIndex = currentIndex.coerceIn(0, cards.size - 1)
    val currentCard = cards[safeIndex]

    // 3D Flip Animation
    val rotation by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(durationMillis = 450, easing = FastOutSlowInEasing),
        label = "card_3d_flip"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top Card Index Header & Navigation
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Card Counter
            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.School,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "কার্ড ${safeIndex + 1} / ${cards.size}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            // Quick Actions: Bookmark & Navigation
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = { onToggleBookmark(currentCard) },
                    modifier = Modifier.testTag("bookmark_button")
                ) {
                    Icon(
                        imageVector = if (currentCard.isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = "বুকমার্ক করুন",
                        tint = if (currentCard.isBookmarked) AmberAccent else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                IconButton(
                    onClick = onPrevCard,
                    enabled = cards.size > 1,
                    modifier = Modifier.testTag("prev_card_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.NavigateBefore,
                        contentDescription = "আগের কার্ড",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }

                IconButton(
                    onClick = onNextCard,
                    enabled = cards.size > 1,
                    modifier = Modifier.testTag("next_card_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.NavigateNext,
                        contentDescription = "পরের কার্ড",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        // 3D Flip Card Container
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(340.dp)
                .graphicsLayer {
                    rotationY = rotation
                    cameraDistance = 12f * density
                }
                .clickable { onFlipCard() }
                .testTag("interactive_flashcard")
        ) {
            if (rotation <= 90f) {
                // FRONT SIDE (Question)
                CardFront(
                    card = currentCard,
                    onFlip = onFlipCard,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                // BACK SIDE (Answer + Spaced Repetition Grading)
                CardBack(
                    card = currentCard,
                    onRate = onRateCard,
                    onFlip = onFlipCard,
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer { rotationY = 180f }
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Bottom Controls: Flip Button & Status Indicator
        if (!isFlipped) {
            Button(
                onClick = onFlipCard,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("flip_card_action_button"),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Flip,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "কার্ডটি উল্টান (Flip to View Answer)",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun CardFront(
    card: FlashcardEntity,
    onFlip: () -> Unit,
    modifier: Modifier = Modifier
) {
    val (subjectColor, subjectBg) = getSubjectColors(card.subject)

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(2.dp, subjectColor.copy(alpha = 0.35f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header row with Subject & Class tag
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = subjectBg,
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(1.dp, subjectColor.copy(alpha = 0.4f))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(subjectColor)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "${card.subject.displayNameBn} (${card.subject.displayNameEn})",
                            color = subjectColor,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = card.grade,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 11.sp,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Topic name
            Text(
                text = "অধ্যায়/বিষয়: ${card.topic}",
                fontSize = 12.5.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )

            // Main Question Body
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, fill = false),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = card.question,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = 26.sp,
                    textAlign = TextAlign.Start
                )
            }

            // Bottom Hint Banner
            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.15f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Psychology,
                            contentDescription = null,
                            tint = subjectColor,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "স্মৃতি থেকে উত্তর মনে করার চেষ্টা করুন",
                            fontSize = 11.5.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Icon(
                        imageVector = Icons.Default.Flip,
                        contentDescription = "ট্যাপ করে উল্টান",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun CardBack(
    card: FlashcardEntity,
    onRate: (CardDifficulty) -> Unit,
    onFlip: () -> Unit,
    modifier: Modifier = Modifier
) {
    val (subjectColor, _) = getSubjectColors(card.subject)

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(2.dp, EmeraldAccent.copy(alpha = 0.4f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = EmeraldLight.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, EmeraldAccent.copy(alpha = 0.4f))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircleOutline,
                            contentDescription = null,
                            tint = EmeraldAccent,
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "সঠিক উত্তর ও ব্যাখ্যা",
                            color = EmeraldAccent,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.clickable { onFlip() }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Flip,
                            contentDescription = "পুনরায় উল্টান",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "প্রশ্ন দেখুন",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // Answer Content Area
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, fill = false)
                    .padding(vertical = 6.dp)
            ) {
                Text(
                    text = card.answer,
                    fontSize = 14.5.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = 22.sp
                )

                if (card.formulaOrNote.isNotBlank()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.25f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Functions,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(15.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "সূত্র: ${card.formulaOrNote}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                }
            }

            // Spaced Repetition Grading Module (Hard, Medium, Easy)
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "রিভিশন রেটিং (স্মরণ করার সহজতা অনুযায়ী চাপুন):",
                    fontSize = 11.5.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 6.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Hard Button
                    GradingButton(
                        label = "Hard",
                        labelBn = "কঠিন",
                        interval = "১ মিনিট পর",
                        color = CrimsonAccent,
                        bgColor = CrimsonContainer.copy(alpha = 0.3f),
                        icon = Icons.Default.SentimentDissatisfied,
                        onClick = { onRate(CardDifficulty.HARD) },
                        modifier = Modifier.weight(1f).testTag("grade_hard_button")
                    )

                    // Medium Button
                    GradingButton(
                        label = "Medium",
                        labelBn = "মাঝারি",
                        interval = "আগামীকাল",
                        color = AmberAccent,
                        bgColor = AmberContainer.copy(alpha = 0.3f),
                        icon = Icons.Default.SentimentNeutral,
                        onClick = { onRate(CardDifficulty.MEDIUM) },
                        modifier = Modifier.weight(1f).testTag("grade_medium_button")
                    )

                    // Easy Button
                    GradingButton(
                        label = "Easy",
                        labelBn = "সহজ",
                        interval = "৪ দিন পর",
                        color = EmeraldAccent,
                        bgColor = EmeraldContainer.copy(alpha = 0.3f),
                        icon = Icons.Default.SentimentSatisfiedAlt,
                        onClick = { onRate(CardDifficulty.EASY) },
                        modifier = Modifier.weight(1f).testTag("grade_easy_button")
                    )
                }
            }
        }
    }
}

@Composable
private fun GradingButton(
    label: String,
    labelBn: String,
    interval: String,
    color: Color,
    bgColor: Color,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = bgColor,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.5.dp, color.copy(alpha = 0.6f)),
        modifier = modifier.clickable { onClick() }
    ) {
        Column(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = labelBn,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Text(
                text = interval,
                fontSize = 9.5.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun EmptyFlashcardState(
    onResetSession: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.AutoAwesome,
                contentDescription = null,
                tint = AmberAccent,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "এই ফিল্টারে কোনো কার্ড পাওয়া যায়নি",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "অন্য কোনো বিষয় নির্বাচন করুন অথবা সকল কার্ড পুনঃস্থাপন করুন",
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onResetSession,
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("সকল কার্ড দেখান (Show All)")
            }
        }
    }
}

private fun getSubjectColors(subject: SubjectType): Pair<Color, Color> {
    return when (subject) {
        SubjectType.PHYSICS -> Pair(BlueSubject, BlueSubject.copy(alpha = 0.15f))
        SubjectType.CHEMISTRY -> Pair(EmeraldAccent, EmeraldLight.copy(alpha = 0.15f))
        SubjectType.MATH -> Pair(AmberAccent, AmberLight.copy(alpha = 0.15f))
        SubjectType.CUSTOM -> Pair(PurpleSubject, PurpleSubject.copy(alpha = 0.15f))
    }
}
