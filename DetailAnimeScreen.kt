package com.example.animetrailerapp

// Import yang diperlukan untuk fitur Android dan Compose
import android.content.Intent // Digunakan untuk membuka aplikasi eksternal, seperti browser atau Instagram.
import android.graphics.Bitmap // Untuk bekerja dengan gambar dalam bentuk bitmap.
import android.net.Uri // Untuk mengelola URI (Uniform Resource Identifier).
import androidx.compose.foundation.Image // Komponen untuk menampilkan gambar di UI.
import androidx.compose.foundation.background // Untuk memberi latar belakang pada komponen.
import androidx.compose.foundation.layout.* // Untuk mengatur tata letak komponen.
import androidx.compose.foundation.shape.RoundedCornerShape // Membuat bentuk sudut membulat.
import androidx.compose.material.icons.Icons // Ikon bawaan dari Jetpack Compose.
import androidx.compose.material.icons.filled.ArrowBack // Ikon panah kembali.
import androidx.compose.material.icons.filled.Star // Ikon bintang.
import androidx.compose.material3.* // Komponen Material Design 3 seperti Text, IconButton, dan lainnya.
import androidx.compose.runtime.Composable // Penanda bahwa fungsi ini merupakan composable function.
import androidx.compose.ui.Alignment // Untuk pengaturan alignment (perataan).
import androidx.compose.ui.Modifier // Untuk memberikan modifikasi pada komponen, seperti padding atau ukuran.
import androidx.compose.ui.draw.clip // Untuk memotong komponen berdasarkan bentuk tertentu.
import androidx.compose.ui.graphics.Brush // Untuk membuat gradien warna.
import androidx.compose.ui.graphics.Color // Untuk mengatur warna.
import androidx.compose.ui.graphics.RectangleShape // Untuk bentuk persegi panjang.
import androidx.compose.ui.graphics.asImageBitmap // Konversi bitmap ke jenis gambar Compose.
import androidx.compose.ui.layout.ContentScale // Untuk mengatur bagaimana gambar ditampilkan (misalnya: Crop, Fit).
import androidx.compose.ui.platform.LocalContext // Untuk mendapatkan konteks Android.
import androidx.compose.ui.res.painterResource // Untuk mengambil resource gambar dari drawable.
import androidx.compose.ui.text.font.FontWeight // Untuk mengatur ketebalan font.
import androidx.compose.ui.text.style.TextAlign // Untuk mengatur perataan teks.
import androidx.compose.ui.unit.dp // Untuk mengatur ukuran dalam satuan dp.
import androidx.compose.ui.unit.sp // Untuk mengatur ukuran font dalam sp.
import androidx.navigation.NavController // Untuk navigasi antar layar dalam Jetpack Compose.
import androidx.navigation.compose.rememberNavController // Untuk membuat objek NavController.
import com.example.animetrailerapp.ui.util.loadImageFromUri // Fungsi khusus untuk memuat gambar dari URI.

// Anotasi OptIn digunakan untuk memberi tahu bahwa fungsi ini menggunakan fitur eksperimental Material3.
@OptIn(ExperimentalMaterial3Api::class)

