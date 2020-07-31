import discord4j.core.GatewayDiscordClient
import discord4j.core.`object`.entity.Message
import discord4j.core.`object`.entity.channel.GuildMessageChannel
import discord4j.core.`object`.entity.channel.PrivateChannel
import discord4j.core.event.domain.message.MessageCreateEvent
import discord4j.rest.util.Permission
import io.netty.channel.MessageSizeEstimator

class DeleteDispatcher(val client: GatewayDiscordClient) {
    fun execute() {
        client.eventDispatcher.on(MessageCreateEvent::class.java)
            .map(MessageCreateEvent::getMessage)
            .filter { l -> l.channel.block() !is PrivateChannel }
            .filter { l -> !l.author.get().isBot }
            .filter { l -> l.content.startsWith("!sil") }
            .filter { l -> l.authorAsMember.block().basePermissions.block().contains(Permission.MANAGE_MESSAGES)}
            .filter { l -> l.content.split(" ").getOrNull(1) != null}
            .filter { l -> l.content.split(" ")[1].toLongOrNull() != null}
            .subscribe { m ->
                (m.channel.block() as GuildMessageChannel).bulkDelete( m.
                channel.block()?.getMessagesBefore(m.id)?.take(m.content.split(" ")[1].toLong())?.map(Message::getId)).subscribe()
                m.delete().subscribe()
                Thread {
                    m.channel.block().createMessage(m.content.split(" ")[1] + " mesaj uzaya fırlatıldı.").block().let { lll ->
                        Thread.sleep(1000)
                        lll.delete().subscribe()
                    }
                }.start()

            }
    }
}