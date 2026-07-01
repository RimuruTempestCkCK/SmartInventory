<?php
require_once 'config.php';

$username = $_POST['username'] ?? '';
$password = $_POST['password'] ?? '';

// Menggunakan tabel 'users' sesuai database lama Anda
$query = "SELECT * FROM users WHERE username = '$username' AND password = '$password'";
$result = mysqli_query($conn, $query);

if (mysqli_num_rows($result) > 0) {
    $user = mysqli_fetch_assoc($result);
    echo json_encode([
        "status" => true,
        "message" => "Login Berhasil",
        "user" => [
            "id_user" => $user['id'],
            "username" => $user['username'],
            "nama_lengkap" => $user['username'], // Di tabel lama tidak ada nama_lengkap, pakai username saja
            "level" => $user['role'] // Di tabel lama kolomnya adalah 'role'
        ]
    ]);
} else {
    echo json_encode([
        "status" => false,
        "message" => "Username atau Password Salah (Catatan: Pastikan password di database adalah plain text)"
    ]);
}
?>
