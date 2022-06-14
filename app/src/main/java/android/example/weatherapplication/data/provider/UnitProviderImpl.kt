package android.example.weatherapplication.data.provider

import android.content.Context
import android.content.SharedPreferences
import android.example.weatherapplication.internal.UnitSystem
import androidx.preference.PreferenceManager

const val UNIT_SYSTEM = "UNIT_SYSTEM"

class UnitProviderImpl(context: Context): PreferenceProvider(context), UnitProvider {

    override fun getUnitSystem(): UnitSystem {
        val selectedName = preference.getString(UNIT_SYSTEM, UnitSystem.METRIC.name)
        return UnitSystem.valueOf(selectedName!!)
    }
}