package es.prog2425.calclog.data

import es.prog2425.calclog.model.Calculo
import es.prog2425.calclog.utils.IUtilFich
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Implementación de [IRepoLog] que gestiona el almacenamiento de logs en archivos de texto.
 */
class RepoLogTxt(private val fichero: IUtilFich) : IRepoLog {

    override var ruta: String? = null
    override var logActual: String? = null

    /**
     * Crea el directorio de logs si no existe.
     */
    override fun crearRutaLog() = ruta?.let { fichero.crearRuta(it) } ?: false

    /**
     * Crea un nuevo archivo de log con nombre basado en la fecha y hora actual.
     */
    override fun crearNuevoLog(): String {
        val fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
        val nombre = "log$fecha.txt"
        val rutaCompleta = "$ruta/$nombre"
        fichero.crearFichero(rutaCompleta)
        logActual = rutaCompleta
        return nombre
    }

    /**
     * Obtiene el contenido del archivo de log más reciente.
     */
    override fun getContenidoUltimoLog(): List<String> {
        val logs = ruta?.let { ficheroLog ->
            fichero.listarFicheros(ficheroLog)
                .filter { it.name.startsWith("log") && it.name.endsWith(".txt") }
                .sortedByDescending { it.name }
                .firstOrNull()
        }
        return logs?.let { fichero.leerFichero(it.path) } ?: emptyList()
    }

    /**
     * Registra un mensaje en el archivo de log actual con marca de tiempo.
     */
    override fun registrarEntrada(mensaje: String) {
        val linea = "${LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))} $mensaje\n"
        logActual?.let { fichero.escribirLinea(it, linea) }
    }

    /**
     * Registra un cálculo formateado en el archivo de log actual.
     */
    override fun registrarEntrada(calculo: Calculo) {
        registrarEntrada(calculo.toString())
    }
}