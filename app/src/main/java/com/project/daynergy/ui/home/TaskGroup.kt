package com.project.daynergy.ui.home

import androidx.compose.ui.graphics.Color
import com.project.daynergy.R
import com.project.daynergy.ui.theme.*
enum class TaskGroup(
    val title: String,
    val iconRes: Int,
    val iconBgColor: Color,
    val accentColor: Color
) {

    OFFICE(
        title = "Office Project",
        iconRes = R.drawable.ic_office_project,
        iconBgColor = OfficeBg,
        accentColor = OfficeAccent
    ),

    PERSONAL(
        title = "Personal Project",
        iconRes = R.drawable.ic_personal_project,
        iconBgColor = PersonalBg,
        accentColor = PersonalAccent
    ),

    STUDY(
        title = "Daily Study",
        iconRes = R.drawable.ic_daily_study,
        iconBgColor = StudyBg,
        accentColor = StudyAccent
    );

    companion object {
        fun fromTitle(title: String): TaskGroup {
            return values().firstOrNull { it.title == title } ?: OFFICE
        }
    }
}
