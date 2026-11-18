# AndroidGraphs

App de ejemplo de la clase **Gráficas con Vico** (TC2007B · Módulo 3 · Desarrollo Móvil).

Muestra dos tipos de gráfica con [Vico](https://guide.vico.patrykandpatrick.com), la librería de gráficas para Jetpack Compose, usando datos locales:

| Gráfica | Datos | Para qué sirve |
|---------|-------|----------------|
| **Barras** | Asistencias por día (Lun–Vie) | Comparar categorías |
| **Líneas** | Asistencia por semana (S1–S8) | Ver una tendencia en el tiempo |

El botón **Simular datos nuevos** cambia los números: las dos gráficas se actualizan y se animan solas. **Restaurar** regresa a los datos originales.

## Estructura

```
app/src/main/java/mx/tec/androidgraphs/
├── MainActivity.kt
├── data/
│   └── DatosLocales.kt        Map<String, Int> por día y por semana
└── ui/
    ├── GraficasViewModel.kt   expone los datos con StateFlow (como si vinieran de la API)
    ├── Graficas.kt            GraficaBarras() y GraficaLineas() con Vico
    ├── GraficasScreen.kt      pantalla con las dos tarjetas y los botones
    └── theme/Theme.kt
```

## Relación con la presentación

| Diapositiva | Código |
|-------------|--------|
| Las 3 piezas de una gráfica | `CartesianChartModelProducer`, `rememberCartesianChart`, `CartesianChartHost` en `Graficas.kt` |
| Paso 1 · Agregar la dependencia | `vico-compose` en `gradle/libs.versions.toml` y `app/build.gradle.kts` |
| Paso 2 · Tu primera gráfica de barras | `GraficaBarras()` |
| Paso 3 · De barras a líneas | `GraficaLineas()`: solo cambian `lineModel` y `rememberLineCartesianLayer` |
| Paso 4 · Etiquetas en el eje X | `EtiquetasKey` y `formatoEtiquetas` en `Graficas.kt` |
| Paso 5 · Graficar datos de tu API | `LaunchedEffect(datos)` + `GraficasViewModel` |

## Dependencia

```toml
[versions]
vico = "3.3.1"

[libraries]
vico-compose = { group = "com.patrykandpatrick.vico", name = "compose", version.ref = "vico" }
```

```kotlin
implementation(libs.vico.compose)
```

El resto de la configuración de Gradle es igual a la de [AppBase](https://github.com/jorflores/AppBase).

## Si algo no compila

- **"requires … compile against version 37"**: Vico 3.3.1 necesita `compileSdk = 37` (ya está así en `app/build.gradle.kts`). Si Android Studio dice que falta la plataforma, instálala desde **SDK Manager > Android 37**. `targetSdk` se queda en 36.
- **Ejemplos de internet con `columnSeries`, `vico.core` o `vico.multiplatform`** son de Vico 1 o 2. En la 3 son `columnModel`, `lineModel` y el módulo `compose`.
- **Import de `CartesianValueFormatter`**: si Android Studio no lo encuentra en `com.patrykandpatrick.vico.compose.cartesian.data`, bórralo y usa Alt+Enter para que lo sugiera. La API está en la guía: *Cartesian charts > CartesianValueFormatter*.
- **La gráfica no se ve**: necesita altura (`Modifier.height(240.dp)`).

## Para el proyecto

Cambia `DatosLocales` por los datos de tu API, resumidos en un `Map<String, Int>` en el ViewModel, por ejemplo:

```kotlin
val porDia = api.getAsistencias()
    .filter { it.presente }
    .groupingBy { it.fecha }
    .eachCount()
```

`GraficaBarras(porDia)` y `GraficaLineas(...)` se usan tal cual.
