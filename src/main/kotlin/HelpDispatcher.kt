import discord4j.core.GatewayDiscordClient
import discord4j.core.event.domain.message.MessageCreateEvent
import discord4j.rest.util.Color
import kotlin.properties.Delegates

class HelpDispatcher(val client: GatewayDiscordClient) {
    fun execute() {
        val KOMUT_LISTESI = arrayOf("yardım","avatar","duyuruyap")
        client.eventDispatcher.on(MessageCreateEvent::class.java)
            .map (MessageCreateEvent::getMessage)
            .filter {!it.author.get().isBot}
            .filter { it.content == "!yardım" }
            .subscribe { msg ->
                msg.channel.block()?.createEmbed {
                    val genel_field_values = "!yardım" +
                            "\n!yardım [komutismi]" +
                            "\n!avatar"
                    val admin_field_values = "!dmduyuru"
                    it.setColor(Color.RED)
                    it.addField("Genel",genel_field_values,true)
                    it.addField("Yöneticiler",admin_field_values,true)
                }?.subscribe()
                msg.author.get().avatarUrl
            }
        client.eventDispatcher.on(MessageCreateEvent::class.java)
            .map (MessageCreateEvent::getMessage)
            .filter { i -> i.content.startsWith("!yardım") }
            .filter { i ->  i.content.split(" ").getOrNull(1) != null  }
            .filter { i ->  i.content.split(" ")[1].equalsAnyOf(KOMUT_LISTESI) }
            .subscribe {
                if (it.content.split(" ")[1].equals("avatar")) {
                    it.channel.block()!!.createEmbed {l ->
                        l.setTitle("!avatar")
                        l.setDescription("Kullanımı:\n" +
                                "`!avatar [kullanıcı etiketi]`" +
                                "\n`!avatar`")
                        l.addField("Açıklama","Kullanıcının kendisinin veya başkasının avatarını görmek için kullanılır.",false)
                        l.addField("Parametreler","`[kullanıcı etiketi]` :: Kendi kullanıcı avatarını döndürmek yerine belirtilen kullanıcının avatarını döndürür.",false)
                        l.addField("Döndürür","`!avatar` :: Kullanıcının kendi avatarı." +
                                "\n`!avatar [kullanıcı etiketi]` :: Etiketlenen kullanıcının avatarı.",false)
                        l.setColor(Color.BLUE)
                    }.subscribe()
                } else if (it.content.split(" ")[1].equals("yardım")) {
                    it.channel.block()!!.createEmbed {l ->
                        l.setTitle("!yardım")
                        l.setDescription("Kullanımı:\n" +
                                "`!yardım`" +
                                "\n`!yardım [komut ismi]`")
                        l.addField("Açıklama","Komutları veya belirli bir komutun yaptığı şeyi görmek için kullanılır.",false)
                        l.addField("Döndürür","`!yardım` :: Yardım mesajı." +
                                "\n`!yardım [komut ismi]` :: Bir komut hakkındaki yardım sayfası.",false)
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