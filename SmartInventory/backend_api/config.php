<?php
$host = "localhost";
$user = "root";
$pass = "";
$db   = "db_inventory_aksesoris"; // Sesuaikan dengan nama database lama Anda

$conn = mysqli_connect($host, $user, $pass, $db);

if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}

header('Content-Type: application/json');
?>
