<?php
require_once '../config.php';

$query = "SELECT * FROM suppliers ORDER BY name ASC";
$result = mysqli_query($conn, $query);

$data = [];
while ($row = mysqli_fetch_assoc($result)) {
    $data[] = $row;
}

echo json_encode($data);
?>
