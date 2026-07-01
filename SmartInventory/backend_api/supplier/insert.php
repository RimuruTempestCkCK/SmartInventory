<?php
require_once '../config.php';

$name = $_POST['name'] ?? '';
$address = $_POST['address'] ?? '';
$phone = $_POST['phone'] ?? '';

if (empty($name)) {
    echo json_encode(["status" => false, "message" => "Nama supplier wajib diisi"]);
    exit;
}

$query = "INSERT INTO suppliers (name, address, phone) VALUES ('$name', '$address', '$phone')";
if (mysqli_query($conn, $query)) {
    echo json_encode(["status" => true, "message" => "Supplier berhasil ditambahkan"]);
} else {
    echo json_encode(["status" => false, "message" => "Gagal: " . mysqli_error($conn)]);
}
?>
