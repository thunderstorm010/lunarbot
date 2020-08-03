package com.thunderstorm010.lunarbot

import discord4j.common.util.Snowflake
import discord4j.core.GatewayDiscordClient
import discord4j.core.`object`.entity.channel.PrivateChannel
import discord4j.core.event.domain.message.MessageCreateEvent

class KayıtDispatcher(val client: GatewayDiscordClient) {
    fun execute() {
        client.eventDispatcher.on(MessageCreateEvent::class.java)
                .filter {it.message.channel.block() !is PrivateChannel}
                .filter {!it.member.get().isBot }
                .filter {it.message.content.startsWith("!kayıtet")}
                .filter { it.member.get().roles.collectList().block()!!.contains(client.getRoleById(Snowflake.of(738553650531795044), Snowflake.of(739193698486845500)).block()) }
                .filter {
                    if (it.message.content.split(" ").getOrNull(1) == null) {
                        it.message.channel.block()!!.createMessage("Parametre (kullanıcı adı) verilmemiş.").subscribe()
                        false
                    } else true
                }
                .filter { if (it.message.content.split(" ").getOrNull(2) == null) {
                    it.message.channel.block()!!.createMessage("Parametre (gerçek isim) verilmemiş.").subscribe()
                    false
                } else true }
                .filter { if (it.message.content.split(" ").getOrNull(3) == null) {
                    it.message.channel.block()!!.createMessage("Parametre (yaş) verilmemiş.").subscribe()
                    false
                } else true
                }
                .filter { if (it.message.content.split(" ").getOrNull(4) == null) {
                    it.message.channel.block()!!.createMessage("Parametre (yapılacak kişi) verilmemiş.").subscribe()
                    false
                } else true }
                .filter { if (!it.message.content.split(" ")[4].matches(Regex("<@!?\\d+>"))) {
                    it.message.channel.block()!!.createMessage("Parametre (yapılacak kişi) geçersiz").subscribe()
                    false
                } else true }
                .filter {if (it.message.userMentions.collectList().block().size > 2) {
                    it.message.channel.block()!!.createMessage("Mesajda birden fazla etiketleme var.").subscribe()
                    false
                } else true }
                .subscribe { mce ->
                    val uye_rolu = client.getRoleById(Snowflake.of(738553650531795044),Snowflake.of(738553650531795049)).block()
                    val kullanici = client.getMemberById(Snowflake.of(738553650531795044),mce.message.userMentions.collectList().block()!![0].id).block()
                    kullanici.edit {
                        it.setNickname(mce.message.content.split(" ")[1] + "/" + mce.message.content.split(" ")[2] + "-[" + mce.message.content.split(" ")[3] + "]")
                        val new_role_ids = kullanici.roleIds + uye_rolu.id
                        it.setRoles(new_role_ids)
                    }.subscribe()


                }

    }
    

}