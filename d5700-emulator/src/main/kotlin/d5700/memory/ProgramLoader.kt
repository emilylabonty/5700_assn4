package d5700.memory

import d5700.error.ProgramLoadException
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.exists
import kotlin.io.path.isRegularFile

class ProgramLoader(
    private val memoryBus: MemoryBus
) {

    fun load(pathText: String): ByteArray {
        val path = Path.of(pathText.trim())

        if (!path.exists()) {
            throw ProgramLoadException("Program file does not exist: $pathText")
        }

        if (!path.isRegularFile()) {
            throw ProgramLoadException("Program path is not a file: $pathText")
        }

        val programBytes = Files.readAllBytes(path)

        if (programBytes.size > Rom.DEFAULT_SIZE) {
            throw ProgramLoadException(
                "Program size ${programBytes.size} bytes exceeds ROM size ${Rom.DEFAULT_SIZE} bytes."
            )
        }

        memoryBus.loadRom(programBytes)
        return programBytes
    }
}