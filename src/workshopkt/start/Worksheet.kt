package workshopkt.start

data class SomeClass(
    val someAttr: String,
    val someAttr2: List<String>,
    val someAttr3: Int,
    var someOptional: String? = null,
    var someOptional2: Int? = 4,
    val lista1 : List<String>? = null,
    val funcion: (String) -> Unit = { nombre -> println("Hola $nombre, me llamo $someAttr")}
)

fun Int.repetir(func : ()-> Unit) { func(); this.dec().takeIf { it > 0 }?.repetir(func)}

fun main() {
    val miObjeto = SomeClass("Mi clase", listOf("hola", "como", "estas", "mica"), 4, someOptional2 = 6)
    miObjeto.apply {
        someOptional2 = 6
        someOptional = "ahre"
    }.also {
        println(it)
        with(it.someAttr2) {
            print(first())
            println(last())
        }
    }.run {
        copy(someAttr = "mi nuevo objeto")
    }.takeUnless { it.someOptional.isNullOrBlank() }?.let {
        println(it)
        it.lista1.orEmpty()
    }

    miObjeto.copy (someOptional2 = null).runCatching { println(someOptional2!!.dec()) }

    4.repetir { miObjeto.funcion("rodri") }
}