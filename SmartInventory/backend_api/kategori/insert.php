<?php
require_once '../config.php';

$nama = $_POST['nama_kategori'] ?? '';

if (!empty($nama)) {
    $query = "INSERT INTO tbl_kategori (nama_kategori) VALUES ('$nama')";
    if (mysqli_query($conn, $query)) {
        echo json_encode(["status" => true, "message" => "Berhasil tambah kategori"]);
    } else {
        echo json_encode(["status" => false, "message" => "Gagal tambah kategori"]);
    }
} else {
    echo json_encode(["status" => false, "message" => "Data tidak lengkap"]);
}
?>
