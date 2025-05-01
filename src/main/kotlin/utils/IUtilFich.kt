package es.prog2425.calclog.utils

import java.io.File

interface IUtilFich {
    fun crearRuta(ruta: String): Boolean
    fun listarFicheros(ruta: String): List<File>
    fun leerFichero(path: String): List<String>
    fun escribirLinea(path: String, linea: String)
    fun crearFichero(path: String)
}