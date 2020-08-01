package com.thunderstorm010.lunarbot

import discord4j.core.GatewayDiscordClient
import discord4j.core.`object`.entity.channel.PrivateChannel
import discord4j.core.event.domain.message.MessageCreateEvent
import discord4j.rest.util.Color

class DmDuyuruDispatcher(val client: GatewayDiscordClient) {
    fun execute() {
        client.eventDispatcher.on(MessageCreateEvent::class.java)

            .filter { i -> i.message.channel.block() !is PrivateChannel }
            .filter { !it.message.author.get().isBot }
            .filter { it.message.content.startsWith("!dmduyuru") }
            .filter { it.message.content.split(" ").getOrNull(0) != null }
            .filter { it.message.authorAsMember.block()!! == it.guild.block().owner.block()}
                .map (MessageCreateEvent::getMessage)
            .subscribe { l ->
                l.channel.block().createEmbed {
                    it.setTitle("!dmduyuru")
                    it.setDescription(l.author.get().username + " lütfen bekle. İşlem bitince bu mesaj silinecek.")
                }.block().let {z ->

                    for (member in l.guild.block().members.collectList().block()) {
                        if (!member.isBot) {
                                member.privateChannel.block().createEmbed {d ->
                                    d.setColor(Color.CYAN)
                                    d.setTitle("Lunar Jailbreak'ten yeni bir duyuru var!")
                                    d.setDescription(l.content.split(" ")[1])
                                    d.setFooter("Duyuruyu yapan: " + l.author.get().username + "#" + l.author.get().discriminator, l.author.get().avatarUrl)
                                }.subscribe()
                        }

                    }
                    z.delete().subscribe()
                    l.delete().subscribe()
                }
            }
    }
}