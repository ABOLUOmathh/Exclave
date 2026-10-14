
package io.nekohasekai.sagernet.ui.profile

import android.os.Bundle
import androidx.preference.EditTextPreference
import androidx.preference.PreferenceFragmentCompat
import io.nekohasekai.sagernet.Key
import io.nekohasekai.sagernet.R
import io.nekohasekai.sagernet.database.DataStore
import io.nekohasekai.sagernet.database.preference.EditTextPreferenceModifiers
import io.nekohasekai.sagernet.fmt.onesocks.OneSocksBean

class OneSocksSettingsActivity :
    ProfileSettingsActivity<OneSocksBean>() {

    override fun createEntity(): OneSocksBean {
        return OneSocksBean()
    }

    override fun OneSocksBean.init() {
        DataStore.profileName = name
        DataStore.serverAddress = serverAddress
        DataStore.serverPort = serverPort
        DataStore.serverPassword = password
        DataStore.serverMethod = method
        DataStore.paddingLen = paddingLen

        // OneSocks UDP / UOT
        DataStore.serverOneSocksUdp = udp
        DataStore.serverOneSocksUot = uot
    }

    override fun OneSocksBean.serialize() {
        name = DataStore.profileName
        serverAddress = DataStore.serverAddress
        serverPort = DataStore.serverPort
        password = DataStore.serverPassword
        method = DataStore.serverMethod
        paddingLen = DataStore.paddingLen

        // OneSocks UDP / UOT
        udp = DataStore.serverOneSocksUdp
        uot = DataStore.serverOneSocksUot
    }

    override fun PreferenceFragmentCompat.createPreferences(
        savedInstanceState: Bundle?,
        rootKey: String?
    ) {
        addPreferencesFromResource(
            R.xml.onesocks_preferences
        )

        findPreference<EditTextPreference>(
            Key.SERVER_PORT
        )!!.apply {
            setOnBindEditTextListener(
                EditTextPreferenceModifiers.Port
            )
        }

        findPreference<EditTextPreference>(
            Key.SERVER_PASSWORD
        )!!.apply {
            summaryProvider = PasswordSummaryProvider
        }
    }
}
