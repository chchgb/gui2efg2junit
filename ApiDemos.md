# Introduction #

API Demos is included in your Android distribution.

# Issues #

## Purgeable Bitmap Crash ##

To reproduce the Bug:

  * select Graphics
  * select PurgeableBitmap
  * select NonPurgeable
  * wait 4 seconds
  * press back and select Purgeable
  * wait

```
11-02 14:47:47.509: W/dalvikvm(31445): threadid=1: thread exiting with uncaught exception (group=0x4001d800)
11-02 14:47:47.519: E/AndroidRuntime(31445): FATAL EXCEPTION: main
11-02 14:47:47.519: E/AndroidRuntime(31445): android.view.WindowManager$BadTokenException: Unable to add window -- token android.os.BinderProxy@52533240 is not valid; is your activity running?
11-02 14:47:47.519: E/AndroidRuntime(31445): 	at android.view.ViewRoot.setView(ViewRoot.java:505)
11-02 14:47:47.519: E/AndroidRuntime(31445): 	at android.view.WindowManagerImpl.addView(WindowManagerImpl.java:177)
11-02 14:47:47.519: E/AndroidRuntime(31445): 	at android.view.WindowManagerImpl.addView(WindowManagerImpl.java:91)
11-02 14:47:47.519: E/AndroidRuntime(31445): 	at android.view.Window$LocalWindowManager.addView(Window.java:424)
11-02 14:47:47.519: E/AndroidRuntime(31445): 	at android.app.Dialog.show(Dialog.java:241)
11-02 14:47:47.519: E/AndroidRuntime(31445): 	at com.example.android.apis.graphics.PurgeableBitmap.showAlertDialog(PurgeableBitmap.java:114)
11-02 14:47:47.519: E/AndroidRuntime(31445): 	at com.example.android.apis.graphics.PurgeableBitmap.access$200(PurgeableBitmap.java:37)
11-02 14:47:47.519: E/AndroidRuntime(31445): 	at com.example.android.apis.graphics.PurgeableBitmap$RefreshHandler.handleMessage(PurgeableBitmap.java:51)
11-02 14:47:47.519: E/AndroidRuntime(31445): 	at android.os.Handler.dispatchMessage(Handler.java:99)
11-02 14:47:47.519: E/AndroidRuntime(31445): 	at android.os.Looper.loop(Looper.java:123)
11-02 14:47:47.519: E/AndroidRuntime(31445): 	at android.app.ActivityThread.main(ActivityThread.java:4627)
11-02 14:47:47.519: E/AndroidRuntime(31445): 	at java.lang.reflect.Method.invokeNative(Native Method)
11-02 14:47:47.519: E/AndroidRuntime(31445): 	at java.lang.reflect.Method.invoke(Method.java:521)
11-02 14:47:47.519: E/AndroidRuntime(31445): 	at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:868)
11-02 14:47:47.519: E/AndroidRuntime(31445): 	at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:626)
11-02 14:47:47.519: E/AndroidRuntime(31445): 	at dalvik.system.NativeStart.main(Native Method)
```

# Test Results from 12 Nov 2011 (w\ AndCrawler 80) #

OVERALL COVERAGE SUMMARY
| name | class, %	 | method, %	 | block, %	 | line, % |
|:-----|:----------|:-----------|:----------|:--------|
| all classes	 | 82%  (469/572)	 | 60%  (1257/2087)	 | 76%  (45286/59975)	 | 64%  (5907/9231) |

Test limited to the first 800 traces. The "Purgeable Bitmap" activity has not been explored since a bug in its content crashes the application.

[Download](http://gui2efg2junit.googlecode.com/files/apidemos_20111112.zip)

# Test Results from 26 Oct 2011 (w\ AndCrawler 50) #

Test limited to the first 400 traces. The "Purgeable Bitmap" activity has not been explored since a bug in its content crashes the application.

[Download](http://gui2efg2junit.googlecode.com/files/apidemos_20111026.zip) - [Image](http://code.google.com/p/gui2efg2junit/downloads/detail?name=apidemos_20111026.png&can=2&q=)

# Test Results from 20 Oct 2011 (w\ AndCrawler 34) #

Test limited to the first 400 traces. The Session ends after 240 traces due to a crash of the tested application in the "Purgeable Bitmap" activity. This is an actual repeatable crash of the API Demos app.

[Download](http://gui2efg2junit.googlecode.com/files/apidemos_20111020.zip)