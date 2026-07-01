<?php
require_once '../config.php';

$id = $_POST['id'] ?? '';

if (empty($id)) {
    echo json_encode(["status" => false, "message" => "ID user wajib diisi"]);
    exit;
}

$query = "DELETE FROM users WHERE id = '$id'";
if (mysqli_query($conn, $query)) {
    echo json_encode(["status" => true, "message" => "User berhasil dihapus"]);
} else {
    echo json_encode(["status" => false, "message" => "Gagal menghapus user: " . mysqli_error($conn)]);
}
?>
