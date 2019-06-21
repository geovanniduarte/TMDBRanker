package com.co.geo.rankertmdb.movielist.data.model.response

import com.google.gson.annotations.SerializedName

data class ConfigResponse(
    @SerializedName("images")
    val images: Images
)

data class Images(
    @SerializedName("base_url")
    val baseUrl: String,
    @SerializedName("secure_base_url")
    val secureBaseUrl: String
)



