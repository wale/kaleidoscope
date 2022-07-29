package au.id.wale.kaleidoscope.token

data class Token(
    var type: TokenType,
    var identStr: String? = null,
    var numValue: Double? = null,
    var unknownChar: Char? = null
)