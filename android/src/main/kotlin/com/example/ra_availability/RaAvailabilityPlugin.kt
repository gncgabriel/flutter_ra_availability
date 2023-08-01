package com.example.ra_availability

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.ar.core.ArCoreApk
import com.google.ar.core.ArCoreApk.Availability
import com.google.ar.core.ArCoreApk.InstallStatus
import com.google.ar.core.exceptions.UnavailableUserDeclinedInstallationException
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

/** RaAvailabilityPlugin */
class RaAvailabilityPlugin : FlutterPlugin, MethodCallHandler, ActivityAware {
    // / The MethodChannel that will the communication between Flutter and native Android
    // /
    // / This local reference serves to register the plugin with the Flutter Engine and unregister it
    // / when the Flutter Engine is detached from the Activity
    private lateinit var channel: MethodChannel

    private lateinit var context: Context
    private var activity: Activity? = null
    private val mUserRequestedInstall = true

    override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "ra_availability")
        channel.setMethodCallHandler(this)
        context = flutterPluginBinding.applicationContext
    }

    override fun onMethodCall(call: MethodCall, result: Result) {
        if (call.method.equals("isSupported")) {
            result.success(isSupported())
        } else {
            result.notImplemented()
        }
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }

    private fun isSupported(): Boolean {
        val arCore: ArCoreApk = ArCoreApk.getInstance()
        return when (arCore.checkAvailability(context)) {
            Availability.SUPPORTED_INSTALLED -> true
            Availability.SUPPORTED_APK_TOO_OLD -> handleInstallRequestAndReturnAvailability(arCore)
            Availability.SUPPORTED_NOT_INSTALLED -> handleInstallRequestAndReturnAvailability(arCore)
            else -> false
        }
    }

    private fun handleInstallRequestAndReturnAvailability(arCore: ArCoreApk): Boolean {
        try {
            return when (
                arCore.requestInstall(
                    activity,
                    mUserRequestedInstall,
                    ArCoreApk.InstallBehavior.REQUIRED,
                    ArCoreApk.UserMessageType.FEATURE,
                )
            ) {
                InstallStatus.INSTALLED -> true
                else -> false
            }
        } catch (e: UnavailableUserDeclinedInstallationException) {
            Log.e("User decline install", "The user declined the install.")
            return false
        } catch (e: Exception) { // Current catch statements.
            Log.e("Unknown exception", e.message.toString())
            return false
        }
    }

    override fun onAttachedToActivity(activityPluginBinding: ActivityPluginBinding) {
        activity = activityPluginBinding.activity
    }

    override fun onDetachedFromActivityForConfigChanges() {
    }

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
    }

    override fun onDetachedFromActivity() {
    }
}
