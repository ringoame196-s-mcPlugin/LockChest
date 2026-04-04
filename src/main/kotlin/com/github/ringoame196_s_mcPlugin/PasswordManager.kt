package com.github.ringoame196_s_mcPlugin

object PasswordManager {
    private lateinit var db: DataBaseManager

    fun init(database: DataBaseManager) {
        db = database
    }
}
