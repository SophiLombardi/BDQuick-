package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.room.Room
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

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun TelaPrinipal() {

    var estouEditando by remember { mutableStateOf(false) }
    var filmeEmEdicao by remember {
        mutableStateOf<Filmes?>(null)
    }
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
                    if (estouEditando){
                        val filmeTemp = filmeEmEdicao!!.copy(nome = nome, desc = desc)
                        atuliazarFilme(filmeTemp, filmesDao)
                        estouEditando = false
                    }else {
                        inserirFilme(nome, desc, filmesDao)
                    }
                }

                nome = ""
                desc = ""

                filmeEmEdicao = null
                filmes = buscarFilmes(filmesDao)
            }
        ) {
            Text("add")
        }
        LazyColumn() {
            items(filmes){ filme ->
                UmFilme(
                    meuFilme = filme,
                    aoRemover = {
                        CoroutineScope(Dispatchers.IO).launch {
                            deletarFilme(filme, filmesDAO)
                            filmes = buscarFilmes(filmesDAO)
                        }
                    },
                    aoAtualizar = {
                        nome = filme.nome
                        desc = filme.desc
                        estouEditando = true
                        filmeEmEdicao = filme
                    }
                }
            ){}
            }
        }

        listaFilmes.forEach {
            Text(it.nome)
        }
    }
}

@Composable
fun UnFilme(meuFilmes: Filmes,aoRemover:(Filmes)->Unit, aoAtulizar:(Filmes)-> Unit){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        border = BorderStroke(1.dp, Color.Black)
    ) {
        Row{
            Text(meuFilmes.nome)
            Text(meuFilmes.desc)
            Button(onClick = {aoAtulizar(meuFilmes)}) { Text("Atulizar")}
            Button(onClick = {aoRemover(meuFilmes)}) { Text("Excluir")}
        }
    }
}