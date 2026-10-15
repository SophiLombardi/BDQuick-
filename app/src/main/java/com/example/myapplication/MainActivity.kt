package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                TelaPrinipal()
            }
        }
    }
}

@Composable
fun TelaPrinipal() {

    var nome by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }

    var listaFilmes by remember {
        mutableStateOf<List<Filmes>>(emptyList())
    }

    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)
    val filmesDao = db.filmesDAO()

    LaunchedEffect(Unit) {
        listaFilmes = buscarFilmes(filmesDao)
        Log.d("bucsa ok","...$listaFilmes")
    }

    Column (){
        TextField("",{nome = it})
        TextField("",{desc = it})
        Button(
            onClick = {

                CoroutineScope(Dispatchers.IO).launch {
                    inserirFilme(nome,desc,filmesDao)
                }

            }) {
            Text("add")
        }


        listaFilmes.forEach {
            Text(it.nome)
        }
    }
}

suspend fun inserirFilme(nome: String, desc: String, filmesDao: FilmesDAO){
    try {
        filmesDao.inserir(Filmes(0, nome, desc))
    }catch (e: Exception){
        Log.e("erro ao adicionar","msg: ${e.message}")
    }
}

suspend fun buscarFilmes(filmesDao: FilmesDAO): List<Filmes>{
    try{
        return filmesDao.buscarFilmes()
    }catch (e: Exception){
        Log.e("erro ao adicionar","msg: ${e.message}")
        return emptyList()
    }
}