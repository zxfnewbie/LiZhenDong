# This is a configuration file for ProGuard.
# http://proguard.sourceforge.net/index.html#manual/usage.html
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-verbose
# Optimization is turned off by default. Dex does not like code run
# through the ProGuard optimize and preverify steps (and performs some
# of these optimizations on its own).
-dontoptimize
-dontpreverify
# If you want to enable optimization, you should include the
# following:
# -optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*
# -optimizationpasses 5
# -allowaccessmodification
#
# Note that you cannot just include these flags in your own
# configuration file; if you are including this file, optimization
# will be turned off. You'll need to either edit this file, or
# duplicate the contents of this file and remove the include of this
# file from your project's proguard.config path property.
-keepattributes *Annotation*
#-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
#-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgent
-keep public class * extends android.preference.Preference
#-keep public class * extends android.support.v4.app.Fragment
#-keep public class * extends android.app.Fragment
-keep public class com.android.vending.licensing.ILicensingService
# For native methods, see http://proguard.sourceforge.net/manual/examples.html#native
-keepclasseswithmembernames class * {
    native <methods>;
}
-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}
# For enumeration classes, see http://proguard.sourceforge.net/manual/examples.html#enumerations
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
-keepclassmembers class **.R$* {
    public static <fields>;
}
# The support library contains references to newer platform versions.
# Don't warn about those in case this app is linking against an older
# platform version.  We know about them, and they are safe.
-dontwarn android.support.**
-dontwarn java.awt.**
-dontwarn com.sothree.slidinguppanel.**
# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

#Math library
-keep class org.matheclipse.** { *; }
-keep class org.apfloat.** { *; }
#-keep class de.lab4inf.math.** { *; }
-keep class cc.redberry.** { *; }
-keep class jp.ac.kobe_u.cs.cream.** { *; }

-keep class com.duy.lambda.** { *; }
-keep class com.duy.stream.** { *; }
-keep class com.duy.calc.casio.** { *; }

-keep class com.android.vending.billing.** { *; }
#-keep class aidl.util.** { *; }

-dontwarn javax.**
-dontwarn java.beans.**
-dontwarn java.lang.management.**
-dontwarn de.lab4inf.math.**
-dontwarn com.google.common.**
-dontwarn java.lang.ClassValue
-dontwarn java.rmi.MarshalledObject
-dontwarn net.minidev.**
-dontwarn com.madrobot.**
-dontwarn sun.misc.**
-dontwarn org.apache.log4j.**
-dontwarn org.cheffo.jeplite.**
-dontwarn jp.ac.kobe_u.cs.cream.**

#Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

