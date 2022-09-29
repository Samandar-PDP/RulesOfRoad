package com.example.rulesofroad.model

import java.io.Serializable

class Symbol : Serializable {
    var id: Int? = null
    var name: String? = null
    var desc: String? = null
    var image: ByteArray? = null
    var type: String? = null

    constructor(id: Int?, name: String?, desc: String?, image: ByteArray?, type: String?) {
        this.id = id
        this.name = name
        this.desc = desc
        this.image = image
        this.type = type
    }

    constructor(name: String?, desc: String?, image: ByteArray?, type: String?) {
        this.name = name
        this.desc = desc
        this.image = image
        this.type = type
    }
}