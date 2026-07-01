<?php
require_once '../config.php';

$id = $_POST['id_barang'] ?? '';

if (!empty($id)) {
    $query = "DELETE FROM tbl_barang WHERE id_barang = '$id'";
    if (mysqli_query($conn, $query)) {
        echo json_encode(["status" => true, "message" => "Berhasil hapus barang"]);
    } else {
        echo json_encode(["status" => false, "message" => "Gagal hapus barang"]);
    }
} else {
    echo json_encode(["status" => false, "message" => "ID tidak ditemukan"]);
}
?>
