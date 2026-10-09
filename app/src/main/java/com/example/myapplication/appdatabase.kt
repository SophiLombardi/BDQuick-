package com.example.myapplication
import androidx.room.processor.Context
import androidx.room3.Database
import androidx.room3.Room
import androidx.room3.RoomDatabase

@Database(entities = [filmes::class], version = 1)
abstract class appdatabase : RoomDatabase(){


    abstract fun filmesDAO(): FilmesDAO

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context) : AppDatabase {

            val tempInstance = INSTANCE

            if(INSTANCE != null){
                return INSTANCE // se o banco ja existe, devolve ele
            } else {
                //se nao, cria o banco

                synchronized(lock = this){

                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        klass = AppDatabase::class.java,
                        name = "app_database"
                    ).build()

                    INSTANCE = instance
                    return instance
                }


            }

        }


    }

}