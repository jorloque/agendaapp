package com.lopez_jorge.agendasapp.models

import java.sql.Connection
import java.sql.DriverManager

object DBConnection {
    private const val URL = "jdbc:mariadb://db2.cvuekwyckcvn.us-east-1.rds.amazonaws.com:3306/agenda?useSSL=false"
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
