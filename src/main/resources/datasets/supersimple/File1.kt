package datasets.supersimple

fun Any.something(): Unit {
    println("a")
}

fun regularFunction(x: Int) = x * x

class A {

    @Deprecated("adad")
    private override infix suspend fun Unit.some(x: String, y: Int) {
        println("b")
        fun Any.doSomethign() = this
    }

    protected fun regularProtected(x: String): String = x
}

fun <T> T.getIt() = this

fun <T : Iterable<Any>, R> T.iterate() = this

fun <T : Number> List<T>.summ(): T = this.first()