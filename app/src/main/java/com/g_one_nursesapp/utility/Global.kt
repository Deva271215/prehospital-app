package com.g_one_nursesapp.utility

import android.app.Application
import com.g_one_nursesapp.entity.SymtompEntity

class Global: Application() {
    companion object {
        @JvmField
        var symtomps = SymtompEntity()
    }
}