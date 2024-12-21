import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyecto_kotlin.Mascota

class SharedMascotaViewModel : ViewModel() {
    private val _mascotaSeleccionada = MutableLiveData<Mascota?>()
    val mascotaSeleccionada: LiveData<Mascota?> get() = _mascotaSeleccionada

    fun seleccionarMascota(mascota: Mascota) {
        _mascotaSeleccionada.value = mascota
    }
}
