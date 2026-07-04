package com.rewire.app.utils

import android.content.Context
import android.graphics.drawable.Drawable

data class ProtectedApp(
    val appName: String,
    val packageName: String,
    val icon: Drawable,
    var enabled: Boolean
)

object InstalledApps {

    private val supportedApps = mapOf(

        "com.instagram.android" to true,

        "com.google.android.youtube" to true,

        "com.snapchat.android" to true,

        "com.zhiliaoapp.musically" to true,

        "com.facebook.katana" to true,

        "com.reddit.frontpage" to true,

        "com.twitter.android" to true,

        "in.mohalla.sharechat" to true,

        "com.sharechat.moj" to true
    )

    fun getSupportedApps(
        context: Context
    ): MutableList<ProtectedApp> {

        val pm = context.packageManager

        val apps = mutableListOf<ProtectedApp>()

        supportedApps.forEach { (packageName, enabled) ->

            try {

                val info =
                    pm.getApplicationInfo(
                        packageName,
                        0
                    )

                apps.add(

                    ProtectedApp(

                        appName =
                            pm.getApplicationLabel(info)
                                .toString(),

                        packageName = packageName,

                        icon =
                            pm.getApplicationIcon(info),

                        enabled = enabled
                    )
                )

            } catch (_: Exception) {

            }
        }

        return apps.sortedBy {

            it.appName

        }.toMutableList()
    }
}