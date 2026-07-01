<?php
require_once '../config.php';

// Database menggunakan string (Varchar) untuk Merk dan Kategori.
// Android mengirimkan 'id_kategori' dan 'id_supplier' sebagai string nama.

$kode = $_POST['kode_barang'] ?? '';
$nama = $_POST['nama_barang'] ?? '';
$kategori = $_POST['id_kategori'] ?? ''; // Berisi nama Rak dari UI
$merk = $_POST['id_supplier'] ?? '';     // Berisi nama Merk dari UI
$harga = $_POST['harga'] ?? 0;
$stok = $_POST['stok'] ?? 0;

if (!empty($nama) && !empty($kode)) {
    // Karena kolom di database 'products' adalah (merk, nama_barang, kategori, harga_beli, harga_jual, stok, rak)
    // Kita set harga_beli dan harga_jual sama sesuai input 'harga' dari Android.
    $query = "INSERT INTO products (merk, nama_barang, kategori, harga_beli, harga_jual, stok, rak)
              VALUES ('$merk', '$nama', '$kategori', '$harga', '$harga', '$stok', '$kode')";

    if (mysqli_query($conn, $query)) {
        echo json_encode(["status" => true, "message" => "Barang '$nama' berhasil ditambahkan ke database"]);
    } else {
        echo json_encode(["status" => false, "message" => "Gagal Database: " . mysqli_error($conn)]);
    }
} else {
    echo json_encode(["status" => false, "message" => "Data tidak lengkap (Nama & Kode wajib diisi)"]);
}
?>
