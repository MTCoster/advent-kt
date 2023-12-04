package net.mtcoster.advent.parsers

private typealias KString = kotlin.String
private typealias JReader = java.io.Reader

interface Parser<T : Any> {
    fun parse(input: JReader): T
    fun parse(input: KString): T

    fun toString(value: T) = value.toString()

    interface Reader<T : Any> : Parser<T> {
        override fun parse(input: KString) = parse(input.reader())
    }

    interface String<T : Any> : Parser<T> {
        override fun parse(input: JReader) = parse(input.readText())
    }

    interface ByLines<T : Any> : Parser<T> {
        fun parseLines(lines: Sequence<KString>): T

        override fun parse(input: JReader) = input.useLines { parseLines(it) }
        override fun parse(input: KString) = parseLines(input.lineSequence())
    }
}


object StringParser : Parser.String<String> {
    override fun parse(input: String) = input
    override fun toString(value: String) = value
}

data object IntParser : Parser.String<Int> {
    override fun parse(input: String) = input.trim().toInt()
}


fun <T : Any> Parser<T>.lines(): Parser<List<T>> = LinesParser(this)

private data class LinesParser<T : Any>(private val inner: Parser<T>) : Parser.ByLines<List<T>> {
    override fun parseLines(lines: Sequence<String>) = lines.map(inner::parse).toList()
    override fun toString(value: List<T>) = value.joinToString("\n", transform = inner::toString)
}


fun <T : Any> Parser<T>.trimmed(): Parser<T> = TrimmedParser(this)

private data class TrimmedParser<T : Any>(private val inner: Parser<T>) : Parser.String<T> {
    override fun parse(input: String) = inner.parse(input.trim())
    override fun toString(value: T) = inner.toString()
}
