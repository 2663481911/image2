package com.dataBinding.image.gallery

import com.google.gson.annotations.SerializedName

data class Pixabay(

    val page_count:Int,
    val status:Int,
    val total_counts:Int,
    val page:Int,
    val data:Array<PhotoItem>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Pixabay

        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        return data.contentHashCode()
    }
}

data class PhotoItem(
    @SerializedName("url")
    val url:String,
    @SerializedName("title")
    val title:String
)