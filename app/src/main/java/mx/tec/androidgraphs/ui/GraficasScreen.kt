package mx.tec.androidgraphs.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun GraficasScreen(vm: GraficasViewModel = viewModel()) {
    val porDia by vm.porDia.collectAsStateWithLifecycle()
    val porSemana by vm.porSemana.collectAsStateWithLifecycle()

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Gráficas con Vico", style = MaterialTheme.typography.headlineMedium)
            Text(
                "Datos locales de ejemplo. En tu proyecto vendrían de la API.",
                style = MaterialTheme.typography.bodyMedium
            )

            Tarjeta(
                titulo = "Barras · Asistencias por día",
                descripcion = "Para comparar categorías. El jueves faltaron más."
            ) {
                GraficaBarras(porDia)
            }

            Tarjeta(
                titulo = "Líneas · Asistencia por semana",
                descripcion = "Para ver una tendencia en el tiempo: va subiendo."
            ) {
                GraficaLineas(porSemana)
            }

            // Cambia los datos: las dos gráficas se actualizan solas (LaunchedEffect(datos))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = { vm.simularDatosNuevos() }) { Text("Simular datos nuevos") }
                OutlinedButton(onClick = { vm.restaurar() }) { Text("Restaurar") }
            }
        }
    }
}

@Composable
private fun Tarjeta(
    titulo: String,
    descripcion: String,
    grafica: @Composable () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(titulo, style = MaterialTheme.typography.titleMedium)
            Text(descripcion, style = MaterialTheme.typography.bodyMedium)
            grafica()
        }
    }
}
