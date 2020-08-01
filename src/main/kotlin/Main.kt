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
        DmDuyuruDispatcher(client).execute()
        EspriDispatcher(client).execute()
        DeleteDispatcher(client).execute()
        CustomCommandDispatcher(client).execute()
        UserJoinedGuildDispatcher(client).execute()

        client.onDisconnect().block()



    }
}