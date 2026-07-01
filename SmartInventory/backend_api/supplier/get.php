<?php
require_once '../config.php';

// Menggunakan tabel 'brands' sebagai pengganti supplier
$query = "SELECT id as id_supplier, name as nama_supplier, '' as alamat, '' as no_hp FROM brands ORDER BY id DESC";
$result = mysqli_query($conn, $query);

$data = [];
while ($row = mysqli_fetch_assoc($result)) {
    $data[] = $row;
}

echo json_encode($data);
?>
