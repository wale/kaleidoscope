package au.id.wale.kaleidoscope.lexer

import au.id.wale.kaleidoscope.token.Token
import au.id.wale.kaleidoscope.token.TokenType

import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader


class Lexer(file: File) {
    private var buffer: BufferedReader
    private var lastChar = ' '

    private val EOF: Char = (-1).toChar()

    init {
        val stream = FileInputStream(file);
        val reader = InputStreamReader(stream);
        buffer = BufferedReader(reader)
    }

    fun getToken(): Token {
        var token: Token

        // Skip any whitespace
        while (Character.isWhitespace(lastChar)) {
            lastChar = buffer.read().toChar();
        }

        // Identifiers: [a-zA-Z][a-zA-Z0-9_]*
        if(Character.isLetter(lastChar)) {
            var identStr = ""
            do {
                identStr += lastChar
                lastChar = buffer.read().toChar()
            } while (Character.isLetterOrDigit(lastChar) || lastChar == '_')

            when {
                identStr == "def" -> {
                    token = Token(TokenType.DEF)
                    return token;
                }
                identStr == "extern" -> {
                    token = Token(TokenType.EXTERN)
                    return token
                }
                else -> {
                    token = Token(TokenType.IDENTIFIER)
                    token.identStr = identStr
                    return token
                }
            }
        }

        // Numbers: [0-9.]+
        if(Character.isDigit(lastChar) || lastChar == '.') {
            var numStr = ""
            do {
                numStr += lastChar
                lastChar = buffer.read().toChar()
            } while (Character.isDigit(lastChar) || lastChar == '.')

            token = Token(TokenType.NUMBER)
            token.numValue = numStr.toDouble()
            return token
        }

        // Comments, ignore tokens
        if(lastChar == '#') {
            do {
                lastChar = buffer.read().toChar()
            } while (lastChar != EOF && lastChar != '\n' && lastChar != '\r')

            if (lastChar != EOF) return getToken()
        }

        // End of file character
        if(lastChar == EOF) {
            token = Token(TokenType.EOF)
            return token
        }

        // Unknown characters
        token = Token(TokenType.UNKNOWN)
        token.unknownChar = lastChar
        lastChar = buffer.read().toChar()
        return token
    }
}