package ru.k0ras1k.tmbot

import com.jessecorbett.diskord.api.common.Guild
import com.jessecorbett.diskord.api.common.GuildMember
import com.jessecorbett.diskord.api.common.UserStatus
import com.jessecorbett.diskord.api.guild.GuildClient
import com.jessecorbett.diskord.api.guild.PatchGuildMember
import com.jessecorbett.diskord.bot.bot
import com.jessecorbett.diskord.bot.classicCommands
import com.jessecorbett.diskord.bot.events
import com.jessecorbett.diskord.internal.client.RestClient
import ru.k0ras1k.tmbot.commands.ExtedsCommandsHandler
import ru.k0ras1k.tmbot.utils.DataBaseChanger
import ru.k0ras1k.tmbot.utils.DataBaseReader


val botInfo = BotInfo()

val BOT_TOKEN = botInfo.BOT_TOKEN

val dataBaseReader = DataBaseReader()
val dataBaseChanger = DataBaseChanger()
val extedsCommandsHandler = ExtedsCommandsHandler()

suspend fun main() {

    bot(BOT_TOKEN) {
        events {
            onMessageCreate { message ->
                val author_id: String = message.author.id
                //TODO add user_nick check on '/'
                if (author_id != botInfo.BOT_ID) {
                    if (!dataBaseReader.userInBase(author_id)) {
                        dataBaseChanger.addUser(author_id, message.author.username)
                    }
                    if (extedsCommandsHandler.oneArgumentCommandHandler(message, author_id) == true || extedsCommandsHandler.twoArgumentsCommandHandler(message, author_id) == true) {
                        message.react("✅")
                        message.reply("Операция выполнена успешно!")
                    }
                    else if (extedsCommandsHandler.oneArgumentCommandHandler(message, author_id) == false || extedsCommandsHandler.twoArgumentsCommandHandler(message, author_id) == false) {
                        message.react("❌")
                        message.reply("У вас не достаточно прав для выполнения данной команды!")
                    }

                    if (dataBaseChanger.getNewRank(author_id) && botInfo.NEW_RANK_MESSAGE_SEND) {
                        message.reply("Вы получили новый уровень - ${dataBaseReader.getRank(author_id)}!")
                    }
                    dataBaseChanger.addMessage(author_id, 1)
                    dataBaseChanger.addXP(author_id, message.content.length)
                }
            }

        }


        classicCommands("!") {
            command("Ping") { message ->
                message.reply("Pong!")
            }
            command("rank") { message ->
                message.reply("Ваш уровень - ${dataBaseReader.getRank(message.author.id)}")
            }
            command("setstatus") { message ->
                setStatus("K0ras1K koding me...", UserStatus.ONLINE)
            }
            command("help") { message ->
                if (dataBaseReader.getUserAdmin(message.author.id)) {
                    message.reply("Вам доступны следующие команды: \n!help\n!usersetrank userid rank\n!usetsetxp userid xp\n!useradd userid\n!userremove userid\n!setuseradmin\n!setstatus\n!ping")
                } else {
                    message.reply("Вам доступны следующие команды: \n!help\n!rank \n!ping")
                }
            }
        }
    }
}







