<?php
require_once '../config.php';

$id_barang = $_POST['id_barang'] ?? '';
$jumlah = $_POST['jumlah'] ?? 0;
$tanggal = $_POST['tanggal'] ?? date('Y-m-d');

if (!empty($id_barang) && $jumlah > 0) {
    // Cek stok di tabel 'products'
    $cek = mysqli_query($conn, "SELECT stok FROM products WHERE id = '$id_barang'");
    $data = mysqli_fetch_assoc($cek);

    if ($data['stok'] >= $jumlah) {
        mysqli_begin_transaction($conn);

        // Simpan ke tabel 'transactions'
        $q1 = "INSERT INTO transactions (product_id, tanggal, qty, type)
               VALUES ('$id_barang', '$tanggal', '$jumlah', 'keluar')";

        $q2 = "UPDATE products SET stok = stok - $jumlah WHERE id = '$id_barang'";

        if (mysqli_query($conn, $q1) && mysqli_query($conn, $q2)) {
            mysqli_commit($conn);
            echo json_encode(["status" => true, "message" => "Stok keluar berhasil dicatat"]);
        } else {
            mysqli_rollback($conn);
            echo json_encode(["status" => false, "message" => "Gagal mencatat stok keluar"]);
        }
    } else {
        echo json_encode(["status" => false, "message" => "Stok tidak cukup! Sisa stok: " . $data['stok']]);
    }
} else {
    echo json_encode(["status" => false, "message" => "Data tidak valid"]);
}
?>
