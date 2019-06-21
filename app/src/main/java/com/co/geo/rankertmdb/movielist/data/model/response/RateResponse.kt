package com.co.geo.rankertmdb.movielist.data.model.response

import com.google.gson.annotations.SerializedName

class RateResponse(
    @SerializedName("status_code")
    val statusCode: Int,
    @SerializedName("status_message")
    val statusMessage: String
) {
}