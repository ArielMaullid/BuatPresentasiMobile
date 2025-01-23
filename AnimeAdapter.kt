package com.example.animetrailerapp

import android.view.LayoutInflater // Untuk membuat (inflate) tampilan dari XML layout ke View.
import android.view.View // Representasi dari elemen tampilan.
import android.view.ViewGroup // Kumpulan elemen tampilan, biasanya digunakan untuk tata letak (layout).
import android.widget.ImageView // Komponen tampilan untuk menampilkan gambar.
import android.widget.TextView // Komponen tampilan untuk menampilkan teks.
import androidx.recyclerview.widget.RecyclerView // Library RecyclerView untuk menampilkan daftar data.
import com.bumptech.glide.Glide // Library untuk memuat gambar dari URL atau URI.

class AnimeAdapter(private val animeList: List<Anime>) : RecyclerView.Adapter<AnimeAdapter.AnimeViewHolder>() {
    // `AnimeAdapter` adalah kelas adaptor untuk RecyclerView yang menerima daftar anime (`animeList`).

    inner class AnimeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // `AnimeViewHolder` adalah kelas ViewHolder untuk mengelola tampilan setiap item dalam daftar.

        val imageViewAnime: ImageView = itemView.findViewById(R.id.imageViewAnime)
        // `imageViewAnime` digunakan untuk menampilkan gambar anime di tampilan item.

        val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        // `textViewTitle` digunakan untuk menampilkan judul anime di tampilan item.

        val textViewRating: TextView = itemView.findViewById(R.id.textViewRating) // Ganti dari Episode ke Rating
        // `textViewRating` digunakan untuk menampilkan rating anime (bukan episode).

        val imageViewStar: ImageView = itemView.findViewById(R.id.imageViewStar) // Tambahkan ikon bintang
        // `imageViewStar` digunakan untuk menampilkan ikon bintang emas sebagai hiasan di tampilan item.
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        // Fungsi untuk membuat ViewHolder baru.

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_anime, parent, false)
        // Membuat (inflate) tampilan item dari file layout XML `item_anime.xml`.

        return AnimeViewHolder(view)
        // Mengembalikan instance `AnimeViewHolder` dengan tampilan item.
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        // Fungsi untuk mengisi data ke dalam ViewHolder.

        val anime = animeList[position]
        // Mendapatkan data anime berdasarkan posisi dalam daftar.

        holder.textViewTitle.text = anime.title
        // Menampilkan judul anime di `textViewTitle`.

        holder.textViewRating.text = anime.rating // Ganti Episode ke Rating
        // Menampilkan rating anime di `textViewRating`.

        // Perbaiki penggunaan Glide untuk imageResId
        Glide.with(holder.itemView.context) // Glide memuat gambar menggunakan konteks tampilan.
            .load(anime.imageResId) // Memuat gambar dari URI atau resource ID `imageResId`.
            .into(holder.imageViewAnime) // Menampilkan gambar yang dimuat ke dalam `imageViewAnime`.

        // Tambahkan ikon bintang emas
        holder.imageViewStar.setImageResource(R.drawable.ic_star_gold) // Pastikan ic_star_gold tersedia
        // Mengatur gambar bintang emas untuk `imageViewStar`.
    }

    override fun getItemCount(): Int {
        return animeList.size
        // Mengembalikan jumlah total item dalam daftar anime.
    }
}
