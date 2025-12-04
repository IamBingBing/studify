package com.example.studify.ui

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// -------- 1) 색 정의 --------
private val md_theme_light_primary = Color(0xFF2B3B69) //버튼색
private val md_theme_light_onPrimary = Color(0xFFFBFCF3) //버튼글씨
private val md_theme_light_background = Color(0xFFF4F6FC) //배경컬러
private val md_theme_light_onBackground = Color(0xFF414848) //글씨컬러
private val md_theme_light_surface = Color(0xFFF4F6FC)   // 상단 카테고리 이름
private val md_theme_light_onSurface = Color(0xFF414848)       // 거기 위 글씨
private val md_theme_light_surfaceVariant = Color(0xFFE0F2EF)  // 살짝 민트 섞인 연한 배경
private val md_theme_light_onSurfaceVariant = Color(0xFF2B2C3A)// 네비게이션 글씨, 아이디 패스워드 글씨
private val md_theme_light_outline = Color(0xFF434A6E)         // 테두리(인디케이터) 민트 계열
private val md_theme_dark_primary = Color(0xFF9EC4BE)
private val md_theme_dark_onPrimary = Color(0xFF00391C)
private val md_theme_dark_background = Color(0xFF1A1C19)
private val md_theme_dark_onBackground = Color(0xFFE2E3DD)
private val md_theme_light_nav = Color(0xFFF0F1F8) //하단네비
private val md_theme_top_nav = Color(0xFFF7F7FD) //상단네비,그룹리스트 목록
private val md_theme_light_textField = Color(0xFFE0E8F5) // 아주 연한 민트


// -------- 2) ColorScheme --------
private val LightColorScheme = lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    background = md_theme_light_background,
    onBackground = md_theme_light_onBackground,

    surface = md_theme_light_surface,
    onSurface = md_theme_light_onSurface,

    surfaceVariant = md_theme_light_surfaceVariant,
    onSurfaceVariant = md_theme_light_onSurfaceVariant,

    outline = md_theme_light_outline,
    tertiaryContainer = md_theme_light_nav,
    primaryContainer = md_theme_top_nav,
    secondaryContainer = md_theme_light_textField,
    onSecondaryContainer = Color(0xFF343636) // 텍스트필드 진한색
)

private val DarkColorScheme = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    background = md_theme_dark_background,
    onBackground = md_theme_dark_onBackground,
)

// -------- 3) Typography / Shapes (일단 기본값) --------
private val AppTypography = Typography()
private val AppShapes = Shapes()

// -------- 4) 최상위 테마 컴포저블 --------
@Composable
fun AppTheme(
    useDarkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme =
        if (useDarkTheme) DarkColorScheme
        else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        shapes = AppShapes,
        content = content
    )
}

