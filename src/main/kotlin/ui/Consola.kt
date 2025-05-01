package es.prog2425.calclog.ui

import java.util.Scanner

/**
 * Implementación de entrada y salida a través de la consola estándar.
 * Proporciona métodos para mostrar mensajes, leer datos del usuario
 * y controlar la interacción mediante el teclado.
 */
class Consola : IEntradaSalida {
    private val scanner = Scanner(System.`in`)

    /**
     * Muestra un mensaje por pantalla, con o sin salto de línea final.
     */
    override fun mostrar(msj: String, salto: Boolean) {
        print("$msj${if (salto) "\n" else ""}")
    }

    /**
     * Muestra un mensaje de error formateado por pantalla.
     */
    override fun mostrarError(msj: String, salto: Boolean) {
        mostrar("ERROR - $msj", salto)
    }

    /**
     * Solicita al usuario que introduzca información por consola.
     */
    override fun pedirInfo(msj: String): String {
        if (msj.isNotEmpty()) mostrar(msj, false)
        return scanner.nextLine().trim()
    }

    /**
     * Solicita al usuario que introduzca un valor decimal.
     */
    override fun pedirDouble(msj: String) = pedirInfo(msj).replace(',', '.').toDoubleOrNull()

    /**
     * Solicita al usuario que introduzca un valor entero.
     */
    override fun pedirEntero(msj: String) = pedirInfo(msj).toIntOrNull()

    /**
     * Pregunta al usuario si desea continuar y valida la respuesta.
     */
    override fun preguntar(msj: String): Boolean {
        do {
            val respuesta = pedirInfo(msj).lowercase()
            when (respuesta) {
                "s", "si" -> return true
                "n", "no" -> return false
                else -> mostrarError("Respuesta no válida. Responde con s, n, si o no.")
            }
        } while (true)
    }

    /**
     * Limpia la pantalla simulando múltiples saltos de línea.
     */
    override fun limpiarPantalla(numSaltos: Int) {
        if (System.console() != null) {
            mostrar("\u001b[H\u001b[2J", false)
            System.out.flush()
        } else {
            repeat(numSaltos) {
                mostrar("")
            }
        }
    }

    /**
     * Pausa la ejecución esperando a que el usuario pulse ENTER.
     */
    override fun pausar(msj: String) {
        pedirInfo("\n" + msj)
        mostrar()
    }

    override fun preguntarModo(msj: String): Boolean {
        do {
            val respuesta = pedirInfo(msj).lowercase()
            when (respuesta) {
                "s", "si" -> return true
                "n", "no" -> return false
                else -> mostrarError("Respuesta no válida. Responde con s, n, si o no.")
            }
        } while (true)
    }

}

