package com.amoschoojs.livinginchrist.networkstream

import com.google.gson.annotations.SerializedName

class Verse
    (
    private val text: String,
    private val content: String,

    @SerializedName("display_ref")
    private val displayRef: String,
    val reference: String,
    val permalink: String,
    val copyright: String,

    @SerializedName("copyrightlink")
    val copyrightLink: String,

    @SerializedName("audiolink")
    val audioLink: String,
    val day: String,
    val month: String,
    val year: String,

    @SerializedName("version")
    val versionBible: String,

    @SerializedName("versionid")
    val versionID: String,
    val merchandising: String
)