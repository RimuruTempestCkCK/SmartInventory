<?php
require_once '../config.php';

$query = "SELECT p.*, c.name as category_name, b.name as brand_name, s.name as supplier_name
          FROM products p
          LEFT JOIN categories c ON p.category_id = c.id
          LEFT JOIN brands b ON p.brand_id = b.id
          LEFT JOIN suppliers s ON p.supplier_id = s.id
          ORDER BY p.id DESC";
$result = mysqli_query($conn, $query);

$data = [];
while ($row = mysqli_fetch_assoc($result)) {
    $data[] = [
        "id" => $row['id'],
        "code" => $row['code'],
        "name" => $row['name'],
        "category_id" => $row['category_id'],
        "category_name" => $row['category_name'],
        "brand_id" => $row['brand_id'],
        "brand_name" => $row['brand_name'],
        "supplier_id" => $row['supplier_id'],
        "supplier_name" => $row['supplier_name'],
        "buy_price" => (float)$row['buy_price'],
        "sell_price" => (float)$row['sell_price'],
        "stok" => (int)$row['stok'],
        "lokasi_rak" => $row['lokasi_rak'],
        "warna" => $row['warna'],
        "kondisi" => $row['kondisi'],
        "gambar" => $row['gambar'],
        "created_at" => $row['created_at']
    ];
}

echo json_encode($data);
?>
