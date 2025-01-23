package com.example.animetrailerapp.ui.util

import android.content.Context // Import untuk menggunakan context, yaitu elemen yang menyediakan akses ke resource, layanan sistem, dll.
import android.graphics.Bitmap // Import untuk merepresentasikan gambar dalam aplikasi.
import android.graphics.BitmapFactory // Import untuk mengonversi file atau stream menjadi objek Bitmap.
import android.net.Uri // Import untuk menangani URI (Uniform Resource Identifier), seperti file atau resource.
import androidx.compose.runtime.Composable // Import untuk menandai fungsi yang dapat digunakan dalam Jetpack Compose.
import androidx.compose.ui.platform.LocalContext // Import untuk mengambil context lokal dari Jetpack Compose.
import com.example.animetrailerapp.R // Import untuk merujuk ke resource aplikasi, seperti drawable, layout, dan string.

@Composable
fun loadImageFromUri(uri: String?): Bitmap? {
    // Fungsi Composable untuk memuat gambar dari URI.
    // Parameter:
    // - uri: String? -> URI opsional yang menunjuk ke lokasi gambar.
    // Return: Bitmap? -> Bitmap hasil atau null jika terjadi error.

    return uri?.let {
        // Memastikan bahwa uri tidak null sebelum memprosesnya.
        val context = LocalContext.current
        // Mengambil context saat ini dari lingkungan Compose untuk mengakses resource dan layanan sistem.

        try {
            when {
                uri.startsWith("content://") || uri.startsWith("file://") -> {
                    // Jika URI berasal dari file atau content resolver:
                    val parsedUri = Uri.parse(uri)
                    // Mengonversi string URI menjadi objek Uri untuk diproses lebih lanjut.
                    context.contentResolver.openInputStream(parsedUri)?.use { inputStream ->
                        // Membuka input stream dari URI menggunakan content resolver.
                        BitmapFactory.decodeStream(inputStream)
                        // Mengonversi stream data menjadi objek Bitmap menggunakan BitmapFactory.
                    }
                }
                uri.startsWith("android.resource://") -> {
                    // Jika URI berasal dari resource drawable:
                    val resId = context.resources.getIdentifier(
                        uri.substringAfterLast("/"),
                        // Mendapatkan nama resource dari bagian terakhir URI.
                        "drawable",
                        // Menentukan bahwa resource yang dicari adalah drawable.
                        context.packageName
                        // Menggunakan package aplikasi untuk mencari resource.
                    )
                    BitmapFactory.decodeResource(context.resources, resId)
                    // Mengonversi drawable menjadi Bitmap menggunakan BitmapFactory.
                }
                else -> null
                // Jika URI tidak cocok dengan pola yang dikenal, mengembalikan null.
            }
        } catch (e: Exception) {
            // Menangani error yang mungkin terjadi selama proses memuat gambar.
            e.printStackTrace()
            // Mencetak stack trace untuk debugging.
            null
            // Mengembalikan null jika terjadi error.
        }
    }
}
