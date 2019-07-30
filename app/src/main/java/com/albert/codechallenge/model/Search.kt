package com.albert.codechallenge.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Search (
    @SerializedName("start")
    @Expose
    var start: Int? = null,
    @SerializedName("num_found")
    @Expose
    var numFound: Int? = null,
    @SerializedName("docs")
    @Expose
    var docs: List<Doc>? = null
)