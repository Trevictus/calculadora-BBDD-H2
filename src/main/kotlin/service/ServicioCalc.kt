package es.prog2425.calclog.service

import es.prog2425.calclog.model.Calculo
import es.prog2425.calclog.model.Operador

/**
 * Clase responsable de realizar operaciones aritméticas y generar resultados como objetos de tipo [Calculo].
 *
 * No extiende de una interfaz porque su funcionalidad es simple, autocontenida y no se prevé
 * que haya múltiples implementaciones de este servicio. En este caso, el uso de una interfaz
 * añadiría complejidad innecesaria. Si en el futuro se necesitase extender su comportamiento
 * (por ejemplo, añadir validaciones específicas o diferentes formas de calcular),
 * podría extraerse una interfaz fácilmente sin modificar el código cliente.
 */
class ServicioCalc {

    /**
     * Realiza la operación matemática según el operador recibido.
     */
    private fun realizarOperacion(num1: Double, operador: Operador, num2: Double): Double {
        return when (operador) {
            Operador.SUMA -> num1 + num2
            Operador.RESTA -> num1 - num2
            Operador.MULTIPLICACION -> num1 * num2
            Operador.DIVISION -> num1 / num2
        }
    }

    /**
     * Recibe los datos de entrada de un cálculo y retorna el objeto [Calculo] con el resultado.
     */
    fun realizarCalculo(num1: Double, operador: Operador, num2: Double): Calculo {
        return Calculo(num1, num2, operador, realizarOperacion(num1, operador, num2))
    }

}