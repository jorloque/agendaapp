package com.lopez_jorge.agendasapp.models

import java.sql.Connection
import java.sql.DriverManager

object DBConnection {
    private const val URL = "jdbc:mariadb://db.cvmci2wemkun.us-east-1.rds.amazonaws.com/agenda?useSSL=false"
    private const val USER = "admin"
    private const val PASSWORD = "adminadmin"

    init {
        try {
            Class.forName("org.mariadb.jdbc.Driver")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getConnection(): Connection? {
        return try {
            DriverManager.getConnection(URL, USER, PASSWORD)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
