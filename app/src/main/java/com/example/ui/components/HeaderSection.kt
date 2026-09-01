package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.ViewList
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.R
import com.example.ui.theme.AmberLight
import com.example.ui.theme.EmeraldLight

@Composable
fun HeaderSection(
    isDarkMode: Boolean,
    onToggleDarkMode: () -> Unit,
    onOpenAddCard: () -> Unit,
    onOpenVaultList: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(240.dp)
            .shadow(8.dp)
    ) {
        // 1. Header Background: User-provided school building image with smooth crossfade and local placeholder
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://i.ibb.co.com/wrRZSz18/IMG-20260901-095802-882.jpg")
                .crossfade(true)
                .placeholder(R.drawable.ic_school_hero)
                .error(R.drawable.ic_school_hero)
                .fallback(R.drawable.ic_school_hero)
                .build(),
            contentDescription = "এইচ এচ কে মাধ্যমিক বিদ্যালয় ভবন",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0F2A4A))
        )

        // 2. Header Dark Overlay (bg-black/60)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.50f),
                            Color.Black.copy(alpha = 0.65f),
                            Color.Black.copy(alpha = 0.85f)
                        )
                    )
                )
        )

        // Top Action Bar inside Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .align(Alignment.TopCenter),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // School Badge
            Surface(
                color = Color.White.copy(alpha = 0.15f),
                shape = RoundedCornerShape(20.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.3f))
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.School,
                        contentDescription = null,
                        tint = AmberLight,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "HSK Flashcard Vault",
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 0.5.sp
                    )
                }
            }

            // Quick Action Buttons
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Vault List button
                IconButton(
                    onClick = onOpenVaultList,
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Color.White.copy(alpha = 0.2f),
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .size(38.dp)
                        .testTag("vault_list_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.ViewList,
                        contentDescription = "সকল কার্ডের তালিকা",
                        modifier = Modifier.size(18.dp)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Add Custom Card button
                IconButton(
                    onClick = onOpenAddCard,
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = EmeraldLight,
                        contentColor = Color.Black
                    ),
                    modifier = Modifier
                        .size(38.dp)
                        .testTag("add_card_header_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "নতুন কার্ড যুক্ত করুন",
                        modifier = Modifier.size(20.dp)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Dark Mode / Exam Focus Toggle
                IconButton(
                    onClick = onToggleDarkMode,
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Color.White.copy(alpha = 0.2f),
                        contentColor = if (isDarkMode) AmberLight else Color.White
                    ),
                    modifier = Modifier
                        .size(38.dp)
                        .testTag("dark_mode_toggle")
                ) {
                    Icon(
                        imageVector = if (isDarkMode) Icons.Default.LightMode else Icons.Default.DarkMode,
                        contentDescription = "নাইট মোড / পরীক্ষা ফোকাস",
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }

        // Centered Main Titles
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Main Title: "এইচ এচ কে মাধ্যমিক বিদ্যালয়"
            Text(
                text = "এইচ এচ কে মাধ্যমিক বিদ্যালয়",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
                lineHeight = 32.sp,
                modifier = Modifier.testTag("main_school_title")
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Subtitle: "স্থাপিত: ১৯৯৪"
            Surface(
                color = EmeraldLight.copy(alpha = 0.22f),
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, EmeraldLight.copy(alpha = 0.5f))
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(AmberLight)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "স্থাপিত: ১৯৯৪",
                        color = Color(0xFFF1F5F9),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 0.8.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "পদার্থ • রসায়ন • গণিত স্মার্ট রিভিশন ইঞ্জিন (Class 9-10)",
                color = Color.White.copy(alpha = 0.82f),
                fontSize = 11.5.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center
            )
        }
    }
}
