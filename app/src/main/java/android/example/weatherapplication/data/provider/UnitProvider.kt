package android.example.weatherapplication.data.provider

import android.example.weatherapplication.internal.UnitSystem

interface UnitProvider {
    fun getUnitSystem(): UnitSystem
}