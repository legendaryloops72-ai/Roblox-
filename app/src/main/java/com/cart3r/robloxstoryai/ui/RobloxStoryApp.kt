package com.cart3r.robloxstoryai.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Ink = Color(0xFF09090D)
private val Panel = Color(0xFF17171F)
private val Crimson = Color(0xFFFF4058)
private val Muted = Color(0xFFAAAAB8)

private enum class Tab(val label: String, val symbol: String) {
    HOME("الرئيسية", "⌂"),
    CREATE("إنشاء قصة", "✦"),
    PROJECTS("مشاريعي", "▤"),
    PROFILE("حسابي", "◉")
}

@Composable
fun RobloxStoryApp() {
    var selectedTab by remember { mutableStateOf(Tab.HOME) }
    var prompt by remember { mutableStateOf("") }
    var genre by remember { mutableStateOf("مغامرة") }

    Scaffold(
        containerColor = Ink,
        bottomBar = {
            NavigationBar(containerColor = Panel, contentColor = Color.White) {
                Tab.entries.forEach { tab ->
                    NavigationBarItem(
                        selected = selectedTab == tab,
                        onClick = { selectedTab = tab },
                        icon = { Text(tab.symbol, fontSize = 20.sp) },
                        label = { Text(tab.label, fontSize = 10.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Crimson,
                            selectedTextColor = Crimson,
                            indicatorColor = Crimson.copy(alpha = 0.13f),
                            unselectedIconColor = Muted,
                            unselectedTextColor = Muted
                        )
                    )
                }
            }
        }
    ) { padding ->
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            Box(Modifier.fillMaxSize().padding(padding).background(Ink)) {
                when (selectedTab) {
                    Tab.HOME -> HomeScreen(onCreate = { selectedTab = Tab.CREATE })
                    Tab.CREATE -> CreateScreen(
                        prompt = prompt,
                        onPromptChange = { prompt = it },
                        genre = genre,
                        onGenreChange = { genre = it }
                    )
                    Tab.PROJECTS -> ProjectsScreen()
                    Tab.PROFILE -> ProfileScreen()
                }
            }
        }
    }
}

@Composable
private fun HomeScreen(onCreate: () -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text("ROBLOX STORY AI", color = Crimson, fontSize = 12.sp, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
                    Text("حوّل خيالك إلى عالم", color = Color.White, fontSize = 27.sp, fontWeight = FontWeight.ExtraBold)
                    Text("قصص ثلاثية الأبعاد تبدأ من فكرة صغيرة.", color = Muted, fontSize = 13.sp)
                }
                Box(
                    Modifier.size(48.dp).background(Crimson.copy(alpha = .15f), RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center
                ) { Text("✦", color = Crimson, fontSize = 27.sp) }
            }
        }
        item {
            Box(
                Modifier.fillMaxWidth()
                    .background(Brush.linearGradient(listOf(Color(0xFF541426), Color(0xFF21101B), Color(0xFF17171F))), RoundedCornerShape(24.dp))
                    .padding(20.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("STORY STUDIO  /  AI + 3D", color = Color(0xFFFFA6B1), fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.5.sp)
                    Text("اصنع مغامرتك القادمة", color = Color.White, fontSize = 23.sp, fontWeight = FontWeight.Bold)
                    Text("اكتب الفكرة، اختر الأبطال، ودع الاستوديو يخطط المشاهد.", color = Color(0xFFE0C9D0), fontSize = 13.sp)
                    Button(onClick = onCreate, colors = ButtonDefaults.buttonColors(containerColor = Crimson), shape = RoundedCornerShape(14.dp)) {
                        Text("ابدأ إنشاء قصة  ✦", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
        item { SectionTitle("ابدأ من هنا", "QUICK START") }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                QuickCard("✎", "فكرة جديدة", "حوّل وصفك إلى سيناريو", Modifier.weight(1f), onCreate)
                QuickCard("♙", "الشخصيات", "جهّز أبطال قصتك", Modifier.weight(1f)) {}
            }
        }
        item { SectionTitle("رحلة الإنشاء", "HOW IT WORKS") }
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                StepRow("01", "اكتب القصة", "Gemini يحول الفكرة إلى مشاهد وحوارات.")
                StepRow("02", "ابنِ العالم", "Unity يعرض الشخصيات والبيئات ثلاثية الأبعاد.")
                StepRow("03", "أخرج عملك", "صدّر فيديو أو افتح التجربة التفاعلية.")
            }
        }
        item {
            Text("نسخة تأسيسية 0.1.0 • محركا الذكاء الاصطناعي وUnity قيد الإعداد", color = Muted, fontSize = 10.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth().padding(top = 8.dp))
        }
    }
}

