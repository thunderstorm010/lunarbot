import discord4j.core.DiscordClientBuilder
import discord4j.core.GatewayDiscordClient


object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val client: GatewayDiscordClient = DiscordClientBuilder.create(args[0])
            .build()
            .login()
            .block()!!

        ReadyEventDispatcher(client).execute()
        HelpDispatcher(client).execute()
        AvatarDispatcher(client).execute()

        client.onDisconnect().block()



    }
}