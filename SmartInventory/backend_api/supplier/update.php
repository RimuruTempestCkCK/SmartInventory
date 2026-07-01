<?php
require_once '../config.php';

$id = $_POST['id'] ?? '';
$name = $_POST['name'] ?? '';
$address = $_POST['address'] ?? '';
$phone = $_POST['phone'] ?? '';

if (empty($id) || empty($name)) {
    echo json_encode(["status" => false, "message" => "ID dan Nama wajib diisi"]);
    exit;
}

$query = "UPDATE suppliers SET name = '$name', address = '$address', phone = '$phone' WHERE id = '$id'";
if (mysqli_query($conn, $query)) {
    echo json_encode(["status" => true, "message" => "Supplier berhasil diperbarui"]);
} else {
    echo json_encode(["status" => false, "message" => "Gagal: " . mysqli_error($conn)]);
}
?>
