package com.example.myapplication

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface FilmesDAO {

        @Insert
        fun inserir(Filme: Filmes) {
        }
    @Query("SELECT * FROM filmes")
    fun buscarFilmes() : List<Filmes>

    @Update
    fun atualizarFilmes(filme: Filmes)

    @Delete
    fun removerFilme(filme: Filmes)

}