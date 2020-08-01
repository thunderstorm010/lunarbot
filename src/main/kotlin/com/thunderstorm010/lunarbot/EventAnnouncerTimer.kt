package com.thunderstorm010.lunarbot

import discord4j.common.util.Snowflake
import discord4j.core.GatewayDiscordClient
import discord4j.core.`object`.entity.channel.GuildMessageChannel
import discord4j.rest.util.Color
import java.util.*

class EventAnnouncerTimer(val client: GatewayDiscordClient): TimerTask() {
    var NEXT_EVENT_ISDISABLED_PROPERTY = false

    override fun run() {
        val bundle: ResourceBundle = ResourceBundle.getBundle("event_texts")
        val asiaTurkey: TimeZone = TimeZone.getTimeZone("GMT+3.00")
        val nowAsiaTurkey: Calendar = Calendar.getInstance(asiaTurkey)
        val minute = nowAsiaTurkey.get(Calendar.MINUTE)
        val hour = nowAsiaTurkey.get(Calendar.HOUR_OF_DAY)
        val day = nowAsiaTurkey.get(Calendar.DAY_OF_WEEK)
        if (hour == 19 && minute == 55 && day == Calendar.SATURDAY && !NEXT_EVENT_ISDISABLED_PROPERTY) {
            val guildMessageChannel: GuildMessageChannel = client.getChannelById(Snowflake.of(738553650611224595)).block() as GuildMessageChannel
            guildMessageChannel.createMessage("@here")
            guildMessageChannel.createEmbed {
                it.setTitle("Yaklaşan bir etkinlik var!")
                it.setDescription("\uD83C\uDF1F(20.00)\uD83C\uDF1F = Toplantı")
                it.addField("Etkinlik Bilgileri","Etkinlik Adı: Toplantı\nEtkinlik Zamanı: Bugün 20:00",true)
                it.setColor(Color.BLUE)
            }.subscribe()
        } else if (hour == 19 && minute == 55 && day == Calendar.SATURDAY && NEXT_EVENT_ISDISABLED_PROPERTY) {
            NEXT_EVENT_ISDISABLED_PROPERTY=false
            return
        } else if (hour == 19 && minute == 55 && day == Calendar.WEDNESDAY && !NEXT_EVENT_ISDISABLED_PROPERTY) {
            val guildMessageChannel: GuildMessageChannel = client.getChannelById(Snowflake.of(738553650611224595)).block() as GuildMessageChannel
            guildMessageChannel.createMessage("@here")
            guildMessageChannel.createEmbed {
                it.setTitle("Yaklaşan bir etkinlik var!")
                it.setDescription("\uD83C\uDF1F(20.00)\uD83C\uDF1F = Toplantı")
                it.addField("Etkinlik Bilgileri","Etkinlik Adı: Toplantı\nEtkinlik Zamanı: Bugün 20:00",true)
                it.setColor(Color.BLUE)
            }.subscribe()
        }// else if (hour == 19 && minute == 55 && day == Calendar.)
    }

}