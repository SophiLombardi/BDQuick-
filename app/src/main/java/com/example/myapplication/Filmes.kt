package com.example.myapplication
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "filmes")
data class Filmes (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val nome: String,
    val desc: String

)