// Fungsi utama untuk menampilkan detail anime.
@Composable
fun DetailAnimeScreen(
    navController: NavController, // Objek untuk navigasi antar layar.
    animeTitle: String, // Judul anime yang akan ditampilkan.
    animeRating: String, // Rating anime.
    animeGenre: String, // Genre anime.
    animeDate: String, // Tanggal rilis anime.
    imageResId: String?, // URI gambar anime (opsional).
    shortInfo: String, // Informasi singkat tentang anime.
    description: String // Deskripsi lengkap tentang anime.
) {
    val context = LocalContext.current // Mendapatkan konteks Android untuk keperluan Intent.

    // Kolom utama untuk mengatur layout secara vertikal.
    Column(
        modifier = Modifier
            .fillMaxSize() // Mengisi seluruh layar.
            .padding(16.dp) // Memberikan padding di semua sisi.
    ) {
        // Bagian atas layar dengan tombol kembali dan ikon Instagram.
        Box(
            modifier = Modifier
                .fillMaxWidth() // Mengisi lebar penuh layar.
                .padding(bottom = 8.dp) // Memberikan jarak di bawah.
        ) {
            // Tombol untuk kembali ke layar sebelumnya.
            IconButton(
                onClick = { navController.popBackStack() }, // Navigasi ke layar sebelumnya.
                modifier = Modifier.align(Alignment.CenterStart) // Posisi di sisi kiri.
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack, // Ikon panah kembali.
                    contentDescription = "Back" // Deskripsi untuk aksesibilitas.
                )
            }
            // Tombol untuk membuka Instagram.
            IconButton(
                onClick = {
                    val instagramUrl = "https://www.instagram.com/arielmaullid/" // URL Instagram.
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(instagramUrl)) // Membuka browser atau aplikasi Instagram.
                    context.startActivity(intent) // Menjalankan intent.
                },
                modifier = Modifier.align(Alignment.CenterEnd) // Posisi di sisi kanan.
            ) {
                Image(
                    painter = painterResource(id = R.drawable.instagram_icon), // Ikon Instagram dari drawable.
                    contentDescription = "Instagram", // Deskripsi untuk aksesibilitas.
                    modifier = Modifier.size(32.dp) // Ukuran ikon 32 dp.
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp)) // Memberikan jarak vertikal.

        // Bagian untuk menampilkan gambar anime.
        val bitmap: Bitmap? = imageResId?.let { loadImageFromUri(it) } // Memuat gambar dari URI.

        // Kotak untuk gambar dengan desain menarik.
        Box(
            modifier = Modifier
                .fillMaxWidth() // Mengisi lebar penuh.
                .height(350.dp) // Tinggi 350 dp.
                .padding(horizontal = 8.dp) // Padding di kiri dan kanan.
                .background(
                    brush = Brush.verticalGradient(
                        listOf(Color.LightGray, Color.White) // Gradien dari abu-abu ke putih.
                    ),
                    shape = RoundedCornerShape(16.dp) // Sudut membulat.
                ),
            contentAlignment = Alignment.BottomCenter // Konten diatur ke bagian bawah tengah.
        ) {
            if (bitmap != null) {
                // Jika gambar tersedia, tampilkan gambar tersebut.
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = animeTitle,
                    contentScale = ContentScale.Crop, // Gambar memenuhi kotak.
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp)
                        .clip(RoundedCornerShape(16.dp)) // Sudut membulat.
                )
            } else {
                // Jika gambar tidak tersedia, tampilkan placeholder.
                Image(
                    painter = painterResource(id = R.drawable.placeholder),
                    contentDescription = animeTitle,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
            }
            // Bayangan gelap di bawah gambar untuk memperjelas judul.
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp) // Tinggi bayangan 80 dp.
                    .background(
                        Brush.verticalGradient(
                            listOf(Color.Transparent, Color.Black), // Gradien dari transparan ke hitam.
                            startY = 0f,
                            endY = Float.POSITIVE_INFINITY
                        ),
                        shape = RectangleShape // Bentuk persegi panjang.
                    )
                    .align(Alignment.BottomCenter) // Posisi di bagian bawah tengah.
            )
            // Judul anime di atas gambar.
            Text(
                text = animeTitle,
                color = Color.White, // Warna teks putih.
                fontSize = 18.sp, // Ukuran font 18 sp.
                fontWeight = FontWeight.Bold, // Ketebalan font tebal.
                modifier = Modifier.padding(8.dp), // Padding di sekitar teks.
                textAlign = TextAlign.Center // Teks rata tengah.
            )
        }

        Spacer(modifier = Modifier.height(16.dp)) // Jarak vertikal.

        // Menampilkan informasi singkat.
        Text(
            text = shortInfo,
            fontSize = 14.sp, // Ukuran font 14 sp.
            color = Color.Gray, // Warna teks abu-abu.
            modifier = Modifier.align(Alignment.CenterHorizontally) // Posisi di tengah horizontal.
        )

        Spacer(modifier = Modifier.height(8.dp)) // Jarak vertikal.

        // Menampilkan rating dengan ikon bintang.
        Row(
            verticalAlignment = Alignment.CenterVertically, // Perataan vertikal ke tengah.
            modifier = Modifier.align(Alignment.CenterHorizontally) // Posisi di tengah horizontal.
        ) {
            Icon(
                imageVector = Icons.Default.Star, // Ikon bintang.
                contentDescription = "Rating", // Deskripsi ikon.
                tint = Color.Yellow, // Warna bintang kuning.
                modifier = Modifier.size(20.dp) // Ukuran ikon 20 dp.
            )
            Text(
                text = animeRating,
                fontSize = 18.sp, // Ukuran font 18 sp.
                fontWeight = FontWeight.Bold, // Ketebalan font tebal.
                modifier = Modifier.padding(start = 8.dp) // Jarak ke kiri 8 dp.
            )
        }

        Spacer(modifier = Modifier.height(16.dp)) // Jarak vertikal.

        // Menampilkan genre dalam bentuk chip.
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp), // Jarak antar chip.
            modifier = Modifier.align(Alignment.CenterHorizontally) // Posisi di tengah horizontal.
        ) {
            animeGenre.split(", ").forEach { genre ->
                // Membuat chip untuk setiap genre.
                AssistChip(
                    onClick = { /* Tidak ada aksi */ },
                    label = { Text(text = genre) }, // Menampilkan teks genre.
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer, // Warna latar chip.
                        labelColor = MaterialTheme.colorScheme.onPrimaryContainer // Warna teks.
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp)) // Jarak vertikal.

        // Menampilkan deskripsi anime.
        Text(
            text = description,
            fontSize = 14.sp, // Ukuran font 14 sp.
            lineHeight = 20.sp, // Jarak antar baris teks.
            textAlign = TextAlign.Center, // Teks rata tengah.
            modifier = Modifier
                .fillMaxWidth() // Mengisi lebar penuh.
                .padding(horizontal = 8.dp) // Padding di kiri dan kanan.
        )

        Spacer(modifier = Modifier.height(8.dp)) // Jarak vertikal.
    }
}
