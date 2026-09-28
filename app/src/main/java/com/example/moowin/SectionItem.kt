package com.example.moowin

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

data class SectionItem(
    val id: String,
    val name: String,
    val icon: ImageVector? = null,
    val content: @Composable () -> Unit
)
