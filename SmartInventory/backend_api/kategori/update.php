<?php
require_once '../config.php';

$id = $_POST['id_kategori'] ?? '';
$nama = $_POST['nama_kategori'] ?? '';

if (!empty($id) && !empty($nama)) {
    $query = "UPDATE brands SET name = '$nama' WHERE id = '$id'";

    if (mysqli_query($conn, $query)) {
        echo json_encode(["status" => true, "message" => "Rak berhasil diupdate"]);
    } else {
        echo json_encode(["status" => false, "message" => "Gagal update"]);
    }
} else {
    echo json_encode(["status" => false, "message" => "Data tidak lengkap"]);
}
?>
