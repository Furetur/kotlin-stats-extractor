package statistic

fun String.splitTokenIntoWords(): String = splitToSubtokens(this).joinToString(" ")

/**
 * This function was stolen from https://github.com/JetBrains-Research/astminer/blob/master-dev/src/main/kotlin/astminer/common/TreeUtil.kt
 */
private fun normalizeToken(token: String, defaultToken: String): String {
    val cleanToken = token.toLowerCase()
        .replace("\\\\n".toRegex(), "") // escaped new line
        .replace("//s+".toRegex(), "") // whitespaces
        .replace("[\"',]".toRegex(), "") // quotes, apostrophies, commas
        .replace("\\P{Print}".toRegex(), "") // unicode weird characters

    val stripped = cleanToken.replace("[^A-Za-z]".toRegex(), "")

    return if (stripped.isEmpty()) {
        val carefulStripped = cleanToken.replace(" ", "_")
        if (carefulStripped.isEmpty()) {
            defaultToken
        } else {
            carefulStripped
        }
    } else {
        stripped
    }
}

/**
 * This function was stolen from https://github.com/JetBrains-Research/astminer/blob/master-dev/src/main/kotlin/astminer/common/TreeUtil.kt
 */
private fun splitToSubtokens(token: String) = token
    .trim()
    .split("(?<=[a-z])(?=[A-Z])|_|[0-9]|(?<=[A-Z])(?=[A-Z][a-z])|\\s+".toRegex())
    .map { s -> normalizeToken(s, "") }
    .filter { it.isNotEmpty() }
    .toList()