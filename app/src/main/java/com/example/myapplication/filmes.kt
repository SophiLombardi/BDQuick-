package com.example.myapplication
import androidx.room3.Entity
import androidx.room.vo.Entity
import androidx.room3.PrimaryKey

@Entity(TableName = "filmes")
data class filmes (
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    val nome: String,
    val desc: String

)