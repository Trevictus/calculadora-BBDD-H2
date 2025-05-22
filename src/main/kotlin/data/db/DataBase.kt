package es.prog2425.calclog.data.db

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

object DataBase {
    // Variable para almacenar la conexión con la base de datos
    private lateinit var connection: Connection

    // Constantes con la URL de la base de datos, usuario y contraseña
    private const val URL = "jdbc:h2:file:./BBDD/BBDD_CALCULADORA"
    private const val USER = "sa"
    private const val PSSWD = ""

    // Bloque de inicialización para cargar el driver de H2 al instanciar el objeto
    init {
        try {
            Class.forName("org.h2.Driver") // Carga el driver de H2
        } catch (e: ClassNotFoundException) {
            // Lanza una excepción si el driver no se encuentra
            throw IllegalStateException("No se pudo cargar el driver de H2", e)
        }
    }

    // Método para establecer la conexión con la base de datos
    fun getConnection(): Connection {
        try {
            connection = DriverManager.getConnection(URL, USER, PSSWD) // Se crea la conexión con la base de datos
            println("CONEXIÓN ESTABLECIDA.") // Mensaje de confirmación
        } catch (e: SQLException) {
            // Manejo de errores en caso de falla en la conexión
            println(e)
        }
        return connection
    }

    // Método para cerrar la conexión con la base de datos
    fun closeConnection(){
        connection.close() // Cierra la conexión
    }
}