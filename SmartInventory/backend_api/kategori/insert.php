<?php
require_once '../config.php';

$name = $_POST['name'] ?? '';

if (empty($name)) {
    echo json_encode(["status" => false, "message" => "Nama kategori wajib diisi"]);
    exit;
}

$query = "INSERT INTO categories (name) VALUES ('$name')";
if (mysqli_query($conn, $query)) {
    echo json_encode(["status" => true, "message" => "Kategori berhasil ditambahkan"]);
} else {
    echo json_encode(["status" => false, "message" => "Gagal: " . mysqli_error($conn)]);
}
?>
