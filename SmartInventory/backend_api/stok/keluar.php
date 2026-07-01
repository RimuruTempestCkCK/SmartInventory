<?php
require_once '../config.php';

$id_barang = $_POST['id_barang'] ?? '';
$jumlah = $_POST['jumlah'] ?? 0;
$tanggal = $_POST['tanggal'] ?? date('Y-m-d');

if (!empty($id_barang) && $jumlah > 0) {
    mysqli_begin_transaction($conn);

    // 1. Cek stok saat ini
    $res = mysqli_query($conn, "SELECT stok FROM products WHERE id = '$id_barang'");
    $data = mysqli_fetch_assoc($res);

    if ($data && $data['stok'] >= $jumlah) {
        // 2. Insert transaksi
        $q1 = "INSERT INTO transactions (product_id, tanggal, qty, type, keterangan)
               VALUES ('$id_barang', '$tanggal', '$jumlah', 'keluar', 'Stok Keluar')";

        // 3. Update stok produk
        $q2 = "UPDATE products SET stok = stok - $jumlah WHERE id = '$id_barang'";

        if (mysqli_query($conn, $q1) && mysqli_query($conn, $q2)) {
            mysqli_commit($conn);
            echo json_encode(["status" => true, "message" => "Stok berhasil dikurangi"]);
        } else {
            mysqli_rollback($conn);
            echo json_encode(["status" => false, "message" => "Gagal update database"]);
        }
    } else {
        echo json_encode(["status" => false, "message" => "Stok tidak mencukupi atau barang tidak ditemukan"]);
    }
} else {
    echo json_encode(["status" => false, "message" => "ID Barang dan Jumlah tidak boleh kosong"]);
}
?>
