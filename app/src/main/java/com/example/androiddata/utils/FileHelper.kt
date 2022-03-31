package com.example.androiddata.utils

import android.app.Application
import android.content.Context
import java.io.File
import java.nio.charset.Charset

class FileHelper {
    companion object {
        fun getTextFromResource(context: Context, resourceId: Int): String {
            return context.resources.openRawResource(resourceId).use {
                it.bufferedReader().use { br ->
                    br.readText()
                }
            }
        }

        fun saveTextToFile(app: Application, text: String?){
            val file = File(app.cacheDir, "monsters.json")
            file.writeText(text ?: "", Charset.defaultCharset())
        }

        fun readTextFile(app: Application): String? {
            File(app.cacheDir, "monsters.json").also {
                return if(it.exists()){
                    return it.readText()
                } else null
            }
        }
    }
}