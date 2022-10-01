package com.example.rulesofroad.model

import java.io.Serializable

class Symbol : Serializable {
    var id: Int? = null
    var name: String? = null
    var desc: String? = null
    var image: ByteArray? = null
    var type: String? = null
    var isLiked: Int = 0


    constructor(
        id: Int?,
        name: String?,
        desc: String?,
        image: ByteArray?,
        type: String?,
        isLiked: Int = 0
    ) {
        this.id = id
        this.name = name
        this.desc = desc
        this.image = image
        this.type = type
        this.isLiked = isLiked
    }

    constructor(name: String?, desc: String?, image: ByteArray?, type: String?, isLiked: Int = 0) {
        this.name = name
        this.desc = desc
        this.image = image
        this.type = type
        this.isLiked = isLiked
    }
}