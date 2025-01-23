package com.example.animetrailerapp

import android.net.Uri // Untuk menangani URI yang menunjuk ke file atau data.
import androidx.activity.compose.rememberLauncherForActivityResult // Untuk meluncurkan aktivitas lain dan menangkap hasilnya.
import androidx.activity.result.contract.ActivityResultContracts // Kontrak untuk aktivitas hasil seperti memilih konten.
import androidx.compose.foundation.Image // Komponen untuk menampilkan gambar.
import androidx.compose.foundation.layout.* // Komponen tata letak.
import androidx.compose.foundation.lazy.LazyColumn // Daftar kolom yang hanya memuat item yang terlihat.
import androidx.compose.foundation.lazy.items // Untuk mengulang data dalam LazyColumn.
import androidx.compose.material.icons.Icons // Kumpulan ikon material.
import androidx.compose.material.icons.filled.Delete // Ikon "Delete".
import androidx.compose.material.icons.filled.Edit // Ikon "Edit".
import androidx.compose.material3.* // Jetpack Compose Material3.
import androidx.compose.runtime.* // State management dalam Compose.
import androidx.compose.ui.Alignment // Untuk menyelaraskan elemen tata letak.
import androidx.compose.ui.Modifier // Modifier untuk memodifikasi tampilan atau tata letak.
import androidx.compose.ui.graphics.asImageBitmap // Untuk mengonversi bitmap ke format yang didukung Compose.
import androidx.compose.ui.text.input.TextFieldValue // Untuk menangani input teks.
import androidx.compose.ui.unit.dp // Satuan ukuran dalam Compose.
import androidx.core.net.toUri // Untuk mengonversi string ke URI.
import androidx.navigation.NavController // Untuk navigasi antar layar.
import com.example.animetrailerapp.ui.util.loadImageFromUri // Fungsi untuk memuat gambar dari URI.

