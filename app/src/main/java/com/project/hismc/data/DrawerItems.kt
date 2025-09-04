package com.project.hismc.data

import androidx.compose.ui.graphics.vector.ImageVector

data class DrawerItems(
    val icon : ImageVector,
    val text : String,
    val badgeCount : Int,
    val hasBadge : Boolean
)
