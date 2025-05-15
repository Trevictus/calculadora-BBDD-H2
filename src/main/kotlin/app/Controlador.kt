package es.prog2425.calclog.app

import es.prog2425.calclog.data.db.DataBase
import es.prog2425.calclog.model.Operador
import es.prog2425.calclog.service.IServicioBBDD
import es.prog2425.calclog.service.ServicioCalc
import es.prog2425.calclog.service.IServicioLog
import es.prog2425.calclog.ui.Consola
import es.prog2425.calclog.ui.IEntradaSalida

/**
 * Controlador principal de la aplicación que gestiona el flujo de ejecución y coordina
 * la interacción entre la interfaz de usuario, la calculadora y el servicio de logs.
 */
class Controlador(
    private val ui: IEntradaSalida,
    private val calculadora: ServicioCalc,
    private val gestorLog: IServicioLog,
    private val gestorBBDD: IServicioBBDD
) {

    companion object {
        private const val RUTA_POR_DEFECTO = "./log"
    }


    private var modo = true


    /**
     * Inicia el flujo principal de la aplicación gestionando argumentos, logs
     * y el ciclo de operaciones interactivas con el usuario.
     */
    fun iniciar(args: Array<String>) {
        modo = ui.preguntarModo()
        if (modo) {
            DataBase.getConnection()
        }

        if (!procesarArgumentos(args)) return

        if (modo) {

            mostrarInfo(gestorBBDD.getInfoUltimoLog())

            gestorBBDD.crearNuevoLog()
        } else {
            mostrarInfo(gestorLog.getInfoUltimoLog())

            gestorLog.crearNuevoLog()
        }
        if (args.size == 4) ejecutarCalculoConArgumentos(args)

        ui.pausar("Pulsa ENTER para iniciar la calculadora...")
        ui.limpiarPantalla()

        bucleCalculosUsuario()
    }

    /**
     * Procesa los argumentos recibidos desde la línea de comandos para establecer
     * la ruta del log y validar su formato.
     */
    private fun procesarArgumentos(args: Array<String>): Boolean {

        val ruta = when (args.size) {
            0 -> RUTA_POR_DEFECTO
            1, 4 -> args[0]
            else -> {
                ui.mostrarError("Número de argumentos inválido. Esperado: 0, 1 o 4.")
                return false
            }
        }
        if (!modo) {
            if (gestorLog.crearRutaLog(ruta)) {
                ui.mostrar("Ruta $ruta creada")
            }
        }
        return true
    }

    /**
     * Muestra el contenido del log más reciente o un mensaje si no existe.
     */
    private fun mostrarInfo(lineas: List<String>) {
        if (lineas.isEmpty()) {
            ui.mostrar("No existe información de un log previo!")
        } else {
            ui.mostrar("Contenido del log más reciente:")
            lineas.forEach { ui.mostrar(it) }
        }
    }

    /**
     * Realiza una operación matemática a partir de los argumentos recibidos.
     */
    private fun ejecutarCalculoConArgumentos(args: Array<String>) {
        val numero1 = args[1].replace(',', '.').toDoubleOrNull()
        val operador = Operador.getOperador(args[2].firstOrNull())
        val numero2 = args[3].replace(',', '.').toDoubleOrNull()

        if (numero1 == null || operador == null || numero2 == null) {
            val msg = "Error en los argumentos: operación no válida."
            ui.mostrarError(msg)
            if (modo) {
                gestorBBDD.registrarEntrada(msg)
            } else {
                gestorLog.registrarEntradaLog(msg)
            }
        } else {
            realizarCalculo(numero1, operador, numero2)
        }
    }

    /**
     * Ejecuta un bucle de cálculos interactivos con el usuario hasta que este decida salir.
     */
    private fun bucleCalculosUsuario() {
        do {
            try {
                val numero1 = ui.pedirDouble("Introduce el primer número: ")
                    ?: throw InfoCalcException("El primer número no es válido!")
                val simbolo = ui.pedirInfo("Introduce el operador (+, -, x, /): ").firstOrNull()
                val operador = Operador.getOperador(simbolo) ?: throw InfoCalcException("El operador no es válido!")
                val numero2 = ui.pedirDouble("Introduce el segundo número: ")
                    ?: throw InfoCalcException("El segundo número no es válido!")

                realizarCalculo(numero1, operador, numero2)
            } catch (e: InfoCalcException) {
                val mensaje = e.message ?: "Se ha producido un error!"
                ui.mostrarError(mensaje)
                if (modo) {
                    gestorBBDD.registrarEntrada(mensaje)
                } else {
                    gestorLog.registrarEntradaLog(mensaje)
                }
            }
        } while (ui.preguntar())

        if (modo) {
            DataBase.closeConnection()
        }
        ui.limpiarPantalla()
    }

    /**
     * Ejecuta un cálculo utilizando parámetros explícitos o solicitándolos al usuario.
     * Registra el resultado en el log.
     */
    private fun realizarCalculo(num1: Double, operador: Operador, num2: Double) {
        val calculo = calculadora.realizarCalculo(num1, operador, num2)
        ui.mostrar(calculo.toString())
        if (modo) {
            gestorBBDD.registrarEntrada(calculo)
        } else {
            gestorLog.registrarEntradaLog(calculo)
        }
    }
}