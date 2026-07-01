<?php
require_once '../config.php';

$id = $_POST['id_kategori'] ?? '';

if (!empty($id)) {
    $query = "DELETE FROM tbl_kategori WHERE id_kategori = '$id'";
    if (mysqli_query($conn, $query)) {
        echo json_encode(["status" => true, "message" => "Berhasil hapus kategori"]);
    } else {
        echo json_encode(["status" => false, "message" => "Gagal hapus kategori"]);
    }
} else {
    echo json_encode(["status" => false, "message" => "ID tidak ditemukan"]);
}
?>
