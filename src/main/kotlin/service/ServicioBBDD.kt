package es.prog2425.calclog.service

import es.prog2425.calclog.data.IRepoLogBBDD
import es.prog2425.calclog.model.Calculo

class ServicioBBDD(private val repoBBDD: IRepoLogBBDD): IServicioBBDD{

    override fun connect(){
        repoBBDD.connect()
    }
    override fun disconnect(){
        repoBBDD.disconnect()
    }
    override fun getInfoUltimoLog(): List<String>{
        return repoBBDD.getContenidoUltimoLog()
    }
    override fun registrarEntrada(mensaje: String){
        repoBBDD.registrarEntrada(mensaje)
    }
    override fun registrarEntrada(calculo: Calculo){
        repoBBDD.registrarEntrada(calculo)
    }
    override fun crearNuevoLog(){
        repoBBDD.crearNuevoLog()
    }
}