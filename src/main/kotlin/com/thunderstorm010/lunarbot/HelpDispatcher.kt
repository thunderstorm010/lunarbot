package com.thunderstorm010.lunarbot

import discord4j.core.GatewayDiscordClient
import discord4j.core.`object`.entity.channel.PrivateChannel
import discord4j.core.event.domain.message.MessageCreateEvent
import discord4j.rest.util.Color

class HelpDispatcher(val client: GatewayDiscordClient) {
    fun execute() {
        val KOMUT_LISTESI = arrayOf("yardım","avatar","dmduyuru","espri","sil","ip","kayıt") // Komut listesi
        client.eventDispatcher.on(MessageCreateEvent::class.java)
            .map (MessageCreateEvent::getMessage)
            .filter { message -> message.channel.block() !is PrivateChannel}
                // Mesajın gönderildiği kanal özel değilse
            .filter {!it.author.get().isBot}
                // Mesajı gönderen bot değilse
            .filter { message -> message.content == "!yardım" }
                // Mesajın içeriği yardım ise
            .subscribe { message ->
                message.channel.block()?.createEmbed { embed -> // Mesaj atılan kanalda embed oluştur
                    val genel_field_values = "!yardım" +  // Genel komutlar
                            "\n!yardım [komutismi]" +
                            "\n!avatar" +
                            "\n!espri"
                    val admin_field_values = "!dmduyuru" + // Admin komutları
                            "\n!sil [msj sayısı]"
                    val custom_field_values = "!ip" +
                            "\n!kayıt"
                    embed.setColor(Color.RED) // Embed rengi: KIRMIZI
                    embed.addField("Genel",genel_field_values,false) // Embed alanı (Genel)
                    embed.addField("Yöneticiler",admin_field_values,false) // Embed alanı (Yöneticiler)
                    embed.addField("Özel Komutlar",custom_field_values,false) // Embed alanı (özel komutlar)
                    embed.setFooter("https://github.com/thunderstorm010/lunarbot","https://github.githubassets.com/images/modules/logos_page/GitHub-Mark.png") // Footer (github linki)
                }?.subscribe()
            }
        client.eventDispatcher.on(MessageCreateEvent::class.java)
            .map (MessageCreateEvent::getMessage)
            .filter { message -> message.channel.block() !is PrivateChannel}
            .filter { message -> message.content.startsWith("!yardım") }
            .filter { message ->  message.content.split(" ").getOrNull(1) != null  }
            .filter { message ->  message.content.split(" ")[1].equalsAnyOf(KOMUT_LISTESI) }
            .subscribe { message ->
                if (message.content.split(" ")[1].equals("avatar")) {
                    message.channel.block()!!.createEmbed {embed ->
                        embed.setTitle("!avatar")
                        embed.addField("Kullanımı","`!avatar [kullanıcı etiketi]`" +
                                "\n`!avatar`",false)
                        embed.addField("Açıklama","Kullanıcının kendisinin veya başkasının avatarını görmek için kullanılır.",false)
                        embed.addField("Parametreler","`[kullanıcı etiketi]` :: Kendi kullanıcı avatarını döndürmek yerine belirtilen kullanıcının avatarını döndürür.",false)
                        embed.addField("Döndürür","`!avatar` :: Kullanıcının kendi avatarı." +
                                "\n`!avatar [kullanıcı etiketi]` :: Etiketlenen kullanıcının avatarı.",false)
                        embed.setColor(Color.BLUE)
                        embed.setFooter("Thunderstorm#0200 tarafından yapıldı","https://cdn.discordapp.com/attachments/738851044724965439/739078655178833960/avatar.png")
                    }.subscribe()
                } else if (message.content.split(" ")[1].equals("yardım")) {
                    message.channel.block()!!.createEmbed {embed ->
                        embed.setTitle("!yardım")
                        embed.addField("Kullanımı","`!yardım`" +
                                "\n`!yardım [komut ismi]`",false)
                        embed.addField("Açıklama","Komut listesi veya bir komut hakkındaki yardım sayfasını göstermek için kullanılır.",false)
                        embed.setColor(Color.BLUE)
                        embed.setFooter("Thunderstorm#0200 tarafından yapıldı","https://cdn.discordapp.com/attachments/738851044724965439/739078655178833960/avatar.png")
                    }.subscribe()
                } else if (message.content.split(" ")[1].equals("dmduyuru")) {
                    message.channel.block()!!.createEmbed {embed ->
                        embed.setTitle("!dmduyuru")
                        embed.addField("Kullanımı","`!dmduyuru [duyurulacak mesaj]`",false)
                        embed.addField("Açıklama","Sunucudaki bütün kullanıcılara bir duyuruyu mesajını iletir.",false)
                        embed.setFooter("Thunderstorm#0200 tarafından yapıldı","https://cdn.discordapp.com/attachments/738851044724965439/739078655178833960/avatar.png")
                        embed.setColor(Color.BLUE)
                    }.subscribe()
                } else if (message.content.split(" ")[1].equals("espri")) {
                    message.channel.block()!!.createEmbed {embed ->
                        embed.setTitle("!espri")
                        embed.addField("Kullanımı","`!espri`",false)
                        embed.addField("Açıklama","Rastgele bir espri mesajı gönderir.",false)
                        embed.setFooter("Thunderstorm#0200 tarafından yapıldı","https://cdn.discordapp.com/attachments/738851044724965439/739078655178833960/avatar.png")
                        embed.setColor(Color.BLUE)
                    }.subscribe()
                } else if (message.content.split(" ")[1].equals("sil")) {
                    message.channel.block()!!.createEmbed {embed ->
                        embed.setTitle("!sil")
                        embed.addField("Kullanımı","`!sil [mesaj sayısı]`",false)
                        embed.addField("Açıklama","Belirtilen sayıdaki mesajları siler.",false)
                        embed.addField("Parametreler","`mesaj sayısı` :: Kaç tane mesaj silineceği",false)
                        embed.setFooter("Thunderstorm#0200 tarafından yapıldı","https://cdn.discordapp.com/attachments/738851044724965439/739078655178833960/avatar.png")
                        embed.setColor(Color.BLUE)
                    }.subscribe()
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