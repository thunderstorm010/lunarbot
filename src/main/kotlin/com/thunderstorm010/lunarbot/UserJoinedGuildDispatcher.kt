package com.thunderstorm010.lunarbot

import discord4j.core.GatewayDiscordClient
import discord4j.core.event.domain.guild.MemberJoinEvent

class UserJoinedGuildDispatcher(val client: GatewayDiscordClient) {
    fun execute() {
        client.eventDispatcher.on(MemberJoinEvent::class.java)
            .filter { l -> !l.member.isBot }
            .subscribe {
                it.member.privateChannel.block()?.createEmbed {
                    it.setTitle("Lunar Jailbreak Discord sunucumuza hoş geldin !")
                    it.setImage("https://cdn.discordapp.com/attachments/690564626236047410/739026247635697694/deneme.png")
                }?.subscribe()

            }
    }
}