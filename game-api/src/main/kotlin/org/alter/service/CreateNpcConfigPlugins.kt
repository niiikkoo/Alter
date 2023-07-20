package org.alter.service

import gg.rsmod.util.ServerProperties
import net.runelite.cache.util.Namer
import org.alter.game.Server
import org.alter.game.fs.def.NpcDef
import org.alter.game.model.World
import org.alter.game.service.Service
import java.nio.file.Path
import java.nio.file.Paths
class CreateNpcConfigPlugins : Service {
    private var directory: Path? = null
    private var dump = false
    override fun init(server: Server, world: World, serviceProperties: ServerProperties) {
        dump = serviceProperties.getOrDefault("dump", false)
        directory = Paths.get(serviceProperties.get<String>("output-path")!!)
        var nullTypes = 0
        var nonCombatNpcs = 0
        var combatNpcs = 0
        val namer = Namer()
        val npcCount = world.definitions.getCount(NpcDef::class.java)
        for (i in 0 until npcCount) {
            val defs = world.definitions.getNullable(NpcDef::class.java, i)
            if (defs != null) {
                if (defs.name == "" || defs.name.lowercase() == "null" || defs.name.isEmpty()) {
                    nullTypes++
                    continue
                }
                if (defs.isAttackable()) {
                    combatNpcs++
                    continue
                } else {
                    nonCombatNpcs++
                }

                //val name = namer.name(defs.name, i)
                //var s: String = directory.toString().plus("/${defs.name}/$name.plugin.kts")
            }
        }
        println("nullTypes: $nullTypes")
        println("nonCombatNpcs: $nonCombatNpcs")
        println("combatNpcs: $combatNpcs")
    }

    override fun postLoad(server: Server, world: World) {
        if (!dump) {
            return
        }
    }

    override fun bindNet(server: Server, world: World) {
    }

    override fun terminate(server: Server, world: World) {
    }
}