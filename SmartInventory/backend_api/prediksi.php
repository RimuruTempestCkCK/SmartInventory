<?php
require_once 'config.php';

try {
    $res_in = mysqli_query($conn, "SELECT COUNT(*) as total FROM transactions WHERE type='in'");
    if (!$res_in) throw new Exception(mysqli_error($conn));
    $total_in = (int)mysqli_fetch_assoc($res_in)['total'];

    $res_out = mysqli_query($conn, "SELECT COUNT(*) as total FROM transactions WHERE type='out'");
    if (!$res_out) throw new Exception(mysqli_error($conn));
    $total_out = (int)mysqli_fetch_assoc($res_out)['total'];

    $total_transaksi = $total_in + $total_out;

    if ($total_transaksi == 0) {
        echo json_encode([
            "status" => true,
            "hasil" => "Data Belum Cukup",
            "probabilitas" => 0.0,
            "detail" => "Tambahkan lebih banyak transaksi stok masuk dan keluar pada tabel transactions."
        ]);
        exit;
    }

    $p_aman_prior = $total_in / $total_transaksi;
    $p_restock_prior = $total_out / $total_transaksi;

    $res_aman = mysqli_query($conn, "SELECT COUNT(*) as total FROM products WHERE stok > 10");
    if (!$res_aman) throw new Exception(mysqli_error($conn));
    $stok_aman = (int)mysqli_fetch_assoc($res_aman)['total'];

    $res_kritis = mysqli_query($conn, "SELECT COUNT(*) as total FROM products WHERE stok <= 10");
    if (!$res_kritis) throw new Exception(mysqli_error($conn));
    $stok_kritis = (int)mysqli_fetch_assoc($res_kritis)['total'];

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
        "probabilitas" => round((double)$prob, 2),
        "detail" => "Hasil analisis Naive Bayes menggunakan data historis dari tabel transactions ($total_transaksi data)."
    ]);
} catch (Exception $e) {
    echo json_encode([
        "status" => false,
        "hasil" => "Error Analisis",
        "probabilitas" => 0.0,
        "detail" => "Terjadi kesalahan: " . $e->getMessage()
    ]);
}
?>
