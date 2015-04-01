# Introduction #

Mileage can be found here:

http://evancharlton.com/projects/mileage/

The source code can be found here:

https://code.google.com/p/android-mileage/source/

# Issues #

## Crawling Limitations ##

The Activity name returned by Robotium is not reliable. The comparator must ignore it.

## Leaked Window ##

This error occurs when the Restarter launch the restart Intent while the "Help" or "About" dialogs are shown. The crawling continues without further issues.

```
10-31 17:26:49.252: E/WindowManager(4043): Activity com.evancharlton.mileage.Mileage has leaked window com.android.internal.policy.impl.PhoneWindow$DecorView@525d2500 that was originally added here
10-31 17:26:49.252: E/WindowManager(4043): android.view.WindowLeaked: Activity com.evancharlton.mileage.Mileage has leaked window com.android.internal.policy.impl.PhoneWindow$DecorView@525d2500 that was originally added here
10-31 17:26:49.252: E/WindowManager(4043): 	at android.view.ViewRoot.<init>(ViewRoot.java:247)
10-31 17:26:49.252: E/WindowManager(4043): 	at android.view.WindowManagerImpl.addView(WindowManagerImpl.java:148)
10-31 17:26:49.252: E/WindowManager(4043): 	at android.view.WindowManagerImpl.addView(WindowManagerImpl.java:91)
10-31 17:26:49.252: E/WindowManager(4043): 	at android.view.Window$LocalWindowManager.addView(Window.java:424)
10-31 17:26:49.252: E/WindowManager(4043): 	at android.app.Dialog.show(Dialog.java:241)
10-31 17:26:49.252: E/WindowManager(4043): 	at com.evancharlton.mileage.HelpDialog.create(HelpDialog.java:67)
10-31 17:26:49.252: E/WindowManager(4043): 	at com.evancharlton.mileage.HelpDialog.create(HelpDialog.java:58)
10-31 17:26:49.252: E/WindowManager(4043): 	at com.evancharlton.mileage.AddFillUpView.onOptionsItemSelected(AddFillUpView.java:448)
10-31 17:26:49.252: E/WindowManager(4043): 	at android.app.Activity.onMenuItemSelected(Activity.java:2195)
10-31 17:26:49.252: E/WindowManager(4043): 	at com.android.internal.policy.impl.PhoneWindow.onMenuItemSelected(PhoneWindow.java:730)
10-31 17:26:49.252: E/WindowManager(4043): 	at com.android.internal.view.menu.MenuItemImpl.invoke(MenuItemImpl.java:143)
10-31 17:26:49.252: E/WindowManager(4043): 	at com.android.internal.view.menu.MenuBuilder.performItemAction(MenuBuilder.java:855)
10-31 17:26:49.252: E/WindowManager(4043): 	at com.android.internal.view.menu.IconMenuView.invokeItem(IconMenuView.java:532)
10-31 17:26:49.252: E/WindowManager(4043): 	at com.android.internal.view.menu.IconMenuItemView.performClick(IconMenuItemView.java:122)
10-31 17:26:49.252: E/WindowManager(4043): 	at android.view.View$PerformClick.run(View.java:8816)
10-31 17:26:49.252: E/WindowManager(4043): 	at android.os.Handler.handleCallback(Handler.java:587)
10-31 17:26:49.252: E/WindowManager(4043): 	at android.os.Handler.dispatchMessage(Handler.java:92)
10-31 17:26:49.252: E/WindowManager(4043): 	at android.os.Looper.loop(Looper.java:123)
10-31 17:26:49.252: E/WindowManager(4043): 	at android.app.ActivityThread.main(ActivityThread.java:4627)
10-31 17:26:49.252: E/WindowManager(4043): 	at java.lang.reflect.Method.invokeNative(Native Method)
10-31 17:26:49.252: E/WindowManager(4043): 	at java.lang.reflect.Method.invoke(Method.java:521)
10-31 17:26:49.252: E/WindowManager(4043): 	at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:868)
10-31 17:26:49.252: E/WindowManager(4043): 	at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:626)
10-31 17:26:49.252: E/WindowManager(4043): 	at dalvik.system.NativeStart.main(Native Method)
```

## Cursor Error ##

This error occurs when the Restarter launch the restart Intent during SQL Import/Export operations. The crawling continues without further issues.

