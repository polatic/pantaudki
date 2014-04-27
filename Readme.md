Custoom
============

Required software :

- android-support-v7-appcompat library
- google-play-services library
- Facebook Android SDK
- ViewPagerIndicator

Libs :
- gcm.jar
- picasso.jar
- google-play-services.jar
- gson-2.2.4.jar
- android-async-http-1.4.4.jar
- android-support-v7-appcompatjar


Attention :
- User LogManager.print(string message) for log the debug. Don't forget set isDebug value to false before launch the apps to goole play.
- Check api in the file api.xml. Sure the api is correct.

How to Export
===============

1. Clean Project by go to menu "Project -> Clean"
2. Change api key by editing "res/values/api.xml"
3. Change app name by editing "res/values/string.xml" -> app_name
4. Update logo
5. Export apk by go to menu "File -> Export -> Signed APK"
6. Test on your phone