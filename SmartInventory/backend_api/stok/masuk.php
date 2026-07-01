<?php
require_once '../config.php';

$id_barang = $_POST['id_barang'] ?? '';
$jumlah = $_POST['jumlah'] ?? 0;
$tanggal = $_POST['tanggal'] ?? date('Y-m-d');
$keterangan = $_POST['keterangan'] ?? '';

if (!empty($id_barang) && $jumlah > 0) {
    mysqli_begin_transaction($conn);

    // Menggunakan tabel 'transactions' sesuai database lama Anda
    $q1 = "INSERT INTO transactions (product_id, tanggal, qty, type, keterangan)
           VALUES ('$id_barang', '$tanggal', '$jumlah', 'masuk', '$keterangan')";

    // Update stok di tabel 'products'
    $q2 = "UPDATE products SET stok = stok + $jumlah WHERE id = '$id_barang'";

    if (mysqli_query($conn, $q1) && mysqli_query($conn, $q2)) {
        mysqli_commit($conn);
        echo json_encode(["status" => true, "message" => "Stok masuk berhasil dicatat"]);
    } else {
        mysqli_rollback($conn);
        echo json_encode(["status" => false, "message" => "Gagal mencatat stok masuk"]);
    }
} else {
    echo json_encode(["status" => false, "message" => "Data tidak valid"]);
}
?>
