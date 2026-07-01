<?php
require_once '../config.php';

$id = $_POST['id'] ?? '';
$name = $_POST['name'] ?? '';

if (empty($id) || empty($name)) {
    echo json_encode(["status" => false, "message" => "ID dan Nama wajib diisi"]);
    exit;
}

$query = "UPDATE categories SET name = '$name' WHERE id = '$id'";
if (mysqli_query($conn, $query)) {
    echo json_encode(["status" => true, "message" => "Kategori berhasil diperbarui"]);
} else {
    echo json_encode(["status" => false, "message" => "Gagal: " . mysqli_error($conn)]);
}
?>
