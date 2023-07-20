package org.alter.api.cfg.scripts

import net.runelite.cache.IndexType
import net.runelite.cache.fs.Store
import org.runestar.cs2.SCRIPT_NAMES
import org.runestar.cs2.bin.Script
import org.runestar.cs2.cg.StrictGenerator
import org.runestar.cs2.decompile
import org.runestar.cs2.util.Loader
import org.runestar.cs2.util.caching
import org.runestar.cs2.util.list
import org.runestar.cs2.util.withIds
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
import kotlin.io.path.absolutePathString
import kotlin.io.path.createFile
import kotlin.io.path.writeBytes

/**
 * @TODO
 */
fun main(args: Array<String>) {
    println("Running..")
    val encodedScripts = getScriptBytes()
    println("encodedScripts: ${encodedScripts.size}")

   val loadDir = Path.of("/temp_cs2/input")
   val saveDir = Path.of("/temp_cs2/scripts", "scripts")

   val generator = StrictGenerator { scriptId, scriptName, script ->
   //Files.writeString(saveDir.resolve("$scriptName.cs2"), script)
   }
   val scriptLoader = Loader { Script(Files.readAllBytes(loadDir.resolve(it.toString()))) }.caching()
   val scriptIds = loadDir.list().mapTo(TreeSet()) { it.toInt() }
   decompile(scriptLoader.withIds(scriptIds), generator)
}




public val filter = listOf(6775, 502)
fun getScriptBytes() : List<Pair<Int, ByteArray>> {
    var scriptList = mutableListOf<Pair<Int, ByteArray>>()
    val store = Store(Paths.get("data", "cache").toFile())
    store.load()
    val configs = store.getIndex(IndexType.CLIENTSCRIPT)
    for (i in 0 until configs.archives.size) {
        if (i in filter) {
            continue
        }
        try {
            val archive = configs.getArchive(i) // Script id
            val scriptFile = archive.getFiles(store.storage.loadArchive(archive)!!).files
            val byteFile = scriptFile[0].contents // <-- File
            scriptList.add(Pair(i, byteFile))
        } catch (e: Exception) {
            println("Exception by script_$i : $e")
        }
    }
    return scriptList
}