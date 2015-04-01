# Introduction #

Wordpress can be found here:

http://android.wordpress.org/download/

The source code can be found here:

http://android.svn.wordpress.org/

See also: WordPress2

# Issues #

## Crawling Limitations ##

The Activity name returned by Robotium is not reliable. The comparator must ignore it.

## Camera Error ##

This is an issue due to lack of camera support of the Android Froyo Emulator.

To reproduce the bug:
  * swap tab to "Pages"
  * click a page
  * click "Edit Page"
  * under "Media" click "Add"
  * click "Take a new photo"

```
11-02 12:17:30.668: D/libEGL(790): egl.cfg not found, using default config
11-02 12:17:30.668: D/libEGL(790): loaded /system/lib/egl/libGLES_android.so
11-02 12:17:30.768: W/dalvikvm(790): threadid=9: thread exiting with uncaught exception (group=0x4001d800)
11-02 12:17:30.768: E/AndroidRuntime(790): FATAL EXCEPTION: GLThread 11
11-02 12:17:30.768: E/AndroidRuntime(790): java.lang.IllegalArgumentException: No configs match configSpec
11-02 12:17:30.768: E/AndroidRuntime(790): 	at android.opengl.GLSurfaceView$BaseConfigChooser.chooseConfig(GLSurfaceView.java:760)
11-02 12:17:30.768: E/AndroidRuntime(790): 	at android.opengl.GLSurfaceView$EglHelper.start(GLSurfaceView.java:916)
11-02 12:17:30.768: E/AndroidRuntime(790): 	at android.opengl.GLSurfaceView$GLThread.guardedRun(GLSurfaceView.java:1246)
11-02 12:17:30.768: E/AndroidRuntime(790): 	at android.opengl.GLSurfaceView$GLThread.run(GLSurfaceView.java:1116)
11-02 12:17:30.848: W/ActivityManager(59):   Force finishing activity com.android.camera/.Camera
```

The error dialog won't close at startup leading the crawler to crash.

## Leaked Window ##

This error occurs when the Restarter launch the restart Intent while dialogs are shown. The crawling continues without further issues.

```
E/WindowManager(  544): Activity org.wordpress.android.tabView has leaked window com.android.internal.policy.impl.PhoneWindow$DecorView@530a7f58 that was originally added here
E/WindowManager(  544): android.view.WindowLeaked: Activity org.wordpress.android.tabView has leaked window com.android.internal.policy.impl.PhoneWindow$DecorView@530a7f58 that was originally added here
E/WindowManager(  544):         at android.view.ViewRoot.<init>(ViewRoot.java:247)
E/WindowManager(  544):         at android.view.WindowManagerImpl.addView(WindowManagerImpl.java:148)
E/WindowManager(  544):         at android.view.WindowManagerImpl.addView(WindowManagerImpl.java:91)
E/WindowManager(  544):         at android.view.Window$LocalWindowManager.addView(Window.java:424)
E/WindowManager(  544):         at android.app.Dialog.show(Dialog.java:241)
E/WindowManager(  544):         at org.wordpress.android.moderateCommentsTab$21.run(moderateCommentsTab.java:1509)
E/WindowManager(  544):         at android.os.Handler.handleCallback(Handler.java:587)
E/WindowManager(  544):         at android.os.Handler.dispatchMessage(Handler.java:92)
E/WindowManager(  544):         at android.os.Looper.loop(Looper.java:123)
E/WindowManager(  544):         at android.app.ActivityThread.main(ActivityThread.java:4627)
E/WindowManager(  544):         at java.lang.reflect.Method.invokeNative(NativeMethod)
E/WindowManager(  544):         at java.lang.reflect.Method.invoke(Method.java:521)
E/WindowManager(  544):         at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:868)
E/WindowManager(  544):         at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:626)
E/WindowManager(  544):         at dalvik.system.NativeStart.main(Native Method)
```

## Unparseable Date Warning ##

This warning occurs when updating one of the lists (comments, posts, pages) either with the "double arrow" ImageButton or as a side effect of an editing.

```
W/System.err(  544): java.text.ParseException: Unparseable date: Wed Nov 02 09:21:31 GMT+00:00+00:00 2011
W/System.err(  544):    at java.text.DateFormat.parse(DateFormat.java:645)
W/System.err(  544):    at org.wordpress.android.moderateCommentsTab$14.callFinished(moderateCommentsTab.java:738)
W/System.err(  544):    at org.wordpress.android.moderateCommentsTab$XMLRPCMethod$1.run(moderateCommentsTab.java:1170)
W/System.err(  544):    at android.os.Handler.handleCallback(Handler.java:587)
W/System.err(  544):    at android.os.Handler.dispatchMessage(Handler.java:92)
W/System.err(  544):    at android.os.Looper.loop(Looper.java:123)
W/System.err(  544):    at android.app.ActivityThread.main(ActivityThread.java:4627)
W/System.err(  544):    at java.lang.reflect.Method.invokeNative(Native Method)
W/System.err(  544):    at java.lang.reflect.Method.invoke(Method.java:521)
W/System.err(  544):    at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:868)
W/System.err(  544):    at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:626)
W/System.err(  544):    at dalvik.system.NativeStart.main(Native Method)
```

# Test Results from 07 Nov 2011 (v1.5.1 with AndCrawler r-72) #

Comparation is done on buttons and editText only.

A bug in the Comparator gives false positives when different activities have widget with the same ID. This has been fixed in the subsequent release of the crawler ~~which will be used to measure coverage~~. (Testing aborted due to unexpected crash at the startup of the instrumented apk.)

The crawler doesn't explore the "Add photo" dialog in order to avoid the crash (see previous results).

[![](http://gui2efg2junit.googlecode.com/files/wordpress_20111107.png)](http://code.google.com/p/gui2efg2junit/downloads/detail?name=wordpress_20111107.png&can=2&q=)

  * GuiTree: http://gui2efg2junit.googlecode.com/files/wordpress_20111107.xml
  * EFG: http://gui2efg2junit.googlecode.com/files/wordpress_efg_20111107.xml
  * DOT: http://gui2efg2junit.googlecode.com/files/wordpress_20111107.dot
  * jUnit: http://gui2efg2junit.googlecode.com/files/AndroidGuiTest_20111107.java

# Test Results from 02 Nov 2011 (v1.5.1 with r-59) #

The crawler crashed as a result of the Camera Error (see Issues). After a back event, activities may compare as new when they aren't because of the "activity name" issue reported above.

  * GuiTree: http://gui2efg2junit.googlecode.com/files/wordpress.xml