```
I/ActivityManager(   59): Starting activity: Intent { act=android.intent.action.MAIN flg=0x14000000 cmp=com.evancharlton.mileage/.Mileage }
E/Cursor  (18738): Finalizing a Cursor that has not been deactivated or closed. database = /data/data/com.evancharlton.mileage/databases/mileage.db, table = fillups, query = SELECT amount, restart, mileage, _id, is_partial, longitude, latitude, comment, date, vehicle_id, co
E/Cursor  (18738): android.database.sqlite.DatabaseObjectNotClosedException: Application did not close the cursor or database object that was opened here
E/Cursor  (18738):      at android.database.sqlite.SQLiteCursor.<init>(SQLiteCursor.java:210)
E/Cursor  (18738):      at android.database.sqlite.SQLiteDirectCursorDriver.query(SQLiteDirectCursorDriver.java:53)
E/Cursor  (18738):      at android.database.sqlite.SQLiteDatabase.rawQueryWithFactory(SQLiteDatabase.java:1345)
E/Cursor  (18738):      at android.database.sqlite.SQLiteDatabase.queryWithFactory(SQLiteDatabase.java:1229)
E/Cursor  (18738):      at android.database.sqlite.SQLiteDatabase.query(SQLiteDatabase.java:1184)
E/Cursor  (18738):      at android.database.sqlite.SQLiteDatabase.query(SQLiteDatabase.java:1264)
E/Cursor  (18738):      at com.evancharlton.mileage.io.output.SQLView$1.run(SQLView.java:36)
E/Cursor  (18738):      at java.lang.Thread.run(Thread.java:1096)
```

# Test Results from 01 Nov 2011 (v2.2.5 with r-58) #

The swapTab event has now a description which is used in the DOT graph.
The comparator uses both id and name of the widgets. (Overall coverage hasn't improved tho')

[![](http://gui2efg2junit.googlecode.com/files/mileage_20111101.png)](http://code.google.com/p/gui2efg2junit/downloads/detail?name=mileage_20111101.png&can=2&q=)

OVERALL COVERAGE SUMMARY
| name	| class, %	| method, %	| block, %	| line, %|
|:-----|:---------|:----------|:---------|:-------|
| all classes	| 62%  (74/119)	| 51%  (293/578)	| 51%  (7406/14463)	| 50%  (1596,7/3212)|

  * GuiTree: http://gui2efg2junit.googlecode.com/files/mileage_20111101.xml
  * EFG: http://gui2efg2junit.googlecode.com/files/mileage_efg_20111101.xml
  * DOT: http://gui2efg2junit.googlecode.com/files/mileage_20111101.dot
  * jUnit: http://gui2efg2junit.googlecode.com/files/MileageGuiTest_20111101.java
  * Coverage: http://gui2efg2junit.googlecode.com/files/coverage_20111101.zip



# Test Results from 31 Oct 2011 (v2.2.5 with r-56) #

Support for menus was added. Most menus are not recognized as different states due to the "activity name" issue and the absence of widgets.
Only the first 3 items in ListViews are explored, hence not all the options in Settings are selected.
Adding new SimpleTypes (MENU and IMAGE) to the Comparator solved the issue with GraphicalActivity experienced in the previous test.

[![](http://gui2efg2junit.googlecode.com/files/mileage_20111031.png)](http://code.google.com/p/gui2efg2junit/downloads/detail?name=mileage_20111031.png&can=2&q=)

OVERALL COVERAGE SUMMARY
| name	| class, %		| method, %		| block, %		| line, %	|
|:-----|:----------|:-----------|:----------|:--------|
| all classes		| 62%  (74/119)		| 52%  (298/578)		| 52%  (7556/14463)		| 51%  (1642,2/3212)	|


  * GuiTree: http://gui2efg2junit.googlecode.com/files/mileage_20111031.xml
  * EFG: http://gui2efg2junit.googlecode.com/files/mileage_efg_20111031.xml
  * DOT: http://gui2efg2junit.googlecode.com/files/mileage_20111031.dot
  * jUnit: http://gui2efg2junit.googlecode.com/files/MileageGuiTest_20111031.java
  * Coverage: http://gui2efg2junit.googlecode.com/files/mileage_coverage_20111031.zip

# Test Results from 26 Oct 2011 (v2.2.5 with r-50) #

Long click support was added.
Due to the reported issue with Robotium, the back event is not injected on the GraphicalActivity, because it's not recognized as a different activity than StatisticsView. This will be fixed in a subsequent release.

[![](http://gui2efg2junit.googlecode.com/files/mileage_20111026.png)](http://code.google.com/p/gui2efg2junit/downloads/detail?name=mileage_20111026.png&can=2&q=)

  * GuiTree: http://gui2efg2junit.googlecode.com/files/mileage_guitree_20111026.xml
  * EFG: http://gui2efg2junit.googlecode.com/files/mileage_efg_20111026.xml
  * DOT: http://gui2efg2junit.googlecode.com/files/mileage_20111026.dot
  * jUnit: http://gui2efg2junit.googlecode.com/files/MileageGuiTest_20111026.java

# Test Results from 23 Oct 2011 (v2.2.5 with r-43) #

[![](http://gui2efg2junit.googlecode.com/files/mileage.png)](http://code.google.com/p/gui2efg2junit/downloads/detail?name=mileage.png&can=2&q=)

  * GuiTree: http://gui2efg2junit.googlecode.com/files/mileage.xml
  * EFG: http://gui2efg2junit.googlecode.com/files/mileage_efg.xml
  * DOT: http://gui2efg2junit.googlecode.com/files/mileage.dot
  * jUnit: http://gui2efg2junit.googlecode.com/files/MileageGuiTest.java