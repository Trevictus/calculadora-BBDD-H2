package es.prog2425.calclog.utils

import java.io.File

/**
 * Implementación de utilidad de gestión de archivos de texto basada en la API estándar de ficheros de Kotlin/Java.
 * Permite crear rutas, leer, escribir y listar archivos en el sistema de ficheros.
 */
class GestorFichTxt : IUtilFich {

    /**
     * Crea un directorio en la ruta indicada si no existe.
     */
    override fun crearRuta(ruta: String): Boolean {
        val dir = File(ruta)
        return if (!dir.exists()) dir.mkdirs() else false
    }

    /**
     * Devuelve una lista con todos los ficheros contenidos en una ruta dada.
     */
    override fun listarFicheros(ruta: String) = File(ruta).listFiles()?.toList() ?: emptyList()

    /**
     * Lee el contenido completo de un archivo línea por línea.
     */
    override fun leerFichero(path: String) = File(path).readLines()

    /**
     * Añade una línea al final de un archivo existente.
     */
    override fun escribirLinea(path: String, linea: String) {
        File(path).appendText(linea)
    }

    /**
     * Crea un nuevo archivo vacío en la ruta especificada.
     */
    override fun crearFichero(path: String) {
        File(path).createNewFile()
    }

}