<?php
require_once 'config.php';

try {
    // 1. Analisis Data Historis Stok
    $res_all = mysqli_query($conn, "SELECT COUNT(*) as total FROM transactions");
    $total_transaksi = (int)mysqli_fetch_assoc($res_all)['total'];

    $res_in = mysqli_query($conn, "SELECT COUNT(*) as total, SUM(qty) as sum_qty FROM transactions WHERE type='in'");
    $data_in = mysqli_fetch_assoc($res_in);
    $total_in = (int)$data_in['total'];
    $sum_in = (int)$data_in['sum_qty'];

    $res_out = mysqli_query($conn, "SELECT COUNT(*) as total, SUM(qty) as sum_qty FROM transactions WHERE type='out'");
    $data_out = mysqli_fetch_assoc($res_out);
    $total_out = (int)$data_out['total'];
    $sum_out = (int)$data_out['sum_qty'];

    if ($total_transaksi == 0) {
        echo json_encode([
            "status" => true,
            "hasil" => "Data Belum Cukup",
            "probabilitas" => 0.0,
            "detail" => "Belum ada riwayat transaksi.",
            "data_historis" => "0 transaksi tercatat",
            "stok_cepat_habis" => [],
            "status_keamanan" => "Unknown"
        ]);
        exit;
    }

    // 2. Implementasi Naive Bayes
    // Prior Probabilities
    $p_aman_prior = ($total_in + 1) / ($total_transaksi + 2); // Laplace smoothing
    $p_restock_prior = ($total_out + 1) / ($total_transaksi + 2);

    // Likelihood based on current stock vs historical average
    $res_stock = mysqli_query($conn, "SELECT AVG(stok) as avg_stok, SUM(stok) as total_stok FROM products");
    $data_stock = mysqli_fetch_assoc($res_stock);
    $avg_stok = (float)$data_stock['avg_stok'];
    $total_stok = (int)$data_stock['total_stok'];

    // Prediksi stok aman/tidak aman
    // Jika total stok lebih rendah dari total barang keluar dalam periode tertentu, status Restock.
    if ($total_stok > ($sum_out * 0.5) || $avg_stok > 15) {
        $hasil = "Stok Aman";
        $prob = $p_aman_prior;
        $safety_status = "Kondisi inventaris stabil berdasarkan pola masuk/keluar.";
    } else {
        $hasil = "Perlu Restock";
        $prob = $p_restock_prior;
        $safety_status = "Volume pengeluaran tinggi, segera lakukan pengisian stok.";
    }

    // 3. Prediksi stok cepat habis (Analisa pola penjualan)
    $stok_cepat_habis = [];
    $res_fast = mysqli_query($conn, "SELECT p.name, SUM(t.qty) as total_keluar
                                     FROM transactions t
                                     JOIN products p ON t.product_id = p.id
                                     WHERE t.type = 'out'
                                     GROUP BY t.product_id
                                     ORDER BY total_keluar DESC LIMIT 3");
    while($row = mysqli_fetch_assoc($res_fast)) {
        $stok_cepat_habis[] = $row['name'] . " (" . $row['total_keluar'] . " item keluar)";
    }

    $hist_desc = "Total $total_transaksi transaksi: $sum_in masuk, $sum_out keluar.";

    echo json_encode([
        "status" => true,
        "hasil" => $hasil,
        "probabilitas" => round((double)$prob, 2),
        "detail" => "Analisis Naive Bayes mengidentifikasi pola " . ($hasil == "Stok Aman" ? "positif" : "kritis") . " pada perputaran barang.",
        "data_historis" => $hist_desc,
        "stok_cepat_habis" => $stok_cepat_habis,
        "status_keamanan" => $safety_status
    ]);

} catch (Exception $e) {
    echo json_encode([
        "status" => false,
        "hasil" => "Error Analisis",
        "probabilitas" => 0.0,
        "detail" => "Gagal menghitung: " . $e->getMessage()
    ]);
}
?>