@Composable
fun AccountPage(
    navController: NavController, // Objek untuk navigasi antar layar.
    animeViewModel: AnimeViewModel // ViewModel untuk data anime.
) {
    // Variabel state untuk menangani input pengguna dan status layar.
    var selectedCategory by remember { mutableStateOf<String?>(null) } // Untuk kategori halaman.
    var title by remember { mutableStateOf(TextFieldValue("")) } // Untuk judul anime.
    var rating by remember { mutableStateOf(TextFieldValue("")) } // Untuk rating anime.
    var genre by remember { mutableStateOf(TextFieldValue("")) } // Untuk genre anime.
    var date by remember { mutableStateOf(TextFieldValue("")) } // Untuk tanggal anime.
    var shortInfo by remember { mutableStateOf(TextFieldValue("")) } // Untuk informasi singkat anime.
    var description by remember { mutableStateOf(TextFieldValue("")) } // Untuk deskripsi anime.
    var imageUri by remember { mutableStateOf<Uri?>(null) } // Untuk URI gambar anime.
    var editingIndex by remember { mutableStateOf<Int?>(null) } // Untuk indeks anime yang sedang diedit.

    // Launcher untuk memilih gambar dari galeri.
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent() // Kontrak untuk memilih konten.
    ) { uri: Uri? ->
        imageUri = uri // Menyimpan URI gambar yang dipilih.
    }

    if (selectedCategory == null) {
        // Jika kategori belum dipilih, tampilkan layar pemilihan kategori.
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center, // Elemen diatur di tengah secara vertikal.
            horizontalAlignment = Alignment.CenterHorizontally // Elemen diatur di tengah secara horizontal.
        ) {
            Text("Pilih Kategori Halaman", style = MaterialTheme.typography.titleMedium) // Teks judul.
            Spacer(modifier = Modifier.height(16.dp)) // Spasi vertikal.
            Button(
                onClick = { selectedCategory = "TOP 10 ANIME" }, // Pilih kategori TOP 10 ANIME.
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("TOP 10 ANIME") // Teks tombol.
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { selectedCategory = "TRENDING ANIME" }, // Pilih kategori TRENDING ANIME.
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("TRENDING ANIME")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { selectedCategory = "AnimeListPage" }, // Pilih kategori AnimeListPage.
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("AnimeListPage")
            }
        }
    } else {
        // Jika kategori sudah dipilih, tampilkan daftar dan formulir.
        LazyColumn(
            contentPadding = PaddingValues(16.dp), // Padding untuk konten.
            verticalArrangement = Arrangement.spacedBy(16.dp), // Spasi antar item.
            modifier = Modifier.fillMaxSize()
        ) {
            // Formulir untuk menambah/mengedit anime.
            item {
                Text(
                    text = "Add/Edit Anime ($selectedCategory)", // Judul formulir.
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Input untuk judul anime.
                TextField(
                    value = title.text,
                    onValueChange = { title = TextFieldValue(it) },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Title") },
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Input untuk rating anime.
                TextField(
                    value = rating.text,
                    onValueChange = { rating = TextFieldValue(it) },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Rating") },
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Input untuk genre anime.
                TextField(
                    value = genre.text,
                    onValueChange = { genre = TextFieldValue(it) },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Genre") },
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Input untuk tanggal anime.
                TextField(
                    value = date.text,
                    onValueChange = { date = TextFieldValue(it) },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Date") },
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Input untuk informasi singkat anime.
                TextField(
                    value = shortInfo.text,
                    onValueChange = { shortInfo = TextFieldValue(it) },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Short Info") },
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Input untuk deskripsi anime.
                TextField(
                    value = description.text,
                    onValueChange = { description = TextFieldValue(it) },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Description") },
                    singleLine = false
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Tombol untuk memilih gambar.
                Button(
                    onClick = { imagePickerLauncher.launch("image/*") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Choose Image")
                }
                Spacer(modifier = Modifier.height(8.dp))

                // Menampilkan gambar yang dipilih.
                imageUri?.let { uri ->
                    val bitmap = loadImageFromUri(uri.toString())
                    if (bitmap != null) {
                        Image(
                            bitmap = bitmap.asImageBitmap(),
                            contentDescription = "Selected Image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                // Tombol untuk menambah/mengedit anime.
                Button(
                    onClick = {
                        val anime = Anime(
                            title.text,
                            rating.text,
                            imageUri?.toString() ?: "",
                            genre.text,
                            date.text,
                            shortInfo.text,
                            description.text
                        )
                        val list = when (selectedCategory) {
                            "TOP 10 ANIME" -> animeViewModel.topAnimeList
                            "TRENDING ANIME" -> animeViewModel.trendingAnimeList
                            else -> animeViewModel.animeList
                        }
                        if (editingIndex != null) {
                            list[editingIndex!!] = anime // Edit anime.
                            editingIndex = null
                        } else {
                            list.add(anime) // Tambah anime.
                        }
                        // Reset input.
                        title = TextFieldValue("")
                        rating = TextFieldValue("")
                        genre = TextFieldValue("")
                        date = TextFieldValue("")
                        shortInfo = TextFieldValue("")
                        description = TextFieldValue("")
                        imageUri = null
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = if (editingIndex != null) "Update" else "Add")
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            // Menampilkan daftar anime dalam kategori.
            val animeList = when (selectedCategory) {
                "TOP 10 ANIME" -> animeViewModel.topAnimeList
                "TRENDING ANIME" -> animeViewModel.trendingAnimeList
                else -> animeViewModel.animeList
            }

            items(animeList) { anime ->
                val index = animeList.indexOf(anime)
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Row(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = anime.title, style = MaterialTheme.typography.titleMedium)
                            Text(text = "Rating: ${anime.rating}", style = MaterialTheme.typography.bodyMedium)
                            Text(text = "Genre: ${anime.genre}", style = MaterialTheme.typography.bodySmall)
                            Text(text = "Date: ${anime.date}", style = MaterialTheme.typography.bodySmall)
                            Text(text = "Short Info: ${anime.shortInfo}", style = MaterialTheme.typography.bodySmall)
                        }
                        IconButton(onClick = {
                            // Edit anime.
                            title = TextFieldValue(anime.title)
                            rating = TextFieldValue(anime.rating)
                            genre = TextFieldValue(anime.genre)
                            date = TextFieldValue(anime.date)
                            shortInfo = TextFieldValue(anime.shortInfo)
                            description = TextFieldValue(anime.description)
                            imageUri = anime.imageResId?.toUri()
                            editingIndex = index
                        }) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit")
                        }
                        IconButton(onClick = {
                            // Hapus anime.
                            animeList.removeAt(index)
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete")
                        }
                    }
                }
            }
        }
    }
}
