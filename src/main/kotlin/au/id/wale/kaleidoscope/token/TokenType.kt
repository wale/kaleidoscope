package au.id.wale.kaleidoscope.token

enum class TokenType {
    // End-of-line
    EOF,
    // Keywords
    DEF,
    EXTERN,
    // Syntax
    IDENTIFIER,
    NUMBER,
    // Unknown chars
    UNKNOWN
}