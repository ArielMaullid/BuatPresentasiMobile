package com.example.animetrailerapp

// Import yang diperlukan
import android.net.Uri // Untuk mengelola URI
import android.os.Bundle // Untuk menyimpan status aplikasi
import androidx.activity.ComponentActivity // Aktivitas utama dalam aplikasi
import androidx.activity.compose.setContent // Menentukan konten tampilan
import androidx.compose.foundation.Image // Menampilkan gambar di UI
import androidx.compose.foundation.background // Memberikan warna latar belakang
import androidx.compose.foundation.layout.* // Untuk pengaturan tata letak
import androidx.compose.foundation.lazy.LazyRow // Untuk membuat daftar horizontal
import androidx.compose.foundation.lazy.itemsIndexed // Untuk iterasi item di LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape // Bentuk dengan sudut membulat
import androidx.compose.material.icons.Icons // Ikon bawaan Compose
import androidx.compose.material.icons.filled.* // Ikon spesifik yang digunakan seperti Home, List, dll
import androidx.compose.material3.* // Komponen Material 3 seperti Scaffold, Card, dll
import androidx.compose.runtime.* // Digunakan untuk state management
import androidx.compose.ui.Alignment // Mengatur perataan komponen
import androidx.compose.ui.Modifier // Modifikasi tampilan komponen
import androidx.compose.ui.graphics.Color // Untuk warna
import androidx.compose.ui.graphics.asImageBitmap // Konversi bitmap ke gambar Compose
import androidx.compose.ui.layout.ContentScale // Menentukan skala tampilan gambar
import androidx.compose.ui.res.painterResource // Mengakses resource drawable
import androidx.compose.ui.text.font.FontWeight // Mengatur ketebalan font
import androidx.compose.ui.unit.dp // Untuk ukuran dalam satuan dp
import androidx.compose.ui.unit.sp // Untuk ukuran font dalam satuan sp
import androidx.lifecycle.viewmodel.compose.viewModel // Mendapatkan instance ViewModel
import androidx.navigation.NavController // Mengelola navigasi antar layar
import androidx.navigation.NavHostController // Objek untuk menyimpan NavHost
import androidx.navigation.compose.NavHost // Struktur navigasi utama
import androidx.navigation.compose.composable // Untuk mendeklarasikan layar
import androidx.navigation.compose.rememberNavController // Membuat objek NavController
import com.example.animetrailerapp.ui.theme.AnimeTrailerAppTheme // Tema aplikasi
import com.example.animetrailerapp.ui.util.loadImageFromUri // Fungsi utilitas untuk memuat gambar

// Kelas utama aplikasi yang mewarisi ComponentActivity
class MainActivity : ComponentActivity() {
    // Fungsi onCreate dipanggil saat aktivitas dibuat
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Menentukan tema aplikasi
            AnimeTrailerAppTheme {
                // Komponen dasar untuk permukaan aplikasi
                Surface(
                    modifier = Modifier.fillMaxSize(), // Memenuhi seluruh layar
                    color = MaterialTheme.colorScheme.background // Warna latar belakang
                ) {
                    // Membuat NavController untuk navigasi
                    val navController = rememberNavController()
                    // Mendapatkan instance ViewModel
                    val animeViewModel: AnimeViewModel = viewModel()
                    // Memanggil fungsi untuk mengatur navigasi
                    NavigationComponent(navController, animeViewModel)
                }
            }
        }
    }
}

// Fungsi untuk mengatur navigasi aplikasi
@Composable
fun NavigationComponent(navController: NavHostController, animeViewModel: AnimeViewModel) {
    NavHost(navController = navController, startDestination = "mainScreen") {
        // Layar utama
        composable("mainScreen") {
            MainScreen(navController, animeViewModel)
        }
        // Layar detail anime dengan parameter
        composable(
            "animeDetail/{animeTitle}/{animeRating}/{animeGenre}/{animeDate}/{shortInfo}/{description}/{imageResId}"
        ) { backStackEntry ->
            // Mendapatkan parameter dari navigasi
            val animeTitle = backStackEntry.arguments?.getString("animeTitle") ?: ""
            val animeRating = backStackEntry.arguments?.getString("animeRating") ?: ""
            val animeGenre = backStackEntry.arguments?.getString("animeGenre") ?: ""
            val animeDate = backStackEntry.arguments?.getString("animeDate") ?: ""
            val shortInfo = backStackEntry.arguments?.getString("shortInfo") ?: ""
            val description = backStackEntry.arguments?.getString("description") ?: ""
            val imageResId = backStackEntry.arguments?.getString("imageResId")?.let { Uri.decode(it) }

            // Memanggil layar DetailAnimeScreen
            DetailAnimeScreen(
                navController = navController,
                animeTitle = animeTitle,
                animeRating = animeRating,
                animeGenre = animeGenre,
                animeDate = animeDate,
                shortInfo = shortInfo,
                description = description,
                imageResId = imageResId
            )
        }
        // Layar akun
        composable("account") {
            AccountPage(navController = navController, animeViewModel = animeViewModel)
        }
    }
}

