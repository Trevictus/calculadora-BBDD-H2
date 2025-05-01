package es.prog2425.calclog.data

import es.prog2425.calclog.model.Calculo
import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.SQLIntegrityConstraintViolationException
import java.time.LocalDateTime

class RepoLogBBDD : IRepoLogBBDD {
    object Fecha_log {
        val fecha_log = LocalDateTime.now().toString()
    }


    private lateinit var connection: Connection
    private lateinit var preparadorSentencias: PreparedStatement

    private val url = "jdbc:h2:tcp://localhost/~/BBDD_CALCULADORA"
    private val user = "sa"
    private val psswd = ""

    override fun connect() {
        try {
            Class.forName("org.h2.Driver")
            println("Driver encontrado")
            connection = DriverManager.getConnection(url, user, psswd)
            println("CONEXIÓN ESTABLECIDA.")
        } catch (e: ClassNotFoundException) {
            println("Driver no encontrado.")
        } catch (e: SQLException) {
            println(e)
        }
    }

    override fun disconnect() {
        connection.close()
    }


    override fun getContenidoUltimoLog(): List<String> {
        val listaDatos: MutableList<String> = mutableListOf()
        try {
            val ultimosLogs =
                "SELECT * FROM LOGS, CALCULO WHERE LOGS.FECHA_LOG = CALCULO.FECHA_LOG AND LOGS.FECHA_LOG = (SELECT MAX(FECHA_LOG) FROM LOGS) ORDER BY FECHA_LOG DESC;"
            preparadorSentencias = connection.prepareStatement(ultimosLogs)

            val resultSet: ResultSet = preparadorSentencias.executeQuery()
            while (resultSet.next()) {
                val fechaHora = resultSet.getTimestamp("CALCULO.FECHA")
                val calculo = resultSet.getString("CALCULO.CALCULO")
                listaDatos.add("$fechaHora $calculo")
            }
            preparadorSentencias.close()
        } catch (e: SQLException) {
            println(e)
        }
        return listaDatos
    }

    override fun registrarEntrada(mensaje: String) {

        try {
            val insercionEnTabla = "INSERT INTO CALCULO (fecha, calculo, fecha_log) " +
                    "VALUES (?,?,?)"
            preparadorSentencias = connection.prepareStatement(insercionEnTabla)
            preparadorSentencias.setString(1, LocalDateTime.now().toString())
            preparadorSentencias.setString(2, mensaje)
            preparadorSentencias.setString(3, Fecha_log.fecha_log)

            preparadorSentencias.executeUpdate()
            preparadorSentencias.close()
        } catch (e: SQLIntegrityConstraintViolationException) {
            println("ERROR clave repetida. $e")
        } catch (e: SQLException) {
            println("ERROR INESPERADO. $e")
        }
    }

    override fun registrarEntrada(calculo: Calculo) {
        registrarEntrada(calculo.toString())
    }

    override fun crearNuevoLog() {

        try {
            val insercionEnTabla = "INSERT INTO LOGS (fecha_log) " +
                    "VALUES (?)"
            preparadorSentencias = connection.prepareStatement(insercionEnTabla)
            preparadorSentencias.setString(1, Fecha_log.fecha_log)

            preparadorSentencias.executeUpdate()
            preparadorSentencias.close()
            Fecha_log.fecha_log
        } catch (e: SQLIntegrityConstraintViolationException) {
            println("ERROR clave repetida. $e")
        } catch (e: SQLException) {
            println("ERROR INESPERADO. $e")
        }

    }
}