<?php
require_once '../config.php';

$query = "SELECT id, username, role FROM users ORDER BY username ASC";
$result = mysqli_query($conn, $query);

$data = [];
while ($row = mysqli_fetch_assoc($result)) {
    $data[] = $row;
}

echo json_encode($data);
?>
