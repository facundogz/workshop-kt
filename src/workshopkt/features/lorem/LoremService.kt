package workshopkt.features.lorem

import java.io.File


object LoremService {
    private val words by lazy {
        File("resources/lorem").readLines().toList()
    }


    fun generate(cant: Int? = 50) = (1..(cant ?: 50)).joinToString(" ") { words.random() }
}

