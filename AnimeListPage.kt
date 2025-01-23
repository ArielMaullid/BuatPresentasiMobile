package com.example.animetrailerapp

import android.net.Uri
import androidx.compose.foundation.Image // Komponen untuk menampilkan gambar.
import androidx.compose.foundation.background // Untuk mengatur latar belakang tampilan.
import androidx.compose.foundation.clickable // Memberikan kemampuan klik pada elemen.
import androidx.compose.foundation.layout.* // Untuk pengaturan layout.
import androidx.compose.foundation.lazy.LazyColumn // Komponen untuk menampilkan daftar secara vertikal.
import androidx.compose.foundation.lazy.LazyRow // Komponen untuk menampilkan daftar secara horizontal.
import androidx.compose.foundation.lazy.items // Untuk membuat daftar item.
import androidx.compose.foundation.shape.RoundedCornerShape // Membuat bentuk sudut bulat.
import androidx.compose.material.icons.Icons // Ikon bawaan Material Design.
import androidx.compose.material.icons.filled.Star // Ikon bintang untuk rating.
import androidx.compose.material3.* // Library Material 3 untuk desain UI modern.
import androidx.compose.runtime.* // State management untuk Compose.
import androidx.compose.ui.Alignment // Untuk mengatur posisi elemen secara horizontal/vertikal.
import androidx.compose.ui.Modifier // Modifikasi tampilan seperti ukuran, padding, dll.
import androidx.compose.ui.graphics.Color // Pengaturan warna.
import androidx.compose.ui.graphics.asImageBitmap // Untuk konversi gambar ke bitmap.
import androidx.compose.ui.layout.ContentScale // Untuk mengatur ukuran gambar di dalam kontainer.
import androidx.compose.ui.res.painterResource // Untuk mengambil resource gambar dari file lokal.
import androidx.compose.ui.text.font.FontWeight // Untuk mengatur ketebalan font.
import androidx.compose.ui.unit.dp // Unit untuk dimensi (padding, margin, dsb).
import androidx.compose.ui.unit.sp // Unit untuk ukuran teks.
import androidx.navigation.NavController // Untuk navigasi antar halaman.
import com.example.animetrailerapp.ui.util.loadImageFromUri // Fungsi utilitas untuk memuat gambar.

