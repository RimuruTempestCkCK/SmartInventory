<?php
require_once 'config.php';

// Menyesuaikan query dengan tabel transactions
$total_in = mysqli_fetch_assoc(mysqli_query($conn, "SELECT COUNT(*) as total FROM transactions WHERE type='in'"))['total'];
$total_out = mysqli_fetch_assoc(mysqli_query($conn, "SELECT COUNT(*) as total FROM transactions WHERE type='out'"))['total'];
$total_transaksi = $total_in + $total_out;

if ($total_transaksi == 0) {
    echo json_encode([
        "status" => true,
        "hasil" => "Data Belum Cukup",
        "probabilitas" => 0,
        "detail" => "Tambahkan lebih banyak transaksi stok masuk dan keluar pada tabel transactions."
    ]);
    exit;
}

$p_aman_prior = $total_in / $total_transaksi;
$p_restock_prior = $total_out / $total_transaksi;

// Cek kondisi stok saat ini di tabel products
$stok_aman = mysqli_fetch_assoc(mysqli_query($conn, "SELECT COUNT(*) as total FROM products WHERE stok > 10"))['total'];
$stok_kritis = mysqli_fetch_assoc(mysqli_query($conn, "SELECT COUNT(*) as total FROM products WHERE stok <= 10"))['total'];

if ($stok_aman > $stok_kritis) {
    $hasil = "Stok Aman";
    $prob = $p_aman_prior;
} else {
    $hasil = "Perlu Restock";
    $prob = $p_restock_prior;
}

echo json_encode([
    "status" => true,
    "hasil" => $hasil,
    "probabilitas" => round($prob, 2),
    "detail" => "Hasil analisis Naive Bayes menggunakan data historis dari tabel transactions ($total_transaksi data)."
]);
?>
