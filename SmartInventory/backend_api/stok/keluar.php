<?php
require_once '../config.php';

$product_id = $_POST['product_id'] ?? '';
$qty = $_POST['qty'] ?? 0;
$date = $_POST['date'] ?? date('Y-m-d');
$description = $_POST['description'] ?? '';

if (empty($product_id) || $qty <= 0) {
    echo json_encode(["status" => false, "message" => "Produk dan jumlah harus valid"]);
    exit;
}

mysqli_begin_transaction($conn);

try {
    // Cek stok cukup
    $check = mysqli_query($conn, "SELECT stok FROM products WHERE id = '$product_id'");
    $row = mysqli_fetch_assoc($check);
    if ($row['stok'] < $qty) {
        throw new Exception("Stok tidak mencukupi");
    }

    $q1 = "INSERT INTO transactions (product_id, type, qty, date, description) VALUES ('$product_id', 'out', '$qty', '$date', '$description')";
    $q2 = "UPDATE products SET stok = stok - $qty WHERE id = '$product_id'";

    if (mysqli_query($conn, $q1) && mysqli_query($conn, $q2)) {
        mysqli_commit($conn);
        echo json_encode(["status" => true, "message" => "Stok keluar berhasil dicatat"]);
    } else {
        throw new Exception(mysqli_error($conn));
    }
} catch (Exception $e) {
    mysqli_rollback($conn);
    echo json_encode(["status" => false, "message" => "Gagal mencatat stok keluar: " . $e->getMessage()]);
}
?>
