package com.example.moowin.Home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import com.example.moowin.SectionItem

object HomeSectionBuilder {
    fun build(): SectionItem {
        return SectionItem(
            id = "home",
            name = "Home",
            icon = Icons.Default.Home,
            content = {
                HomeScreen()
            }
        )
    }
}
