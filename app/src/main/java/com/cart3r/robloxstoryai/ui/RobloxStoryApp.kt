package com.cart3r.robloxstoryai.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cart3r.robloxstoryai.data.StoryProject
import com.cart3r.robloxstoryai.data.StoryRepository
import com.cart3r.robloxstoryai.data.buildStoryProject

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
    val context = LocalContext.current
    val repository = remember(context) { StoryRepository(context) }
    var projects by remember { mutableStateOf(repository.load()) }
    var selectedTab by remember { mutableStateOf(Tab.HOME) }
    var selectedProject by remember { mutableStateOf<StoryProject?>(null) }
    var prompt by remember { mutableStateOf("") }
    var genre by remember { mutableStateOf("مغامرة") }

    Scaffold(
        containerColor = Ink,
        bottomBar = {
            NavigationBar(containerColor = Panel, contentColor = Color.White) {
                Tab.entries.forEach { tab ->
                    NavigationBarItem(
                        selected = selectedTab == tab,
                        onClick = {
                            selectedTab = tab
                            if (tab != Tab.PROJECTS) selectedProject = null
                        },
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
                    Tab.HOME -> HomeScreen(projects = projects, onCreate = {
                        selectedProject = null
                        selectedTab = Tab.CREATE
                    }, onOpenProject = {
                        selectedProject = it
                        selectedTab = Tab.PROJECTS
                    })
                    Tab.CREATE -> CreateScreen(
                        prompt = prompt,
                        onPromptChange = { prompt = it },
                        genre = genre,
                        onGenreChange = { genre = it },
                        onGenerate = {
                            val project = buildStoryProject(prompt, genre)
                            projects = repository.save(project)
                            prompt = ""
                            selectedProject = project
                            selectedTab = Tab.PROJECTS
                        }
                    )
                    Tab.PROJECTS -> {
                        val opened = selectedProject
                        if (opened == null) {
                            ProjectsScreen(
                                projects = projects,
                                onOpen = { selectedProject = it },
                                onDelete = { id -> projects = repository.delete(id) }
                            )
                        } else {
                            ProjectDetailScreen(
                                project = opened,
                                onBack = { selectedProject = null },
                                onDelete = {
                                    projects = repository.delete(opened.id)
                                    selectedProject = null
                                }
                            )
                        }
                    }
                    Tab.PROFILE -> ProfileScreen(projectCount = projects.size)
                }
            }
        }
    }
}

@Composable
private fun HomeScreen(
    projects: List<StoryProject>,
    onCreate: () -> Unit,
    onOpenProject: (StoryProject) -> Unit
) {
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
                Box(Modifier.size(48.dp).background(Crimson.copy(alpha = .15f), RoundedCornerShape(16.dp)), contentAlignment = Alignment.Center) {
                    Text("✦", color = Crimson, fontSize = 27.sp)
                }
            }
        }
        item {
            Box(
                Modifier.fillMaxWidth()
                    .background(Brush.linearGradient(listOf(Color(0xFF541426), Color(0xFF21101B), Panel)), RoundedCornerShape(24.dp))
                    .padding(20.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("STORY STUDIO  /  AI + 3D", color = Color(0xFFFFA6B1), fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.5.sp)
                    Text("اصنع مغامرتك القادمة", color = Color.White, fontSize = 23.sp, fontWeight = FontWeight.Bold)
                    Text("اكتب الفكرة، اختر النوع، واحصل على مخطط أولي لمشاهد قصتك.", color = Color(0xFFE0C9D0), fontSize = 13.sp)
                    Button(onClick = onCreate, colors = ButtonDefaults.buttonColors(containerColor = Crimson), shape = RoundedCornerShape(14.dp)) {
                        Text("ابدأ إنشاء قصة  ✦", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
        item { SectionTitle("استوديوك", "YOUR STUDIO") }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatCard("مشاريعك", projects.size.toString(), Modifier.weight(1f))
                StatCard("محرك القصة", "جاهز محلياً", Modifier.weight(1f))
            }
        }
        item { SectionTitle("أحدث القصص", "RECENT PROJECTS") }
        if (projects.isEmpty()) {
            item { EmptyState("▤", "لا توجد قصص بعد", "ابدأ بفكرة صغيرة، وسنرتبها إلى مشاهد.") }
        } else {
            items(projects.take(3), key = { it.id }) { project ->
                ProjectRow(project = project, onClick = { onOpenProject(project) })
            }
        }
        item { SectionTitle("رحلة الإنشاء", "HOW IT WORKS") }
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                StepRow("01", "اكتب الفكرة", "حوّل وصفك إلى مخطط مشاهد أولي.")
                StepRow("02", "ابنِ العالم", "مرحلة لاحقة لربط الشخصيات والبيئات ثلاثية الأبعاد.")
                StepRow("03", "أخرج عملك", "تجهيز تصدير الفيديو والتجربة التفاعلية في مراحل قادمة.")
            }
        }
        item {
            Text("النسخة 0.2.0 • حفظ المشاريع محلياً • ربط Gemini وUnity لاحقاً", color = Muted, fontSize = 10.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth().padding(top = 8.dp))
        }
    }
}

