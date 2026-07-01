<?php
require_once '../config.php';

// Database Anda menggunakan Nama (Varchar) untuk Merk dan Kategori, bukan ID.
// Maka kita ambil stringnya langsung dari Android.

$kode = $_POST['kode_barang'] ?? '';
$nama = $_POST['nama_barang'] ?? '';
$kategori = $_POST['id_kategori'] ?? ''; // Di Android dikirim nama kategori
$merk = $_POST['id_supplier'] ?? '';     // Di Android dikirim nama merk
$harga_beli = $_POST['harga'] ?? 0;
$harga_jual = $_POST['harga'] ?? 0;
$stok = $_POST['stok'] ?? 0;

if (!empty($nama)) {
    $query = "INSERT INTO products (merk, nama_barang, kategori, harga_beli, harga_jual, stok, rak)
              VALUES ('$merk', '$nama', '$kategori', '$harga_beli', '$harga_jual', '$stok', '$kode')";

    if (mysqli_query($conn, $query)) {
        echo json_encode(["status" => true, "message" => "Barang berhasil ditambah"]);
    } else {
        echo json_encode(["status" => false, "message" => "Gagal: " . mysqli_error($conn)]);
    }
} else {
    echo json_encode(["status" => false, "message" => "Data tidak lengkap"]);
}
?>
