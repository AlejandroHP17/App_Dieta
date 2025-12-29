plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.dokka)
}

android {
    namespace = "com.liftechnology.planalimenticio"

    buildFeatures {
        viewBinding = true // Habilita View Binding para interactuar con las vistas de forma segura.
        buildConfig = true // Genera la clase BuildConfig, útil para variables de entorno.
    }
    
    compileOptions {
        targetCompatibility = JavaVersion.VERSION_21 // Define la versión mínima de la API de Java para la compatibilidad del código.
        sourceCompatibility = JavaVersion.VERSION_21 // Define la versión del código fuente de Java.
    }

    /**
     * @description Configuración para resolver conflictos de archivos duplicados durante el empaquetado del APK.
     * `pickFirst` - Estrategia que le indica a Gradle que, en caso de encontrar archivos duplicados, elija el primero que encuentre y descarte los demás.
     */
    packaging {
        resources {
            pickFirsts.add("META-INF/**")
        }
    }
}

dependencies {
    /**
     * @description Define las dependencias del módulo `app`.
     * `platform(libs.androidx.compose.bom)` - Bill of Materials (BOM) para Jetpack Compose, que gestiona las versiones de las librerías de Compose.
     * `implementation(libs.bundles.*)` - Implementa varios bundles de librerías definidos en el archivo `libs.versions.toml`.
     * `implementation(project(":..."))` - Declara las dependencias a otros módulos del proyecto, estableciendo la arquitectura modular.
     */
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.androidx.basic)

    implementation(libs.bundles.koin)
    implementation(libs.bundles.androidx.ui)
    implementation(libs.bundles.junit.test)
    implementation(libs.bundles.retrofit)
    implementation(libs.bundles.animation)
    implementation(libs.bundles.compose)
    implementation(libs.bundles.gson)
    implementation(libs.bundles.compose.unit)
    implementation(libs.bundles.test)
    implementation(libs.bundles.room)
}

/**
 * @description Configuración para la tarea de Dokka, que genera una documentación unificada para todos los módulos del proyecto.
 * `outputDirectory` - Define el directorio donde se guardará la documentación generada.
 * `moduleName` - Establece el título principal que aparecerá en la documentación.
 */
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
    }
}

tasks.withType<org.jetbrains.dokka.gradle.DokkaMultiModuleTask>().configureEach {
    outputDirectory.set(buildDir.resolve("dokka"))
    moduleName.set("Registro Educativo - API Completa")
}

