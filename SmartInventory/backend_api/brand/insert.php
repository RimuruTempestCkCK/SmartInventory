<?php
require_once '../config.php';

$name = $_POST['name'] ?? '';

if (empty($name)) {
    echo json_encode(["status" => false, "message" => "Nama brand harus diisi"]);
    exit;
}

$query = "INSERT INTO brands (name) VALUES ('$name')";
if (mysqli_query($conn, $query)) {
    echo json_encode(["status" => true, "message" => "Brand berhasil ditambahkan"]);
} else {
    echo json_encode(["status" => false, "message" => "Gagal menambahkan brand: " . mysqli_error($conn)]);
}
?>
