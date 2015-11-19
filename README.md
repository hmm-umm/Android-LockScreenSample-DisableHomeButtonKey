# LockScreenSample-Disable HomeButton or HomeKey Event
Android LockScreenSample Using Service - Disable HomeButton Key Event
LockScreenSample use Android Service. So, Ignores HomeButton Key Event.

Download
--------

Current version: [1.0.1]

Gradle:
```groovy
compile 'com.github.dubulee:lockscreendiablehomebuttonkey:1.0.1'
```

As there are a lot questions about "how to disable home button in android?" on `Stack Overflow`, such as

* [how to disable home button in android?](http://stackoverflow.com/questions/17183905/how-to-disable-home-button-in-android)
* [Android - Is It possible to disable the click of home button](http://stackoverflow.com/questions/2162182/android-is-it-possible-to-disable-the-click-of-home-button)
* [How to disable Home and other system buttons in Android?](http://stackoverflow.com/questions/17549478/how-to-disable-home-and-other-system-buttons-in-android)


This Sample support UnSoft Home Key(UnVirtual Home Key) like Samsung Galaxy 
And Soft Home Key(Virtual Home Key) like LG G, Google Nexus.

This Sample is similar to Microsoft NextLock, DaumKaKao KaKaoTalk Notification Cover

Test Samsung Galaxy,LG G, Nexus5

###UnSoftKey - Test Samsung
![ScreenShot](rawimg/unsoftkey_unlock_samsung.gif)![ScreenShot](rawimg/unsoftkey_lock_samsung.gif)

###SoftKey - Test LG G, Nexus5

![ScreenShot](rawimg/softkey_unlock_lg.gif)![ScreenShot](rawimg/softkey_lock_lg.gif) ![ScreenShot](rawimg/softkey_unlock_nexus5.gif)




## How to use

Test Start
```java
Lockscreen.getInstance(ContextInstance).startLockscreenService();
```

Test Stop
```java
Lockscreen.getInstance(ContextInstance).stopLockscreenService();
```

## Customization
### Welcome any Modification
You customize lockscreenunsingservice.lib files


License
=======
Copyright 2015 DUBULEE

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
