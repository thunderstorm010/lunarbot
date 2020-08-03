@file:JvmName("Main")
package com.thunderstorm010.lunarbot

import discord4j.core.DiscordClientBuilder
import discord4j.core.GatewayDiscordClient


object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val client: GatewayDiscordClient = DiscordClientBuilder.create(args[0])
            .build()
            .login()
            .block()!!
        System.setProperty("http.agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36")
        ReadyEventDispatcher(client).execute()
        HelpDispatcher(client).execute()
        AvatarDispatcher(client).execute()
        DmDuyuruDispatcher(client).execute()
        EspriDispatcher(client).execute()
        DeleteDispatcher(client).execute()
        CustomCommandDispatcher(client).execute()
        UserJoinedGuildDispatcher(client).execute()
        KayıtDispatcher(client).execute()

        client.onDisconnect().block()



    }
}