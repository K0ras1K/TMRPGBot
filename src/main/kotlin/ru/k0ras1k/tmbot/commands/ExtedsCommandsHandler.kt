package ru.k0ras1k.tmbot.commands

import com.jessecorbett.diskord.api.common.Message
import ru.k0ras1k.tmbot.dataBaseChanger
import ru.k0ras1k.tmbot.dataBaseReader

class ExtedsCommandsHandler {

    fun oneArgumentCommandHandler(message: Message, user_id: String): Boolean? {
        if (dataBaseReader.getUserAdmin(user_id)) {
            //User add to databse
            if (message.content.startsWith("!useradd")) {
                dataBaseChanger.addUser(message.content.removeRange(0..8), "added")
                return true
            }
            //User remove from database
            if (message.content.startsWith("!userremove")) {
                dataBaseChanger.removeUser(message.content.removeRange(0..11))
                return true
            }
            //User change admin status
            if (message.content.startsWith("!usersetadmin")) {
                dataBaseChanger.setUserAdmin(message.content.removeRange(0..13))
                return true
            }
            //User mute
            if (message.content.startsWith("!mute")) {
                dataBaseChanger.updateUserMute(message.content.removeRange(0..7), true)
                return true
            }
            //User unmute
            if (message.content.startsWith("!unmute")) {
                dataBaseChanger.updateUserMute(message.content.removeRange(0..7), false)
                return true
            }

        } else if (!dataBaseReader.getUserAdmin(user_id)) {
            if (message.content.startsWith("!userremove") || message.content.startsWith("!usersetadmin")
                || message.content.startsWith("!useradd") || message.content.startsWith("!mute")
                || message.content.startsWith("!unmute")) {
                return false
            }
        }
        return null
    }


    fun twoArgumentsCommandHandler(message: Message, user_id: String): Boolean? {
        if (dataBaseReader.getUserAdmin(user_id)) {
            //User set rank
            if (message.content.startsWith("!usersetrank")) {
                val array = message.content.removeRange(0..12).split(" ").toTypedArray()
                dataBaseChanger.setUserRank(array[0], array[1].toInt())
                return true
            }
            //User set xp
            if (message.content.startsWith("!usersetxp")) {
                val array = message.content.removeRange(0..10).split(" ").toTypedArray()
                dataBaseChanger.setUserXp(array[0], array[1].toInt())
                return true
            }
            //User add xp
            if (message.content.startsWith("!userxpadd")) {
                val array = message.content.removeRange(0..10).split(" ").toTypedArray()
                dataBaseChanger.addXP(array[0], array[1].toInt())
                return true

            }
            //User update nick
            if (message.content.startsWith("!userupdatenick")) {
                val array = message.content.removeRange(0..15).split(" ").toTypedArray()
                dataBaseChanger.updateUserNick(array[0], array[1])
                return true
            }
        } else if (!dataBaseReader.getUserAdmin(user_id)) {
            if (message.content.startsWith("!usersetrank") || message.content.startsWith("!usersetrank")
                || message.content.startsWith("!usersetxp") || message.content.startsWith("!userupdatenick")
            ) {
                return false
            }
        }
        return null
    }
}


