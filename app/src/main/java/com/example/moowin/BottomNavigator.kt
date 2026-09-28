package com.example.moowin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun BottomNavigator(
    sections: List<SectionItem>,
    modifier: Modifier = Modifier
) {
    var selectedSectionId by remember { mutableStateOf(sections.firstOrNull()?.id ?: "") }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                sections.forEach { section ->
                    NavigationBarItem(
                        selected = section.id == selectedSectionId,
                        onClick = { selectedSectionId = section.id },
                        icon = {
                            if (section.icon != null) {
                                Icon(imageVector = section.icon, contentDescription = section.name)
                            }
                        },
                        label = { Text(text = section.name) }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            val currentSection = sections.find { it.id == selectedSectionId }
            currentSection?.content?.invoke()
        }
    }
}
