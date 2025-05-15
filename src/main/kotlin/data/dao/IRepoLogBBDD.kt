package es.prog2425.calclog.data.dao

import es.prog2425.calclog.model.Calculo

interface IRepoLogBBDD {

    fun getContenidoUltimoLog(): List<String>
    fun registrarEntrada(mensaje: String)
    fun registrarEntrada(calculo: Calculo)
    fun crearNuevoLog()
}