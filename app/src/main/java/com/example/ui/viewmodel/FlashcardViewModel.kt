package com.example.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.model.CardDifficulty
import com.example.data.model.FlashcardEntity
import com.example.data.model.SubjectType
import com.example.data.repository.FlashcardRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class DashboardStats(
    val totalCards: Int = 0,
    val memorizedCards: Int = 0,
    val pendingTodayCards: Int = 0,
    val progressPercent: Float = 0f,
    val physicsCount: Int = 0,
    val chemistryCount: Int = 0,
    val mathCount: Int = 0,
    val customCount: Int = 0,
    val studyStreakDays: Int = 3
)

class FlashcardViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getDatabase(application)
    private val repository = FlashcardRepository(db.flashcardDao())

    private val prefs = application.getSharedPreferences("hsk_flashcard_prefs", Context.MODE_PRIVATE)

    // Dark Mode / Exam Focus Mode
    private val _isDarkMode = MutableStateFlow(prefs.getBoolean("exam_focus_dark_mode", false))
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    // Selected Subject Filter: null means ALL
    private val _selectedSubject = MutableStateFlow<SubjectType?>(null)
    val selectedSubject: StateFlow<SubjectType?> = _selectedSubject.asStateFlow()

    // Search query for Vault
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // Current Flashcard Study Index
    private val _currentCardIndex = MutableStateFlow(0)
    val currentCardIndex: StateFlow<Int> = _currentCardIndex.asStateFlow()

    // Card Flip State
    private val _isCardFlipped = MutableStateFlow(false)
    val isCardFlipped: StateFlow<Boolean> = _isCardFlipped.asStateFlow()

    // Filter only Bookmarks toggle
    private val _showOnlyBookmarks = MutableStateFlow(false)
    val showOnlyBookmarks: StateFlow<Boolean> = _showOnlyBookmarks.asStateFlow()

    // Filter only Due / Pending cards
    private val _showOnlyDue = MutableStateFlow(false)
    val showOnlyDue: StateFlow<Boolean> = _showOnlyDue.asStateFlow()

    // UI Message for toasts/snackbars
    private val _userMessage = MutableStateFlow<String?>(null)
    val userMessage: StateFlow<String?> = _userMessage.asStateFlow()

    // Raw Cards Flow from DB
    val rawCards: StateFlow<List<FlashcardEntity>> = repository.allCards.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // Filtered Cards for Study Engine
    val filteredCards: StateFlow<List<FlashcardEntity>> = combine(
        rawCards,
        _selectedSubject,
        _searchQuery,
        _showOnlyBookmarks,
        _showOnlyDue
    ) { cards, subject, query, bookmarksOnly, dueOnly ->
        val now = System.currentTimeMillis()
        cards.filter { card ->
            val matchSubject = subject == null || card.subject == subject
            val matchQuery = query.isBlank() ||
                card.question.contains(query, ignoreCase = true) ||
                card.answer.contains(query, ignoreCase = true) ||
                card.topic.contains(query, ignoreCase = true) ||
                card.formulaOrNote.contains(query, ignoreCase = true)
            val matchBookmark = !bookmarksOnly || card.isBookmarked
            val matchDue = !dueOnly || (card.difficulty == CardDifficulty.NEW || card.difficulty == CardDifficulty.HARD || card.nextReviewAt <= now)

            matchSubject && matchQuery && matchBookmark && matchDue
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // Dashboard Statistics Calculation
    val dashboardStats: StateFlow<DashboardStats> = rawCards.combine(filteredCards) { allCards, _ ->
        val total = allCards.size
        val now = System.currentTimeMillis()
        val memorized = allCards.count { it.difficulty == CardDifficulty.EASY || it.reviewCount >= 2 }
        val pending = allCards.count { it.difficulty == CardDifficulty.NEW || it.difficulty == CardDifficulty.HARD || it.nextReviewAt <= now }
        val progress = if (total > 0) (memorized.toFloat() / total.toFloat()) else 0f

        val physics = allCards.count { it.subject == SubjectType.PHYSICS }
        val chemistry = allCards.count { it.subject == SubjectType.CHEMISTRY }
        val math = allCards.count { it.subject == SubjectType.MATH }
        val custom = allCards.count { it.isCustom }

        DashboardStats(
            totalCards = total,
            memorizedCards = memorized,
            pendingTodayCards = pending,
            progressPercent = progress,
            physicsCount = physics,
            chemistryCount = chemistry,
            mathCount = math,
            customCount = custom,
            studyStreakDays = 4
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = DashboardStats()
    )

    init {
        viewModelScope.launch {
            repository.initializeIfNeeded()
        }
    }

    fun toggleDarkMode() {
        val next = !_isDarkMode.value
        _isDarkMode.value = next
        prefs.edit().putBoolean("exam_focus_dark_mode", next).apply()
    }

    fun setSubjectFilter(subject: SubjectType?) {
        _selectedSubject.value = subject
        _currentCardIndex.value = 0
        _isCardFlipped.value = false
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun toggleBookmarksFilter() {
        _showOnlyBookmarks.value = !_showOnlyBookmarks.value
        _currentCardIndex.value = 0
        _isCardFlipped.value = false
    }

    fun toggleDueFilter() {
        _showOnlyDue.value = !_showOnlyDue.value
        _currentCardIndex.value = 0
        _isCardFlipped.value = false
    }

    fun flipCard() {
        _isCardFlipped.value = !_isCardFlipped.value
    }

    fun nextCard() {
        val total = filteredCards.value.size
        if (total > 0) {
            _currentCardIndex.value = (_currentCardIndex.value + 1) % total
            _isCardFlipped.value = false
        }
    }

    fun prevCard() {
        val total = filteredCards.value.size
        if (total > 0) {
            _currentCardIndex.value = if (_currentCardIndex.value - 1 < 0) total - 1 else _currentCardIndex.value - 1
            _isCardFlipped.value = false
        }
    }

    fun jumpToCard(index: Int) {
        val total = filteredCards.value.size
        if (index in 0 until total) {
            _currentCardIndex.value = index
            _isCardFlipped.value = false
        }
    }

    fun rateCurrentCard(difficulty: CardDifficulty) {
        val cards = filteredCards.value
        val currentIndex = _currentCardIndex.value
        if (currentIndex in cards.indices) {
            val card = cards[currentIndex]
            viewModelScope.launch {
                repository.rateCard(card, difficulty)
                val msg = when (difficulty) {
                    CardDifficulty.HARD -> "কঠিন: ১ মিনিট পর পুনরায় রিভিশন দিন"
                    CardDifficulty.MEDIUM -> "মাঝারি: আগামীকাল পর্যালোচনা হবে"
                    CardDifficulty.EASY -> "সহজ: ৪ দিন পর রিভিশন (মুখস্থ তালিকায় সংরক্ষিত)"
                    CardDifficulty.NEW -> "নতুন হিসেবে রাখা হয়েছে"
                }
                _userMessage.value = msg
                // Smooth transition to next card
                if (cards.size > 1) {
                    _isCardFlipped.value = false
                    _currentCardIndex.value = (currentIndex + 1) % cards.size
                } else {
                    _isCardFlipped.value = false
                }
            }
        }
    }

    fun toggleBookmarkCurrentCard(card: FlashcardEntity) {
        viewModelScope.launch {
            repository.toggleBookmark(card)
        }
    }

    fun addCustomCard(
        subject: SubjectType,
        topic: String,
        question: String,
        answer: String,
        formula: String
    ) {
        viewModelScope.launch {
            repository.addCard(
                subject = subject,
                topic = topic,
                question = question,
                answer = answer,
                formulaOrNote = formula
            )
            _userMessage.value = "নতুন প্রশ্ন সফলভাবে ভল্টে যুক্ত হয়েছে!"
        }
    }

    fun deleteCard(card: FlashcardEntity) {
        viewModelScope.launch {
            repository.deleteCard(card)
            _userMessage.value = "কার্ডটি মুছে ফেলা হয়েছে"
        }
    }

    fun resetAllProgress() {
        viewModelScope.launch {
            repository.resetAllProgress()
            _currentCardIndex.value = 0
            _isCardFlipped.value = false
            _userMessage.value = "সমস্ত অগ্রগতি রিসেট এবং মৌলিক সূত্রগুলো পুনঃস্থাপিত করা হয়েছে"
        }
    }

    fun clearUserMessage() {
        _userMessage.value = null
    }
}
