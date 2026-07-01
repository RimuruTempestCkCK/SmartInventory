<?php
require_once '../config.php';

$id = $_POST['id'] ?? '';
$username = $_POST['username'] ?? '';
$password = $_POST['password'] ?? '';
$role = $_POST['role'] ?? 'admin';

if (empty($id) || empty($username)) {
    echo json_encode(["status" => false, "message" => "ID dan Username wajib diisi"]);
    exit;
}

if (!empty($password)) {
    $query = "UPDATE users SET username = '$username', password = '$password', role = '$role' WHERE id = '$id'";
} else {
    $query = "UPDATE users SET username = '$username', role = '$role' WHERE id = '$id'";
}

if (mysqli_query($conn, $query)) {
    echo json_encode(["status" => true, "message" => "User berhasil diperbarui"]);
} else {
    echo json_encode(["status" => false, "message" => "Gagal memperbarui user: " . mysqli_error($conn)]);
}
?>
