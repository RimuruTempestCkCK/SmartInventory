<?php
require_once '../config.php';

$id = $_POST['id'] ?? '';

if (empty($id)) {
    echo json_encode(["status" => false, "message" => "ID barang wajib diisi"]);
    exit;
}

$query = "DELETE FROM products WHERE id = '$id'";
if (mysqli_query($conn, $query)) {
    echo json_encode(["status" => true, "message" => "Produk berhasil dihapus"]);
} else {
    echo json_encode(["status" => false, "message" => "Gagal menghapus produk: " . mysqli_error($conn)]);
}
?>
