package com.example.myapplication

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query

@Dao
interface filmesDAO {

        @Insert
        fun inserir(Filme: filmes) {
        }
    @Query("SELECT * FROM filmes")
    fun buscarFilmes() : List<filmes>

}