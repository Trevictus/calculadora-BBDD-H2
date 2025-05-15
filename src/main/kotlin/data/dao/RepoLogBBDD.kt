package es.prog2425.calclog.data.dao

import es.prog2425.calclog.data.db.DataBase
import es.prog2425.calclog.model.Calculo

import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.SQLIntegrityConstraintViolationException
import java.time.LocalDateTime

class RepoLogBBDD : IRepoLogBBDD {
    object FechaLog {
        val fecha_log = LocalDateTime.now().toString()
    }


    private lateinit var preparadorSentencias: PreparedStatement


    override fun getContenidoUltimoLog(): List<String> {
        val listaDatos: MutableList<String> = mutableListOf()
        var resultSet: ResultSet? = null
        try {
            val ultimosLogs =
                "SELECT * FROM LOGS, " +
                        "CALCULO WHERE LOGS.FECHA_LOG = CALCULO.FECHA_LOG " +
                        "AND LOGS.FECHA_LOG = (SELECT MAX(FECHA_LOG) FROM LOGS) " +
                        "ORDER BY FECHA_LOG DESC;"
            preparadorSentencias = DataBase.getConnection().prepareStatement(ultimosLogs)

            resultSet = preparadorSentencias.executeQuery()
            while (resultSet.next()) {
                val fechaHora = resultSet.getTimestamp("CALCULO.FECHA")
                val calculo = resultSet.getString("CALCULO.CALCULO")
                listaDatos.add("$fechaHora $calculo")
            }
            preparadorSentencias.close()
        } catch (e: SQLException) {
            throw IllegalStateException("ERROR al intentar obtener de la base de datos. $e")
        } catch (e: Exception) {
            throw IllegalStateException("ERROR INESPERADO. $e")
        } finally {
            try {
                resultSet?.close()
                preparadorSentencias.close()
                DataBase.closeConnection()
            } catch (e: Exception){
                throw IllegalStateException("ERROR DE CIERRE")
            }
        }
        return listaDatos
    }

    override fun registrarEntrada(mensaje: String) {

        try {
            val insercionEnTabla = "INSERT INTO CALCULO (fecha, calculo, fecha_log) " +
                    "VALUES (?,?,?)"
            preparadorSentencias = DataBase.getConnection().prepareStatement(insercionEnTabla)
            preparadorSentencias.setString(1, LocalDateTime.now().toString())
            preparadorSentencias.setString(2, mensaje)
            preparadorSentencias.setString(3, FechaLog.fecha_log)

            preparadorSentencias.executeUpdate()
            preparadorSentencias.close()
        } catch (e: SQLIntegrityConstraintViolationException) {
            throw IllegalStateException("ERROR clave repetida. $e")
        } catch (e: SQLException) {
            throw IllegalStateException("ERROR al insertar en la base de datos. $e")
        } catch (e: Exception) {
            throw IllegalStateException("ERROR INESPERADO. $e")
        } finally {
            try {
                preparadorSentencias.close()
                DataBase.closeConnection()
            } catch (e: Exception){
                throw IllegalStateException("ERROR DE CIERRE")
            }
        }
    }

    override fun registrarEntrada(calculo: Calculo) {
        registrarEntrada(calculo.toString())
    }

    override fun crearNuevoLog() {

        try {
            val insercionEnTabla = "INSERT INTO LOGS (fecha_log) " +
                    "VALUES (?)"
            preparadorSentencias = DataBase.getConnection().prepareStatement(insercionEnTabla)
            preparadorSentencias.setString(1, FechaLog.fecha_log)

            preparadorSentencias.executeUpdate()
            preparadorSentencias.close()
            FechaLog.fecha_log
        } catch (e: SQLIntegrityConstraintViolationException) {
            throw IllegalStateException("ERROR clave repetida. $e")
        } catch (e: SQLException) {
            throw IllegalStateException("ERROR al insertar en la base de datos. $e")
        } catch (e: Exception) {
            throw IllegalStateException("ERROR INESPERADO. $e")
        } finally {
            try {
                preparadorSentencias.close()
                DataBase.closeConnection()
            } catch (e: Exception){
                throw IllegalStateException("ERROR DE CIERRE")
            }
        }

    }
}