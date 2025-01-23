package com.example.animetrailerapp

// Import yang diperlukan
import android.content.Intent // Untuk navigasi antar aktivitas
import android.os.Bundle // Untuk menyimpan status aktivitas
import androidx.activity.ComponentActivity // Kelas dasar untuk aktivitas di Jetpack Compose
import androidx.activity.compose.setContent // Untuk mengatur konten dengan Jetpack Compose
import androidx.compose.foundation.Image // Untuk menampilkan gambar
import androidx.compose.foundation.background // Memberikan warna latar belakang
import androidx.compose.foundation.layout.* // Untuk pengaturan tata letak
import androidx.compose.material3.* // Komponen Material 3 seperti Surface, Button, dll
import androidx.compose.runtime.Composable // Anotasi untuk fungsi yang dapat dikomposisi
import androidx.compose.ui.Alignment // Untuk mengatur perataan komponen
import androidx.compose.ui.Modifier // Modifier untuk menyesuaikan tampilan komponen
import androidx.compose.ui.graphics.Color // Untuk warna
import androidx.compose.ui.layout.ContentScale // Untuk skala gambar
import androidx.compose.ui.res.painterResource // Untuk memuat gambar dari resource
import androidx.compose.ui.text.TextStyle // Untuk gaya teks
import androidx.compose.ui.text.font.FontWeight // Untuk ketebalan font
import androidx.compose.ui.text.style.TextAlign // Untuk perataan teks
import androidx.compose.ui.unit.dp // Untuk ukuran dalam satuan dp
import androidx.compose.ui.unit.sp // Untuk ukuran font dalam satuan sp
import com.example.animetrailerapp.ui.theme.AnimeTrailerAppTheme // Tema aplikasi

// Kelas utama untuk Splash Screen
class SplashScreenActivity : ComponentActivity() {
    // Fungsi onCreate dipanggil saat aktivitas dibuat
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Mengatur tema aplikasi
            AnimeTrailerAppTheme {
                // Permukaan aplikasi dengan warna latar belakang
                Surface(
                    modifier = Modifier.fillMaxSize(), // Mengisi seluruh layar
                    color = MaterialTheme.colorScheme.background // Warna latar dari tema
                ) {
                    // Menampilkan SplashScreen
                    SplashScreen {
                        // Navigasi ke halaman utama setelah tombol diklik
                        startActivity(Intent(this, MainActivity::class.java)) // Intent ke MainActivity
                        finish() // Mengakhiri aktivitas SplashScreen
                    }
                }
            }
        }
    }
}

// Fungsi composable untuk menampilkan tampilan SplashScreen
@Composable
fun SplashScreen(onStartClicked: () -> Unit) {
    // Kolom utama untuk tata letak vertikal
    Column(
        modifier = Modifier
            .fillMaxSize() // Mengisi seluruh layar
            .background(Color(0xFF1C1B3F)) // Memberikan warna latar biru gelap
            .padding(16.dp), // Padding di semua sisi
        horizontalAlignment = Alignment.CenterHorizontally, // Komponen di tengah horizontal
        verticalArrangement = Arrangement.Center // Komponen di tengah vertikal
    ) {
        // Gambar logo di bagian atas
        Image(
            painter = painterResource(id = R.drawable.anime_logo), // Memuat gambar dari resource
            contentDescription = "Anime Lovers Logo", // Deskripsi gambar untuk aksesibilitas
            contentScale = ContentScale.Fit, // Menyesuaikan gambar agar sesuai tanpa memotong
            modifier = Modifier
                .size(300.dp) // Ukuran gambar
                .padding(bottom = 1.dp) // Padding di bawah gambar
        )

        Spacer(modifier = Modifier.height(0.dp)) // Jarak antara gambar dan teks (tidak ada jarak)

        // Teks judul
        Text(
            text = "Heyyooo! ANIMELOVERS", // Teks judul utama
            style = TextStyle(
                color = Color.White, // Warna putih
                fontSize = 28.sp, // Ukuran font
                fontWeight = FontWeight.Bold, // Ketebalan font
                textAlign = TextAlign.Center // Teks rata tengah
            )
        )

        Spacer(modifier = Modifier.height(8.dp)) // Jarak antara judul dan deskripsi

        // Teks deskripsi
        Text(
            text = "SELAMAT DATANG di aplikasi Animelovers, Semoga hari-hari mu menyedihkan slalu yaa.", // Teks deskripsi
            style = TextStyle(
                color = Color(0xFFCCCCCC), // Warna abu-abu terang
                fontSize = 16.sp, // Ukuran font
                textAlign = TextAlign.Center // Teks rata tengah
            ),
            modifier = Modifier.padding(horizontal = 16.dp) // Padding di sisi kiri dan kanan
        )

        Spacer(modifier = Modifier.height(32.dp)) // Jarak antara deskripsi dan tombol

        // Tombol "Ayo Mulai"
        Button(
            onClick = { onStartClicked() }, // Aksi ketika tombol diklik
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF6750A4), // Warna tombol (ungu)
                contentColor = Color.White // Warna teks tombol
            ),
            shape = MaterialTheme.shapes.medium, // Bentuk tombol medium (default tema)
            modifier = Modifier
                .fillMaxWidth() // Tombol mengisi lebar layar
                .padding(horizontal = 16.dp) // Padding di sisi kiri dan kanan
        ) {
            // Teks di dalam tombol
            Text(
                text = "Ayo Mulai", // Teks tombol
                style = TextStyle(
                    fontSize = 18.sp, // Ukuran font
                    fontWeight = FontWeight.Bold // Ketebalan font
                )
            )
        }
    }
}
