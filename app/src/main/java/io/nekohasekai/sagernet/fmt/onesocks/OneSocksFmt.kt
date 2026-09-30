package io.nekohasekai.sagernet.fmt.onesocks

import libexclavecore.Libexclavecore
import io.nekohasekai.sagernet.ktx.queryParameter


fun parseOneSocks(url:String):OneSocksBean{

    val link=Libexclavecore.parseURL(url)


    android.util.Log.e(
        "OneSocks",
        "url=$url host=${link.getHost()} port=${link.getPort()} user=${link.getUsername()}"
    )


    return OneSocksBean().apply {


        serverAddress = link.host

        serverPort = link.port


        method = "aes-128-ctr"


        password =
            link.username
                ?: ""


        paddingLen =
            link.queryParameter("padding")
                ?: ""

        udp =
            link.queryParameter("udp") == "1"


        uot =
            link.queryParameter("uot") == "1"


        version =
            link.queryParameter("v")
                ?.toIntOrNull()
                ?: 1


        name =
            link.fragment

        android.util.Log.e(
            "OneSocks",
            "Bean address=$serverAddress port=$serverPort password=$password"
        )


    }

}



fun OneSocksBean.toUri(): String {

    val query = ArrayList<String>()

    query.add(
        "v=${version}"
    )

    if (udp) {
        query.add("udp=1")
    }

    if (uot) {
        query.add("uot=1")
    }

    if (paddingLen.isNotEmpty()) {
        query.add(
            "padding=${paddingLen}"
        )
    }

    query.add("e=1")


    return buildString {

        append("os://")

        append(password)

        append("@")

        append(serverAddress)

        append(":")

        append(serverPort)

        append("?")

        append(query.joinToString("&"))

        if(name.isNotEmpty()){

            append("#")

            append(
                java.net.URLEncoder.encode(
                    name,
                    "UTF-8"
                )
            )

        }
    }
}