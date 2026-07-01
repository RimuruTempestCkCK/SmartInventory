package com.example.smartinventory.data.api

import com.example.smartinventory.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("login.php")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Response<LoginResponse>

    // Kategori
    @GET("kategori/get.php")
    suspend fun getCategories(): Response<List<Category>>

    @FormUrlEncoded
    @POST("kategori/insert.php")
    suspend fun insertCategory(
        @Field("nama_kategori") name: String
    ): Response<BaseResponse>

    @FormUrlEncoded
    @POST("kategori/update.php")
    suspend fun updateCategory(
        @Field("id_kategori") id: String,
        @Field("nama_kategori") name: String
    ): Response<BaseResponse>

    @FormUrlEncoded
    @POST("kategori/delete.php")
    suspend fun deleteCategory(
        @Field("id_kategori") id: String
    ): Response<BaseResponse>

    // Supplier
    @GET("supplier/get.php")
    suspend fun getSuppliers(): Response<List<Supplier>>

    @FormUrlEncoded
    @POST("supplier/insert.php")
    suspend fun insertSupplier(
        @Field("nama_supplier") name: String,
        @Field("alamat") address: String,
        @Field("no_hp") phone: String
    ): Response<BaseResponse>

    @FormUrlEncoded
    @POST("supplier/update.php")
    suspend fun updateSupplier(
        @Field("id_supplier") id: String,
        @Field("nama_supplier") name: String,
        @Field("alamat") address: String,
        @Field("no_hp") phone: String
    ): Response<BaseResponse>

    @FormUrlEncoded
    @POST("supplier/delete.php")
    suspend fun deleteSupplier(
        @Field("id_supplier") id: String
    ): Response<BaseResponse>

    // Barang
    @GET("barang/get.php")
    suspend fun getProducts(): Response<List<Product>>

    @FormUrlEncoded
    @POST("barang/insert.php")
    suspend fun insertProduct(
        @Field("kode_barang") code: String,
        @Field("nama_barang") name: String,
        @Field("id_kategori") categoryId: String,
        @Field("id_supplier") supplierId: String,
        @Field("harga") price: Double,
        @Field("stok") stock: Int
    ): Response<BaseResponse>

    @FormUrlEncoded
    @POST("barang/update.php")
    suspend fun updateProduct(
        @Field("id_barang") id: String,
        @Field("kode_barang") code: String,
        @Field("nama_barang") name: String,
        @Field("id_kategori") categoryId: String,
        @Field("id_supplier") supplierId: String,
        @Field("harga") price: Double,
        @Field("stok") stock: Int
    ): Response<BaseResponse>

    @FormUrlEncoded
    @POST("barang/delete.php")
    suspend fun deleteProduct(
        @Field("id_barang") id: String
    ): Response<BaseResponse>

    // Stok
    @FormUrlEncoded
    @POST("stok/masuk.php")
    suspend fun stockIn(
        @Field("id_barang") productId: String,
        @Field("jumlah") quantity: Int,
        @Field("tanggal") date: String,
        @Field("keterangan") info: String
    ): Response<BaseResponse>

    @FormUrlEncoded
    @POST("stok/keluar.php")
    suspend fun stockOut(
        @Field("id_barang") productId: String,
        @Field("jumlah") quantity: Int,
        @Field("tanggal") date: String
    ): Response<BaseResponse>

    @GET("stok/history.php")
    suspend fun getHistory(): Response<List<Transaction>>

    // Dashboard & Prediksi
    @GET("dashboard.php")
    suspend fun getDashboardStats(): Response<DashboardStats>

    @GET("prediksi.php")
    suspend fun getPrediction(): Response<PredictionResponse>
}
