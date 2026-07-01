<?php
require_once '../config.php';

// Menggunakan tabel 'brands' sebagai pengganti kategori/merk
$query = "SELECT id as id_kategori, name as nama_kategori FROM brands ORDER BY id DESC";
$result = mysqli_query($conn, $query);

$data = [];
while ($row = mysqli_fetch_assoc($result)) {
    $data[] = $row;
}

echo json_encode($data);
?>
