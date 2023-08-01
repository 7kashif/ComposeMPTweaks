import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.kashif.composemptweaks.MainView

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
       MainView()
    }
}