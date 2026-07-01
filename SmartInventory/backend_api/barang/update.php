<?php
require_once '../config.php';

$id = $_POST['id_barang'] ?? '';
$kode = $_POST['kode_barang'] ?? '';
$nama = $_POST['nama_barang'] ?? '';
$kategori = $_POST['id_kategori'] ?? '';
$merk = $_POST['id_supplier'] ?? '';
$harga_beli = $_POST['harga'] ?? 0;
$harga_jual = $_POST['harga'] ?? 0;
$stok = $_POST['stok'] ?? 0;

if (!empty($id) && !empty($nama)) {
    $query = "UPDATE products SET
                merk = '$merk',
                nama_barang = '$nama',
                kategori = '$kategori',
                harga_beli = '$harga_beli',
                harga_jual = '$harga_jual',
                stok = '$stok',
                rak = '$kode'
              WHERE id = '$id'";

    if (mysqli_query($conn, $query)) {
        echo json_encode(["status" => true, "message" => "Barang berhasil diupdate"]);
    } else {
        echo json_encode(["status" => false, "message" => "Gagal update"]);
    }
} else {
    echo json_encode(["status" => false, "message" => "Data tidak lengkap"]);
}
?>
