package es.prog2425.calclog.service

import es.prog2425.calclog.model.Calculo

// Interfaz que define los métodos para interactuar con la base de datos
interface IServicioBBDD {
    fun getInfoUltimoLog(): List<String>
    fun registrarEntrada(mensaje: String)
    fun registrarEntrada(calculo: Calculo)
    fun crearNuevoLog()
}