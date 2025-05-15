package es.prog2425.calclog.data.db

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

object DataBase {
    private lateinit var connection: Connection

    private const val URL = "jdbc:h2:file:./BBDD/BBDD_CALCULADORA"
    private const val USER = "sa"
    private const val PSSWD = ""

    init {
        try {
            Class.forName("org.h2.Driver")
        } catch (e: ClassNotFoundException) {
            throw IllegalStateException("No se pudo cargar el driver de H2", e)
        }
    }


    fun getConnection(): Connection {
        try {
            connection = DriverManager.getConnection(URL, USER, PSSWD)
            println("CONEXIÓN ESTABLECIDA.")
        } catch (e: SQLException) {
            println(e)
        }
        return connection
    }

    fun closeConnection(){
        connection.close()
    }
}