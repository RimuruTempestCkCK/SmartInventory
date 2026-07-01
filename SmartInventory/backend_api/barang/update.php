<?php
require_once '../config.php';

$id = $_POST['id_barang'] ?? '';
$kode = $_POST['kode_barang'] ?? '';
$nama = $_POST['nama_barang'] ?? '';
$id_kategori = $_POST['id_kategori'] ?? '';
$id_supplier = $_POST['id_supplier'] ?? '';
$harga = $_POST['harga'] ?? 0;
$stok = $_POST['stok'] ?? 0;

if (!empty($id) && !empty($nama)) {
    $query = "UPDATE tbl_barang SET
                kode_barang = '$kode',
                nama_barang = '$nama',
                id_kategori = '$id_kategori',
                id_supplier = '$id_supplier',
                harga = '$harga',
                stok = '$stok'
              WHERE id_barang = '$id'";
    if (mysqli_query($conn, $query)) {
        echo json_encode(["status" => true, "message" => "Berhasil update barang"]);
    } else {
        echo json_encode(["status" => false, "message" => "Gagal update barang"]);
    }
} else {
    echo json_encode(["status" => false, "message" => "Data tidak lengkap"]);
}
?>
