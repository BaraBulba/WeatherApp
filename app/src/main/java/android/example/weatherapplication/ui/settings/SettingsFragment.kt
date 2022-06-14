package android.example.weatherapplication.ui.settings

import android.example.weatherapplication.R
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.setFragmentResult
import androidx.preference.PreferenceFragmentCompat
import com.google.android.gms.location.LocationServices
import okio.Options


class SettingsFragment : PreferenceFragmentCompat() {


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preference)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = "Настройки"
        (activity as AppCompatActivity).supportActionBar?.subtitle = null
        (activity as AppCompatActivity).supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.rgb(123,31,162)))
    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        (activity as AppCompatActivity).supportActionBar?.title = "Настройки"
//        (activity as AppCompatActivity).supportActionBar?.subtitle = null
//        (activity as AppCompatActivity).supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.BLUE))
//    }

//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        (activity as AppCompatActivity).supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        var view = inflater.inflate(R.layout.fragment_settings, container, false)
//        return view
//    }
}