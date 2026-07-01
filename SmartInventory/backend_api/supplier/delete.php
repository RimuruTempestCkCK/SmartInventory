<?php
require_once '../config.php';

$id = $_POST['id_supplier'] ?? '';

if (!empty($id)) {
    $query = "DELETE FROM tbl_supplier WHERE id_supplier = '$id'";
    if (mysqli_query($conn, $query)) {
        echo json_encode(["status" => true, "message" => "Berhasil hapus supplier"]);
    } else {
        echo json_encode(["status" => false, "message" => "Gagal hapus supplier"]);
    }
} else {
    echo json_encode(["status" => false, "message" => "ID tidak ditemukan"]);
}
?>
