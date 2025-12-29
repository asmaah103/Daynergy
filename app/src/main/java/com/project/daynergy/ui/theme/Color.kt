package com.project.daynergy.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

/* ---------- BRAND COLORS ---------- */

val Primary = Color(0xFF5B3EE4)
val OnPrimary = Color.White

/* ---------- LIGHT MODE COLORS ---------- */

val BackgroundLight = Color(0xFFF7F6FB)

val SecondaryContainerLight = Color(0xFFEDE9FF)
val OnSecondaryContainerLight = Color(0xFF1C1B1F)

/* Task Group Backgrounds (LIGHT) */
val OfficeBg = Color(0xFFFFE4F2)
val PersonalBg = Color(0xFFEDE4FF)
val StudyBg = Color(0xFFFFE6D4)

/* ---------- DARK MODE COLORS ---------- */

val BackgroundDark = Color(0xFF0F0F14)
val SurfaceDark = Color(0xFF16161E)
val SurfaceVariantDark = Color(0xFF1E1E2A)

val SecondaryContainerDark = Color(0xFF24243A)
val OnSecondaryContainerDark = Color(0xFFE6E6FF)

/* ---------- LIGHT COLOR SCHEME ---------- */

val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,

    background = BackgroundLight,
    onBackground = Color.Black,

    surface = Color.White,
    onSurface = Color.Black,

    surfaceVariant = Color(0xFFF3F2FF),
    onSurfaceVariant = Color(0xFF1C1B1F),

    secondaryContainer = SecondaryContainerLight,
    onSecondaryContainer = OnSecondaryContainerLight
)

/* ---------- DARK COLOR SCHEME (REAL DARK) ---------- */

val DarkColorScheme = darkColorScheme(
    primary = Primary,
    onPrimary = Color.White,

    background = BackgroundDark,
    onBackground = Color(0xFFEAEAF0),

    surface = SurfaceDark,
    onSurface = Color(0xFFEAEAF0),

    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = Color(0xFFD0D0E0),

    secondaryContainer = SecondaryContainerDark,
    onSecondaryContainer = OnSecondaryContainerDark
)

/* ---------- TASK GROUP ACCENT COLORS ---------- */

val OfficeAccent = Color(0xFF0087FF)
val PersonalAccent = Color(0xFFFF7A00)
val StudyAccent = Color(0xFF3A7AFE)
