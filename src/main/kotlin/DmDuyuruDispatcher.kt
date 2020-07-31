import discord4j.core.GatewayDiscordClient
import discord4j.core.`object`.entity.channel.PrivateChannel
import discord4j.core.event.domain.message.MessageCreateEvent
import discord4j.rest.util.Color
import discord4j.rest.util.Permission

class DmDuyuruDispatcher(val client: GatewayDiscordClient) {
    fun execute() {
        client.eventDispatcher.on(MessageCreateEvent::class.java)
            .map(MessageCreateEvent::getMessage)
            .filter { i -> i.channel.block() !is PrivateChannel }
            .filter { !it.author.get().isBot }
            .filter { it.content.startsWith("!dmduyuru") }
            .filter { it.content.split(" ").getOrNull(0) != null }
            .filter { it.authorAsMember.block()!!.basePermissions.block()!!.contains(Permission.ADMINISTRATOR)}
            .subscribe { l ->
                l.channel.block().createMessage(l.author.get().mention + ", lütfen bekle. İşlem bitince bu mesaj silinecek.").block().let { z ->
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
                }
            }
    }
}