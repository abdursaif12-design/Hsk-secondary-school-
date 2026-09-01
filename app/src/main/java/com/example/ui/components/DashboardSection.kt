package com.example.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.PendingActions
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.SubjectType
import com.example.ui.theme.AmberAccent
import com.example.ui.theme.AmberLight
import com.example.ui.theme.BlueSubject
import com.example.ui.theme.CrimsonLight
import com.example.ui.theme.EmeraldAccent
import com.example.ui.theme.EmeraldLight
import com.example.ui.theme.PurpleSubject
import com.example.ui.viewmodel.DashboardStats

@Composable
fun DashboardSection(
    stats: DashboardStats,
    selectedSubject: SubjectType?,
    onSelectSubject: (SubjectType?) -> Unit,
    showOnlyBookmarks: Boolean,
    onToggleBookmarks: () -> Unit,
    showOnlyDue: Boolean,
    onToggleDue: () -> Unit,
    modifier: Modifier = Modifier
) {
    val animatedProgress by animateFloatAsState(
        targetValue = stats.progressPercent.coerceIn(0f, 1f),
        animationSpec = tween(durationMillis = 600),
        label = "study_progress_animation"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Section Title
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Timeline,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "শিক্ষার্থী ড্যাশবোর্ড (Student Dashboard)",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            // Streak Badge
            Surface(
                color = AmberLight.copy(alpha = 0.18f),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, AmberAccent.copy(alpha = 0.4f))
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.LocalFireDepartment,
                        contentDescription = "অধ্যয়ন স্ট্রিক",
                        tint = AmberAccent,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${stats.studyStreakDays} দিন স্ট্রিক",
                        fontSize = 11.5.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = AmberAccent
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Quick Stats Cards Grid
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            StatCard(
                title = "মুখস্থ সম্পন্ন",
                value = "${stats.memorizedCards}",
                subtitle = "কার্ড আয়ত্তে",
                icon = Icons.Default.CheckCircle,
                iconColor = EmeraldAccent,
                backgroundColor = EmeraldLight.copy(alpha = 0.12f),
                borderColor = EmeraldLight.copy(alpha = 0.35f),
                modifier = Modifier.weight(1f)
            )

            StatCard(
                title = "আজকের রিভিশন",
                value = "${stats.pendingTodayCards}",
                subtitle = "রিভিউ বাকি",
                icon = Icons.Default.PendingActions,
                iconColor = AmberAccent,
                backgroundColor = AmberLight.copy(alpha = 0.12f),
                borderColor = AmberLight.copy(alpha = 0.35f),
                modifier = Modifier.weight(1f)
            )

            StatCard(
                title = "মোট ভল্ট কার্ড",
                value = "${stats.totalCards}",
                subtitle = "প্রশ্ন ও সূত্র",
                icon = Icons.Default.Layers,
                iconColor = BlueSubject,
                backgroundColor = BlueSubject.copy(alpha = 0.12f),
                borderColor = BlueSubject.copy(alpha = 0.35f),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Visual Study Progress Bar Card
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
            ),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("study_progress_card")
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "স্মার্ট রিভিশন অগ্রগতি",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "${(animatedProgress * 100).toInt()}% সম্পন্ন",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = EmeraldAccent
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                LinearProgressIndicator(
                    progress = { animatedProgress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                        .clip(RoundedCornerShape(5.dp)),
                    color = EmeraldAccent,
                    trackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                    strokeCap = StrokeCap.Round
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = if (stats.progressPercent >= 0.8f) "চমৎকার! আপনার পরীক্ষার প্রস্তুতি প্রায় সম্পূর্ণ 🎉"
                    else if (stats.progressPercent >= 0.4f) "ভালো অগ্রগতি! বাকি কার্ডগুলো আজই রিভিশন দিন 🚀"
                    else "নিয়মিত স্পেসড রিপিটেশন দিয়ে স্মরণশক্তি বাড়ান 💡",
                    fontSize = 11.5.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Subject Filter Scrollable Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SubjectChip(
                label = "সকল বিষয় (${stats.totalCards})",
                isSelected = selectedSubject == null && !showOnlyBookmarks && !showOnlyDue,
                onClick = {
                    if (showOnlyBookmarks) onToggleBookmarks()
                    if (showOnlyDue) onToggleDue()
                    onSelectSubject(null)
                }
            )

            SubjectChip(
                label = "পদার্থবিজ্ঞান (${stats.physicsCount})",
                isSelected = selectedSubject == SubjectType.PHYSICS && !showOnlyBookmarks && !showOnlyDue,
                badgeColor = BlueSubject,
                onClick = {
                    if (showOnlyBookmarks) onToggleBookmarks()
                    if (showOnlyDue) onToggleDue()
                    onSelectSubject(SubjectType.PHYSICS)
                }
            )

            SubjectChip(
                label = "রসায়ন (${stats.chemistryCount})",
                isSelected = selectedSubject == SubjectType.CHEMISTRY && !showOnlyBookmarks && !showOnlyDue,
                badgeColor = EmeraldAccent,
                onClick = {
                    if (showOnlyBookmarks) onToggleBookmarks()
                    if (showOnlyDue) onToggleDue()
                    onSelectSubject(SubjectType.CHEMISTRY)
                }
            )

            SubjectChip(
                label = "গণিত (${stats.mathCount})",
                isSelected = selectedSubject == SubjectType.MATH && !showOnlyBookmarks && !showOnlyDue,
                badgeColor = AmberAccent,
                onClick = {
                    if (showOnlyBookmarks) onToggleBookmarks()
                    if (showOnlyDue) onToggleDue()
                    onSelectSubject(SubjectType.MATH)
                }
            )

            if (stats.customCount > 0) {
                SubjectChip(
                    label = "কাস্টম (${stats.customCount})",
                    isSelected = selectedSubject == SubjectType.CUSTOM && !showOnlyBookmarks && !showOnlyDue,
                    badgeColor = PurpleSubject,
                    onClick = {
                        if (showOnlyBookmarks) onToggleBookmarks()
                        if (showOnlyDue) onToggleDue()
                        onSelectSubject(SubjectType.CUSTOM)
                    }
                )
            }

            // Quick Due Filter Chip
            Surface(
                color = if (showOnlyDue) CrimsonLight.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(
                    1.dp,
                    if (showOnlyDue) CrimsonLight else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                ),
                modifier = Modifier.clickable { onToggleDue() }
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.PendingActions,
                        contentDescription = null,
                        tint = if (showOnlyDue) CrimsonLight else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "আজকের বাকি",
                        fontSize = 12.sp,
                        fontWeight = if (showOnlyDue) FontWeight.Bold else FontWeight.Normal,
                        color = if (showOnlyDue) CrimsonLight else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Bookmarks Filter Chip
            Surface(
                color = if (showOnlyBookmarks) AmberLight.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(
                    1.dp,
                    if (showOnlyBookmarks) AmberAccent else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                ),
                modifier = Modifier.clickable { onToggleBookmarks() }
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Bookmark,
                        contentDescription = null,
                        tint = if (showOnlyBookmarks) AmberAccent else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "সংরক্ষিত",
                        fontSize = 12.sp,
                        fontWeight = if (showOnlyBookmarks) FontWeight.Bold else FontWeight.Normal,
                        color = if (showOnlyBookmarks) AmberAccent else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun StatCard(
    title: String,
    value: String,
    subtitle: String,
    icon: ImageVector,
    iconColor: Color,
    backgroundColor: Color,
    borderColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        border = BorderStroke(1.dp, borderColor),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    fontSize = 11.5.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(16.dp)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = value,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = subtitle,
                fontSize = 10.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun SubjectChip(
    label: String,
    isSelected: Boolean,
    badgeColor: Color? = null,
    onClick: () -> Unit
) {
    val bg = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
    val text = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
    val border = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)

    Surface(
        color = bg,
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, border),
        modifier = Modifier.clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (badgeColor != null && !isSelected) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(badgeColor)
                )
                Spacer(modifier = Modifier.width(6.dp))
            }
            Text(
                text = label,
                fontSize = 12.5.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = text
            )
        }
    }
}
