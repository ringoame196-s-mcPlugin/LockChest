package com.github.ringoame196_s_mcPlugin.util

import org.mindrot.jbcrypt.BCrypt

object HashManager {
    fun hash(password: String): String {
        return BCrypt.hashpw(password, BCrypt.gensalt(8))
    }

    fun verify(input: String, hashed: String): Boolean {
        return BCrypt.checkpw(input, hashed)
    }
}