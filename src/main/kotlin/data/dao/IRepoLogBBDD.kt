package es.prog2425.calclog.data.dao

import es.prog2425.calclog.model.Calculo

// Interfaz que define los métodos para interactuar con la base de datos de registros (logs)
interface IRepoLogBBDD {
    fun getContenidoUltimoLog(): List<String>
    fun registrarEntrada(mensaje: String)
    fun registrarEntrada(calculo: Calculo)
    fun crearNuevoLog()
}