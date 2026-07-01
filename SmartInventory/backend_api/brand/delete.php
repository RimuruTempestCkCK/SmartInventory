<?php
require_once '../config.php';

$id = $_POST['id'] ?? '';

if (empty($id)) {
    echo json_encode(["status" => false, "message" => "ID brand harus diisi"]);
    exit;
}

$query = "DELETE FROM brands WHERE id = '$id'";
if (mysqli_query($conn, $query)) {
    echo json_encode(["status" => true, "message" => "Brand berhasil dihapus"]);
} else {
    echo json_encode(["status" => false, "message" => "Gagal menghapus brand: " . mysqli_error($conn)]);
}
?>
