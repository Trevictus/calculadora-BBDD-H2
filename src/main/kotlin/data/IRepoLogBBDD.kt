package es.prog2425.calclog.data

import es.prog2425.calclog.model.Calculo

interface IRepoLogBBDD {
    fun connect()
    fun disconnect()
    fun getContenidoUltimoLog(): List<String>
    fun registrarEntrada(mensaje: String)
    fun registrarEntrada(calculo: Calculo)
    fun crearNuevoLog()
}