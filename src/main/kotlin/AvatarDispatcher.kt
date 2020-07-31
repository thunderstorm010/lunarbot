import discord4j.core.GatewayDiscordClient
import discord4j.core.`object`.entity.User
import discord4j.core.event.domain.message.MessageCreateEvent
import java.io.*
import java.net.MalformedURLException
import java.net.URL
import java.net.URLConnection
import java.nio.charset.Charset


class AvatarDispatcher(val client:GatewayDiscordClient) {
    fun execute() {
        client.eventDispatcher.on(MessageCreateEvent::class.java)
            .filter {l -> l.message.content.startsWith("!avatar", true) }
            .filter {l -> !l.message.author.get().isBot}
            .subscribe {
                if (it.message.content == "!avatar") {
                    it.message.channel.block().createMessage {z ->
                        saveUrl("avatar.png",it.message.author.get().avatarUrl)
                        z.setContent("İşte avatarın, " + it.message.author.get().mention)
                        z.addFile("avatar.png",File("avatar.png").inputStream())
                    }.subscribe()
                    File("avatar.png").delete()
                } else if (it.message.content.startsWith("!avatar") && it.message.userMentions.collectList().block()?.size!! > 0 && it.message.userMentions.collectList().block()?.size!! < 2) {
                    it.message.channel.block().createMessage { z->
                        z.setContent("İşte " + it.message.userMentions.collectList().block()?.get(0)?.mention + " kullanıcısının avatarı; ")
                        saveUrl("avatar.png",it.message.userMentions.collectList().block()?.get(0)?.avatarUrl)
                        z.addFile("avatar.png",File("avatar.png").inputStream())
                    }
                        .subscribe()
                    File("avatar.png").delete()

                }
            }
    }

    @Throws(MalformedURLException::class, IOException::class)
    fun saveUrl(filename: String?, urlString: String?) {
        System.setProperty("http.agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.105 Safari/537.36")


        var `in`: BufferedInputStream? = null
        var fout: FileOutputStream? = null
        try {
            `in` = BufferedInputStream(URL(urlString).openStream())
            fout = FileOutputStream(filename)
            val data = ByteArray(1024)
            var count: Int
            while (`in`.read(data, 0, 1024).also { count = it } != -1) {
                fout.write(data, 0, count)
            }
        } finally {
            `in`?.close()
            fout?.close()
        }
    }
}