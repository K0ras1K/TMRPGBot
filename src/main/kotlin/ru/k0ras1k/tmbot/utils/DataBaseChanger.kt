package ru.k0ras1k.tmbot.utils

import org.ktorm.dsl.*
import org.ktorm.entity.find
import org.ktorm.entity.sequenceOf
import ru.k0ras1k.tmbot.dataBaseChanger
import ru.k0ras1k.tmbot.entities.NotesEntity

class DataBaseChanger: DataBaseReader() {

    //Add user to database
    fun addUser(user_id: String, user_nick: String) {
        database.insert(NotesEntity) {
            set(it.USER_ID, user_id)
            set(it.USER_NICK, user_nick.toString())
        }
    }


    //Remove user from database
    fun removeUser(user_id: String) {
        database.delete(NotesEntity) {
            it.USER_ID eq user_id
        }
    }


    //Add user experience
    fun addXP(user_id: String, xp: Int) {
        database.update(NotesEntity) {
            set(it.USER_XP, it.USER_XP + xp)
            where { it.USER_ID eq user_id }
        }
    }


    //Add user message counter
    fun addMessage(user_id: String, message_count: Int) {
        database.update(NotesEntity) {
            set(it.USER_MESSAGES, it.USER_MESSAGES + message_count)
            where { it.USER_ID eq user_id }
        }
    }


    //Change user admin status
    fun setUserAdmin(user_id: String) {
        if (!getUserAdmin(user_id)) {
            database.update(NotesEntity) {
                set(it.USER_ADMIN, true)
                where { it.USER_ID eq user_id }
            }
        } else {
            database.update(NotesEntity) {
                set(it.USER_ADMIN, false)
                where { it.USER_ID eq user_id }
            }
        }
    }


    //Update user rank
    fun updateRank(user_id: String) {
        database.update(NotesEntity) {
            set(it.USER_XP, 10)
            set(it.USER_RANG, it.USER_RANG + 1)
            where { it.USER_ID eq user_id }
        }
    }


    //Set user rank
    fun setUserRank(user_id: String, rank: Int) {
        database.update(NotesEntity) {
            set(it.USER_RANG, rank)
            where { it.USER_ID eq user_id }
        }
    }


    //Set user xp
    fun setUserXp(user_id: String, xp: Int) {
        database.update(NotesEntity) {
            set(it.USER_XP, xp)
            where { it.USER_ID eq user_id }
        }
    }


    //Check and set user lvl if user experience max
    fun getNewRank(user_id: String): Boolean {
        val finder = database.sequenceOf(NotesEntity).find { it.USER_ID eq user_id }
        if (finder != null) {
            if (finder.USER_XP > getRank(user_id) * 200) {
                updateRank(user_id)
                return true
            }
        }
        return false
    }


    //Update user nick
    fun updateUserNick(user_id: String, user_nick: String) {
        database.update(NotesEntity) {
            set(it.USER_NICK, user_nick)
            where { it.USER_ID eq user_id }
        }
    }


    //Update user mute
    fun updateUserMute(user_id: String, muted: Boolean) {
        database.update(NotesEntity) {
            set(it.USER_MUTE, muted)
            where { it.USER_ID eq user_id }
        }
    }
}