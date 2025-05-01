package es.prog2425.calclog.model

enum class Operador(val simboloUi: Char, val simbolos: List<Char>) {
    SUMA('+', listOf('+')),
    RESTA('-', listOf('-')),
    MULTIPLICACION('x', listOf('*', 'x')),
    DIVISION('/', listOf(':', '/'));

    companion object {
        fun getOperador(operador: Char?) = operador?.let { op -> entries.find { op in it.simbolos } }
    }
}