package com.thunderstorm010.lunarbot

import discord4j.core.GatewayDiscordClient
import discord4j.core.`object`.presence.Activity
import discord4j.core.`object`.presence.Presence
import discord4j.core.event.domain.lifecycle.ReadyEvent

class ReadyEventDispatcher(val client: GatewayDiscordClient) {
    fun execute() {
        client.eventDispatcher.on(ReadyEvent::class.java)
            .subscribe {
                client
                    .updatePresence(Presence.online(Activity.playing("Made by Thunderstorm")))
                    .subscribe()
            }
    }
}