# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# ============================================
# Keep line numbers for debugging stack traces
# ============================================
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# ============================================
# Kotlin
# ============================================
-keep class kotlin.Metadata { *; }
-keepclassmembers class **$WhenMappings {
    <fields>;
}
-keepclassmembers class kotlin.Metadata {
    public <methods>;
}
-assumenosideeffects class kotlin.jvm.internal.Intrinsics {
    static void checkParameterIsNotNull(java.lang.Object, java.lang.String);
}

# ============================================
# Kotlin Coroutines
# ============================================
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepclassmembers class kotlinx.** {
    volatile <fields>;
}
-keepclassmembers class kotlinx.coroutines.** {
    volatile <fields>;
}

# ============================================
# Room Database
# ============================================
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-keepclassmembers class * {
    @androidx.room.* <methods>;
}
-dontwarn androidx.room.paging.**

# ============================================
# Gson
# ============================================
-keepattributes Signature
-keepattributes *Annotation*
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.** { *; }
-keep class com.google.gson.stream.** { *; }
-keepclassmembers,allowobfuscation class * {
    @com.google.gson.annotations.SerializedName <fields>;
}
-keepclassmembers class * implements com.google.gson.TypeAdapter {
    <init>(com.google.gson.Gson);
}
-keepclassmembers class * implements com.google.gson.TypeAdapterFactory {
    <init>();
}
-keepclassmembers class * implements com.google.gson.JsonSerializer {
    <methods>;
}
-keepclassmembers class * implements com.google.gson.JsonDeserializer {
    <methods>;
}

# Keep clases de entidades Room que se serializan con Gson
-keep class com.liftechnology.planalimenticio.data.local.entity.** { *; }
-keep class com.liftechnology.planalimenticio.data.local.model.** { *; }

# ============================================
# Koin Dependency Injection
# ============================================
-keep class org.koin.** { *; }
-keep class org.koin.core.** { *; }
-keep class org.koin.dsl.** { *; }
-dontwarn org.koin.**

# ============================================
# Jetpack Compose
# ============================================
-keep class androidx.compose.** { *; }
-keep class androidx.compose.runtime.** { *; }
-keep class androidx.compose.ui.** { *; }
-keep class androidx.compose.foundation.** { *; }
-keep class androidx.compose.material3.** { *; }
-keep class androidx.compose.material.** { *; }
-keep class androidx.compose.animation.** { *; }
-dontwarn androidx.compose.**

# ============================================
# Navigation Compose
# ============================================
-keep class androidx.navigation.** { *; }
-keep interface androidx.navigation.** { *; }
-dontwarn androidx.navigation.**

# ============================================
# Parcelize
# ============================================
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# ============================================
# Modelos y Data Classes
# ============================================
-keep class com.liftechnology.planalimenticio.model.** { *; }
-keep class com.liftechnology.planalimenticio.data.local.entity.** { *; }
-keep class com.liftechnology.planalimenticio.data.local.model.** { *; }

# ============================================
# ViewModels
# ============================================
-keep class * extends androidx.lifecycle.ViewModel { *; }
-keep class * extends androidx.lifecycle.AndroidViewModel { *; }

# ============================================
# Application
# ============================================
-keep class com.liftechnology.planalimenticio.framework.BaseApplication { *; }

# ============================================
# Use Cases
# ============================================
-keep class com.liftechnology.planalimenticio.domain.usecase.** { *; }

# ============================================
# Repositories
# ============================================
-keep class com.liftechnology.planalimenticio.data.local.repository.** { *; }

# ============================================
# DAOs
# ============================================
-keep interface com.liftechnology.planalimenticio.data.local.dao.** { *; }

# ============================================
# Mappers
# ============================================
-keep class com.liftechnology.planalimenticio.mapper.** { *; }

# ============================================
# Remove logging in release builds
# ============================================
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
}