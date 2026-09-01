package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.components.AddCardDialog
import com.example.ui.components.DashboardSection
import com.example.ui.components.FlashcardEngine
import com.example.ui.components.HeaderSection
import com.example.ui.components.VaultListSheet
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.FlashcardViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: FlashcardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val isDarkMode by viewModel.isDarkMode.collectAsStateWithLifecycle()

            MyApplicationTheme(darkTheme = isDarkMode) {
                MainFlashcardApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun MainFlashcardApp(
    viewModel: FlashcardViewModel,
    modifier: Modifier = Modifier
) {
    val isDarkMode by viewModel.isDarkMode.collectAsStateWithLifecycle()
    val selectedSubject by viewModel.selectedSubject.collectAsStateWithLifecycle()
    val filteredCards by viewModel.filteredCards.collectAsStateWithLifecycle()
    val rawCards by viewModel.rawCards.collectAsStateWithLifecycle()
    val currentIndex by viewModel.currentCardIndex.collectAsStateWithLifecycle()
    val isCardFlipped by viewModel.isCardFlipped.collectAsStateWithLifecycle()
    val dashboardStats by viewModel.dashboardStats.collectAsStateWithLifecycle()
    val showOnlyBookmarks by viewModel.showOnlyBookmarks.collectAsStateWithLifecycle()
    val showOnlyDue by viewModel.showOnlyDue.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val userMessage by viewModel.userMessage.collectAsStateWithLifecycle()

    var showAddCardDialog by remember { mutableStateOf(false) }
    var showVaultListSheet by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(userMessage) {
        userMessage?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.clearUserMessage()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .widthIn(max = 680.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 1. Hero / Header Section with building background and school branding
                HeaderSection(
                    isDarkMode = isDarkMode,
                    onToggleDarkMode = { viewModel.toggleDarkMode() },
                    onOpenAddCard = { showAddCardDialog = true },
                    onOpenVaultList = { showVaultListSheet = true }
                )

                Spacer(modifier = Modifier.height(6.dp))

                // 2. Student Dashboard with Progress Bar and Stats
                DashboardSection(
                    stats = dashboardStats,
                    selectedSubject = selectedSubject,
                    onSelectSubject = { subject -> viewModel.setSubjectFilter(subject) },
                    showOnlyBookmarks = showOnlyBookmarks,
                    onToggleBookmarks = { viewModel.toggleBookmarksFilter() },
                    showOnlyDue = showOnlyDue,
                    onToggleDue = { viewModel.toggleDueFilter() }
                )

                // 3. Flashcard Revision Engine
                FlashcardEngine(
                    cards = filteredCards,
                    currentIndex = currentIndex,
                    isFlipped = isCardFlipped,
                    onFlipCard = { viewModel.flipCard() },
                    onNextCard = { viewModel.nextCard() },
                    onPrevCard = { viewModel.prevCard() },
                    onRateCard = { difficulty -> viewModel.rateCurrentCard(difficulty) },
                    onToggleBookmark = { card -> viewModel.toggleBookmarkCurrentCard(card) },
                    onResetSession = {
                        viewModel.setSubjectFilter(null)
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Bottom Footer: Developer attribution
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(
                            1.dp,
                            MaterialTheme.colorScheme.outline.copy(alpha = 0.15f)
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Code,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Programme the app by md saif",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }

    // 4. Add Custom Card Dialog
    if (showAddCardDialog) {
        AddCardDialog(
            onDismiss = { showAddCardDialog = false },
            onAddCard = { subject, topic, question, answer, formula ->
                viewModel.addCustomCard(subject, topic, question, answer, formula)
            }
        )
    }

    // 5. Vault List Bottom Sheet
    if (showVaultListSheet) {
        VaultListSheet(
            cards = rawCards,
            searchQuery = searchQuery,
            onSearchChange = { viewModel.setSearchQuery(it) },
            onSelectCard = { index ->
                viewModel.setSubjectFilter(null)
                viewModel.jumpToCard(index)
            },
            onDeleteCard = { card -> viewModel.deleteCard(card) },
            onResetAll = { viewModel.resetAllProgress() },
            onDismiss = { showVaultListSheet = false }
        )
    }
}

