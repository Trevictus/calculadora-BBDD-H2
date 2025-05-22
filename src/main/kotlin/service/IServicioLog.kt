package es.prog2425.calclog.service

import es.prog2425.calclog.model.Calculo

// Interfaz que define los métodos para gestionar el registro de logs en la aplicación
interface IServicioLog {
    fun registrarEntradaLog(mensaje: String)
    fun registrarEntradaLog(calculo: Calculo)
    fun getInfoUltimoLog(): List<String>
    fun crearNuevoLog()
    fun crearRutaLog(ruta: String): Boolean
}