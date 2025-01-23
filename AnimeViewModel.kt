package com.example.animetrailerapp

// Import untuk ViewModel dari Android Jetpack
import androidx.compose.runtime.mutableStateListOf // Untuk membuat list yang bisa berubah secara dinamis.
import androidx.lifecycle.ViewModel // ViewModel adalah komponen arsitektur Android untuk mengelola data UI.

class AnimeViewModel : ViewModel() {
    // ViewModel digunakan untuk mengelola data yang bertahan melintasi perubahan konfigurasi, seperti rotasi layar.

    // Data untuk TOP 10 ANIME
    val topAnimeList = mutableStateListOf(
        // `mutableStateListOf` digunakan untuk membuat daftar yang dinamis dan dapat dipantau oleh Jetpack Compose.
        Anime(
            // Setiap item di dalam `topAnimeList` adalah objek `Anime`.
            "Gotoubun no Hanayome", // Judul anime
            "8.16", // Rating
            "android.resource://com.example.animetrailerapp/drawable/anime1", // Resource URI untuk gambar
            "Action", // Genre
            "8 Jan 2026", // Tanggal rilis
            "Winter 2026 | Movie | Coming Soon", // Informasi tambahan
            "Fuutarou Uesugi merupakan siswa cerdas namun memiliki hidup yang sulit..." // Deskripsi panjang tentang anime.
        ),
        // Tambahan objek Anime lainnya...
    )

    // Data untuk TRENDING ANIME
    val trendingAnimeList = mutableStateListOf(
        // Sama seperti `topAnimeList`, ini adalah daftar untuk anime yang sedang tren.
        Anime(
            "Seirei Gensuki",
            "7.03",
            "android.resource://com.example.animetrailerapp/drawable/anime11",
            "Comedy",
            "1 Feb 2026",
            "Winter 2026 | Movie | Coming Soon",
            "Cerita berpusat kepada Amakawa Haruto, seorang laki-laki yang tewas..."
        )
        // Tambahan anime lainnya...
    )

    // Data untuk halaman AnimeListPage
    val animeList = mutableStateListOf(
        // Daftar utama yang digunakan untuk halaman daftar anime.
        Anime(
            "Date A Live",
            "7.49",
            "android.resource://com.example.animetrailerapp/drawable/anime21",
            "Josei",
            "24 Feb 2026",
            "Winter 2026 | Movie | Coming Soon",
            "30 tahun yang lalu gempa yang sangat dahsyat terjadi..."
        )
        // Tambahan anime lainnya...
    )

    // Daftar genre yang tersedia
    val genreList = listOf(
        "All", // Genre "All" untuk menampilkan semua anime.
        "Action",
        "Adventure",
        "Comedy",
        "Demons",
        "Horror",
        "Josei",
        "Magic",
        "Martial Arts",
        "Sports"
    )

    // Fungsi untuk menambahkan anime
    fun addAnime(anime: Anime) {
        // Menambahkan objek `Anime` baru ke dalam `animeList`.
        animeList.add(anime)
    }

    // Fungsi untuk memperbarui data anime
    fun updateAnime(index: Int, updatedAnime: Anime) {
        // Mengganti data pada indeks tertentu dengan data `updatedAnime`.
        if (index in animeList.indices) {
            // Mengecek apakah indeks valid.
            animeList[index] = updatedAnime
        }
    }

    // Fungsi untuk menghapus anime
    fun deleteAnime(index: Int) {
        // Menghapus data anime dari `animeList` berdasarkan indeks.
        if (index in animeList.indices) {
            // Mengecek apakah indeks valid sebelum menghapus.
            animeList.removeAt(index)
        }
    }

    // Fungsi untuk memfilter anime berdasarkan genre
    fun filterAnimeByGenre(selectedGenre: String): List<Anime> {
        // Memfilter `animeList` berdasarkan genre yang dipilih.
        return if (selectedGenre == "All") {
            // Jika genre "All", kembalikan seluruh daftar.
            animeList
        } else {
            // Jika genre lain dipilih, kembalikan hanya anime yang sesuai dengan genre tersebut.
            animeList.filter { it.genre == selectedGenre }
        }
    }
}
