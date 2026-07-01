<?php
require_once '../config.php';

$id = $_POST['id_supplier'] ?? '';

if (!empty($id)) {
    $query = "DELETE FROM brands WHERE id = '$id'";

    if (mysqli_query($conn, $query)) {
        echo json_encode(["status" => true, "message" => "Merk berhasil dihapus"]);
    } else {
        echo json_encode(["status" => false, "message" => "Gagal hapus dari database"]);
    }
} else {
    echo json_encode(["status" => false, "message" => "ID tidak valid"]);
}
?>
