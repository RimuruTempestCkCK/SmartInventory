<?php
require_once '../config.php';

$nama = $_POST['nama_kategori'] ?? '';

if (!empty($nama)) {
    // Tabel di database Anda adalah 'brands'
    $query = "INSERT INTO brands (name) VALUES ('$nama')";

    if (mysqli_query($conn, $query)) {
        echo json_encode(["status" => true, "message" => "Rak '$nama' berhasil ditambahkan"]);
    } else {
        echo json_encode(["status" => false, "message" => "Gagal: " . mysqli_error($conn)]);
    }
} else {
    echo json_encode(["status" => false, "message" => "Nama rak kosong"]);
}
?>
