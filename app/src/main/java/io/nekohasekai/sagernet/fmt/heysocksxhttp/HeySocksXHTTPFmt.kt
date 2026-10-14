package io.nekohasekai.sagernet.fmt.heysocksxhttp

import java.net.URI
import java.net.URLDecoder
import java.net.URLEncoder

private fun queryValue(rawQuery: String?, key: String): String? = rawQuery
    ?.split('&')
    ?.firstNotNullOfOrNull { parameter ->
        val parts = parameter.split('=', limit = 2)
        if (URLDecoder.decode(parts[0], "UTF-8") == key) {
            URLDecoder.decode(parts.getOrElse(1) { "" }, "UTF-8")
        } else null
    }

private fun encode(value: String): String =
    URLEncoder.encode(value, "UTF-8").replace("+", "%20")

private fun parseFlag(value: String?): Boolean =
    value == "1" || value.equals("true", ignoreCase = true)

/** Parse custom xhttp:// URIs without emitting any credentials to logs. */
fun parseHeySocksXHTTP(url: String): HeySocksXHTTPBean {
    val uri = URI(url.trim())
    require(uri.scheme.equals("xhttp", ignoreCase = true)) { "Not a HeySocks XHTTP URI" }
    require(!uri.host.isNullOrEmpty() && uri.port in 1..65535) { "Invalid HeySocks XHTTP server" }
    // URI.userInfo decodes %3A. Preserve the complete decoded user-info as
    // the protocol password; do NOT split it into username and password.
    val fullPassword = uri.userInfo.orEmpty()
    require(fullPassword.isNotEmpty()) { "Missing HeySocks XHTTP password" }
    val q = uri.rawQuery
    val h = queryValue(q, "h")
    require(h.isNullOrEmpty()) { "Unsupported HeySocks XHTTP h parameter" }
    val uotVersion = queryValue(q, "uot-version")?.toIntOrNull() ?: 0
    require(uotVersion in 0..2) { "Unsupported HeySocks XHTTP UOT version" }
    val v = queryValue(q, "v")?.toIntOrNull() ?: 1
    require(v == 1) { "Unsupported HeySocks XHTTP link version: $v" }

    return HeySocksXHTTPBean().apply {
        serverAddress = uri.host
        serverPort = uri.port
        password = fullPassword
        method = queryValue(q, "cipher") ?: "aes-128-ctr"
        fakeTCP = queryValue(q, "fake-tcp").orEmpty()
        fakeUDP = queryValue(q, "fake-udp").orEmpty()
        paddingLen = queryValue(q, "padding").orEmpty()
        udp = parseFlag(queryValue(q, "udp"))
        uot = parseFlag(queryValue(q, "uot"))
        this.uotVersion = uotVersion
        e = parseFlag(queryValue(q, "e"))
        linkVersion = v
        name = uri.fragment.orEmpty()
        initializeDefaultValues()
    }
}

fun HeySocksXHTTPBean.toUri(): String {
    // Bracket IPv6 literals; do not leak or log credentials.
    val host = if (serverAddress.contains(':') && !serverAddress.startsWith("["))
        "[$serverAddress]" else serverAddress
    val parameters = listOf(
        "v=${linkVersion}",
        "cipher=${encode(method)}",
        "udp=${if (udp) 1 else 0}",
        "uot=${if (uot) 1 else 0}",
        "uot-version=$uotVersion",
        "padding=${encode(paddingLen)}",
        "e=${if (e) 1 else 0}",
        "fake-tcp=${encode(fakeTCP)}",
        "fake-udp=${encode(fakeUDP)}"
    )
    return buildString {
        append("xhttp://")
        append(encode(password))
        append('@')
        append(host)
        append(':')
        append(serverPort)
        append('?')
        append(parameters.joinToString("&"))
        if (name.isNotEmpty()) {
            append('#')
            append(encode(name))
        }
    }
}
