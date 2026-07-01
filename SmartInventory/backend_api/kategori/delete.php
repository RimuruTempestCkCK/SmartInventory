<?php
require_once '../config.php';

$id = $_POST['id'] ?? '';

if (empty($id)) {
    echo json_encode(["status" => false, "message" => "ID wajib diisi"]);
    exit;
}

$query = "DELETE FROM categories WHERE id = '$id'";
if (mysqli_query($conn, $query)) {
    echo json_encode(["status" => true, "message" => "Kategori berhasil dihapus"]);
} else {
    echo json_encode(["status" => false, "message" => "Gagal: " . mysqli_error($conn)]);
}
?>
