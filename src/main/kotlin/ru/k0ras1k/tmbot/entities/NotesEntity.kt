package ru.k0ras1k.tmbot.entities

import com.sun.org.apache.xpath.internal.operations.Bool
import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.boolean
import org.ktorm.schema.int
import org.ktorm.schema.text

interface NoteEntity : Entity<NoteEntity> {
    val USER_ID: String
    val USER_RANG: Int
    val USER_XP: Int
    val USER_ADMIN: Boolean
    val USER_BAN: Boolean
    val USER_NICK: String
    val USER_MESSAGES: Int
    val USER_MUTE: Boolean
}


object NotesEntity : Table<NoteEntity>("tmbot") {
    val USER_ID = text("user_id").primaryKey().bindTo { it.USER_ID }
    val USER_RANG = int("user_rang").bindTo { it.USER_RANG }
    val USER_XP = int("user_xp").bindTo { it.USER_XP }
    val USER_ADMIN = boolean("user_admin").bindTo { it.USER_ADMIN }
    val USER_BAN = boolean("user_ban").bindTo { it.USER_BAN }
    val USER_NICK = text("user_nick").bindTo { it.USER_NICK }
    val USER_MESSAGES = int("user_messages").bindTo { it.USER_MESSAGES }
    val USER_MUTE = boolean("user_mute").bindTo { it.USER_MUTE }
}