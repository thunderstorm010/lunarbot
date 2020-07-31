import discord4j.core.GatewayDiscordClient
import discord4j.core.`object`.entity.channel.PrivateChannel
import discord4j.core.event.domain.message.MessageCreateEvent
import discord4j.rest.util.Color
import kotlin.properties.Delegates

class HelpDispatcher(val client: GatewayDiscordClient) {
    fun execute() {
        val KOMUT_LISTESI = arrayOf("yardım","avatar","dmduyuru","espri","sil")
        client.eventDispatcher.on(MessageCreateEvent::class.java)
            .map (MessageCreateEvent::getMessage)
            .filter { i -> i.channel.block() !is PrivateChannel}
            .filter {!it.author.get().isBot}
            .filter { it.content == "!yardım" }
            .subscribe { msg ->
                msg.channel.block()?.createEmbed {
                    val genel_field_values = "!yardım" +
                            "\n!yardım [komutismi]" +
                            "\n!avatar" +
                            "\n!espri"
                    val admin_field_values = "!dmduyuru" +
                            "\n!sil [msj sayısı]"
                    it.setColor(Color.RED)
                    it.addField("Genel",genel_field_values,false)
                    it.addField("Yöneticiler",admin_field_values,false)
                    it.setFooter("https://github.com/thunderstorm010/lunarbot","https://github.githubassets.com/images/modules/logos_page/GitHub-Mark.png")
                }?.subscribe()
                msg.author.get().avatarUrl
            }
        client.eventDispatcher.on(MessageCreateEvent::class.java)
            .map (MessageCreateEvent::getMessage)
            .filter { i -> i.channel.block() !is PrivateChannel}
            .filter { i -> i.content.startsWith("!yardım") }
            .filter { i ->  i.content.split(" ").getOrNull(1) != null  }
            .filter { i ->  i.content.split(" ")[1].equalsAnyOf(KOMUT_LISTESI) }
            .subscribe {
                if (it.content.split(" ")[1].equals("avatar")) {
                    it.channel.block()!!.createEmbed {l ->
                        l.setTitle("!avatar")
                        l.addField("Kullanımı","`!avatar [kullanıcı etiketi]`" +
                                "\n`!avatar`",false)
                        l.addField("Açıklama","Kullanıcının kendisinin veya başkasının avatarını görmek için kullanılır.",false)
                        l.addField("Parametreler","`[kullanıcı etiketi]` :: Kendi kullanıcı avatarını döndürmek yerine belirtilen kullanıcının avatarını döndürür.",false)
                        l.addField("Döndürür","`!avatar` :: Kullanıcının kendi avatarı." +
                                "\n`!avatar [kullanıcı etiketi]` :: Etiketlenen kullanıcının avatarı.",false)
                        l.setColor(Color.BLUE)
                        l.setFooter("Thunderstorm#0200 tarafından yapıldı","https://cdn.discordapp.com/attachments/738553651030786158/738672015518728282/dclogosu.png")
                    }.subscribe()
                } else if (it.content.split(" ")[1].equals("yardım")) {
                    it.channel.block()!!.createEmbed {l ->
                        l.setTitle("!yardım")
                        l.addField("Kullanımı","`!yardım`" +
                                "\n`!yardım [komut ismi]`",false)
                        l.addField("Açıklama","Komut listesi veya bir komut hakkındaki yardım sayfasını göstermek için kullanılır.",false)

                        l.setColor(Color.BLUE)
                        l.setFooter("Thunderstorm#0200 tarafından yapıldı","https://cdn.discordapp.com/attachments/738553651030786158/738672015518728282/dclogosu.png")
                    }.subscribe()
                } else if (it.content.split(" ")[1].equals("dmduyuru")) {
                    it.channel.block()!!.createEmbed {l ->
                        l.setTitle("!dmduyuru")
                        l.addField("Kullanımı","`!dmduyuru [duyurulacak mesaj]`",false)
                        l.addField("Açıklama","Sunucudaki bütün kullanıcılara bir duyuruyu mesajını iletir.",false)
                        l.setFooter("Thunderstorm#0200 tarafından yapıldı","https://cdn.discordapp.com/attachments/738553651030786158/738672015518728282/dclogosu.png")
                        l.setColor(Color.BLUE)
                    }.subscribe()
                } else if (it.content.split(" ")[1].equals("espri")) {
                    it.channel.block()!!.createEmbed {l ->
                        l.setTitle("!espri")
                        l.addField("Kullanımı","`!espri`",false)
                        l.addField("Açıklama","Rastgele bir espri mesajı gönderir.",false)
                        l.setFooter("Thunderstorm#0200 tarafından yapıldı","https://cdn.discordapp.com/attachments/738553651030786158/738672015518728282/dclogosu.png")
                        l.setColor(Color.BLUE)
                    }.subscribe()
                } else if (it.content.split(" ")[1].equals("sil")) {
                    it.channel.block()!!.createEmbed {l ->
                        l.setTitle("!sil")
                        l.addField("Kullanımı","`!sil [mesaj sayısı]`",false)
                        l.addField("Açıklama","Belirtilen sayıdaki mesajları siler.",false)
                        l.addField("Parametreler","`mesaj sayısı` :: Kaç tane mesaj silineceği",false)
                        l.setFooter("Thunderstorm#0200 tarafından yapıldı","https://cdn.discordapp.com/attachments/738553651030786158/738672015518728282/dclogosu.png")
                        l.setColor(Color.BLUE)
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