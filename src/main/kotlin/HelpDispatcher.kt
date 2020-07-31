import discord4j.core.GatewayDiscordClient
import discord4j.core.event.domain.message.MessageCreateEvent
import discord4j.rest.util.Color
import kotlin.properties.Delegates

class HelpDispatcher(val client: GatewayDiscordClient) {
    fun execute() {
        client.eventDispatcher.on(MessageCreateEvent::class.java)
            .map (MessageCreateEvent::getMessage)
            .filter {!it.author.get().isBot}
            .filter { it.content == "!yardım" }
            .subscribe { msg ->
                msg.channel.block()?.createEmbed {
                    val genel_field_values = "!yardım :: Bu mesajı yazar." +
                            "\n!yardım [komutismi] :: wBir komut için yardım mesajı yazar."
                    val admin_field_values = "!duyuruyap :: Duyuru yapar."
                    it.setColor(Color.RED)
                    it.addField("Genel",genel_field_values,true)
                    it.addField("Yöneticiler",admin_field_values,true)
                }?.subscribe()
            }
    }
}