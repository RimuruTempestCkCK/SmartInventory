<?php
require_once '../config.php';

$code = $_POST['code'] ?? '';
$name = $_POST['name'] ?? '';
$category_id = $_POST['category_id'] ?? null;
$brand_id = $_POST['brand_id'] ?? null;
$supplier_id = $_POST['supplier_id'] ?? null;
$buy_price = $_POST['buy_price'] ?? 0;
$sell_price = $_POST['sell_price'] ?? 0;
$stok = $_POST['stok'] ?? 0;
$lokasi_rak = $_POST['lokasi_rak'] ?? '';
$warna = $_POST['warna'] ?? '';
$kondisi = $_POST['kondisi'] ?? 'Baru';

if (empty($code) || empty($name)) {
    echo json_encode(["status" => false, "message" => "Kode dan Nama barang wajib diisi"]);
    exit;
}

$query = "INSERT INTO products (code, name, category_id, brand_id, supplier_id, buy_price, sell_price, stok, lokasi_rak, warna, kondisi)
          VALUES ('$code', '$name', '$category_id', '$brand_id', '$supplier_id', '$buy_price', '$sell_price', '$stok', '$lokasi_rak', '$warna', '$kondisi')";

if (mysqli_query($conn, $query)) {
    echo json_encode(["status" => true, "message" => "Produk berhasil ditambahkan"]);
} else {
    echo json_encode(["status" => false, "message" => "Gagal menambahkan produk: " . mysqli_error($conn)]);
}
?>
