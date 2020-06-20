package com.killianrvt.memoapplication

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "memos")
class MemoDTO {
    // Propriétés
    @PrimaryKey(autoGenerate = true)
    var memoId: Long = 0
    // Setter
    // Getter
    var intitule: String? = null

    // Constructeur
    constructor(intitule: String?) {
        this.intitule = intitule
    }

}