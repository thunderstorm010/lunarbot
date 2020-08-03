package com.thunderstorm010.lunarbot

import discord4j.core.GatewayDiscordClient
import discord4j.core.`object`.entity.channel.PrivateChannel
import discord4j.core.event.domain.message.MessageCreateEvent
import kotlin.system.exitProcess

class ShutdownDispatcher(val client: GatewayDiscordClient) {
    fun execute() {
        client.eventDispatcher.on(MessageCreateEvent::class.java)
            .filter {it.message.channel.block() !is PrivateChannel}
            .filter { !it.member.get().isBot }
            .filter { it.message.content == "!shutdown" }
            .filter {it.member.get() == it.guild.block()!!.owner.block()}
            .subscribe {
                exitProcess(0)
            }
    }
}