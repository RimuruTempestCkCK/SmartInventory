<?php
require_once '../config.php';

$nama = $_POST['nama_supplier'] ?? '';
$alamat = $_POST['alamat'] ?? '';
$no_hp = $_POST['no_hp'] ?? '';

if (!empty($nama)) {
    $query = "INSERT INTO tbl_supplier (nama_supplier, alamat, no_hp) VALUES ('$nama', '$alamat', '$no_hp')";
    if (mysqli_query($conn, $query)) {
        echo json_encode(["status" => true, "message" => "Berhasil tambah supplier"]);
    } else {
        echo json_encode(["status" => false, "message" => "Gagal tambah supplier"]);
    }
} else {
    echo json_encode(["status" => false, "message" => "Data tidak lengkap"]);
}
?>
