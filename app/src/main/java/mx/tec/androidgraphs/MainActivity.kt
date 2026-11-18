package mx.tec.androidgraphs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import mx.tec.androidgraphs.ui.GraficasScreen
import mx.tec.androidgraphs.ui.theme.AndroidGraphsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidGraphsTheme {
                GraficasScreen()
            }
        }
    }
}
