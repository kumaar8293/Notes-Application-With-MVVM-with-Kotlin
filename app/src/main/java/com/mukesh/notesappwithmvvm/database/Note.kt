package com.mukesh.notesappwithmvvm.database

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable

/*This is a room entity
 *Room Annotation (Entity) at compile time it will create all the necessary code  to create an SQLite table in this object
 *https://developer.android.com/training/data-storage/room/defining-data#java
 */
@Entity(tableName = "note_table")
class Note : Serializable {
    //Because we are not passing id with constructor
    //Id will be auto-generated
    @PrimaryKey(autoGenerate = true)
    var id = 0
    // @ColumnInfo(name = "title")  we can change column name like this
    var title: String? = null
    var description: String? = null
    var priority = 0

    constructor(title: String?, description: String?, priority: Int) {
        this.title = title
        this.description = description
        this.priority = priority
    }
    @Ignore
    constructor() {
    }
}