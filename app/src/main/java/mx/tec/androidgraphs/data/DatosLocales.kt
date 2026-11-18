package mx.tec.androidgraphs.data

/**
 * Datos de ejemplo, guardados en la app (sin internet).
 * En el proyecto real vendrían de la API, por ejemplo:
 *
 *   val porDia = api.getAsistencias()
 *       .filter { it.presente }
 *       .groupingBy { it.fecha }
 *       .eachCount()
 *
 * Para graficar solo hace falta un Map<String, Int>: etiqueta → número.
 */
object DatosLocales {

    // Barras: comparar categorías (presentes por día de la semana)
    val asistenciasPorDia: Map<String, Int> = linkedMapOf(
        "Lun" to 18,
        "Mar" to 22,
        "Mié" to 20,
        "Jue" to 15,
        "Vie" to 21,
    )

    // Líneas: tendencia en el tiempo (promedio de asistencia por semana)
    val asistenciasPorSemana: Map<String, Int> = linkedMapOf(
        "S1" to 16,
        "S2" to 18,
        "S3" to 17,
        "S4" to 20,
        "S5" to 19,
        "S6" to 22,
        "S7" to 21,
        "S8" to 23,
    )
}
