<?php
require_once '../config.php';

// Menggunakan tabel 'products' sesuai database lama Anda
$query = "SELECT * FROM products ORDER BY id DESC";
$result = mysqli_query($conn, $query);

$data = [];
while ($row = mysqli_fetch_assoc($result)) {
    // Menyesuaikan format agar dibaca aplikasi Android
    $data[] = [
        "id_barang" => $row['id'],
        "kode_barang" => $row['rak'] ?? '-', // Menggunakan 'rak' sebagai kode barang jika tidak ada kolom kode
        "nama_barang" => $row['nama_barang'],
        "id_kategori" => "0",
        "nama_kategori" => $row['kategori'],
        "id_supplier" => "0",
        "nama_supplier" => $row['merk'],
        "harga" => $row['harga_jual'],
        "stok" => $row['stok']
    ];
}

echo json_encode($data);
?>
