package es.prog2425.calclog.data.dao

import es.prog2425.calclog.model.Calculo

// Interfaz que define los métodos para gestionar registros de logs en la aplicación
interface IRepoLog {
    var ruta: String? // Variable que almacena la ruta donde se guarda el log
    var logActual: String? // Variable que contiene el nombre o identificador del log activo

    fun crearRutaLog(): Boolean
    fun crearNuevoLog(): String
    fun getContenidoUltimoLog(): List<String>
    fun registrarEntrada(mensaje: String)
    fun registrarEntrada(calculo: Calculo)
}