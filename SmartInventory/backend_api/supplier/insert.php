<?php
require_once '../config.php';

$nama = $_POST['nama_supplier'] ?? '';

if (!empty($nama)) {
    // Di database Anda, Supplier dan Kategori sama-sama masuk ke tabel 'brands'
    $query = "INSERT INTO brands (name) VALUES ('$nama')";

    if (mysqli_query($conn, $query)) {
        echo json_encode(["status" => true, "message" => "Merk '$nama' berhasil ditambahkan"]);
    } else {
        echo json_encode(["status" => false, "message" => "Gagal insert ke database"]);
    }
} else {
    echo json_encode(["status" => false, "message" => "Nama merk wajib diisi"]);
}
?>
