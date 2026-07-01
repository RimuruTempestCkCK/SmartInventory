<?php
require_once '../config.php';

$query = "SELECT t.*, b.nama_barang
          FROM transactions t
          JOIN products b ON t.product_id = b.id
          ORDER BY t.id DESC";
$result = mysqli_query($conn, $query);

$data = [];
while ($row = mysqli_fetch_assoc($result)) {
    $data[] = [
        "id_transaksi" => $row['id'],
        "id_barang" => $row['product_id'],
        "nama_barang" => $row['nama_barang'],
        "tanggal" => $row['tanggal'],
        "jumlah" => $row['qty'],
        "jenis" => ucfirst($row['type']), // 'masuk' -> 'Masuk'
        "keterangan" => $row['keterangan']
    ];
}

echo json_encode($data);
?>
