<?php
require_once '../config.php';

$id = $_POST['id'] ?? '';
$name = $_POST['name'] ?? '';

if (empty($id) || empty($name)) {
    echo json_encode(["status" => false, "message" => "ID dan nama brand harus diisi"]);
    exit;
}

$query = "UPDATE brands SET name = '$name' WHERE id = '$id'";
if (mysqli_query($conn, $query)) {
    echo json_encode(["status" => true, "message" => "Brand berhasil diperbarui"]);
} else {
    echo json_encode(["status" => false, "message" => "Gagal memperbarui brand: " . mysqli_error($conn)]);
}
?>
