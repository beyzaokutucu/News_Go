package com.project.newsgo.data.entity


import com.google.gson.annotations.SerializedName

data class Source(
    @SerializedName("id")
    val id: Any?,
    @SerializedName("name")
    val name: String
){
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "name" to name
        )
    }

    companion object {
        fun fromMap(map: Map<String, Any>): Source {
            return Source(
                id = map["id"] as String?,
                name = map["name"] as String
            )
        }
    }
}