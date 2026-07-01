<?php
require_once '../config.php';

$id = $_POST['id'] ?? '';

if (empty($id)) {
    echo json_encode(["status" => false, "message" => "ID wajib diisi"]);
    exit;
}

mysqli_begin_transaction($conn);
try {
    mysqli_query($conn, "UPDATE products SET category_id = NULL WHERE category_id = '$id'");
    
    $query = "DELETE FROM categories WHERE id = '$id'";
    if (mysqli_query($conn, $query)) {
        mysqli_commit($conn);
        echo json_encode(["status" => true, "message" => "Kategori berhasil dihapus"]);
    } else {
        throw new Exception(mysqli_error($conn));
    }
} catch (Exception $e) {
    mysqli_rollback($conn);
    echo json_encode(["status" => false, "message" => "Gagal: " . $e->getMessage()]);
}
?>
