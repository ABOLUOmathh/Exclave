package io.nekohasekai.sagernet.ui.profile

import android.os.Bundle
import android.text.InputType
import androidx.preference.EditTextPreference
import androidx.preference.PreferenceFragmentCompat
import io.nekohasekai.sagernet.Key
import io.nekohasekai.sagernet.R
import io.nekohasekai.sagernet.database.DataStore
import io.nekohasekai.sagernet.database.preference.EditTextPreferenceModifiers
import io.nekohasekai.sagernet.fmt.heysocksxhttp.HeySocksXHTTPBean

class HeySocksXHTTPSettingsActivity : ProfileSettingsActivity<HeySocksXHTTPBean>() {
    override fun createEntity(): HeySocksXHTTPBean = HeySocksXHTTPBean()

    override fun HeySocksXHTTPBean.init() {
        DataStore.profileName = name
        DataStore.serverAddress = serverAddress
        DataStore.serverPort = serverPort
        DataStore.serverPassword = password
        DataStore.serverMethod = method
        DataStore.paddingLen = paddingLen
        DataStore.xhttpFakeTCP = fakeTCP
        DataStore.xhttpFakeUDP = fakeUDP
        DataStore.xhttpUdp = udp
        DataStore.xhttpUot = uot
        DataStore.xhttpUotVersion = uotVersion
        DataStore.xhttpE = e
    }

    override fun HeySocksXHTTPBean.serialize() {
        name = DataStore.profileName
        serverAddress = DataStore.serverAddress
        serverPort = DataStore.serverPort
        password = DataStore.serverPassword
        method = DataStore.serverMethod
        paddingLen = DataStore.paddingLen
        fakeTCP = DataStore.xhttpFakeTCP
        fakeUDP = DataStore.xhttpFakeUDP
        udp = DataStore.xhttpUdp
        uot = DataStore.xhttpUot
        uotVersion = DataStore.xhttpUotVersion
        e = DataStore.xhttpE
    }

    override fun PreferenceFragmentCompat.createPreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.heysocks_xhttp_preferences)
        findPreference<EditTextPreference>(Key.SERVER_PORT)?.apply {
            setOnBindEditTextListener(EditTextPreferenceModifiers.Port)
        }
        findPreference<EditTextPreference>(Key.SERVER_PASSWORD)?.apply {
            summaryProvider = PasswordSummaryProvider
        }
        findPreference<EditTextPreference>(Key.XHTTP_UOT_VERSION)?.apply {
            setOnBindEditTextListener { field -> field.inputType = InputType.TYPE_CLASS_NUMBER }
        }
    }
}
