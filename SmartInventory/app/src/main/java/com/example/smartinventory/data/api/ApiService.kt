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

    // Categories
    @GET("kategori/get.php")
    suspend fun getCategories(): Response<List<Category>>

    @FormUrlEncoded
    @POST("kategori/insert.php")
    suspend fun insertCategory(
        @Field("name") name: String
    ): Response<BaseResponse>

    @FormUrlEncoded
    @POST("kategori/update.php")
    suspend fun updateCategory(
        @Field("id") id: String,
        @Field("name") name: String
    ): Response<BaseResponse>

    @FormUrlEncoded
    @POST("kategori/delete.php")
    suspend fun deleteCategory(
        @Field("id") id: String
    ): Response<BaseResponse>

    // Brands
    @GET("brand/get.php")
    suspend fun getBrands(): Response<List<Brand>>

    @FormUrlEncoded
    @POST("brand/insert.php")
    suspend fun insertBrand(
        @Field("name") name: String
    ): Response<BaseResponse>

    @FormUrlEncoded
    @POST("brand/update.php")
    suspend fun updateBrand(
        @Field("id") id: String,
        @Field("name") name: String
    ): Response<BaseResponse>

    @FormUrlEncoded
    @POST("brand/delete.php")
    suspend fun deleteBrand(
        @Field("id") id: String
    ): Response<BaseResponse>

    // Suppliers
    @GET("supplier/get.php")
    suspend fun getSuppliers(): Response<List<Supplier>>

    @FormUrlEncoded
    @POST("supplier/insert.php")
    suspend fun insertSupplier(
        @Field("name") name: String,
        @Field("address") address: String,
        @Field("phone") phone: String
    ): Response<BaseResponse>

    @FormUrlEncoded
    @POST("supplier/update.php")
    suspend fun updateSupplier(
        @Field("id") id: String,
        @Field("name") name: String,
        @Field("address") address: String,
        @Field("phone") phone: String
    ): Response<BaseResponse>

    @FormUrlEncoded
    @POST("supplier/delete.php")
    suspend fun deleteSupplier(
        @Field("id") id: String
    ): Response<BaseResponse>

    // Products
    @GET("barang/get.php")
    suspend fun getProducts(): Response<List<Product>>

    @FormUrlEncoded
    @POST("barang/insert.php")
    suspend fun insertProduct(
        @Field("code") code: String,
        @Field("name") name: String,
        @Field("category_id") categoryId: String,
        @Field("brand_id") brandId: String,
        @Field("supplier_id") supplierId: String,
        @Field("buy_price") buyPrice: Double,
        @Field("sell_price") sellPrice: Double,
        @Field("stok") stock: Int,
        @Field("lokasi_rak") shelfLocation: String,
        @Field("warna") color: String,
        @Field("kondisi") condition: String
    ): Response<BaseResponse>

    @FormUrlEncoded
    @POST("barang/update.php")
    suspend fun updateProduct(
        @Field("id") id: String,
        @Field("code") code: String,
        @Field("name") name: String,
        @Field("category_id") categoryId: String,
        @Field("brand_id") brandId: String,
        @Field("supplier_id") supplierId: String,
        @Field("buy_price") buyPrice: Double,
        @Field("sell_price") sellPrice: Double,
        @Field("stok") stock: Int,
        @Field("lokasi_rak") shelfLocation: String,
        @Field("warna") color: String,
        @Field("kondisi") condition: String
    ): Response<BaseResponse>

    @FormUrlEncoded
    @POST("barang/delete.php")
    suspend fun deleteProduct(
        @Field("id") id: String
    ): Response<BaseResponse>

    // Transactions / Stok
    @FormUrlEncoded
    @POST("stok/masuk.php")
    suspend fun stockIn(
        @Field("product_id") productId: String,
        @Field("qty") quantity: Int,
        @Field("date") date: String,
        @Field("description") description: String
    ): Response<BaseResponse>

    @FormUrlEncoded
    @POST("stok/keluar.php")
    suspend fun stockOut(
        @Field("product_id") productId: String,
        @Field("qty") quantity: Int,
        @Field("date") date: String,
        @Field("description") description: String
    ): Response<BaseResponse>

    @GET("stok/history.php")
    suspend fun getHistory(): Response<List<Transaction>>

    // Dashboard & Predictions
    @GET("dashboard.php")
    suspend fun getDashboardStats(): Response<DashboardStats>

    @GET("prediksi.php")
    suspend fun getPrediction(): Response<PredictionResponse>

    // Users
    @GET("users/get.php")
    suspend fun getUsers(): Response<List<User>>

    @FormUrlEncoded
    @POST("users/insert.php")
    suspend fun insertUser(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("role") role: String
    ): Response<BaseResponse>

    @FormUrlEncoded
    @POST("users/update.php")
    suspend fun updateUser(
        @Field("id") id: String,
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("role") role: String
    ): Response<BaseResponse>

    @FormUrlEncoded
    @POST("users/delete.php")
    suspend fun deleteUser(
        @Field("id") id: String
    ): Response<BaseResponse>
}
