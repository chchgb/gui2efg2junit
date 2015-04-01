# Introduction #

Disk Usage can be found here:

http://code.google.com/p/diskusage/

# Issues #

## No more data ##

This error occurred restarting while performing the scan. The crawling continues without further issues.

```
11-05 09:25:49.824: E/diskusage(3976): native error
11-05 09:25:49.824: E/diskusage(3976): java.lang.RuntimeException: Error: no more data
11-05 09:25:49.824: E/diskusage(3976): 	at com.google.android.diskusage.NativeScanner.read(NativeScanner.java:177)
11-05 09:25:49.824: E/diskusage(3976): 	at com.google.android.diskusage.NativeScanner.getByte(NativeScanner.java:187)
11-05 09:25:49.824: E/diskusage(3976): 	at com.google.android.diskusage.NativeScanner.runScanner(NativeScanner.java:154)
11-05 09:25:49.824: E/diskusage(3976): 	at com.google.android.diskusage.NativeScanner.scan(NativeScanner.java:262)
11-05 09:25:49.824: E/diskusage(3976): 	at com.google.android.diskusage.DiskUsage.scan(DiskUsage.java:564)
11-05 09:25:49.824: E/diskusage(3976): 	at com.google.android.diskusage.LoadableActivity$2.run(LoadableActivity.java:131)
```

## Leaked Window ##

As usual, this occurs when restarting with a dialog on screen.

```
11-05 09:30:19.274: E/WindowManager(4033): Activity com.google.android.diskusage.AppUsage has leaked window com.android.internal.policy.impl.PhoneWindow$DecorView@52533e20 that was originally added here
11-05 09:30:19.274: E/WindowManager(4033): android.view.WindowLeaked: Activity com.google.android.diskusage.AppUsage has leaked window com.android.internal.policy.impl.PhoneWindow$DecorView@52533e20 that was originally added here
11-05 09:30:19.274: E/WindowManager(4033): 	at android.view.ViewRoot.<init>(ViewRoot.java:247)
11-05 09:30:19.274: E/WindowManager(4033): 	at android.view.WindowManagerImpl.addView(WindowManagerImpl.java:148)
11-05 09:30:19.274: E/WindowManager(4033): 	at android.view.WindowManagerImpl.addView(WindowManagerImpl.java:91)
11-05 09:30:19.274: E/WindowManager(4033): 	at android.view.Window$LocalWindowManager.addView(Window.java:424)
11-05 09:30:19.274: E/WindowManager(4033): 	at android.app.Dialog.show(Dialog.java:241)
11-05 09:30:19.274: E/WindowManager(4033): 	at com.google.android.diskusage.RendererManager.switchRenderer(RendererManager.java:77)
11-05 09:30:19.274: E/WindowManager(4033): 	at com.google.android.diskusage.DiskUsageMenu$5.onMenuItemClick(DiskUsageMenu.java:134)
11-05 09:30:19.274: E/WindowManager(4033): 	at com.android.internal.view.menu.MenuItemImpl.invoke(MenuItemImpl.java:137)
11-05 09:30:19.274: E/WindowManager(4033): 	at com.android.internal.view.menu.MenuBuilder.performItemAction(MenuBuilder.java:855)
11-05 09:30:19.274: E/WindowManager(4033): 	at com.android.internal.view.menu.IconMenuView.invokeItem(IconMenuView.java:532)
11-05 09:30:19.274: E/WindowManager(4033): 	at com.android.internal.view.menu.IconMenuItemView.performClick(IconMenuItemView.java:122)
11-05 09:30:19.274: E/WindowManager(4033): 	at android.view.View$PerformClick.run(View.java:8816)
11-05 09:30:19.274: E/WindowManager(4033): 	at android.os.Handler.handleCallback(Handler.java:587)
11-05 09:30:19.274: E/WindowManager(4033): 	at android.os.Handler.dispatchMessage(Handler.java:92)
11-05 09:30:19.274: E/WindowManager(4033): 	at android.os.Looper.loop(Looper.java:123)
11-05 09:30:19.274: E/WindowManager(4033): 	at android.app.ActivityThread.main(ActivityThread.java:4627)
11-05 09:30:19.274: E/WindowManager(4033): 	at java.lang.reflect.Method.invokeNative(Native Method)
11-05 09:30:19.274: E/WindowManager(4033): 	at java.lang.reflect.Method.invoke(Method.java:521)
11-05 09:30:19.274: E/WindowManager(4033): 	at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:868)
11-05 09:30:19.274: E/WindowManager(4033): 	at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:626)
11-05 09:30:19.274: E/WindowManager(4033): 	at dalvik.system.NativeStart.main(Native Method)
```

# Test Results from 05 Nov 2011 (v3.0alpha[rev. 68] with AndroidCrawler r-64) #

First test results. Comparation was done on buttons only.

[![](http://gui2efg2junit.googlecode.com/files/diskusage.png)](http://code.google.com/p/gui2efg2junit/downloads/detail?name=diskusage.png&can=2&q=)

OVERALL COVERAGE SUMMARY
| name |	class, % |	method, % |	block, % |	line, % |
|:-----|:---------|:----------|:---------|:--------|
| all classes |	64%  (94/148) |	59%  (443/754) |	54%  (11201/20701) |	54%  (2343,3/4315) |


  * GuiTree: http://gui2efg2junit.googlecode.com/files/diskusage.xml
  * EFG: http://gui2efg2junit.googlecode.com/files/diskusage_efg.xml
  * DOT: http://gui2efg2junit.googlecode.com/files/diskusage.dot
  * jUnit: http://gui2efg2junit.googlecode.com/files/DiskusageGuiTest.java
  * Coverage: http://gui2efg2junit.googlecode.com/files/diskusage_coverage.zip

## Analysis ##

The coverage report points out how several parts of the application are not exercised by the crawler.

The main view showing the disk usage (FileSystemView, directly extends View) is a custom widget, hence its behaviour cannot be inferred. The only ways to operate on it would be add the support for FileSystemView into the crawler or to use a random sequence of interactions.

Also, not being able to browse the disk tree to select a file have several consequences:

  * the delete function is never enabled;
  * the show function is never enabled;
  * the functions to infer the MIME type of the selected file (and the functions defined on that type) are never used;

Additionally, the GUI specific methods (onClick, onOptionsItemSelected, ...) are never called.

Additional issues limiting the code coverage are:

  * some modules are never run because specific to a particular version of the Android system (Gingerbread, Eclair...);
  * changing the "filters" have no effect until the "Rescan" button is pressed, but the crawler only presses the button once;
  * some methods deal with exceptional situations such as an empty SD card, a full SD card, low memory, and so on.