package com.example.myapplication

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query

@Dao
interface FilmesDAO {

        @Insert
        fun inserir(Filme: Filmes) {
        }
    @Query("SELECT * FROM filmes")
    fun buscarFilmes() : List<Filmes>

}