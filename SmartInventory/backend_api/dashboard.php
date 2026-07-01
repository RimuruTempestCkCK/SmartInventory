<?php
require_once 'config.php';

$total_barang = mysqli_fetch_assoc(mysqli_query($conn, "SELECT COUNT(*) as total FROM products"))['total'];
$total_brand = mysqli_fetch_assoc(mysqli_query($conn, "SELECT COUNT(*) as total FROM brands"))['total'];
$total_supplier = mysqli_fetch_assoc(mysqli_query($conn, "SELECT COUNT(*) as total FROM suppliers"))['total'];

$tgl = date('Y-m-d');
$stok_masuk = mysqli_fetch_assoc(mysqli_query($conn, "SELECT SUM(qty) as total FROM transactions WHERE type='in' AND date='$tgl'"))['total'] ?? 0;
$stok_keluar = mysqli_fetch_assoc(mysqli_query($conn, "SELECT SUM(qty) as total FROM transactions WHERE type='out' AND date='$tgl'"))['total'] ?? 0;

$barang_limit = [];
$res = mysqli_query($conn, "SELECT p.*, b.name as brand_name, c.name as category_name
                            FROM products p
                            LEFT JOIN brands b ON p.brand_id = b.id
                            LEFT JOIN categories c ON p.category_id = c.id
                            WHERE p.stok <= 5");
while($row = mysqli_fetch_assoc($res)) {
    $barang_limit[] = [
        "id" => $row['id'],
        "code" => $row['code'],
        "name" => $row['name'],
        "brand_id" => $row['brand_id'],
        "brand_name" => $row['brand_name'],
        "category_id" => $row['category_id'],
        "category_name" => $row['category_name'],
        "stok" => (int)$row['stok'],
        "lokasi_rak" => $row['lokasi_rak']
    ];
}

echo json_encode([
    "total_barang" => (int)$total_barang,
    "total_brand" => (int)$total_brand,
    "total_supplier" => (int)$total_supplier,
    "stok_masuk_hari_ini" => (int)$stok_masuk,
    "stok_keluar_hari_ini" => (int)$stok_keluar,
    "barang_limit" => $barang_limit
]);
?>