// Fungsi untuk layar utama aplikasi
@Composable
fun MainScreen(navController: NavController, animeViewModel: AnimeViewModel) {
    var selectedTab by remember { mutableStateOf("home") } // State untuk tab yang dipilih

    Scaffold(
        topBar = { BannerHeader() }, // Header banner
        bottomBar = {
            BottomNavigationBar(
                selectedTab = selectedTab, // Tab yang dipilih
                onTabSelected = { selectedTab = it } // Fungsi untuk mengganti tab
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues) // Padding berdasarkan Scaffold
            ) {
                // Konten berdasarkan tab yang dipilih
                when (selectedTab) {
                    "home" -> {
                        Text(
                            text = "TOP 10 ANIME",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                        AnimeRow(animeViewModel.topAnimeList, navController)

                        Spacer(modifier = Modifier.height(42.dp))

                        Text(
                            text = "ANIME TRENDING",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                        AnimeRow(animeViewModel.trendingAnimeList, navController)
                    }
                    "anime_list" -> AnimeListPage(navController = navController, animeViewModel = animeViewModel)
                    "account" -> AccountPage(navController = navController, animeViewModel = animeViewModel)
                }
            }
        }
    )
}

// Fungsi untuk bottom navigation bar
@Composable
fun BottomNavigationBar(selectedTab: String, onTabSelected: (String) -> Unit) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        modifier = Modifier.height(82.dp) // Tinggi navigation bar
    ) {
        // Tab Home
        NavigationBarItem(
            selected = selectedTab == "home",
            onClick = { onTabSelected("home") },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home"
                )
            },
            label = { Text("Home") }
        )
        // Tab Anime List
        NavigationBarItem(
            selected = selectedTab == "anime_list",
            onClick = { onTabSelected("anime_list") },
            icon = {
                Icon(
                    imageVector = Icons.Default.List,
                    contentDescription = "Anime List"
                )
            },
            label = { Text("Anime List") }
        )
        // Tab Account
        NavigationBarItem(
            selected = selectedTab == "account",
            onClick = { onTabSelected("account") },
            icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Account"
                )
            },
            label = { Text("Account") }
        )
    }
}

// Fungsi untuk menampilkan daftar anime secara horizontal
@Composable
fun AnimeRow(animeList: List<Anime>, navController: NavController) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp) // Jarak antar item
    ) {
        itemsIndexed(animeList) { _, anime ->
            AnimeCardHorizontalFull(anime, navController)
        }
    }
}

// Fungsi untuk menampilkan kartu anime
@Composable
fun AnimeCardHorizontalFull(anime: Anime, navController: NavController) {
    Card(
        onClick = {
            // Navigasi ke layar detail anime
            if (anime.title.isNotEmpty() && anime.rating.isNotEmpty() && anime.genre.isNotEmpty() &&
                anime.date.isNotEmpty() && anime.shortInfo.isNotEmpty() && anime.description.isNotEmpty()
            ) {
                navController.navigate(
                    "animeDetail/${anime.title}/${anime.rating}/${anime.genre}/${anime.date}/${Uri.encode(anime.shortInfo)}/${Uri.encode(anime.description)}/${Uri.encode(anime.imageResId)}"
                )
            }
        },
        modifier = Modifier
            .width(155.dp)
            .aspectRatio(0.75f)
            .padding(vertical = 1.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            val bitmap = loadImageFromUri(anime.imageResId)
            if (bitmap != null) {
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = anime.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.placeholder),
                    contentDescription = anime.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .align(Alignment.BottomCenter)
            ) {
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = anime.title,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        maxLines = 1
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Rating",
                            tint = Color.Yellow,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = anime.rating,
                            color = Color.White,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

// Fungsi untuk menampilkan header banner
@Composable
fun BannerHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(92.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.banner_image),
            contentDescription = "Banner Header",
            modifier = Modifier.fillMaxSize()
        )
    }
}
