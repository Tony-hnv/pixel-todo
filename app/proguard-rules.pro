# Add project specific ProGuard rules here.
-keepattributes *Annotation*
-keep class com.pixeltodo.app.domain.model.** { *; }
-keep class com.pixeltodo.app.data.local.** { *; }
-keep class com.pixeltodo.app.data.** { *; }

# Retrofit
-keepattributes Signature
-keepattributes Exceptions
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }

# Gson
-keepattributes Signature
-keep class com.google.gson.** { *; }
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# Room
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-dontwarn androidx.room.paging.**