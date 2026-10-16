# ExclaveHX

ExclaveHX is a high-performance, multi-protocol proxy client for Android, based on Exclave with customized protocol enhancements, independent packaging (`com.github.dyhkwonghx.sagernet`), and automatic release signing.

ExclaveHX 是基于 Exclave 深度定制的高性能多协议 Android 代理客户端。支持独立包名（`com.github.dyhkwonghx.sagernet`）以确保与官方原版共存，提供特色协议扩展与全架构自动化签名。

---

## 🌟 Key Features / 核心特性

- **Enhanced VLESS Protocol Suite / 增强型 VLESS 协议体系**:
  - Full compatibility with VLESS + REALITY + XTLS-Vision (`flow=xtls-rprx-vision`, `security=reality`).
  - Native support for handshake authentication signature and dynamic session token updates (`e` & `h` parameter negotiation).
  - Modern TLS handshake stabilization: automatic suppression of incompatible post-quantum cipher suites (`disableX25519MLKEM768=true`) to eliminate uTLS Chrome disconnects on REALITY endpoints.
  - Strict URI lossless fidelity: standard 32-character unhyphenated UUID preservation and canonical query ordering across import/export.
  - 完整支持 VLESS + REALITY + XTLS-Vision 传输。
  - 原生支持握手认证签名校验与动态 Token 响应回写（`e` 与 `h` 参数协商更新）。
  - TLS 握手稳定性加固：自适应规避后量子密码扩展（`disableX25519MLKEM768`），彻底消除 REALITY 握手断连。
  - 双向无损导入导出：完整保持 32 位原生 UUID 格式与标准规范化参数排序。

- **Multi-Protocol Integration / 多协议全量集成**:
  - **Shadowsocks (2022 AEAD & Legacy UOT)**: Clean TCP, UDP-over-TCP, and password/PSK validation.
  - **XHTTP / SplitHTTP**: Adaptive multiplexing stream transport and native UDP support.
  - **OneSocks Suite**: High-efficiency proxy protocol with native UDP and UOT v2 support.
  - Standard Protocols: Shadowsocks, Trojan, Hysteria 2, AnyTLS, mieru, TUIC, Juicity, VMess, WireGuard, TrustTunnel, Snell, SOCKS5.
  - **Shadowsocks (2022 AEAD 与 Legacy UOT)**：标准 TCP/UDP 传输与密码规范化校验。
  - **XHTTP / SplitHTTP**：自适应流传输分流及原生 UDP 支持。
  - **OneSocks 协议体系**：高效代理协议套件，支持原生 UDP 与 UOT v2。
  - 原版全协议兼容：Shadowsocks, Trojan, Hysteria 2, AnyTLS, mieru, TUIC, Juicity, VMess, WireGuard, TrustTunnel, Snell, SOCKS5 等。

- **Independent Application ID / 独立包名共存**:
  - Application ID: `com.github.dyhkwonghx.sagernet`
  - Display Name: `ExclaveHX`
  - Can be installed alongside official Exclave without data conflict or overwriting.
  - 独立包名，可与官方原版同时安装在同一设备上，配置数据完全隔离，互不冲突。

---

## 📱 Download & Architecture Guide / 下载与架构指南

Download the latest signed release APK from [GitHub Releases](https://github.com/ABOLUOmathh/Exclave/releases).

前往 [GitHub Releases](https://github.com/ABOLUOmathh/Exclave/releases) 下载最新签名发布包。

| File Pattern / 文件名称 | Architecture / 适用架构 | Target Devices / 适用设备 |
| :--- | :--- | :--- |
| `ExclaveHX-*-arm64-v8a-*.apk` | 64-bit ARM (Recommended / 推荐) | Modern Android phones & tablets (Android 7.0+) / 绝大多数现代安卓手机与平板 |
| `ExclaveHX-*-armeabi-v7a-*.apk` | 32-bit ARM | Older phones, TV boxes, or IoT devices / 老旧手机、机顶盒或车载设备 |
| `ExclaveHX-*-x86_64-*.apk` | 64-bit x86 | Mainstream PC Android emulators (MuMu, LDPlayer, BlueStacks 64-bit) / PC 安卓模拟器 |
| `ExclaveHX-*-x86-*.apk` | 32-bit x86 | 32-bit emulators or Intel-based legacy tablets / 32 位模拟器或平板 |
| `ExclaveHX-*-legacy-*.apk` | Universal Legacy (Android 5.0 - 6.0) | Devices running legacy Android systems / 极老旧设备（Android 5.0+） |

---

## 🛠️ Build from source / 源码编译

- Prerequisites: JDK 21, Go 1.27+, Android SDK Platform 37.2, NDK r30.
- Linux / macOS:
  - Build core: `./library/core/build.sh`
  - Download assets: `./gradlew :app:downloadAssets`
  - Build ExclaveHX: `./gradlew :app:assembleOssRelease`
- Windows (x64):
  - Build core: `.\library\core\build.bat`
  - Download assets: `.\gradlew.bat :app:downloadAssets`
  - Build ExclaveHX: `.\gradlew.bat :app:assembleOssRelease`
- Output artifacts are generated in `app/build/outputs/apk/oss/release/`.

---

## 📄 License & Acknowledgments / 许可证与鸣谢

- Licensed under [GNU General Public License v3.0](LICENSE).
- Upstream project: [Exclave](https://github.com/ExclaveNetwork/Exclave) by dyhkwong.
- Based on [SagerNet](https://github.com/SagerNet/SagerNet) and [Shadowsocks Android](https://github.com/shadowsocks/shadowsocks-android).
