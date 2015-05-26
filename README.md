# LockScreenSample-Disable HomeButton Event
Android LockScreenSample Using Service - Disable HomeButton Event
LockScreenSample use Android Service. So, Ignores HomeButton Event.

As there are a lot questions about "how to disable home button in android?" on `Stack Overflow`, such as

* [how to disable home button in android?][4]
* [Android - Is It possible to disable the click of home button][5]
* [How to disable Home and other system buttons in Android?][6]


This Sample support UnSoft Home Key(UnVirtual Home Key) like Samsung Galaxy 
And Soft Home Key(Virtual Home Key) like LG G, Google Nexus.

Test Samsung Galaxy,LG G, Nexus5

###UnSoftKey - Test Samsung
![ScreenShot](rawimg/unsoftkey_unlock_samsung.gif)![ScreenShot](rawimg/unsoftkey_lock_samsung.gif)

###SoftKey - Test LG G, Nexus5

![ScreenShot](rawimg/softkey_unlock_lg.gif)![ScreenShot](rawimg/softkey_lock_lg.gif) ![ScreenShot](rawimg/softkey_unlock_nexus5.gif)




## How to use

Test Start
```java
Lockscreen.getInstance(mContext).startLockscreenService();
```

Test Stop
```java
Lockscreen.getInstance(mContext).stopLockscreenService();
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
