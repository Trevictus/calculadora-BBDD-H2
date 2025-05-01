package es.prog2425.calclog.data

import es.prog2425.calclog.model.Calculo

interface IRepoLog {
    var ruta: String?
    var logActual: String?

    fun crearRutaLog(): Boolean
    fun crearNuevoLog(): String
    fun getContenidoUltimoLog(): List<String>
    fun registrarEntrada(mensaje: String)
    fun registrarEntrada(calculo: Calculo)
}