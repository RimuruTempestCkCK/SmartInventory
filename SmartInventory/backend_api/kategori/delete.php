<?php
require_once '../config.php';

$id = $_POST['id_kategori'] ?? '';

if (!empty($id)) {
    $query = "DELETE FROM brands WHERE id = '$id'";

    if (mysqli_query($conn, $query)) {
        echo json_encode(["status" => true, "message" => "Rak berhasil dihapus"]);
    } else {
        echo json_encode(["status" => false, "message" => "Gagal hapus database"]);
    }
} else {
    echo json_encode(["status" => false, "message" => "ID tidak ditemukan"]);
}
?>
