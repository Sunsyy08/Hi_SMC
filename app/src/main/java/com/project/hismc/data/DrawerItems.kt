package com.project.hismc.data

import androidx.compose.ui.graphics.vector.ImageVector
import okhttp3.Route

data class DrawerItems(
    val icon : ImageVector,
    val text : String,
    val badgeCount : Int,
    val hasBadge : Boolean,
    val route : String
)
