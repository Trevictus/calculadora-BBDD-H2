package es.prog2425.calclog.service

import es.prog2425.calclog.data.IRepoLog
import es.prog2425.calclog.model.Calculo

/**
 * Servicio que actúa como intermediario entre la lógica de aplicación y el repositorio de logs.
 * Encapsula la lógica de negocio relacionada con la gestión de registros de log.
 */
class ServicioLog(private val repositorio: IRepoLog) : IServicioLog {

    /**
     * Registra un mensaje de texto en el archivo de log.
     */
    override fun registrarEntradaLog(mensaje: String) {
        repositorio.registrarEntrada(mensaje)
    }

    /**
     * Registra un cálculo formateado en el archivo de log.
     */
    override fun registrarEntradaLog(calculo: Calculo) {
        repositorio.registrarEntrada(calculo)
    }

    /**
     * Recupera el contenido del archivo de log más reciente.
     */
    override fun getInfoUltimoLog(): List<String> {
        return repositorio.getContenidoUltimoLog()
    }

    /**
     * Crea un nuevo archivo de log vacío y lo establece como actual.
     */
    override fun crearNuevoLog() {
        repositorio.crearNuevoLog()
    }

    /**
     * Crea el directorio de logs si no existe y actualiza la ruta en el repositorio.
     */
    override fun crearRutaLog(ruta: String): Boolean {
        repositorio.ruta = ruta
        return repositorio.crearRutaLog()
    }
}