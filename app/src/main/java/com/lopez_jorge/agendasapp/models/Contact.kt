package com.lopez_jorge.agendasapp.models

data class Contact(
    val id: Int? = null,       // opcional, lo genera MySQL
    val name: String,
    val email: String,
    val address: String,
    val phone: String
)