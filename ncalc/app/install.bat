mkdir release

adb uninstall com.test1.calculator.free
adb install -r release\app-release.apk
adb shell am start -n "com.test1.calculator.free/com.test1.calculator.activities.ActivitySplashScreen" -a android.intent.action.MAIN -c android.intent.category.LAUNCHER
exit