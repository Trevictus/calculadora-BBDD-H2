package es.prog2425.calclog.model

// Clase de datos para representar un cálculo matemático
data class Calculo(
    val numero1: Double, // Primer número del cálculo
    val numero2: Double, // Segundo número del cálculo
    val operador: Operador, // Operador matemático utilizado (+, -, *, /)
    val resultado: Double // Resultado del cálculo
) {
    // Método que devuelve una representación en cadena del cálculo con formato legible
    override fun toString(): String {
        return "%.2f %s %.2f = %.2f".format(numero1, operador.simboloUi, numero2, resultado)
    }
}

