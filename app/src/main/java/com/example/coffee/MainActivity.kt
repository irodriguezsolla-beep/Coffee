package com.example.coffee

import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.coffee.ui.theme.CoffeeTheme

class MainActivity : ComponentActivity() {
    private var tiempoActivo: Long = 0L
    private var tiempoPause: Long = 0L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CoffeeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    IU(modifier = Modifier.padding(innerPadding))
                }
            }
        }
        Log.d("Estado", "OnCreate()")

    }
    override fun onStart() {
        super.onStart()
        tiempoActivo = SystemClock.elapsedRealtime()
        if (tiempoPause != 0L) {
            val tiempoActual = SystemClock.elapsedRealtime()
            val tiempoEnPausaSeg = (tiempoActual - tiempoPause) /
                    1000.0
            Log.d("Estado", "2. onStart: La pantalla es visible nuevamente.")
            Log.d("Tiempo", "La app estuvo pausada  durante: $tiempoEnPausaSeg segundos")
        } else {
            Log.d("Estado", "2. onStart: La pantalla es visible por primera vez")
        }
    }
    override fun onResume() {
        super.onResume()
        Log.d("Estado", "OnResume()")
    }

    override fun onPause() {
        super.onPause()

        if (tiempoActivo != 0L) {
            val tiempoActual2 = SystemClock.elapsedRealtime()
            val tiempoEnPausaSeg2 = (tiempoActual2 - tiempoActivo)/
                    1000.0
            Log.d("Estado", "4. onPause: La pantalla perdió el foco")
            Log.d("Tiempo", "La app estuvo activa durante: $tiempoEnPausaSeg2 segundos")
        } else {
            Log.d("Estado", "4. onPause: La pantalla perdió el foco por primera vez")
        }
    }

    override fun onStop() {
        super.onStop()
        Log.d("Estado", "OnStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Estado", "OnDestroy()")
    }
}


@Composable
private fun IU(
    modifier: Modifier = Modifier,
    names: List<String> = listOf("Cafe solo", "Cafe largo", "Cafe con leche")
) {
    Column(modifier = modifier.padding(vertical = 4.dp),horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Coffee",
            modifier = Modifier.padding(vertical = 8.dp)
        )
        for (name in names) {
            BotonesCafes(name = name)
        }
    }

}
@Composable
fun BotonesCafes(name: String, modifier: Modifier = Modifier) {

    var expanded by remember { mutableStateOf(false) }

    val extraPadding = if (expanded) 48.dp else 0.dp

    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Column(modifier = Modifier.padding(24.dp),) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = name,
                    modifier = Modifier.weight(1f)
                )
                ElevatedButton(
                    onClick = { expanded = !expanded }
                ) {
                    Text("Select")
                }
            }

            if (expanded) {
                Text("Cafe selecionado: $name")
                Azucar(modifier = Modifier.padding(top = 16.dp))
            }
        }

    }
}

@Composable
fun Azucar(modifier: Modifier = Modifier,
           numeros : List<Int> = listOf(1,2,3,4,5)
){
    var nivelSeleccionado by remember { mutableStateOf(1) }

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "Nivel de azucar: $nivelSeleccionado",
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            for (numero in numeros) {
                ElevatedButton(
                    onClick = { nivelSeleccionado = numero },
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(text = "$numero")
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CoffeeTheme {
        IU()
    }
}
