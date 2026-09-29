package io.nekohasekai.sagernet.fmt.onesocks

import libexclavecore.Libexclavecore
import io.nekohasekai.sagernet.ktx.queryParameter


fun parseOneSocks(url: String): OneSocksBean {

    val link = Libexclavecore.parseURL(url)

    return OneSocksBean().apply {

        serverAddress = link.host

        serverPort = when {
            !link.hasPort() -> error("invalid port")
            else -> link.port
        }


        method =
            link.username
                ?: "aes-128-ctr"


        password =
            link.password
                ?: ""


        paddingLen =
            link.queryParameter("padding")
                ?: ""


        name =
            link.fragment

    }
}



fun OneSocksBean.toUri(): String {

    val builder =
        Libexclavecore.newURL("onesocks")


    builder.setHostPort(
        serverAddress,
        serverPort
    )


    builder.username = method


    if (password.isNotEmpty()) {
        builder.password = password
    }


    if (paddingLen.isNotEmpty()) {

        builder.addQueryParameter(
            "padding",
            paddingLen
        )

    }


    if (name.isNotEmpty()) {
        builder.fragment = name
    }


    return builder.string
}
