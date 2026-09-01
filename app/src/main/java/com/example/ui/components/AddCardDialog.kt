package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCard
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.model.SubjectType
import com.example.ui.theme.AmberAccent
import com.example.ui.theme.BlueSubject
import com.example.ui.theme.EmeraldAccent
import com.example.ui.theme.PurpleSubject

@Composable
fun AddCardDialog(
    onDismiss: () -> Unit,
    onAddCard: (SubjectType, String, String, String, String) -> Unit
) {
    var selectedSubject by remember { mutableStateOf(SubjectType.PHYSICS) }
    var topic by remember { mutableStateOf("") }
    var question by remember { mutableStateOf("") }
    var answer by remember { mutableStateOf("") }
    var formula by remember { mutableStateOf("") }

    var questionError by remember { mutableStateOf(false) }
    var answerError by remember { mutableStateOf(false) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .padding(vertical = 20.dp)
                .testTag("add_card_dialog"),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Dialog Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.AddCard,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "নতুন কার্ড যুক্ত করুন",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "বন্ধ করুন",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Text(
                    text = "আপনার পাঠ্যবইয়ের কঠিন প্রশ্ন বা সমীকরণটি নিজের ভল্টে সংরক্ষণ করুন",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 2.dp, bottom = 14.dp)
                )

                // 1. Subject Category Selection
                Text(
                    text = "বিষয় নির্বাচন করুন (Subject):",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 6.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    SubjectOption(
                        title = "পদার্থ",
                        color = BlueSubject,
                        isSelected = selectedSubject == SubjectType.PHYSICS,
                        onClick = { selectedSubject = SubjectType.PHYSICS },
                        modifier = Modifier.weight(1f)
                    )

                    SubjectOption(
                        title = "রসায়ন",
                        color = EmeraldAccent,
                        isSelected = selectedSubject == SubjectType.CHEMISTRY,
                        onClick = { selectedSubject = SubjectType.CHEMISTRY },
                        modifier = Modifier.weight(1f)
                    )

                    SubjectOption(
                        title = "গণিত",
                        color = AmberAccent,
                        isSelected = selectedSubject == SubjectType.MATH,
                        onClick = { selectedSubject = SubjectType.MATH },
                        modifier = Modifier.weight(1f)
                    )

                    SubjectOption(
                        title = "অন্যান্য",
                        color = PurpleSubject,
                        isSelected = selectedSubject == SubjectType.CUSTOM,
                        onClick = { selectedSubject = SubjectType.CUSTOM },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // 2. Topic Input
                OutlinedTextField(
                    value = topic,
                    onValueChange = { topic = it },
                    label = { Text("অধ্যায় বা বিষয়ের নাম (ঐচ্ছিক)") },
                    placeholder = { Text("যেমন: বলবিদ্যা, অম্ল-ক্ষারক, স্থানাঙ্ক জ্যামিতি") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // 3. Question Text Input
                OutlinedTextField(
                    value = question,
                    onValueChange = {
                        question = it
                        if (it.isNotBlank()) questionError = false
                    },
                    label = { Text("পাঠ্যবইয়ের প্রশ্ন বা সমস্যা *") },
                    placeholder = { Text("বাংলায় বা সমীকরণে প্রশ্নটি লিখুন...") },
                    minLines = 3,
                    maxLines = 5,
                    isError = questionError,
                    supportingText = if (questionError) {
                        { Text("অনুগ্রহ করে প্রশ্নটি লিখুন", color = MaterialTheme.colorScheme.error) }
                    } else null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("question_input_field"),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // 4. Answer Input
                OutlinedTextField(
                    value = answer,
                    onValueChange = {
                        answer = it
                        if (it.isNotBlank()) answerError = false
                    },
                    label = { Text("সঠিক উত্তর ও বিস্তারিত ব্যাখ্যা *") },
                    placeholder = { Text("উত্তর ও ধাপগুলো ব্যাখ্যা করুন...") },
                    minLines = 3,
                    maxLines = 6,
                    isError = answerError,
                    supportingText = if (answerError) {
                        { Text("অনুগ্রহ করে উত্তরটি লিখুন", color = MaterialTheme.colorScheme.error) }
                    } else null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("answer_input_field"),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // 5. Formula / Shortcut Note
                OutlinedTextField(
                    value = formula,
                    onValueChange = { formula = it },
                    label = { Text("গুরুত্বপূর্ণ সূত্র বা শর্টকাট নোট (ঐচ্ছিক)") },
                    placeholder = { Text("যেমন: F = ma, বা sin²θ + cos²θ = 1") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("বাতিল")
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Button(
                        onClick = {
                            var hasError = false
                            if (question.isBlank()) {
                                questionError = true
                                hasError = true
                            }
                            if (answer.isBlank()) {
                                answerError = true
                                hasError = true
                            }
                            if (!hasError) {
                                onAddCard(
                                    selectedSubject,
                                    topic.ifBlank { "কাস্টম নোট" },
                                    question.trim(),
                                    answer.trim(),
                                    formula.trim()
                                )
                                onDismiss()
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        modifier = Modifier.testTag("save_custom_card_button")
                    ) {
                        Icon(imageVector = Icons.Default.Check, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("ভল্টে সেভ করুন")
                    }
                }
            }
        }
    }
}

@Composable
private fun SubjectOption(
    title: String,
    color: Color,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bg = if (isSelected) color.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
    val border = if (isSelected) color else MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)

    Surface(
        color = bg,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(if (isSelected) 1.8.dp else 1.dp, border),
        modifier = modifier.clickable { onClick() }
    ) {
        Column(
            modifier = Modifier.padding(vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(color)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = title,
                fontSize = 11.5.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) color else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
