package com.example.beersrealmapp

import io.realm.RealmObject

class BeerModel : RealmObject {
    var id: String = ""
    var name: String = ""
    var brewery: String = ""
    var imageUrl: String = ""
    var abv: Int = 0
}