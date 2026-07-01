<?php
require_once '../config.php';

$id = $_POST['id_supplier'] ?? '';
$nama = $_POST['nama_supplier'] ?? '';
$alamat = $_POST['alamat'] ?? '';
$no_hp = $_POST['no_hp'] ?? '';

if (!empty($id) && !empty($nama)) {
    $query = "UPDATE tbl_supplier SET nama_supplier = '$nama', alamat = '$alamat', no_hp = '$no_hp' WHERE id_supplier = '$id'";
    if (mysqli_query($conn, $query)) {
        echo json_encode(["status" => true, "message" => "Berhasil update supplier"]);
    } else {
        echo json_encode(["status" => false, "message" => "Gagal update supplier"]);
    }
} else {
    echo json_encode(["status" => false, "message" => "Data tidak lengkap"]);
}
?>
