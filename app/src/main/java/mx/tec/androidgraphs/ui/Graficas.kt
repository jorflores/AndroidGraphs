package mx.tec.androidgraphs.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.compose.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.compose.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.compose.cartesian.data.columnModel
import com.patrykandpatrick.vico.compose.cartesian.data.lineModel
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.common.data.ExtraStore

// ---------------------------------------------------------------------------
// Paso 4 · Etiquetas en el eje X
// Vico coloca los valores en x = 0, 1, 2…; guardamos las etiquetas
// ("Lun", "Mar"…) junto con los datos y un formateador las traduce.
// ---------------------------------------------------------------------------

// Una "llave" para guardar las etiquetas en el ExtraStore de la gráfica
private val EtiquetasKey = ExtraStore.Key<List<String>>()

// Traduce la posición (0, 1, 2…) a la etiqueta ("Lun", "Mar"…)
private val formatoEtiquetas = CartesianValueFormatter { context, x, _ ->
    context.model.extraStore[EtiquetasKey].getOrElse(x.toInt()) { "" }
}

// ---------------------------------------------------------------------------
// Gráfica de BARRAS: comparar categorías
// ---------------------------------------------------------------------------
@Composable
fun GraficaBarras(datos: Map<String, Int>, modifier: Modifier = Modifier) {
    // 1. Los datos
    val modelProducer = remember { CartesianChartModelProducer() }

    // Paso 5: se repite cada vez que cambian los datos
    LaunchedEffect(datos) {
        if (datos.isEmpty()) return@LaunchedEffect   // Vico necesita al menos un valor
        modelProducer.runTransaction {
            columnModel { series(datos.values) }
            extras { it[EtiquetasKey] = datos.keys.toList() }
        }
    }

    // 2. Cómo se ve  +  3. Quién la dibuja
    CartesianChartHost(
        chart = rememberCartesianChart(
            rememberColumnCartesianLayer(),                               // barras
            startAxis = VerticalAxis.rememberStart(),                     // eje Y
            bottomAxis = HorizontalAxis.rememberBottom(valueFormatter = formatoEtiquetas),
        ),
        modelProducer = modelProducer,
        modifier = modifier.fillMaxWidth().height(240.dp),               // sin altura no se ve
    )
}

// ---------------------------------------------------------------------------
// Gráfica de LÍNEAS: tendencia en el tiempo
// Comparada con la de barras cambian solo 2 cosas:
//   columnModel → lineModel   y   rememberColumnCartesianLayer → rememberLineCartesianLayer
// ---------------------------------------------------------------------------
@Composable
fun GraficaLineas(datos: Map<String, Int>, modifier: Modifier = Modifier) {
    val modelProducer = remember { CartesianChartModelProducer() }

    LaunchedEffect(datos) {
        if (datos.isEmpty()) return@LaunchedEffect
        modelProducer.runTransaction {
            lineModel { series(datos.values) }
            extras { it[EtiquetasKey] = datos.keys.toList() }
        }
    }

    CartesianChartHost(
        chart = rememberCartesianChart(
            rememberLineCartesianLayer(),                                 // líneas
            startAxis = VerticalAxis.rememberStart(),
            bottomAxis = HorizontalAxis.rememberBottom(valueFormatter = formatoEtiquetas),
        ),
        modelProducer = modelProducer,
        modifier = modifier.fillMaxWidth().height(240.dp),
    )
}
