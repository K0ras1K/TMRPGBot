package ru.k0ras1k.tmbot

import com.jessecorbett.diskord.api.common.UserStatus
import com.jessecorbett.diskord.bot.bot
import com.jessecorbett.diskord.bot.classicCommands
import com.jessecorbett.diskord.bot.events
import ru.k0ras1k.tmbot.utils.DataBaseChanger
import ru.k0ras1k.tmbot.utils.DataBaseReader


val botInfo = BotInfo()

val BOT_TOKEN = botInfo.BOT_TOKEN

val dataBaseReader = DataBaseReader()
val dataBaseChanger = DataBaseChanger()

suspend fun main() {

    bot(BOT_TOKEN) {
        events {
            onMessageCreate { message ->
                var author_id: String = message.author.id

//                if (!dataBaseReader.getUserBan(author_id)) {
                if (dataBaseReader.getUserAdmin(author_id)) {
                    //User remove from databse
                    if (message.content.contains("!userremove")) {
                        dataBaseChanger.removeUser(message.content.removeRange(0..11))
                        message.react("✅")
                        message.reply("Вы успешно удалили пользователя ${message.content.removeRange(0..11)} из базы данных!")
                    }
                    //User change admin status
                    if (message.content.contains("!usersetadmin")) {
                        dataBaseChanger.setUserAdmin(message.content.removeRange(0..13))
                        message.react("✅")
                        message.reply("Вы успешно изменили состояние ${message.content.removeRange(0..13)}")
                    }
                    //User add to databse
                    if (message.content.contains("!useradd")) {
                        dataBaseChanger.addUser(message.content.removeRange(0..8))
                        message.react("✅")
                        message.reply("Пользователь ${message.content.removeRange(0..8)} был успешно зарегистрирован в базе данных!")
                    }
                    //User set rank
                    if (message.content.contains("!usersetrank")) {
                        var array = message.content.removeRange(0..12).split(" ").toTypedArray()
                        dataBaseChanger.setUserRank(array[0], array[1].toInt())
                        message.react("✅")
                        message.reply("Вы успешно изменили ранг пользователю ${array[0]}")
                    }
                    //User set xp
                    if (message.content.contains("!usersetxp")) {
                        var array = message.content.removeRange(0..10).split(" ").toTypedArray()
                        dataBaseChanger.setUserXp(array[0], array[1].toInt())
                        message.react("✅")
                        message.reply("Вы успешно изменили опыт пользователю ${array[0]}")
                    }

                } else {
                    if (message.content.contains("!userremove")) {
                        message.react("❌")
                        message.reply("У вас не достаточно прав для выполнения данной команды!")
                    }
                    if (message.content.contains("!usersetadmin")) {
                        message.react("❌")
                        message.reply("У вас не достаточно прав для выполнения данной команды!")
                    }
                    if (message.content.contains("!useradd")) {
                        message.react("❌")
                        message.reply("У вас не достаточно прав для выполнения данной команды!")
                    }
                    if (message.content.contains("!usersetrank")) {
                        message.react("❌")
                        message.reply("У вас не достаточно прав для выполнения данной команды!")
                    }
                    if (message.content.contains("!usersetxp")) {
                        message.react("❌")
                        message.reply("У вас не достаточно прав для выполнения данной команды!")
                    }
                }

                if (!dataBaseReader.userInBase(author_id) && message.author.isBot == false) {
                    dataBaseChanger.addUser(author_id)
                }
                if (dataBaseChanger.getNewRank(author_id)) {
                    message.reply("Вы получили новый уровень - ${dataBaseReader.getRank(author_id)}!")
                }
                dataBaseChanger.addXP(author_id, message.content.length)
            }
//                else {
//                    Ban("", message.author)
//                }
        }



        classicCommands("!") {
            command("Ping") { message ->
                message.reply("Pong!")
            }
            command("rank") { message ->
                message.reply("Ваш уровень - ${dataBaseReader.getRank(message.author.id)}")
            }
            command("setstatus") {
                message ->
                setStatus("K0ras1K koding me...", UserStatus.ONLINE)
            }
        }

    }
}





