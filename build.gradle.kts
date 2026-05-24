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
        property("sonar.newCode.referenceBranch", "main")
        property(
            "sonar.coverage.jacoco.xmlReportPaths",
            "${rootDir}/app/build/reports/jacoco/jacocoTestReport/jacocoTestReport.xml"
        )
        property(
            "sonar.androidLint.reportPaths",
            "${rootDir}/app/build/reports/lint-results-debug.xml"
        )
        property(
            "sonar.coverage.exclusions",
            listOf(
                "**/data/ai/GeminiService.kt",
                "**/ui/theme/**",
                "**/*Activity.kt",
                "**/presentation/chat/components/**",
                "**/presentation/chat/viewmodel/ChatViewModelFactory.kt",
                "**/presentation/ActionPilotApp.kt",
                "**/presentation/ChatRoute.kt",
                "**/domain/usecase/GetAiActionUseCase.kt",
                "**/domain/action/ActionExecutor.kt",
                "**/domain/action/ActionHandler.kt",
                "**/domain/action/ActionType.kt",
                "**/data/repository/AIActionRepositoryImpl.kt",
                "**/presentation/chat/effect/ChatEffect.kt",
                "**/presentation/chat/ui/**"
            ).joinToString(",")
        )
    }
}

tasks.named("sonar") {
    dependsOn(":app:lintDebug")
    dependsOn(":app:jacocoTestReport")
}