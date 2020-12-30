package net.perfectdreams.sequins.text

object StringUtils {
    private val SPLIT_ON_NEW_LINES_BUT_KEEP_NEW_LINES_REGEX = Regex("((?<=\n)|(?=\n))")

    /**
     * Chunks the [list] into different strings, split on "\n" that can fit into a [maxLength]
     *
     * If the string in the [list] is larger than the [maxLength], it will be appened as is, unless if [forceSplit] is enabled.
     *
     * If [forceSplit] is enabled and [forceSplitOnSpaces] is also enabled, the function will try to split the string between the spaces in the string.
     * If [forceSplit] is enabled and [forceSplitOnSpaces] is disabled, the function will split the string using [chunked].
     *
     * It is recommended to set [forceSplit] to true if you don't want any overflowing strings in the result!
     *
     * Example:
     * ```
     * val input = listOf("hello world!\nlori is very cute!!\nand pantufa is also very cute!!\n")
     *
     * val chunkedLines = chunkedLines(input, 34)
     * // The chunkedLines will contain the following strings:
     * // ["hello world!\nlori is very cute!!\n"] and ["and pantufa is also very cute!!\n"]
     * ```
     *
     * @param list               a list of strings that will be merged until [maxLength] is reached
     * @param maxLength          the max length that a string can have in the result
     * @param forceSplit         if the string should be forcefully split if it doesn't fit
     * @param forceSplitOnSpaces if []forceSplit] is enabled, enabling this will split the string on spaces
     * @return the chunked [list]
    */
    fun chunkedLines(input: String, maxLength: Int, forceSplit: Boolean = false, forceSplitOnSpaces: Boolean = true) = chunkedLines(SPLIT_ON_NEW_LINES_BUT_KEEP_NEW_LINES_REGEX.split(input), maxLength, forceSplit, forceSplitOnSpaces)

    /**
     * Chunks the [list] into different strings that can fit into a [maxLength]
     *
     * If the string in the [list] is larger than the [maxLength], it will be appened as is, unless if [forceSplit] is enabled.
     *
     * If [forceSplit] is enabled and [forceSplitOnSpaces] is also enabled, the function will try to split the string between the spaces in the string.
     * If [forceSplit] is enabled and [forceSplitOnSpaces] is disabled, the function will split the string using [chunked].
     *
     * It is recommended to set [forceSplit] to true if you don't want any overflowing strings in the result!
     *
     * Example:
     * ```
     * val input = listOf("hello world!\n", "lori is very cute!!\n", "and pantufa is also very cute!!\n")
     *
     * val chunkedLines = chunkedLines(input, 34)
     * // The chunkedLines will contain the following strings:
     * // ["hello world!\nlori is very cute!!\n"] and ["and pantufa is also very cute!!\n"]
     * ```
     *
     * @param list               a list of strings that will be merged until [maxLength] is reached
     * @param maxLength          the max length that a string can have in the result
     * @param forceSplit         if the string should be forcefully split if it doesn't fit
     * @param forceSplitOnSpaces if []forceSplit] is enabled, enabling this will split the string on spaces
     * @return the chunked [list]
     */
    fun chunkedLines(list: List<String>, maxLength: Int, forceSplit: Boolean = false, forceSplitOnSpaces: Boolean = true): List<String> {
        val builder = StringBuilder()

        val chunkedStringList = mutableListOf<String>()

        for (string in list) {
            var builderIsClear = false

            if (builder.length + string.length > maxLength) {
                // Only append if the builder is not empty, if we appended anyway, big strings would have a empty string appended to
                if (builder.isNotEmpty()) {
                    chunkedStringList.add(builder.toString())
                    builder.clear()
                }
                builderIsClear = true
            }

            // Before we are going to check if the string really fits here
            if (builderIsClear && builder.length + string.length > maxLength) {
                if (forceSplit) {
                    if (forceSplitOnSpaces) {
                        // If we are going to force a split on spaces, we need to split everything and then figure out where it should be split up
                        val splittedOnSpaces = string.split(" ")

                        // Yay recursiveness!
                        val chunkedOnSpaces = chunkedLines(splittedOnSpaces.map { "$it " }, maxLength, true, false)
                        chunkedStringList.addAll(chunkedOnSpaces)
                    } else {
                        chunkedStringList.addAll(string.chunked(maxLength))
                    }
                    continue
                }
            }

            builder.append(string)
        }

        if (builder.isNotEmpty())
            chunkedStringList.add(builder.toString())

        return chunkedStringList
    }
}