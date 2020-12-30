import net.perfectdreams.sequins.text.StringUtils
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ChunkedLinesTest {
    @Test
    fun `test chunked max length 100, force split true, forceSplitOnSpaces true, with single big string`() {
        val output = StringUtils.chunkedLines(
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce non vehicula nunc. Vivamus suscipit, nibh nec tincidunt vehicula, ante orci auctor arcu, sed dictum enim leo ut metus. Proin luctus sem quam, at consequat lorem ultrices eget. Suspendisse tortor turpis, vestibulum vitae sapien in, interdum ultricies eros. Sed facilisis mauris ac est feugiat viverra. Fusce iaculis, nisi vel aliquet varius, risus mauris egestas ipsum, eget aliquam leo augue sed augue. Donec sit amet semper erat. Aenean ut metus vulputate dui vehicula congue tempor a dui. In hac habitasse platea dictumst. Aliquam sed vestibulum nulla. Praesent cursus felis eu pellentesque dignissim.",
            100,
            true,
            true
        )

        output.forEach {
            failIfBiggerThanLength(it, 100)
        }
    }

    @Test
    fun `test chunked max length 25, force split true, forceSplitOnSpaces false, with single big string`() {
        val output = StringUtils.chunkedLines(
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce non vehicula nunc. Vivamus suscipit, nibh nec tincidunt vehicula, ante orci auctor arcu, sed dictum enim leo ut metus. Proin luctus sem quam, at consequat lorem ultrices eget. Suspendisse tortor turpis, vestibulum vitae sapien in, interdum ultricies eros. Sed facilisis mauris ac est feugiat viverra. Fusce iaculis, nisi vel aliquet varius, risus mauris egestas ipsum, eget aliquam leo augue sed augue. Donec sit amet semper erat. Aenean ut metus vulputate dui vehicula congue tempor a dui. In hac habitasse platea dictumst. Aliquam sed vestibulum nulla. Praesent cursus felis eu pellentesque dignissim.",
            25,
            true,
            false
        )

        output.forEach {
            failIfBiggerThanLength(it, 25)
        }
    }

    @Test
    fun `split up Loritta is Cute`() {
        val lines = mutableListOf<String>()

        repeat(100) {
            lines.add("Loritta is Cute!")
        }

        val x = StringUtils.chunkedLines(
            lines.map { "$it\n" },
            34
        )

        assertThat(x.size).isEqualTo(50)
        x.forEach {
            failIfBiggerThanLength(it, 34)
        }
        assertThat(
            x.toString()
                .replace("\n", "\\n")
        ).isEqualTo("""[Loritta is Cute!\nLoritta is Cute!\n, Loritta is Cute!\nLoritta is Cute!\n, Loritta is Cute!\nLoritta is Cute!\n, Loritta is Cute!\nLoritta is Cute!\n, Loritta is Cute!\nLoritta is Cute!\n, Loritta is Cute!\nLoritta is Cute!\n, Loritta is Cute!\nLoritta is Cute!\n, Loritta is Cute!\nLoritta is Cute!\n, Loritta is Cute!\nLoritta is Cute!\n, Loritta is Cute!\nLoritta is Cute!\n, Loritta is Cute!\nLoritta is Cute!\n, Loritta is Cute!\nLoritta is Cute!\n, Loritta is Cute!\nLoritta is Cute!\n, Loritta is Cute!\nLoritta is Cute!\n, Loritta is Cute!\nLoritta is Cute!\n, Loritta is Cute!\nLoritta is Cute!\n, Loritta is Cute!\nLoritta is Cute!\n, Loritta is Cute!\nLoritta is Cute!\n, Loritta is Cute!\nLoritta is Cute!\n, Loritta is Cute!\nLoritta is Cute!\n, Loritta is Cute!\nLoritta is Cute!\n, Loritta is Cute!\nLoritta is Cute!\n, Loritta is Cute!\nLoritta is Cute!\n, Loritta is Cute!\nLoritta is Cute!\n, Loritta is Cute!\nLoritta is Cute!\n, Loritta is Cute!\nLoritta is Cute!\n, Loritta is Cute!\nLoritta is Cute!\n, Loritta is Cute!\nLoritta is Cute!\n, Loritta is Cute!\nLoritta is Cute!\n, Loritta is Cute!\nLoritta is Cute!\n, Loritta is Cute!\nLoritta is Cute!\n, Loritta is Cute!\nLoritta is Cute!\n, Loritta is Cute!\nLoritta is Cute!\n, Loritta is Cute!\nLoritta is Cute!\n, Loritta is Cute!\nLoritta is Cute!\n, Loritta is Cute!\nLoritta is Cute!\n, Loritta is Cute!\nLoritta is Cute!\n, Loritta is Cute!\nLoritta is Cute!\n, Loritta is Cute!\nLoritta is Cute!\n, Loritta is Cute!\nLoritta is Cute!\n, Loritta is Cute!\nLoritta is Cute!\n, Loritta is Cute!\nLoritta is Cute!\n, Loritta is Cute!\nLoritta is Cute!\n, Loritta is Cute!\nLoritta is Cute!\n, Loritta is Cute!\nLoritta is Cute!\n, Loritta is Cute!\nLoritta is Cute!\n, Loritta is Cute!\nLoritta is Cute!\n, Loritta is Cute!\nLoritta is Cute!\n, Loritta is Cute!\nLoritta is Cute!\n, Loritta is Cute!\nLoritta is Cute!\n]""")
    }

    @Test
    fun `simple chunked example`() {
        val input = listOf("hello world!\n", "lori is very cute!!\n", "and pantufa is also very cute!!\n")
        val split = StringUtils.chunkedLines(input, 34)

        assertThat(split.size).isEqualTo(2)
    }

    @Test
    fun `check if both methods match`() {
        val input = listOf("hello world!\n", "lori is very cute!!\n", "and pantufa is also very cute!!\n")
        val split = StringUtils.chunkedLines(input, 34)

        val input2 = "hello world!\nlori is very cute!!\nand pantufa is also very cute!!\n"
        val split2 = StringUtils.chunkedLines(input2, 34)

        assertThat(split).isEqualTo(split2)
    }

    private fun failIfBiggerThanLength(string: String, length: Int) = assertThat(string.length).isLessThanOrEqualTo(length)
}