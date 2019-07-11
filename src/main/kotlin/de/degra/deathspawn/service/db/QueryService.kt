package de.degra.deathspawn.service.db

import de.degra.deathspawn.entity.DeathPointEntity
import java.io.FileReader
import java.sql.Connection
import java.sql.PreparedStatement

object QueryService {
    var isEnabled = false;
    var connection: Connection? = null
    lateinit var pDeleteById: PreparedStatement
    lateinit var pInsertNew: PreparedStatement
    lateinit var pSelectAll: PreparedStatement
    lateinit var pSelectById: PreparedStatement

    fun createTable() {
        if (isInvalidConnection()) {
            return
        }

        getStatement("CreateTable.sql").execute()
    }

    fun getAll(): List<DeathPointEntity> {
        if (isInvalidConnection()) {
            return emptyList()
        }
        
        val results = getStatement("SelectAll.sql").executeQuery()
        //TODO: Map
    }

    private fun isInvalidConnection():Boolean{
        return !isEnabled || connection == null
    }

    private fun getStatement(path: String): PreparedStatement {
        val reader = FileReader("sql/$path")
        val payload = reader.readText()
        reader.close()

        connection!!.prepareStatement(path)
    }

}