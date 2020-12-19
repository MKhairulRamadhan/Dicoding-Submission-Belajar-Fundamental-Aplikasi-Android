package com.khairul.githubuser.activity

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.khairul.githubuser.R
import com.khairul.githubuser.broadcaset.AlarmReceiver
import kotlinx.android.synthetic.main.activity_setting_notification.*

class SettingNotificationActivity : AppCompatActivity() {

    companion object {
        const val PREFS = "Setting"
        private const val DAILY = "Daily"
    }

    private lateinit var alarmReceiver: AlarmReceiver
    private lateinit var mSharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_notification)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = getString(R.string.setting_string)

        alarmReceiver = AlarmReceiver()
        mSharedPreferences = getSharedPreferences(PREFS, Context.MODE_PRIVATE)

        reminder.isChecked = mSharedPreferences.getBoolean(DAILY, false)
        reminder.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                alarmReceiver.setRepeatingAlarm(
                    this,
                    AlarmReceiver.TYPE_REPETYPE,
                    getString(R.string.reminder_message)
                )
            } else {
                alarmReceiver.cancelAlarm(this)
            }
            saveChangeReminder(isChecked)
        }
    }

    private fun saveChangeReminder(value: Boolean) {
        val editor = mSharedPreferences.edit()
        editor.putBoolean(DAILY, value)
        editor.apply()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

}