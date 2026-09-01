package com.example.data.initial

import com.example.data.model.CardDifficulty
import com.example.data.model.FlashcardEntity
import com.example.data.model.SubjectType

object InitialFlashcards {
    fun getPreloadedCards(): List<FlashcardEntity> {
        return listOf(
            // --- Physics (পদার্থবিজ্ঞান) ---
            FlashcardEntity(
                id = 1,
                subject = SubjectType.PHYSICS,
                topic = "গতি ও বল (Motion & Force)",
                question = "নিউটনের গতির দ্বিতীয় সূত্রটি বিবৃত করো এবং বলের গাণিতিক সমীকরণ কী?",
                answer = "কোনো বস্তুর ভরবেগের পরিবর্তনের হার তার ওপর প্রযুক্ত বলের সমানুপাতিক এবং বল যেদিকে ক্রিয়া করে বস্তুর ভরবেগের পরিবর্তনও সেদিকে ঘটে।\n\nসমীকরণ: F = ma\n(F = প্রযুক্ত বল, m = বস্তুর ভর, a = ত্বরণ)",
                formulaOrNote = "F = ma | SI একক: নিউটন (Newton, N)",
                grade = "নবম-দশম শ্রেণি",
                difficulty = CardDifficulty.NEW
            ),
            FlashcardEntity(
                id = 2,
                subject = SubjectType.PHYSICS,
                topic = "কাজ, শক্তি ও ক্ষমতা",
                question = "গতিশক্তি (Kinetic Energy) এবং বিভব শক্তির (Potential Energy) সমীকরণ ও এসআই একক কী?",
                answer = "গতিশক্তি (Ek) = ½mv²\nবিভব শক্তি (Ep) = mgh\n\n(m = ভর, v = বেগ, g = অভিকর্ষজ ত্বরণ = 9.8 ms⁻², h = উচ্চতা)\nউভয় শক্তির এসআই একক হলো জুল (Joule - J)।",
                formulaOrNote = "Ek = ½mv² , Ep = mgh (একক: J)",
                grade = "নবম-দশম শ্রেণি",
                difficulty = CardDifficulty.NEW
            ),
            FlashcardEntity(
                id = 3,
                subject = SubjectType.PHYSICS,
                topic = "চলতড়িৎ (Current Electricity)",
                question = "ওহমের সূত্রটি (Ohm's Law) বিবৃত করো এবং গাণিতিক রূপ লেখ।",
                answer = "স্থির তাপমাত্রায় কোনো নির্দিষ্ট পরিবাহীর মধ্য দিয়ে প্রবাহিত তড়িৎপ্রবাহ (I) পরিবাহীর দুই প্রান্তের বিভব পার্থক্যের (V) সমানুপাতিক।\n\nগাণিতিক রূপ: V = IR অথবা I = V / R\n(যেখানে R হলো পরিবাহীর রোধ, একক: ওহম Ω)",
                formulaOrNote = "V = IR  =>  I = V / R (একক: ওহম Ω)",
                grade = "নবম-দশম শ্রেণি",
                difficulty = CardDifficulty.NEW
            ),
            FlashcardEntity(
                id = 4,
                subject = SubjectType.PHYSICS,
                topic = "আলোর প্রতিসরণ (Refraction of Light)",
                question = "স্নেলের সূত্র (Snell's Law) বা প্রতিসরাঙ্কের সূত্রটি কী?",
                answer = "একজোড়া নির্দিষ্ট মাধ্যম এবং একটি নির্দিষ্ট বর্ণের আলোর জন্য আপতন কোণের সাইন (sin i) ও প্রতিসরণ কোণের সাইনের (sin r) অনুপাত সর্বদা ধ্রুবক থাকে।\n\nসূত্র: (sin i) / (sin r) = η (ধ্রুবক বা প্রতিসরাঙ্ক)",
                formulaOrNote = "sin(i) / sin(r) = η₁₂",
                grade = "নবম-দশম শ্রেণি",
                difficulty = CardDifficulty.NEW
            ),
            FlashcardEntity(
                id = 5,
                subject = SubjectType.PHYSICS,
                topic = "পদার্থের অবস্থা ও চাপ",
                question = "আর্কিমিডিসের নীতি এবং প্লবতার সমীকরণ কী?",
                answer = "কোনো বস্তুকে স্থির তরল বা বায়বীয় পদার্থে আংশিক বা সম্পূর্ণ নিমজ্জিত করলে বস্তুটি কিছু ওজন হারায়। এই হারানো ওজন বস্তুটি দ্বারা অপসারিত তরল বা বায়বীয় পদার্থের ওজনের সমান।\n\nপ্লবতা (Upthrust) B = Vρg\n(V = নিমজ্জিত আয়তন, ρ = তরলের ঘনত্ব, g = অভিকর্ষজ ত্বরণ)",
                formulaOrNote = "প্লবতা B = V × ρ × g (একক: N)",
                grade = "নবম-দশম শ্রেণি",
                difficulty = CardDifficulty.NEW
            ),

            // --- Chemistry (রসায়ন) ---
            FlashcardEntity(
                id = 6,
                subject = SubjectType.CHEMISTRY,
                topic = "পর্যায় সারণি (Periodic Table)",
                question = "আধুনিক পর্যায় সারণির মূল ভিত্তি কী এবং এতে কয়টি পর্যায় ও কয়টি গ্রুপ রয়েছে?",
                answer = "আধুনিক পর্যায় সারণির মূল ভিত্তি হলো মৌলের ইলেকট্রন বিন্যাস।\n\nএতে মোট ৭টি পর্যায় (অনুভূমিক সারি) এবং ১৮টি গ্রুপ (উল্লম্ব কলাম) রয়েছে। মৌল সংখ্যা মোট ১১৮টি।",
                formulaOrNote = "৭টি পর্যায় + ১৮টি গ্রুপ = ১১৮টি মৌল",
                grade = "নবম-দশম শ্রেণি",
                difficulty = CardDifficulty.NEW
            ),
            FlashcardEntity(
                id = 7,
                subject = SubjectType.CHEMISTRY,
                topic = "গ্যাসের আচরণ ও মোল",
                question = "আদর্শ গ্যাস সমীকরণটি (Ideal Gas Law) লেখ এবং প্রতীকগুলোর পরিচয় দাও।",
                answer = "আদর্শ গ্যাস সমীকরণ: PV = nRT\n\n• P = গ্যাসের চাপ (Pressure)\n• V = গ্যাসের আয়তন (Volume)\n• n = গ্যাসের মোল সংখ্যা (Moles = W/M)\n• R = সার্বজনীন গ্যাস ধ্রুবক (8.314 J mol⁻¹ K⁻¹)\n• T = পরম তাপমাত্রা (Kelvin, K)",
                formulaOrNote = "PV = nRT = (W/M)RT",
                grade = "নবম-দশম শ্রেণি",
                difficulty = CardDifficulty.NEW
            ),
            FlashcardEntity(
                id = 8,
                subject = SubjectType.CHEMISTRY,
                topic = "রাসায়নিক বন্ধন ও আয়ন",
                question = "ক্যাটায়ন (Cation) এবং অ্যানায়ন (Anion) কীভাবে গঠিত হয়? উদাহরণ দাও।",
                answer = "• ক্যাটায়ন: নিরপেক্ষ পরমাণু যোজ্যতা স্তর থেকে ১ বা একাধিক ইলেকট্রন ত্যাগ করে ধনাত্মক আয়নে পরিণত হয়। (যেমন: Na → Na⁺ + e⁻)\n\n• অ্যানায়ন: নিরপেক্ষ পরমাণু বাইরের স্তর থেকে ইলেকট্রন গ্রহণ করে ঋণাত্মক আয়নে পরিণত হয়। (যেমন: Cl + e⁻ → Cl⁻)",
                formulaOrNote = "ধাতু → ক্যাটায়ন (+) | অধাতু → অ্যানায়ন (-)",
                grade = "নবম-দশম শ্রেণি",
                difficulty = CardDifficulty.NEW
            ),
            FlashcardEntity(
                id = 9,
                subject = SubjectType.CHEMISTRY,
                topic = "রাসায়নিক বিক্রিয়া ও ক্ষয়",
                question = "মরিচা (Rust) কী এবং এর সঠিক রাসায়নিক সংকেত কী?",
                answer = "লোহা জলীয় বাষ্প ও অক্সিজেনের উপস্থিতিতে বিক্রিয়া করে আর্দ্র ফেরিক অক্সাইড তৈরি করে, যা মরিচা নামে পরিচিত।\n\nমরিচার সংকেত: Fe₂O₃·nH₂O (হাইড্রেটেড ফেরিক অক্সাইড)\nবিক্রিয়া: 4Fe + 3O₂ + 2nH₂O → 2(Fe₂O₃·nH₂O)",
                formulaOrNote = "সংকেত: Fe₂O₃ · nH₂O",
                grade = "নবম-দশম শ্রেণি",
                difficulty = CardDifficulty.NEW
            ),
            FlashcardEntity(
                id = 10,
                subject = SubjectType.CHEMISTRY,
                topic = "এসিড, ক্ষারক ও লবণ",
                question = "সালফিউরিক এসিড, হাইড্রোক্লোরিক এসিড ও নাইট্রিক এসিডের রাসায়নিক সংকেত লেখ।",
                answer = "১. সালফিউরিক এসিড (Sulfuric Acid): H₂SO₄ (রসায়নের রাজা বলা হয়)\n২. হাইড্রোক্লোরিক এসিড (Hydrochloric Acid): HCl\n৩. নাইট্রিক এসিড (Nitric Acid): HNO₃\n৪. কস্টিক সোডা (ক্ষার): NaOH",
                formulaOrNote = "H₂SO₄, HCl, HNO₃, NaOH",
                grade = "নবম-দশম শ্রেণি",
                difficulty = CardDifficulty.NEW
            ),

            // --- Mathematics (গণিত) ---
            FlashcardEntity(
                id = 11,
                subject = SubjectType.MATH,
                topic = "বীজগণিত - দ্বিঘাত সমীকরণ",
                question = "দ্বিঘাত সমীকরণ ax² + bx + c = 0 এর মূল বা বীজ নির্ণয়ের সার্বজনীন সূত্রটি কী?",
                answer = "দ্বিঘাত সমীকরণ ax² + bx + c = 0 (যেখানে a ≠ 0) এর মূলসমূহ:\n\nx = (-b ± √(b² - 4ac)) / (2a)\n\nএখানে নিশ্চায়ক (Discriminant) D = b² - 4ac। যদি D > 0 হয় মূলদ্বয় বাস্তব ও অসমান হবে।",
                formulaOrNote = "x = [-b ± √(b² - 4ac)] / 2a",
                grade = "নবম-দশম শ্রেণি",
                difficulty = CardDifficulty.NEW
            ),
            FlashcardEntity(
                id = 12,
                subject = SubjectType.MATH,
                topic = "ত্রিকোণমিতি (Trigonometry)",
                question = "ত্রিকোণমিতির মৌলিক ৩টি পিথাগোরীয় অভেদাবলী লেখ।",
                answer = "১. sin²θ + cos²θ = 1\n২. sec²θ - tan²θ = 1  (বা sec²θ = 1 + tan²θ)\n৩. cosec²θ - cot²θ = 1  (বা cosec²θ = 1 + cot²θ)",
                formulaOrNote = "sin²θ + cos²θ = 1 | sec²θ - tan²θ = 1",
                grade = "নবম-দশম শ্রেণি",
                difficulty = CardDifficulty.NEW
            ),
            FlashcardEntity(
                id = 13,
                subject = SubjectType.MATH,
                topic = "পরিমিতি (Mensuration)",
                question = "সমবাহু ত্রিভুজ, আয়তক্ষেত্র ও বৃত্তের ক্ষেত্রফলের সূত্রগুলো কী?",
                answer = "• সমবাহু ত্রিভুজের ক্ষেত্রফল = (√3 / 4) × a²  (a = বাহুর দৈর্ঘ্য)\n• আয়তক্ষেত্রের ক্ষেত্রফল = দৈর্ঘ্য × প্রস্থ\n• বৃত্তের ক্ষেত্রফল = πr²  (r = ব্যাসার্ধ, পরিধি = 2πr)\n• সিলিন্ডারের আয়তন = πr²h",
                formulaOrNote = "সমবাহু: (√3/4)a² | বৃত্ত: πr² | পরিধি: 2πr",
                grade = "নবম-দশম শ্রেণি",
                difficulty = CardDifficulty.NEW
            ),
            FlashcardEntity(
                id = 14,
                subject = SubjectType.MATH,
                topic = "সমান্তর ও গুণোত্তর ধারা",
                question = "সমান্তর ধারার n-তম পদ এবং প্রথম n-সংখ্যক পদের সমষ্টির সূত্র কী?",
                answer = "প্রথম পদ = a, সাধারণ অন্তর = d হলে:\n\n• n-তম পদ = a + (n - 1)d\n• প্রথম n-সংখ্যক পদের সমষ্টি Sn = (n / 2) × [2a + (n - 1)d]\n\nগুণোত্তর ধারার n-তম পদ = arⁿ⁻¹",
                formulaOrNote = "nth Term = a + (n-1)d | Sn = (n/2)[2a + (n-1)d]",
                grade = "নবম-দশম শ্রেণি",
                difficulty = CardDifficulty.NEW
            ),
            FlashcardEntity(
                id = 15,
                subject = SubjectType.MATH,
                topic = "লগারিদম (Logarithm)",
                question = "লগারিদমের মৌলিক গুণফল, ভাগফল ও ঘাত রূপান্তরের সূত্রগুলো লেখ।",
                answer = "১. logₐ (M × N) = logₐ M + logₐ N\n২. logₐ (M / N) = logₐ M - logₐ N\n৩. logₐ (Mᵏ) = k × logₐ M\n৪. logₐ a = 1 এবং logₐ 1 = 0\n৫. ভিত্তি পরিবর্তন: logₐ b = (log_c b) / (log_c a)",
                formulaOrNote = "log(MN) = log M + log N | log(M/N) = log M - log N",
                grade = "নবম-দশম শ্রেণি",
                difficulty = CardDifficulty.NEW
            )
        )
    }
}
