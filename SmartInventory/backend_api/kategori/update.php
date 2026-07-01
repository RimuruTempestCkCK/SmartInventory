<?php
require_once '../config.php';

$id = $_POST['id_kategori'] ?? '';
$nama = $_POST['nama_kategori'] ?? '';

if (!empty($id) && !empty($nama)) {
    $query = "UPDATE tbl_kategori SET nama_kategori = '$nama' WHERE id_kategori = '$id'";
    if (mysqli_query($conn, $query)) {
        echo json_encode(["status" => true, "message" => "Berhasil update kategori"]);
    } else {
        echo json_encode(["status" => false, "message" => "Gagal update kategori"]);
    }
} else {
    echo json_encode(["status" => false, "message" => "Data tidak lengkap"]);
}
?>
