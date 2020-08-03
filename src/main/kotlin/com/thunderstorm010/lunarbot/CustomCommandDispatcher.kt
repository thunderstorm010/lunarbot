package com.thunderstorm010.lunarbot

import discord4j.common.util.Snowflake
import discord4j.core.GatewayDiscordClient
import discord4j.core.`object`.entity.channel.PrivateChannel
import discord4j.core.event.domain.message.MessageCreateEvent
import discord4j.rest.util.Color

class CustomCommandDispatcher(val client: GatewayDiscordClient) {
    fun execute() {
        val komutlistesi = arrayOf("!kayıt","!ip")
        client.eventDispatcher.on(MessageCreateEvent::class.java)
                .map (MessageCreateEvent::getMessage)
                //.filter { it.channel.block() == client.getChannelById(Snowflake.of(738553650611224590)) || it.channel.block() == client.getChannelById(Snowflake.of(738553651655737377)) }
                .filter { it.channel.block() !is PrivateChannel }
                .filter { !it.author.get().isBot }
                .filter { it.content.equalsAnyOf(komutlistesi)}
                .subscribe {
                    if (it.content == "!kayıt" /* && it.channel.block() == client.getChannelById(Snowflake.of(738553650611224590))*/) {
                        it.channel.block()?.createEmbed {
                            println("embed triggered!")
                            it.setTitle("!kayıt")
                            it.setDescription("``\uD83C\uDF1FSunucumuza kayıt olmak için aşağıdaki formu doldurmalısınız \uD83C\uDF1F``\n" +
                                    "\n" +
                                    "```Nicknameniz : \n" +
                                    "İsminiz : \n" +
                                    "Yaşınız : \n" +
                                    "Steam Profil Linkiniz : ```\n" +
                                    "``\uD83C\uDF0CNotlar\uD83C\uDF0C``\n" +
                                    "\n" +
                                    "**:sparkles:Eğer günler geçmesine rağmen kayıt edilmediyseniz kayıt gereksinimlerini karşılamıyorsunuzdur.\n" +
                                    ":sparkles:Bilgileri doğru vermediğiniz sürece kayıt edilmezsiniz.\n" +
                                    ":sparkles:Bilgileri verdikten sonra acele etmeyin en geç gün içinde kayıt edilirsiniz.**"
                                    )

                            it.setFooter("Made by Thunderstorm","https://cdn.discordapp.com/attachments/738851044724965439/739078655178833960/avatar.png")
                            it.setColor(Color.BLUE)
                        }?.subscribe()
                        //738553651655737377
                    } else if (it.content == "!ip"/* && it.channel.block() == client.getChannelById(Snowflake.of(738553651655737377))*/) {
                        it.channel.block()?.createEmbed {
                            it.setColor(Color.BLUE)
                            it.setDescription("**:star2: Sunucu IP Adresi : 185.193.165.2**\n" +
                                    "**:star2: Discord Adresimiz :  https://discord.gg/UcJayRk**")
                            it.setTitle("!ip")
                            it.setFooter("Made by Thunderstorm#0200","https://cdn.discordapp.com/attachments/738851044724965439/739078655178833960/avatar.png")
                        }?.subscribe()
                    }


                }

    }
    fun String.equalsAnyOf(i: Array<String>): Boolean {
        for (s in i) {
            if (this.equals(s)) return true
        }
        return false
    }
}