<?php
require_once 'config.php';

$username = $_POST['username'] ?? '';
$password = $_POST['password'] ?? '';

$query = "SELECT * FROM users WHERE username = '$username'";
$result = mysqli_query($conn, $query);

if (mysqli_num_rows($result) > 0) {
    $user = mysqli_fetch_assoc($result);
    $hashedPassword = $user['password'];

    // Verifikasi password input dengan Hash di database
    if (password_verify($password, $hashedPassword)) {
        echo json_encode([
            "status" => true,
            "message" => "Login Berhasil",
            "user" => [
                "id_user" => $user['id'],
                "username" => $user['username'],
                "nama_lengkap" => $user['username'],
                "level" => $user['role']
            ]
        ]);
    } else {
        echo json_encode(["status" => false, "message" => "Password Salah"]);
    }
} else {
    echo json_encode(["status" => false, "message" => "Username Tidak Ditemukan"]);
}
?>