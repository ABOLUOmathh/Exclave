/******************************************************************************
 *                                                                            *
 * Copyright (C) 2023 by Matsuri authors                                      *
 *                                                                            *
 * This program is free software: you can redistribute it and/or modify       *
 * it under the terms of the GNU General Public License as published by       *
 * the Free Software Foundation, either version 3 of the License, or          *
 *  (at your option) any later version.                                       *
 *                                                                            *
 * This program is distributed in the hope that it will be useful,            *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of             *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the              *
 * GNU General Public License for more details.                               *
 *                                                                            *
 * You should have received a copy of the GNU General Public License          *
 * along with this program. If not, see <http://www.gnu.org/licenses/>.       *
 *                                                                            *
 ******************************************************************************/

package io.nekohasekai.sagernet.group

import io.nekohasekai.sagernet.fmt.AbstractBean
import io.nekohasekai.sagernet.fmt.heysocksxhttp.HeySocksXHTTPBean
import io.nekohasekai.sagernet.fmt.onesocks.OneSocksBean
import io.nekohasekai.sagernet.fmt.shadowsocks.ShadowsocksBean
import io.nekohasekai.sagernet.fmt.shadowsocks.isBlackstone2022

object Protocols {

    class Deduplication(
        val bean: AbstractBean, val type: String
    ) {

        fun hash(): String {
            val fields: List<Any?> = when (bean) {
                is ShadowsocksBean -> {
                    // Preserve legacy deduplication for every non-BLACKSTONE SS node.
                    if (!bean.isBlackstone2022()) return bean.serverAddress + bean.serverPort + type
                    listOf(type, bean.serverAddress, bean.serverPort,
                        bean.method, bean.password, bean.plugin, bean.singUoT)
                }
                is HeySocksXHTTPBean -> listOf(
                    type, bean.serverAddress, bean.serverPort,
                    bean.method, bean.password,
                    bean.fakeTCP, bean.fakeUDP,
                    bean.udp, bean.uot, bean.uotVersion,
                    bean.paddingLen, bean.e, bean.linkVersion
                )
                is OneSocksBean -> listOf(
                    type, bean.serverAddress, bean.serverPort,
                    bean.method, bean.password, bean.paddingLen,
                    bean.udp, bean.uot, bean.version
                )
                else -> return bean.serverAddress + bean.serverPort + type
            }

            return fields.joinToString(separator = "", prefix = "config:") { value ->
                val text = value?.toString()
                if (text == null) "-1:" else "${text.length}:$text"
            }
        }

        override fun hashCode(): Int {
            return hash().toByteArray().contentHashCode()
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Deduplication

            return hash() == other.hash()
        }

    }

}