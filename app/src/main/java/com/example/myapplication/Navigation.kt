package com.example.myapplication

import android.content.Intent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class BottomNavEntry(
    val route: String,
    val icon: ImageVector,
    val label: String,
    val targetActivity: Class<*>?
)

val bottomNavEntries = listOf(
    BottomNavEntry(
        route = "home",
        icon = Icons.Default.Home,
        label = "Início",
        targetActivity = PetHome::class.java
    ),
    BottomNavEntry(
        route = "calendar",
        icon = Icons.Default.DateRange,
        label = "Calendário",
        targetActivity = Calendario::class.java
    ),
    BottomNavEntry(
        route = "tasks",
        icon = Icons.AutoMirrored.Filled.List,
        label = "Tarefas",
        targetActivity = MainActivity::class.java
    ),
    BottomNavEntry(
        route = "store",
        icon = Icons.Default.ShoppingCart,
        label = "Loja",
        targetActivity = Loja::class.java
    ),
    BottomNavEntry(
        route = "profile",
        icon = Icons.Default.Person,
        label = "Perfil",
        targetActivity = Perfil::class.java
    )
)

/**
 * Barra de navegação inferior (visual do projeto quick: Material3 NavigationBar).
 * A navegação continua sendo feita por Activities, como no BDQuick.
 */
@Composable
fun CustomBottomNavigation(currentRoute: String = "home") {
    val context = LocalContext.current

    Surface(color = Color.White, shadowElevation = 8.dp) {
        NavigationBar(containerColor = Color.White) {
            bottomNavEntries.forEach { entry ->
                NavigationBarItem(
                    selected = currentRoute == entry.route,
                    onClick = {
                        if (entry.route == currentRoute) return@NavigationBarItem

                        entry.targetActivity?.let { target ->
                            val intent = Intent(context, target).apply {
                                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
                                        Intent.FLAG_ACTIVITY_SINGLE_TOP
                            }
                            context.startActivity(intent)
                        }
                    },
                    icon = { Icon(entry.icon, contentDescription = entry.label) },
                    label = {
                        Text(
                            entry.label,
                            fontSize = 10.sp,
                            fontWeight = if (currentRoute == entry.route) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                )
            }
        }
    }
}
