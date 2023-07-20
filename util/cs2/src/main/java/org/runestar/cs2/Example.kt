package org.runestar.cs2

import org.runestar.cs2.bin.Script
import org.runestar.cs2.cg.StrictGenerator
import org.runestar.cs2.util.Loader
import org.runestar.cs2.util.caching
import org.runestar.cs2.util.list
import org.runestar.cs2.util.withIds
import java.nio.file.Files
import java.nio.file.Path
import java.util.TreeSet

fun main() {

    val loadDir = Path.of("input")
    val saveDir = Path.of("scripts", "scripts")
    Files.createDirectories(saveDir)

    val generator = StrictGenerator { scriptId, scriptName, script ->
        Files.writeString(saveDir.resolve("$scriptName.cs2"), script)
    }

    val scriptLoader = Loader { Script(Files.readAllBytes(loadDir.resolve(it.toString()))) }.caching()
    val scriptIds = loadDir.list().mapTo(TreeSet()) { it.toInt() }

    decompile(scriptLoader.withIds(scriptIds), generator)
}