# Rencana Pengerjaan Project Inventory Android + REST API + Naive Bayes

## Gambaran Arsitektur

``` text
Android (Kotlin)
        |
   Retrofit (HTTP/JSON)
        |
REST API PHP
        |
     MySQL
```

## Tahap 1 - Analisis Website

-   Pelajari source code website.
-   Pahami struktur database.
-   Identifikasi tabel:
    -   tbl_user
    -   tbl_barang
    -   tbl_kategori
    -   tbl_supplier
    -   tbl_stok_masuk
    -   tbl_stok_keluar
-   Pahami alur login, stok masuk, stok keluar, dan laporan.

## Tahap 2 - REST API PHP

Struktur API:

``` text
/api
    login.php

    /barang
        get.php
        insert.php
        update.php
        delete.php

    /kategori
        get.php
        insert.php
        update.php
        delete.php

    /supplier
        get.php
        insert.php
        update.php
        delete.php

    /stok
        masuk.php
        keluar.php
        history.php

    dashboard.php
    laporan.php
    prediksi.php
```

Semua endpoint mengembalikan JSON.

## Tahap 3 - Project Android

Teknologi: - Kotlin - MVVM - Retrofit - ViewBinding - Material Design
3 - RecyclerView - SharedPreferences

Struktur project:

``` text
app/
 ├── data
 │    ├── api
 │    ├── model
 │    └── repository
 ├── ui
 │    ├── login
 │    ├── dashboard
 │    ├── barang
 │    ├── kategori
 │    ├── supplier
 │    ├── stokmasuk
 │    ├── stokkeluar
 │    ├── history
 │    ├── laporan
 │    └── prediksi
 ├── network
 ├── preference
 └── utils
```

## Tahap 4 - Login

-   Username
-   Password
-   Login API
-   Simpan session menggunakan SharedPreferences.

## Tahap 5 - Dashboard

Menampilkan: - Total Barang - Total Supplier - Stok Masuk Hari Ini -
Stok Keluar Hari Ini - Barang Hampir Habis

## Tahap 6 - CRUD Barang

Field: - Kode - Nama - Kategori - Supplier - Harga - Stok

Fitur: - List - Tambah - Edit - Hapus - Search

## Tahap 7 - CRUD Kategori

-   List
-   Tambah
-   Edit
-   Hapus

## Tahap 8 - CRUD Supplier

Field: - Nama - Alamat - Nomor HP

Fitur: - List - Tambah - Edit - Hapus

## Tahap 9 - Stok Masuk

Input: - Barang - Tanggal - Jumlah - Keterangan

Update stok:

    stok = stok + jumlah

## Tahap 10 - Stok Keluar

Input: - Barang - Tanggal - Jumlah

Update stok:

    stok = stok - jumlah

## Tahap 11 - Riwayat Transaksi

Menampilkan: - Tanggal - Barang - Jenis Transaksi - Jumlah

## Tahap 12 - Monitoring Stok

Jika stok di bawah batas minimum maka tampilkan peringatan.

## Tahap 13 - Notifikasi

Jika stok menipis tampilkan alert pada dashboard.

## Tahap 14 - Laporan

Filter: - Tanggal Awal - Tanggal Akhir

Output: - Barang - Masuk - Keluar - Sisa

## Tahap 15 - Naive Bayes

Disarankan diproses di backend PHP.

Flow:

1.  Android mengirim data.
2.  API menjalankan perhitungan Naive Bayes.
3.  API mengembalikan hasil prediksi.
4.  Android menampilkan hasil.

Contoh output:

``` json
{
  "hasil":"Stok Aman",
  "probabilitas":0.91
}
```

## Tahap 16 - Testing

Checklist: - Login - Dashboard - CRUD Barang - CRUD Kategori - CRUD
Supplier - Stok Masuk - Stok Keluar - Riwayat - Monitoring - Laporan -
Prediksi Naive Bayes

## Urutan Pengerjaan

1.  Analisis website.
2.  Analisis database.
3.  Buat REST API.
4.  Testing API menggunakan Postman.
5.  Buat project Android.
6.  Implementasi Login.
7.  Dashboard.
8.  CRUD Barang.
9.  CRUD Kategori.
10. CRUD Supplier.
11. Stok Masuk.
12. Stok Keluar.
13. Riwayat.
14. Monitoring.
15. Laporan.
16. Naive Bayes.
17. Testing.
18. Bug fixing.
19. Finalisasi source code.

---
**STATUS UPDATE (7/1/2026):**
Database refactoring completed. All Android models, API services, and backend PHP scripts are now synchronized with the `db_inventory_aksesoris` schema.
- Separate `brands` and `categories` tables implemented.
- Product model updated with full relational support (category_id, brand_id, supplier_id).
- Transaction logic updated to `in`/`out`/`reject`.
- Naive Bayes logic updated for new transaction types.