@Composable
private fun CreateScreen(
    prompt: String,
    onPromptChange: (String) -> Unit,
    genre: String,
    onGenreChange: (String) -> Unit
) {
    Column(Modifier.fillMaxSize().padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("استوديو القصص", color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.ExtraBold)
        Text("صف فكرتك وسنجهزها لتصبح سيناريو منظماً.", color = Muted, fontSize = 13.sp)
        OutlinedTextField(
            value = prompt,
            onValueChange = onPromptChange,
            modifier = Modifier.fillMaxWidth().heightIn(min = 150.dp),
            placeholder = { Text("مثال: ثلاثة أصدقاء يكتشفون بوابة سرية تحت مدرستهم...") },
            label = { Text("فكرة القصة") },
            shape = RoundedCornerShape(18.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Crimson,
                unfocusedBorderColor = Color(0xFF454552),
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedLabelColor = Crimson,
                unfocusedLabelColor = Muted,
                cursorColor = Crimson
            )
        )
        Text("نوع القصة", color = Color.White, fontWeight = FontWeight.Bold)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("مغامرة", "كوميديا", "غموض", "خيال").forEach { item ->
                FilterChip(
                    selected = genre == item,
                    onClick = { onGenreChange(item) },
                    label = { Text(item) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Crimson.copy(alpha = .2f),
                        selectedLabelColor = Color.White,
                        labelColor = Muted
                    )
                )
            }
        }
        Button(
            onClick = { },
            enabled = prompt.isNotBlank(),
            modifier = Modifier.fillMaxWidth().height(54.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Crimson, disabledContainerColor = Color(0xFF4A2930)),
            shape = RoundedCornerShape(16.dp)
        ) { Text("توليد مخطط القصة  ✦", fontWeight = FontWeight.Bold) }
        Text("سيتم ربط هذا الزر بخدمة Gemini في المرحلة الثالثة.", color = Muted, fontSize = 11.sp)
    }
}

@Composable
private fun ProjectsScreen() {
    Column(Modifier.fillMaxSize().padding(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        Text("مشاريعي", color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.ExtraBold)
        Text("ستظهر القصص التي تحفظها هنا.", color = Muted, fontSize = 13.sp)
        EmptyState("▤", "لا توجد مشاريع بعد", "أنشئ قصتك الأولى لتبدأ مكتبتك.")
    }
}

@Composable
private fun ProfileScreen() {
    Column(Modifier.fillMaxSize().padding(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        Text("حسابي", color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.ExtraBold)
        EmptyState("◉", "مرحباً بك في الاستوديو", "سيتم إعداد الحساب والمزامنة السحابية لاحقاً.")
    }
}

@Composable
private fun SectionTitle(title: String, overline: String) {
    Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
        Text(overline, color = Crimson, fontSize = 9.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.5.sp)
        Text(title, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun QuickCard(icon: String, title: String, subtitle: String, modifier: Modifier, onClick: () -> Unit) {
    Column(
        modifier.clickable(onClick = onClick).background(Panel, RoundedCornerShape(18.dp)).padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(icon, color = Crimson, fontSize = 25.sp)
        Text(title, color = Color.White, fontWeight = FontWeight.Bold)
        Text(subtitle, color = Muted, fontSize = 11.sp)
    }
}

@Composable
private fun StepRow(number: String, title: String, description: String) {
    Row(
        Modifier.fillMaxWidth().background(Panel, RoundedCornerShape(16.dp)).padding(14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(number, color = Crimson, fontSize = 17.sp, fontWeight = FontWeight.ExtraBold)
        Column {
            Text(title, color = Color.White, fontWeight = FontWeight.Bold)
            Text(description, color = Muted, fontSize = 11.sp)
        }
    }
}

@Composable
private fun EmptyState(icon: String, title: String, subtitle: String) {
    Column(
        Modifier.fillMaxWidth().background(Panel, RoundedCornerShape(20.dp)).padding(28.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(icon, color = Crimson, fontSize = 36.sp)
        Text(title, color = Color.White, fontWeight = FontWeight.Bold)
        Text(subtitle, color = Muted, fontSize = 12.sp, textAlign = TextAlign.Center)
    }
}
