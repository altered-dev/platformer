package me.altered.platformer.objects

interface WorldContext : ObjectContainer {

    val player: PlayerContext

    // TODO: camera

    fun reference(name: String): ObjectContext
}
