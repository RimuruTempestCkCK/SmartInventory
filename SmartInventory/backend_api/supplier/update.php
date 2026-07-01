<?php
require_once '../config.php';

$id = $_POST['id_supplier'] ?? '';
$nama = $_POST['nama_supplier'] ?? '';

if (!empty($id) && !empty($nama)) {
    $query = "UPDATE brands SET name = '$nama' WHERE id = '$id'";

    if (mysqli_query($conn, $query)) {
        echo json_encode(["status" => true, "message" => "Merk berhasil diupdate"]);
    } else {
        echo json_encode(["status" => false, "message" => "Gagal update database"]);
    }
} else {
    echo json_encode(["status" => false, "message" => "ID dan Nama wajib diisi"]);
}
?>
