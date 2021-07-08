package com.amoschoojs.livinginchrist.networkstream

import com.google.gson.annotations.SerializedName

class Verse
    (
    @SerializedName("text")
    val text: String,

    @SerializedName("content")
    val content: String,

    @SerializedName("display_ref")
    val displayRef: String,

    @SerializedName("reference")
    val reference: String,

    @SerializedName("permalink")
    val permalink: String,

    @SerializedName("copyright")
    val copyright: String,

    @SerializedName("copyrightlink")
    val copyrightLink: String,

    @SerializedName("audiolink")
    val audioLink: String,
    @SerializedName("day")
    val day: String,

    @SerializedName("month")
    val month: String,

    @SerializedName("year")
    val year: String,

    @SerializedName("version")
    val versionBible: String,

    @SerializedName("version_id")
    val versionID: String,

    @SerializedName("merchandising")
    val merchandising: String
)
{
     override fun toString(): String {
        return content
    }
    }