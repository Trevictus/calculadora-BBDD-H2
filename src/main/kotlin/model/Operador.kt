package es.prog2425.calclog.model

// Enumeración que representa los operadores matemáticos básicos
enum class Operador(val simboloUi: Char, val simbolos: List<Char>) {
    // Definición de los operadores con sus símbolos principales y variantes aceptadas
    SUMA('+', listOf('+')), // Operador de suma
    RESTA('-', listOf('-')), // Operador de resta
    MULTIPLICACION('x', listOf('*', 'x')), // Operador de multiplicación con símbolos alternativos
    DIVISION('/', listOf(':', '/')); // Operador de división con variantes

    companion object {
        // Método para obtener un operador a partir de un carácter
        fun getOperador(operador: Char?) =
            operador?.let { op -> entries.find { op in it.simbolos } }
    }
}