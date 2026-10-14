package io.nekohasekai.sagernet.fmt.onesocks

import java.net.URI
import java.net.URLDecoder
import java.net.URLEncoder


private fun encode(value: String): String =
    URLEncoder.encode(value, "UTF-8")
        .replace("+", "%20")


private fun queryValue(
    query: String?,
    key: String
): String? {

    return query
        ?.split("&")
        ?.firstNotNullOfOrNull {

            val parts = it.split("=", limit = 2)

            val name =
                URLDecoder.decode(
                    parts[0],
                    "UTF-8"
                )

            if (name == key) {

                URLDecoder.decode(
                    parts.getOrElse(1) { "" },
                    "UTF-8"
                )

            } else null
        }
}


private fun parseFlag(value:String?):Boolean {

    return value=="1" ||
            value.equals(
                "true",
                ignoreCase = true
            )

}



fun parseOneSocks(url:String):OneSocksBean {


    val uri =
        URI(
            url.trim()
        )


    require(
        uri.scheme.equals(
            "os",
            ignoreCase = true
        )
    ){
        "Not a OneSocks URI"
    }


    require(
        !uri.host.isNullOrEmpty()
                &&
        uri.port in 1..65535
    ){
        "Invalid OneSocks server"
    }



    /*
     * 保留完整 userInfo
     *
     * 兼容:
     * os://password@host:port
     *
     * 不拆 username/password
     */
    val password =
        uri.userInfo.orEmpty()



    require(
        password.isNotEmpty()
    ){
        "Missing OneSocks password"
    }



    val query =
        uri.rawQuery



    val version =
        queryValue(
            query,
            "v"
        )
        ?.toIntOrNull()
        ?:1



    require(
        version in 1..2
    ){
        "Unsupported OneSocks version"
    }



    return OneSocksBean()
        .apply {


            serverAddress =
                uri.host


            serverPort =
                uri.port



            method =
                "aes-128-ctr"



            this.password =
                password



            paddingLen =
                queryValue(
                    query,
                    "padding"
                )
                ?: ""



            udp =
                parseFlag(
                    queryValue(
                        query,
                        "udp"
                    )
                )



            uot =
                parseFlag(
                    queryValue(
                        query,
                        "uot"
                    )
                )



            this.version =
                version



            name =
                uri.fragment
                    ?: ""



            initializeDefaultValues()

        }

}




fun OneSocksBean.toUri():String {


    val host =
        if(
            serverAddress.contains(":")
            &&
            !serverAddress.startsWith("[")
        ){

            "[$serverAddress]"

        }else{

            serverAddress

        }



    val query =
        ArrayList<String>()



    query.add(
        "v=${version}"
    )


    if(udp){

        query.add(
            "udp=1"
        )

    }


    if(uot){

        query.add(
            "uot=1"
        )

    }



    if(
        paddingLen.isNotEmpty()
    ){

        query.add(
            "padding=${encode(paddingLen)}"
        )

    }



    /*
     * 保留原协议行为
     *
     * OneSocks 默认携带 e=1
     */
    query.add(
        "e=1"
    )



    return buildString {


        append(
            "os://"
        )


        append(
            encode(password)
        )


        append(
            "@"
        )


        append(
            host
        )


        append(
            ":"
        )


        append(
            serverPort
        )



        if(query.isNotEmpty()){


            append(
                "?"
            )


            append(
                query.joinToString("&")
            )

        }



        if(
            name.isNotEmpty()
        ){

            append("#")


            append(
                encode(name)
            )

        }


    }

}
