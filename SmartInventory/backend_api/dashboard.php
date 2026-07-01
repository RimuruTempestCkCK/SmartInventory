<?php
require_once 'config.php';

$total_barang = mysqli_fetch_assoc(mysqli_query($conn, "SELECT COUNT(*) as total FROM products"))['total'];
$total_supplier = mysqli_fetch_assoc(mysqli_query($conn, "SELECT COUNT(*) as total FROM brands"))['total'];

$tgl = date('Y-m-d');
$stok_masuk = mysqli_fetch_assoc(mysqli_query($conn, "SELECT SUM(qty) as total FROM transactions WHERE type='masuk' AND tanggal='$tgl'"))['total'] ?? 0;
$stok_keluar = mysqli_fetch_assoc(mysqli_query($conn, "SELECT SUM(qty) as total FROM transactions WHERE type='keluar' AND tanggal='$tgl'"))['total'] ?? 0;

$barang_limit = [];
$res = mysqli_query($conn, "SELECT * FROM products WHERE stok <= 5");
while($row = mysqli_fetch_assoc($res)) {
    $barang_limit[] = [
        "id_barang" => $row['id'],
        "nama_barang" => $row['nama_barang'],
        "stok" => $row['stok']
    ];
}

echo json_encode([
    "total_barang" => (int)$total_barang,
    "total_supplier" => (int)$total_supplier,
    "stok_masuk_hari_ini" => (int)$stok_masuk,
    "stok_keluar_hari_ini" => (int)$stok_keluar,
    "barang_limit" => $barang_limit
]);
?>
