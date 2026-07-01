<?php
require_once '../config.php';

$username = $_POST['username'] ?? '';
$password = $_POST['password'] ?? '';
$role = $_POST['role'] ?? 'staff';

if (!empty($username) && !empty($password)) {
    // Mengubah password menjadi Hash sebelum disimpan
    $hashedPassword = password_hash($password, PASSWORD_BCRYPT);

    $query = "INSERT INTO users (username, password, role) VALUES ('$username', '$hashedPassword', '$role')";

    if (mysqli_query($conn, $query)) {
        echo json_encode(["status" => true, "message" => "User berhasil ditambah dengan password terenkripsi"]);
    } else {
        echo json_encode(["status" => false, "message" => "Gagal menambah user"]);
    }
} else {
    echo json_encode(["status" => false, "message" => "Data tidak lengkap"]);
}
?>
