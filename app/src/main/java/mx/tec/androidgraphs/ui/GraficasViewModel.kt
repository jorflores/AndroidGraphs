package mx.tec.androidgraphs.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import mx.tec.androidgraphs.data.DatosLocales
import kotlin.random.Random

/**
 * Expone los datos con StateFlow, igual que si vinieran de la API.
 * "simularDatosNuevos" cambia los números para ver cómo la gráfica
 * se actualiza (y se anima) sola cuando llegan datos nuevos.
 */
class GraficasViewModel : ViewModel() {

    private val _porDia = MutableStateFlow(DatosLocales.asistenciasPorDia)
    val porDia: StateFlow<Map<String, Int>> = _porDia.asStateFlow()

    private val _porSemana = MutableStateFlow(DatosLocales.asistenciasPorSemana)
    val porSemana: StateFlow<Map<String, Int>> = _porSemana.asStateFlow()

    fun simularDatosNuevos() {
        _porDia.value = _porDia.value.mapValues { Random.nextInt(10, 25) }
        _porSemana.value = _porSemana.value.mapValues { Random.nextInt(12, 25) }
    }

    fun restaurar() {
        _porDia.value = DatosLocales.asistenciasPorDia
        _porSemana.value = DatosLocales.asistenciasPorSemana
    }
}