@Composable
private fun CreateScreen(
    prompt: String,
    onPromptChange: (String) -> Unit,
    genre: String,
    onGenreChange: (String) -> Unit,
    onGenerate: () -> Unit
) {
    Column(Modifier.fillMaxSize().padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("استوديو القصص", color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.ExtraBold)
        Text("اكتب فكرتك، وسنقسمها إلى أربعة مشاهد قابلة للتطوير.", color = Muted, fontSize = 13.sp)
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
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("مغامرة", "كوميديا", "غموض", "خيال").forEach { item ->
                FilterChip(
                    selected = genre == item,
                    onClick = { onGenreChange(item) },
                    label = { Text(item, fontSize = 11.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Crimson.copy(alpha = .2f),
                        selectedLabelColor = Color.White,
                        labelColor = Muted
                    )
                )
            }
        }
        Button(
            onClick = onGenerate,
            enabled = prompt.isNotBlank(),
            modifier = Modifier.fillMaxWidth().height(54.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Crimson, disabledContainerColor = Color(0xFF4A2930)),
            shape = RoundedCornerShape(16.dp)
        ) { Text("إنشاء مخطط وحفظ القصة  ✦", fontWeight = FontWeight.Bold) }
        Text("يعمل إنشاء المخطط حالياً دون اتصال. سيتم استبدال المحرك المحلي بـ Gemini عند إعداد الخادم الآمن.", color = Muted, fontSize = 11.sp)
    }
}

@Composable
private fun ProjectsScreen(
    projects: List<StoryProject>,
    onOpen: (StoryProject) -> Unit,
    onDelete: (String) -> Unit
) {
    Column(Modifier.fillMaxSize().padding(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        Text("مشاريعي", color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.ExtraBold)
        Text("قصصك محفوظة على هذا الجهاز.", color = Muted, fontSize = 13.sp)
        if (projects.isEmpty()) {
            EmptyState("▤", "لا توجد مشاريع بعد", "أنشئ قصتك الأولى لتظهر هنا.")
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(projects, key = { it.id }) { project ->
                    Column(Modifier.fillMaxWidth().background(Panel, RoundedCornerShape(16.dp)).padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Column(Modifier.weight(1f).clickable { onOpen(project) }) {
                                Text(project.title, color = Color.White, fontWeight = FontWeight.Bold)
                                Text("${project.genre} • ${project.scenes.size} مشاهد", color = Muted, fontSize = 11.sp)
                            }
                            Text("فتح", color = Crimson, modifier = Modifier.clickable { onOpen(project) }.padding(8.dp))
                            Text("حذف", color = Color(0xFFFF8795), modifier = Modifier.clickable { onDelete(project.id) }.padding(8.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ProjectDetailScreen(project: StoryProject, onBack: () -> Unit, onDelete: () -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text("‹  العودة إلى المشاريع", color = Crimson, modifier = Modifier.clickable(onClick = onBack).padding(vertical = 8.dp))
            Text(project.title, color = Color.White, fontSize = 25.sp, fontWeight = FontWeight.ExtraBold)
            Text("النوع: ${project.genre}", color = Muted, fontSize = 12.sp)
        }
        item {
            Column(Modifier.fillMaxWidth().background(Panel, RoundedCornerShape(18.dp)).padding(16.dp)) {
                Text("الفكرة الأصلية", color = Crimson, fontWeight = FontWeight.Bold)
                Text(project.prompt, color = Color.White, fontSize = 14.sp)
            }
        }
        item { SectionTitle("مخطط المشاهد", "STORY OUTLINE") }
        items(project.scenes.size) { index ->
            StepRow("0${index + 1}", "المشهد ${index + 1}", project.scenes[index])
        }
        item {
            OutlinedButton(onClick = onDelete, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFFF8795))) {
                Text("حذف المشروع")
            }
        }
    }
}

@Composable
private fun ProfileScreen(projectCount: Int) {
    Column(Modifier.fillMaxSize().padding(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        Text("حسابي", color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.ExtraBold)
        EmptyState("◉", "مرحباً بك في الاستوديو", "عدد المشاريع المحفوظة على جهازك: $projectCount")
        Text("المزامنة السحابية وتسجيل الدخول ستضاف في مرحلة Firebase.", color = Muted, fontSize = 12.sp)
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
private fun StatCard(title: String, value: String, modifier: Modifier) {
    Column(modifier.background(Panel, RoundedCornerShape(18.dp)).padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(title, color = Muted, fontSize = 11.sp)
        Text(value, color = Color.White, fontSize = 17.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun ProjectRow(project: StoryProject, onClick: () -> Unit) {
    Row(
        Modifier.fillMaxWidth().clickable(onClick = onClick).background(Panel, RoundedCornerShape(16.dp)).padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.weight(1f)) {
            Text(project.title, color = Color.White, fontWeight = FontWeight.Bold)
            Text("${project.genre} • ${project.scenes.size} مشاهد", color = Muted, fontSize = 11.sp)
        }
        Text("فتح  ‹", color = Crimson, fontSize = 12.sp)
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
