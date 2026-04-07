package com.github.ringoame196_s_mcPlugin

import java.util.UUID

object PasswordManager {
    private lateinit var db: DataBaseManager
    private val lockData = mutableMapOf<LockLocation, LockData>()

    fun init(database: DataBaseManager) {
        db = database
    }

    fun addLockData(lockLocation: LockLocation, data: LockData) {
        lockData[lockLocation] = data
    }

    fun removeLockData(lockLocation: LockLocation) {
        lockData.remove(lockLocation)
    }

    fun exists(lockLocation: LockLocation): Boolean {
        return lockData.containsKey(lockLocation)
    }

    fun authenticateOwner(lockLocation: LockLocation, owner: UUID): Boolean {
        val data = lockData[lockLocation] ?: return false
        return data.owner == owner
    }

    fun authenticatePassWord(lockLocation: LockLocation, inputPassword: String): Boolean {
        val data = lockData[lockLocation] ?: return false
        val savedHash = data.passWord
        return HashManager.verify(inputPassword, savedHash)
    }
}
