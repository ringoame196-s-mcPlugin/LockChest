package com.github.ringoame196_s_mcPlugin.service

import com.github.ringoame196_s_mcPlugin.data.db.DataBaseManager
import com.github.ringoame196_s_mcPlugin.data.model.LockData
import com.github.ringoame196_s_mcPlugin.data.model.LockLocation
import com.github.ringoame196_s_mcPlugin.util.HashManager
import java.util.UUID

object PasswordService {
    private lateinit var db: DataBaseManager
    private val lockData = mutableMapOf<LockLocation, LockData>()

    private const val TABLE_NAME_KEY = "locks"
    private const val WORLD_NAME_KEY = "world_name"
    private const val X_KEY = "x"
    private const val Y_KEY = "y"
    private const val Z_KEY = "z"
    private const val OWNER_UUID_KEY = "owner_uuid"
    private const val PASSWORD_HASH_KEY = "password_hash"

    fun init(database: DataBaseManager) {
        db = database
    }

    fun addLockData(lockLocation: LockLocation, data: LockData) {
        lockData[lockLocation] = data
    }

    fun saveDB(lockLocation: LockLocation, data: LockData) {
        val world = lockLocation.world
        val x = lockLocation.x
        val y = lockLocation.y
        val z = lockLocation.z
        val ownerUUID = data.owner
        val passHash = data.passWord

        val sql = "INSERT INTO $TABLE_NAME_KEY ($WORLD_NAME_KEY,$X_KEY,$Y_KEY,$Z_KEY,$OWNER_UUID_KEY,$PASSWORD_HASH_KEY) VALUES (?,?,?,?,?,?);"
        db.executeUpdate(sql, listOf(world, x, y, z, ownerUUID, passHash))
    }

    fun loadDB() {
        val sql = "SELECT * FROM $TABLE_NAME_KEY"

        db.query(sql) { rows ->
            rows.forEach { row ->
                val world = row[WORLD_NAME_KEY] as String
                val x = row[X_KEY] as Int
                val y = row[Y_KEY] as Int
                val z = row[Z_KEY] as Int
                val owner = UUID.fromString(row[OWNER_UUID_KEY] as String)
                val password = row[PASSWORD_HASH_KEY] as String

                val loc = LockLocation(world, x, y, z)
                val data = LockData(password, owner)

                addLockData(loc, data)
            }
        }
    }

    fun removeLockData(lockLocation: LockLocation) {
        lockData.remove(lockLocation)
    }

    fun removeDB(lockLocation: LockLocation) {
        val sql = "DELETE FROM $TABLE_NAME_KEY WHERE $WORLD_NAME_KEY = ? AND $X_KEY = ? AND $Y_KEY = ? AND $Z_KEY = ?;"
        val world = lockLocation.world
        val x = lockLocation.x
        val y = lockLocation.y
        val z = lockLocation.z
        db.executeUpdate(sql, listOf(world, x, y, z))
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
        if (data.passWord == "LOCKING") {
            return false
        }

        val savedHash = data.passWord
        return HashManager.verify(inputPassword, savedHash)
    }
}
