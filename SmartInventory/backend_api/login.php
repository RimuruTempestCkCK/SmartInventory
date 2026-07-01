<?php
require_once 'config.php';

$username = $_POST['username'] ?? '';
$password = $_POST['password'] ?? '';

$query = "SELECT * FROM users WHERE username = '$username'";
$result = mysqli_query($conn, $query);

if (mysqli_num_rows($result) > 0) {
    $user = mysqli_fetch_assoc($result);
    $storedPassword = $user['password'];

    // Coba verifikasi hash, jika gagal coba cek plain text (untuk data dummy di SQL)
    if (password_verify($password, $storedPassword) || $password === $storedPassword) {
        echo json_encode([
            "status" => true,
            "message" => "Login Berhasil",
            "user" => [
                "id" => $user['id'],
                "username" => $user['username'],
                "role" => $user['role']
            ]
        ]);
    } else {
        echo json_encode(["status" => false, "message" => "Password Salah"]);
    }
} else {
    echo json_encode(["status" => false, "message" => "Username Tidak Ditemukan"]);
}
?>
