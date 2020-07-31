import discord4j.core.GatewayDiscordClient
import discord4j.core.event.domain.guild.MemberJoinEvent

class UserJoinedGuildDispatcher(val client: GatewayDiscordClient) {
    fun execute() {
        client.eventDispatcher.on(MemberJoinEvent::class.java)
            .filter { l -> !l.member.isBot }
            .subscribe {
                TODO("good night")
                //it.member.privateChannel.block().createMessage()
            }
    }
}