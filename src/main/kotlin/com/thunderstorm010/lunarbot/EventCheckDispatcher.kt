package com.thunderstorm010.lunarbot

import discord4j.common.util.Snowflake
import discord4j.core.GatewayDiscordClient
import discord4j.core.`object`.entity.channel.GuildMessageChannel
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


class EventCheckDispatcher(val client: GatewayDiscordClient) {
    fun execute() {
        var nowUtc: Date = Date()
        var asiaTurkey: TimeZone = TimeZone.getTimeZone("GMT+3.00")
        var nowAsiaTurkey: Calendar = Calendar.getInstance(asiaTurkey)
        nowAsiaTurkey.time = nowUtc
        val scheduledExecutorService = Executors.newScheduledThreadPool(5)
        var t = Timer()


    }


}