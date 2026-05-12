// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.13.2" apply false
    id("org.jetbrains.kotlin.android") version "2.0.21" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.21" apply false
    id("org.sonarqube") version "7.2.3.7755"
}

sonar {
    properties {
        property("sonar.projectKey", "OddzMint-Net_ActionPilotAI")
        property("sonar.organization", "oddzmint-net")
        property("sonar.host.url", "https://sonarcloud.io")
        property(
            "sonar.coverage.jacoco.xmlReportPaths",
            "app/build/reports/jacoco/jacocoTestReport/jacocoTestReport.xml"
        )
        property(
            "sonar.androidLint.reportPaths",
            "app/build/reports/lint-results-debug.xml"
        )
        property(
            "sonar.coverage.exclusions",
            listOf(
                "**/domain/handlers/**",
                "**/data/ai/GeminiService.kt",
                "**/ui/theme/**",
                "**/*Activity.kt",
                "**/*Screen.kt"
            ).joinToString(",")
        )
    }
}