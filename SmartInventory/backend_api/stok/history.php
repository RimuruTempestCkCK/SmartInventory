<?php
require_once '../config.php';

$query = "SELECT t.*, p.name as product_name
          FROM transactions t
          JOIN products p ON t.product_id = p.id
          ORDER BY t.date DESC, t.id DESC";
$result = mysqli_query($conn, $query);

$data = [];
while ($row = mysqli_fetch_assoc($result)) {
    $data[] = [
        "id" => $row['id'],
        "product_id" => $row['product_id'],
        "product_name" => $row['product_name'],
        "type" => $row['type'],
        "qty" => (int)$row['qty'],
        "date" => $row['date'],
        "description" => $row['description']
    ];
}

echo json_encode($data);
?>
