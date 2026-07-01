<?php
require_once '../config.php';

$nama = $_POST['nama_barang'] ?? '';
$merk = $_POST['id_supplier'] ?? ''; // Di Android kita kirim id_supplier, di sini kita simpan ke kolom 'merk'
$kategori = $_POST['id_kategori'] ?? ''; // Di Android kita kirim id_kategori, di sini kita simpan ke kolom 'kategori'
$harga_beli = $_POST['harga'] ?? 0;
$harga_jual = $_POST['harga'] ?? 0;
$stok = $_POST['stok'] ?? 0;
$rak = $_POST['kode_barang'] ?? ''; // Pakai kode_barang dari android untuk kolom rak

if (!empty($nama)) {
    $query = "INSERT INTO products (merk, nama_barang, kategori, harga_beli, harga_jual, stok, rak)
              VALUES ('$merk', '$nama', '$kategori', '$harga_beli', '$harga_jual', '$stok', '$rak')";
    if (mysqli_query($conn, $query)) {
        echo json_encode(["status" => true, "message" => "Berhasil tambah barang"]);
    } else {
        echo json_encode(["status" => false, "message" => "Gagal tambah barang"]);
    }
} else {
    echo json_encode(["status" => false, "message" => "Data tidak lengkap"]);
}
?>
