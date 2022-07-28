package ru.k0ras1k.tmbot.utils

import com.sun.org.apache.xpath.internal.operations.Bool
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.entity.find
import org.ktorm.entity.firstOrNull
import org.ktorm.entity.sequenceOf
import ru.k0ras1k.tmbot.BotInfo
import ru.k0ras1k.tmbot.entities.NotesEntity

open class DataBaseReader {

    val botInfo = BotInfo()

    val DATABASE_URL = botInfo.DATABASE_URL
    val DATABASE_USER = botInfo.DATABASE_USER
    val DATABASE_PASSWORD = botInfo.DATABASE_PASSWORD

    val database = Database.connect(
        url = DATABASE_URL,
        driver = "com.mysql.jdbc.Driver",
        user = DATABASE_USER,
        password = DATABASE_PASSWORD
    )


    //Check availability user in database
    fun userInBase(user_id: String): Boolean {
        val finder = database.sequenceOf(NotesEntity).firstOrNull { it.USER_ID eq user_id }
        if (finder != null) {
            return true
        }
        return false
    }


    //Return user admin status
    fun getUserAdmin(user_id: String): Boolean {
        val finder = database.sequenceOf(NotesEntity).find { it.USER_ID eq user_id }
        if (finder != null) {
            if (finder.USER_ADMIN) {
                return true
            }
        }
        return false
    }


    //Return user Rank
    fun getRank(user_id: String): Int {
        val finder = database.sequenceOf(NotesEntity).find { (it.USER_ID eq user_id) }
        return finder!!.USER_RANG
    }


    //Return user ban
    fun getUserBan(user_id: String): Boolean {
        val finder = database.sequenceOf(NotesEntity).find { (it.USER_ID eq user_id) }
        if (finder == null) {
            return true
        }
        return false
    }
}