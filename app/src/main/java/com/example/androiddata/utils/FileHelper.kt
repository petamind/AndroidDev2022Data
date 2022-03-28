package com.example.androiddata.utils

import android.content.Context

class FileHelper {
    companion object {
        fun getTextFromResource(context: Context, resourceId: Int): String {
            return context.resources.openRawResource(resourceId).use {
                it.bufferedReader().use { br ->
                    br.readText()
                }
            }
        }
    }
}