@Composable
fun AnimeListPage(navController: NavController, animeViewModel: AnimeViewModel) {
    var selectedGenre by remember { mutableStateOf("All") } // State untuk menyimpan genre yang dipilih.
    var searchText by remember { mutableStateOf("") } // State untuk menyimpan teks pencarian.

    // Daftar genre
    val genreList = remember {
        listOf("All", "Action", "Adventure", "Comedy", "Demons", "Horror", "Josei", "Magic", "Martial Arts")
        // Daftar semua genre yang dapat dipilih.
    }

    // Filter daftar anime berdasarkan genre dan pencarian
    val filteredAnimeList = animeViewModel.animeList.filter { anime ->
        (selectedGenre == "All" || anime.genre == selectedGenre) &&
                anime.title.contains(searchText, ignoreCase = true)
        // Hanya menampilkan anime yang sesuai dengan genre yang dipilih dan teks pencarian.
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Genre Pilihan", // Judul bagian genre.
            fontWeight = FontWeight.Bold, // Teks dengan huruf tebal.
            fontSize = 18.sp, // Ukuran teks.
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp) // Padding luar.
        )
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp), // Padding pada daftar horizontal.
            horizontalArrangement = Arrangement.spacedBy(8.dp) // Jarak antar item.
        ) {
            items(genreList) { genre ->
                GenreChip(
                    genre = genre, // Mengirimkan genre.
                    isSelected = genre == selectedGenre, // Menandai jika genre sedang dipilih.
                    onClick = { selectedGenre = genre } // Mengubah genre yang dipilih.
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp)) // Jarak antar elemen.

        // Search Bar
        OutlinedTextField(
            value = searchText, // Teks yang diinputkan.
            onValueChange = { searchText = it }, // Mengubah state pencarian ketika teks diubah.
            label = { Text("Search Anime") }, // Label untuk search bar.
            modifier = Modifier
                .fillMaxWidth() // Memenuhi lebar layar.
                .padding(horizontal = 16.dp), // Padding luar.
            singleLine = true // Input hanya satu baris.
        )

        Spacer(modifier = Modifier.height(16.dp)) // Jarak antar elemen.

        // Daftar anime
        LazyColumn(
            contentPadding = PaddingValues(8.dp), // Padding luar.
            verticalArrangement = Arrangement.spacedBy(8.dp) // Jarak antar item.
        ) {
            items(filteredAnimeList.chunked(2)) { rowItems -> // Menampilkan daftar anime per baris (2 item per baris).
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp), // Jarak antar item di baris.
                    modifier = Modifier.fillMaxWidth() // Baris memenuhi lebar layar.
                ) {
                    rowItems.forEach { anime -> // Iterasi setiap anime.
                        Box(
                            modifier = Modifier
                                .weight(1f) // Membagi ruang sama rata antar item.
                                .fillMaxHeight() // Item memenuhi tinggi baris.
                        ) {
                            AnimeCardVertical(anime, navController) // Menampilkan kartu anime.
                        }
                    }
                    if (rowItems.size < 2) { // Jika item di baris kurang dari 2, tambahkan spacer untuk meratakan.
                        repeat(2 - rowItems.size) {
                            Spacer(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GenreChip(genre: String, isSelected: Boolean, onClick: () -> Unit) {
    // Chip untuk memilih genre.
    Surface(
        shape = RoundedCornerShape(16.dp), // Membuat sudut bulat.
        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer,
        // Warna berubah tergantung apakah genre sedang dipilih.
        modifier = Modifier
            .wrapContentWidth() // Lebar menyesuaikan konten.
            .height(40.dp) // Tinggi tetap.
            .clickable { onClick() } // Klik untuk mengubah genre yang dipilih.
    ) {
        Box(
            contentAlignment = Alignment.Center, // Teks berada di tengah.
            modifier = Modifier.padding(horizontal = 16.dp) // Padding dalam.
        ) {
            Text(
                text = genre, // Teks genre.
                fontSize = 14.sp, // Ukuran teks.
                fontWeight = FontWeight.Medium, // Huruf medium.
                color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onPrimaryContainer
                // Warna teks berubah tergantung apakah genre sedang dipilih.
            )
        }
    }
}

@Composable
fun AnimeCardVertical(anime: Anime, navController: NavController) {
    // Kartu vertikal untuk menampilkan anime.
    Card(
        onClick = {
            // Navigasi ke detail anime jika data valid.
            if (anime.title.isNotEmpty() && anime.rating.isNotEmpty() && anime.genre.isNotEmpty() && anime.date.isNotEmpty()) {
                navController.navigate(
                    "animeDetail/${anime.title}/${anime.rating}/${anime.genre}/${anime.date}/${Uri.encode(anime.shortInfo)}/${Uri.encode(anime.description)}/${Uri.encode(anime.imageResId)}"
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth() // Kartu memenuhi lebar layar.
            .aspectRatio(0.75f), // Rasio 4:3.
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer // Warna latar belakang kartu.
        ),
        shape = RoundedCornerShape(8.dp) // Sudut bulat.
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            val bitmap = anime.imageResId?.let { loadImageFromUri(it) } // Memuat gambar anime.
            if (bitmap != null) {
                Image(
                    bitmap = bitmap.asImageBitmap(), // Menampilkan gambar.
                    contentDescription = anime.title, // Deskripsi untuk aksesibilitas.
                    contentScale = ContentScale.Crop, // Memenuhi kontainer.
                    modifier = Modifier.fillMaxSize() // Ukuran penuh.
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.placeholder), // Gambar placeholder jika gambar tidak tersedia.
                    contentDescription = anime.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth() // Latar belakang gelap untuk teks.
                    .background(Color.Black.copy(alpha = 0.5f))
                    .align(Alignment.BottomStart) // Di bagian bawah kartu.
            ) {
                Column(
                    modifier = Modifier
                        .padding(8.dp) // Padding teks.
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp) // Jarak antar teks.
                ) {
                    Text(
                        text = anime.title, // Judul anime.
                        color = Color.White, // Warna teks putih.
                        fontWeight = FontWeight.Bold, // Huruf tebal.
                        fontSize = 14.sp, // Ukuran teks.
                        maxLines = 1 // Maksimal 1 baris.
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Star, // Ikon bintang untuk rating.
                            contentDescription = "Rating",
                            tint = Color.Yellow, // Warna bintang kuning.
                            modifier = Modifier.size(16.dp) // Ukuran ikon.
                        )
                        Text(
                            text = anime.rating, // Menampilkan rating.
                            color = Color.White, // Warna teks putih.
                            fontSize = 12.sp, // Ukuran teks.
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                    Text(
                        text = anime.date, // Tanggal rilis anime.
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}
