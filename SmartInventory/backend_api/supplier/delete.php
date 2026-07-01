<?php
require_once '../config.php';

$id = $_POST['id'] ?? '';

if (empty($id)) {
    echo json_encode(["status" => false, "message" => "ID wajib diisi"]);
    exit;
}

mysqli_begin_transaction($conn);
try {
    mysqli_query($conn, "UPDATE products SET supplier_id = NULL WHERE supplier_id = '$id'");
    
    $query = "DELETE FROM suppliers WHERE id = '$id'";
    if (mysqli_query($conn, $query)) {
        mysqli_commit($conn);
        echo json_encode(["status" => true, "message" => "Supplier berhasil dihapus"]);
    } else {
        throw new Exception(mysqli_error($conn));
    }
} catch (Exception $e) {
    mysqli_rollback($conn);
    echo json_encode(["status" => false, "message" => "Gagal: " . $e->getMessage()]);
}
?>
