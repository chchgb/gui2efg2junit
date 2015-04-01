# Introduction #

Wordpress 2 is being developed at the moment. Here's the Trac repository.

https://android.trac.wordpress.org/

The source code can be found here:

http://android.svn.wordpress.org/

See also: WordpressForAndroid



# Issues #

## Stats Crash ##

#### Crash when hit the Stats button on the dashboard ####

Affects changeset 434 and subsequent. To reproduce the bug:

  * Click _Stats_

```
11-23 19:25:37.920: D/AndroidRuntime(811): Shutting down VM
11-23 19:25:37.950: W/dalvikvm(811): threadid=1: thread exiting with uncaught exception (group=0x40015560)
11-23 19:25:37.970: E/AndroidRuntime(811): FATAL EXCEPTION: main
11-23 19:25:37.970: E/AndroidRuntime(811): java.lang.RuntimeException: Unable to start activity ComponentInfo{org.wordpress.android/org.wordpress.android.ViewStats}: java.lang.NullPointerException
11-23 19:25:37.970: E/AndroidRuntime(811): 	at android.app.ActivityThread.performLaunchActivity(ActivityThread.java:1647)
11-23 19:25:37.970: E/AndroidRuntime(811): 	at android.app.ActivityThread.handleLaunchActivity(ActivityThread.java:1663)
11-23 19:25:37.970: E/AndroidRuntime(811): 	at android.app.ActivityThread.access$1500(ActivityThread.java:117)
11-23 19:25:37.970: E/AndroidRuntime(811): 	at android.app.ActivityThread$H.handleMessage(ActivityThread.java:931)
11-23 19:25:37.970: E/AndroidRuntime(811): 	at android.os.Handler.dispatchMessage(Handler.java:99)
11-23 19:25:37.970: E/AndroidRuntime(811): 	at android.os.Looper.loop(Looper.java:123)
11-23 19:25:37.970: E/AndroidRuntime(811): 	at android.app.ActivityThread.main(ActivityThread.java:3683)
11-23 19:25:37.970: E/AndroidRuntime(811): 	at java.lang.reflect.Method.invokeNative(Native Method)
11-23 19:25:37.970: E/AndroidRuntime(811): 	at java.lang.reflect.Method.invoke(Method.java:507)
11-23 19:25:37.970: E/AndroidRuntime(811): 	at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:839)
11-23 19:25:37.970: E/AndroidRuntime(811): 	at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:597)
11-23 19:25:37.970: E/AndroidRuntime(811): 	at dalvik.system.NativeStart.main(Native Method)
11-23 19:25:37.970: E/AndroidRuntime(811): Caused by: java.lang.NullPointerException
11-23 19:25:37.970: E/AndroidRuntime(811): 	at org.wordpress.android.ViewStats.initStats(ViewStats.java:310)
11-23 19:25:37.970: E/AndroidRuntime(811): 	at org.wordpress.android.ViewStats.onCreate(ViewStats.java:114)
11-23 19:25:37.970: E/AndroidRuntime(811): 	at android.app.Instrumentation.callActivityOnCreate(Instrumentation.java:1047)
11-23 19:25:37.970: E/AndroidRuntime(811): 	at android.app.ActivityThread.performLaunchActivity(ActivityThread.java:1611)
11-23 19:25:37.970: E/AndroidRuntime(811): 	... 11 more
```

See: [Ticket 215](https://android.trac.wordpress.org/ticket/215) - [Changeset 437](https://android.trac.wordpress.org/changeset/437)

## Ghost Spam Comment ##

#### Caching issues on deleted spam comment ####

This bug affects changeset 394 and subsequent (checked up to 434).

When you have only one comment left in the blog, flag it as spam and then delete it, it still gets displayed in the list until you enter a new comment. Even hitting the refresh button won't help: you need to restart the device to have the comment list shown empty.

To reproduce the bug, make sure the test blog has only one comment left, then:

  * Click Comments
  * Long click the comment
  * Click Spam
  * Long click the comment
  * Click Delete

The comment is still there. If you want to make things worse:

  * Click the comment
  * Click Reply
  * Click Cancel
  * Hit the BACK key

The comment is now shown as Approved!

This error is shown in the log:

```
11-23 08:12:45.234: E/Database(364): close() was never explicitly called on database '/data/data/org.wordpress.android/databases/wordpress' 
11-23 08:12:45.234: E/Database(364): android.database.sqlite.DatabaseObjectNotClosedException: Application did not close the cursor or database object that was opened here
11-23 08:12:45.234: E/Database(364): 	at android.database.sqlite.SQLiteDatabase.<init>(SQLiteDatabase.java:1847)
11-23 08:12:45.234: E/Database(364): 	at android.database.sqlite.SQLiteDatabase.openDatabase(SQLiteDatabase.java:820)
11-23 08:12:45.234: E/Database(364): 	at android.database.sqlite.SQLiteDatabase.openOrCreateDatabase(SQLiteDatabase.java:854)
11-23 08:12:45.234: E/Database(364): 	at android.database.sqlite.SQLiteDatabase.openOrCreateDatabase(SQLiteDatabase.java:847)
11-23 08:12:45.234: E/Database(364): 	at android.app.ContextImpl.openOrCreateDatabase(ContextImpl.java:544)
11-23 08:12:45.234: E/Database(364): 	at android.content.ContextWrapper.openOrCreateDatabase(ContextWrapper.java:203)
11-23 08:12:45.234: E/Database(364): 	at org.wordpress.android.WordPressDB.<init>(WordPressDB.java:112)
11-23 08:12:45.234: E/Database(364): 	at org.wordpress.android.Comments.changeCommentStatus(Comments.java:218)
11-23 08:12:45.234: E/Database(364): 	at org.wordpress.android.Comments.access$2(Comments.java:214)
11-23 08:12:45.234: E/Database(364): 	at org.wordpress.android.Comments$3.run(Comments.java:185)
11-23 08:12:45.924: I/WordPress(364): response = HTTP/1.1 200 OK
11-23 08:12:56.263: W/KeyCharacterMap(364): No keyboard for id 0
11-23 08:12:56.263: W/KeyCharacterMap(364): Using default keymap: /system/usr/keychars/qwerty.kcm.bin
```

The app won't crash, but a dialog with a 404 error pops up leading the crawling to failure.

The bug has been fixed by removing the comments table from the cache when it's empty, and updating the user interface in order to show an empty list.

See: [Ticket 232](https://android.trac.wordpress.org/ticket/232) - [Changeset 435](https://android.trac.wordpress.org/changeset/435) - [Changeset 441](https://android.trac.wordpress.org/changeset/441)

## Share with Wordpress ##

#### The app crashes when trying to share a page via Wordpress ####

Changeset 394 crashes when the user opens a page and tries to share it via Wordpress. Sharing via SMS messaging works.
The crash tipically occurs when there's only one blog added to the app.

To reproduce the bug:

  * Click Pages
  * Open an item in the list
  * Click the share button (double arrow between the pencil and the trashcan)
  * Click Wordpress

```
11-20 16:13:19.709: D/AndroidRuntime(4238): Shutting down VM
11-20 16:13:19.709: W/dalvikvm(4238): threadid=1: thread exiting with uncaught exception (group=0x40015560)
11-20 16:13:19.729: E/AndroidRuntime(4238): FATAL EXCEPTION: main
11-20 16:13:19.729: E/AndroidRuntime(4238): java.lang.RuntimeException: Unable to start activity ComponentInfo{org.wordpress.android/org.wordpress.android.EditPost}: java.lang.NullPointerException
11-20 16:13:19.729: E/AndroidRuntime(4238): 	at android.app.ActivityThread.performLaunchActivity(ActivityThread.java:1647)
11-20 16:13:19.729: E/AndroidRuntime(4238): 	at android.app.ActivityThread.handleLaunchActivity(ActivityThread.java:1663)
11-20 16:13:19.729: E/AndroidRuntime(4238): 	at android.app.ActivityThread.access$1500(ActivityThread.java:117)
11-20 16:13:19.729: E/AndroidRuntime(4238): 	at android.app.ActivityThread$H.handleMessage(ActivityThread.java:931)
11-20 16:13:19.729: E/AndroidRuntime(4238): 	at android.os.Handler.dispatchMessage(Handler.java:99)
11-20 16:13:19.729: E/AndroidRuntime(4238): 	at android.os.Looper.loop(Looper.java:123)
11-20 16:13:19.729: E/AndroidRuntime(4238): 	at android.app.ActivityThread.main(ActivityThread.java:3683)
11-20 16:13:19.729: E/AndroidRuntime(4238): 	at java.lang.reflect.Method.invokeNative(Native Method)
11-20 16:13:19.729: E/AndroidRuntime(4238): 	at java.lang.reflect.Method.invoke(Method.java:507)
11-20 16:13:19.729: E/AndroidRuntime(4238): 	at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:839)
11-20 16:13:19.729: E/AndroidRuntime(4238): 	at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:597)
11-20 16:13:19.729: E/AndroidRuntime(4238): 	at dalvik.system.NativeStart.main(Native Method)
11-20 16:13:19.729: E/AndroidRuntime(4238): Caused by: java.lang.NullPointerException
11-20 16:13:19.729: E/AndroidRuntime(4238): 	at org.wordpress.android.EditPost.setContent(EditPost.java:941)
11-20 16:13:19.729: E/AndroidRuntime(4238): 	at org.wordpress.android.EditPost.onCreate(EditPost.java:166)
11-20 16:13:19.729: E/AndroidRuntime(4238): 	at android.app.Instrumentation.callActivityOnCreate(Instrumentation.java:1047)
11-20 16:13:19.729: E/AndroidRuntime(4238): 	at android.app.ActivityThread.performLaunchActivity(ActivityThread.java:1611)
11-20 16:13:19.729: E/AndroidRuntime(4238): 	... 11 more
11-20 16:13:19.770: W/ActivityManager(61):   Force finishing activity org.wordpress.android/.EditPost
```

The bug was solved by calling the EditPost.setContent() method after the content view is set for the activity.

See: [Ticket 218](https://android.trac.wordpress.org/ticket/218) - [Changeset 446](https://android.trac.wordpress.org/changeset/446)

## Wrong Login and Stats ##

#### The app crashes when refreshing the Stats if the user/pass pair is wrong ####

Changeset 424 crashes when the user tries to refresh the Stats, if a wrong user/pass pair was provided in the Settings.

To reproduce the bug:

  * Click Settings
  * Change the user name and/or the password
  * Push the BACK key
  * Click Stats
  * Wait for the login request
  * Press the refresh button (double arrows)

```
11-14 14:40:23.975: E/AndroidRuntime(1460): FATAL EXCEPTION: Thread-82
11-14 14:40:23.975: E/AndroidRuntime(1460): android.view.ViewRoot$CalledFromWrongThreadException: Only the original thread that created a view hierarchy can touch its views.
11-14 14:40:23.975: E/AndroidRuntime(1460): 	at android.view.ViewRoot.checkThread(ViewRoot.java:2932)
11-14 14:40:23.975: E/AndroidRuntime(1460): 	at android.view.ViewRoot.requestLayout(ViewRoot.java:629)
11-14 14:40:23.975: E/AndroidRuntime(1460): 	at android.view.View.requestLayout(View.java:8267)
11-14 14:40:23.975: E/AndroidRuntime(1460): 	at android.view.View.requestLayout(View.java:8267)
11-14 14:40:23.975: E/AndroidRuntime(1460): 	at android.view.View.requestLayout(View.java:8267)
11-14 14:40:23.975: E/AndroidRuntime(1460): 	at android.widget.RelativeLayout.requestLayout(RelativeLayout.java:257)
11-14 14:40:23.975: E/AndroidRuntime(1460): 	at android.view.View.requestLayout(View.java:8267)
11-14 14:40:23.975: E/AndroidRuntime(1460): 	at android.widget.RelativeLayout.requestLayout(RelativeLayout.java:257)
11-14 14:40:23.975: E/AndroidRuntime(1460): 	at android.view.View.requestLayout(View.java:8267)
11-14 14:40:23.975: E/AndroidRuntime(1460): 	at android.widget.RelativeLayout.requestLayout(RelativeLayout.java:257)
11-14 14:40:23.975: E/AndroidRuntime(1460): 	at android.view.View.requestLayout(View.java:8267)
11-14 14:40:23.975: E/AndroidRuntime(1460): 	at android.widget.RelativeLayout.requestLayout(RelativeLayout.java:257)
11-14 14:40:23.975: E/AndroidRuntime(1460): 	at android.view.View.requestLayout(View.java:8267)
11-14 14:40:23.975: E/AndroidRuntime(1460): 	at android.view.View.requestLayout(View.java:8267)
11-14 14:40:23.975: E/AndroidRuntime(1460): 	at android.widget.ImageView.setImageDrawable(ImageView.java:322)
11-14 14:40:23.975: E/AndroidRuntime(1460): 	at org.wordpress.android.util.WPTitleBar.stopRotatingRefreshIcon(WPTitleBar.java:400)
11-14 14:40:23.975: E/AndroidRuntime(1460): 	at org.wordpress.android.ViewStats.getStatsData(ViewStats.java:798)
11-14 14:40:23.975: E/AndroidRuntime(1460): 	at org.wordpress.android.ViewStats.access$1(ViewStats.java:322)
11-14 14:40:23.975: E/AndroidRuntime(1460): 	at org.wordpress.android.ViewStats$2$1.run(ViewStats.java:174)
```

When the login fails, the app asks for the correct user name and password, and expects the user to either provide them or cancel the operation. The click on the refresh button is probably unhandled.

The bug disappeared when the stats loading was changed to use an AsyncTask.

See: [Ticket 217](https://android.trac.wordpress.org/ticket/217) - [Changeset 447](https://android.trac.wordpress.org/changeset/447)


## Refresh Stats Crash ##

#### The app crashes when the Go button is pressed in the Stats activity ####

Rev. 422 crashes when attempting to reload the Stats.

To reproduce the bug:

  * Click Stats
  * Wait for the stats loading
  * Click Go

```
11-14 13:36:33.609: W/dalvikvm(372): threadid=1: thread exiting with uncaught exception (group=0x40015560)
11-14 13:36:33.619: E/AndroidRuntime(372): FATAL EXCEPTION: main
11-14 13:36:33.619: E/AndroidRuntime(372): java.lang.NullPointerException
11-14 13:36:33.619: E/AndroidRuntime(372): 	at org.wordpress.android.ViewStats$2.onClick(ViewStats.java:174)
11-14 13:36:33.619: E/AndroidRuntime(372): 	at android.view.View.performClick(View.java:2485)
11-14 13:36:33.619: E/AndroidRuntime(372): 	at android.view.View$PerformClick.run(View.java:9080)
11-14 13:36:33.619: E/AndroidRuntime(372): 	at android.os.Handler.handleCallback(Handler.java:587)
11-14 13:36:33.619: E/AndroidRuntime(372): 	at android.os.Handler.dispatchMessage(Handler.java:92)
11-14 13:36:33.619: E/AndroidRuntime(372): 	at android.os.Looper.loop(Looper.java:123)
11-14 13:36:33.619: E/AndroidRuntime(372): 	at android.app.ActivityThread.main(ActivityThread.java:3683)
11-14 13:36:33.619: E/AndroidRuntime(372): 	at java.lang.reflect.Method.invokeNative(Native Method)
11-14 13:36:33.619: E/AndroidRuntime(372): 	at java.lang.reflect.Method.invoke(Method.java:507)
11-14 13:36:33.619: E/AndroidRuntime(372): 	at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:839)
11-14 13:36:33.619: E/AndroidRuntime(372): 	at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:597)
11-14 13:36:33.619: E/AndroidRuntime(372): 	at dalvik.system.NativeStart.main(Native Method)
```

The issue apparently had something to do with the spinning wheel dialog. It was solved using the animated double arrow in the upper right corner instead of the dialog.

See: [Ticket 212](https://android.trac.wordpress.org/ticket/212) - [Changeset 423](https://android.trac.wordpress.org/changeset/423)

## Geotag Crash ##

#### Geotagging enabled leads to a crash while editing a page ####

Rev. 421 crashes when attempting to edit a page with geotagging enabled. The problem is likely to be with an element in the layout and not with the geotag function in itself since it happens while creating the UI widgets.

To reproduce the bug:

  * Click Settings
  * Click Geotag Posts
  * Click Save
  * Click Pages
  * Click "About" (or any other page)
  * Click the Edit imagebutton (the notepad icon in the upper right corner)

```
11-11 17:35:08.427: D/AndroidRuntime(2113): Shutting down VM
11-11 17:35:08.427: W/dalvikvm(2113): threadid=1: thread exiting with uncaught exception (group=0x40015560)
11-11 17:35:08.457: E/AndroidRuntime(2113): FATAL EXCEPTION: main
11-11 17:35:08.457: E/AndroidRuntime(2113): java.lang.RuntimeException: Unable to start activity ComponentInfo{org.wordpress.android/org.wordpress.android.EditPost}: java.lang.NullPointerException
11-11 17:35:08.457: E/AndroidRuntime(2113): 	at android.app.ActivityThread.performLaunchActivity(ActivityThread.java:1647)
11-11 17:35:08.457: E/AndroidRuntime(2113): 	at android.app.ActivityThread.handleLaunchActivity(ActivityThread.java:1663)
11-11 17:35:08.457: E/AndroidRuntime(2113): 	at android.app.ActivityThread.access$1500(ActivityThread.java:117)
11-11 17:35:08.457: E/AndroidRuntime(2113): 	at android.app.ActivityThread$H.handleMessage(ActivityThread.java:931)
11-11 17:35:08.457: E/AndroidRuntime(2113): 	at android.os.Handler.dispatchMessage(Handler.java:99)
11-11 17:35:08.457: E/AndroidRuntime(2113): 	at android.os.Looper.loop(Looper.java:123)
11-11 17:35:08.457: E/AndroidRuntime(2113): 	at android.app.ActivityThread.main(ActivityThread.java:3683)
11-11 17:35:08.457: E/AndroidRuntime(2113): 	at java.lang.reflect.Method.invokeNative(Native Method)
11-11 17:35:08.457: E/AndroidRuntime(2113): 	at java.lang.reflect.Method.invoke(Method.java:507)
11-11 17:35:08.457: E/AndroidRuntime(2113): 	at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:839)
11-11 17:35:08.457: E/AndroidRuntime(2113): 	at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:597)
11-11 17:35:08.457: E/AndroidRuntime(2113): 	at dalvik.system.NativeStart.main(Native Method)
11-11 17:35:08.457: E/AndroidRuntime(2113): Caused by: java.lang.NullPointerException
11-11 17:35:08.457: E/AndroidRuntime(2113): 	at org.wordpress.android.EditPost.enableLBSButtons(EditPost.java:510)
11-11 17:35:08.457: E/AndroidRuntime(2113): 	at org.wordpress.android.EditPost.onCreate(EditPost.java:393)
11-11 17:35:08.457: E/AndroidRuntime(2113): 	at android.app.Instrumentation.callActivityOnCreate(Instrumentation.java:1047)
11-11 17:35:08.457: E/AndroidRuntime(2113): 	at android.app.ActivityThread.performLaunchActivity(ActivityThread.java:1611)
11-11 17:35:08.457: E/AndroidRuntime(2113): 	... 11 more
```

The crash is due to the fact that the geolocation widget is only defined in the layout for editing posts, not in the page editor.
The solution was to make sure that that function is bypassed when we're editing a page.

See: [Ticket 211](https://android.trac.wordpress.org/ticket/211) - [Changeset 422](https://android.trac.wordpress.org/changeset/422)

## Ghost Page ##

#### Attempt to open a non existing page when clicking on the headers of the list ####

Rev 419 crashes when clicking on the _Pages_ label in the pages list. The crash seems to be dependent to the last opened page.
To reproduce the bug, make sure to have a Local draft saved, then:

  * Click _Pages_
  * Press the home button (in the upper right corner)
  * Press the BACK key
  * Press the _Pages_ label

```
11-11 14:46:32.763: E/AndroidRuntime(557): FATAL EXCEPTION: main
11-11 14:46:32.763: E/AndroidRuntime(557): android.database.CursorIndexOutOfBoundsException: Index 0 requested, with a size of 0
11-11 14:46:32.763: E/AndroidRuntime(557): 	at android.database.AbstractCursor.checkPosition(AbstractCursor.java:580)
11-11 14:46:32.763: E/AndroidRuntime(557): 	at android.database.AbstractWindowedCursor.checkPosition(AbstractWindowedCursor.java:214)
11-11 14:46:32.763: E/AndroidRuntime(557): 	at android.database.AbstractWindowedCursor.getString(AbstractWindowedCursor.java:41)
11-11 14:46:32.763: E/AndroidRuntime(557): 	at org.wordpress.android.WordPressDB.loadPost(WordPressDB.java:1169)
11-11 14:46:32.763: E/AndroidRuntime(557): 	at org.wordpress.android.models.Post.<init>(Post.java:84)
11-11 14:46:32.763: E/AndroidRuntime(557): 	at org.wordpress.android.ViewPosts$2.onItemClick(ViewPosts.java:316)
11-11 14:46:32.763: E/AndroidRuntime(557): 	at android.widget.AdapterView.performItemClick(AdapterView.java:284)
11-11 14:46:32.763: E/AndroidRuntime(557): 	at android.widget.ListView.performItemClick(ListView.java:3513)
11-11 14:46:32.763: E/AndroidRuntime(557): 	at android.widget.AbsListView$PerformClick.run(AbsListView.java:1812)
11-11 14:46:32.763: E/AndroidRuntime(557): 	at android.os.Handler.handleCallback(Handler.java:587)
11-11 14:46:32.763: E/AndroidRuntime(557): 	at android.os.Handler.dispatchMessage(Handler.java:92)
11-11 14:46:32.763: E/AndroidRuntime(557): 	at android.os.Looper.loop(Looper.java:123)
11-11 14:46:32.763: E/AndroidRuntime(557): 	at android.app.ActivityThread.main(ActivityThread.java:3683)
11-11 14:46:32.763: E/AndroidRuntime(557): 	at java.lang.reflect.Method.invokeNative(Native Method)
11-11 14:46:32.763: E/AndroidRuntime(557): 	at java.lang.reflect.Method.invoke(Method.java:507)
11-11 14:46:32.763: E/AndroidRuntime(557): 	at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:839)
11-11 14:46:32.763: E/AndroidRuntime(557): 	at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:597)
11-11 14:46:32.763: E/AndroidRuntime(557): 	at dalvik.system.NativeStart.main(Native Method)
```

The crash is caused by the click on the header of the list, which is not supposed to trigger an event, but which does.
The solution was to ignore clicks on the headers.

See: [Ticket 210](https://android.trac.wordpress.org/ticket/210) - [Changeset 421](https://android.trac.wordpress.org/changeset/421)

## View in Browser ##

#### Refreshing the web view then opening it in an external browser yields crash ####

All rev.s from 412 to 418 suffer a crash when the following sequence of events is played in rapid succession:

  * Click _Read_
  * Press the MENU key
  * Click _View in Browser_

```
11-10 20:27:24.251: W/dalvikvm(12946): threadid=1: thread exiting with uncaught exception (group=0x40015560)
11-10 20:27:24.271: E/AndroidRuntime(12946): FATAL EXCEPTION: main
11-10 20:27:24.271: E/AndroidRuntime(12946): android.content.ActivityNotFoundException: No Activity found to handle Intent { act=android.intent.action.VIEW dat=data:text/html;UTF-8,%3Chead%3E%3Cscript%20type=%22text/javascript%22%3Efunction%20submitform(){document.loginform.submit();}%20%3C/script%3E%3C/head%3E%3Cbody%20onload=%22submitform()%22%3E%3Cform%20style=%22visibility:hidden;%22%20name=%22loginform%22%20id=%22loginform%22%20action=%22https://androidcrawler.wordpress.com/wp-login.php%22%20method=%22post%22%3E%3Cinput%20type=%22text%22%20name=%22log%22%20id=%22user_login%22%20value=%22androidcrawler69%22/%3E%3C/label%3E%3Cinput%20type=%22password%22%20name=%22pwd%22%20id=%22user_pass%22%20value=%22paran01d75%22%20/%3E%3C/label%3E%3Cinput%20type=%22submit%22%20name=%22wp-submit%22%20id=%22wp-submit%22%20value=%22Log%20In%22%20/%3E%3Cinput%20type=%22hidden%22%20name=%22redirect_to%22%20value=%22http://en.wordpress.com/reader/mobile/v2%22%20/%3E%3C/form%3E%3C/body%3E }
11-10 20:27:24.271: E/AndroidRuntime(12946): 	at android.app.Instrumentation.checkStartActivityResult(Instrumentation.java:1409)
11-10 20:27:24.271: E/AndroidRuntime(12946): 	at android.app.Instrumentation.execStartActivity(Instrumentation.java:1379)
11-10 20:27:24.271: E/AndroidRuntime(12946): 	at android.app.Activity.startActivityForResult(Activity.java:2827)
11-10 20:27:24.271: E/AndroidRuntime(12946): 	at android.app.Activity.startActivity(Activity.java:2933)
11-10 20:27:24.271: E/AndroidRuntime(12946): 	at org.wordpress.android.Read.onOptionsItemSelected(Read.java:130)
11-10 20:27:24.271: E/AndroidRuntime(12946): 	at android.app.Activity.onMenuItemSelected(Activity.java:2205)
11-10 20:27:24.271: E/AndroidRuntime(12946): 	at com.android.internal.policy.impl.PhoneWindow.onMenuItemSelected(PhoneWindow.java:748)
11-10 20:27:24.271: E/AndroidRuntime(12946): 	at com.android.internal.view.menu.MenuItemImpl.invoke(MenuItemImpl.java:143)
11-10 20:27:24.271: E/AndroidRuntime(12946): 	at com.android.internal.view.menu.MenuBuilder.performItemAction(MenuBuilder.java:855)
11-10 20:27:24.271: E/AndroidRuntime(12946): 	at com.android.internal.view.menu.IconMenuView.invokeItem(IconMenuView.java:532)
11-10 20:27:24.271: E/AndroidRuntime(12946): 	at com.android.internal.view.menu.IconMenuItemView.performClick(IconMenuItemView.java:122)
11-10 20:27:24.271: E/AndroidRuntime(12946): 	at android.view.View$PerformClick.run(View.java:9080)
11-10 20:27:24.271: E/AndroidRuntime(12946): 	at android.os.Handler.handleCallback(Handler.java:587)
11-10 20:27:24.271: E/AndroidRuntime(12946): 	at android.os.Handler.dispatchMessage(Handler.java:92)
11-10 20:27:24.271: E/AndroidRuntime(12946): 	at android.os.Looper.loop(Looper.java:123)
11-10 20:27:24.271: E/AndroidRuntime(12946): 	at android.app.ActivityThread.main(ActivityThread.java:3683)
11-10 20:27:24.271: E/AndroidRuntime(12946): 	at java.lang.reflect.Method.invokeNative(Native Method)
11-10 20:27:24.271: E/AndroidRuntime(12946): 	at java.lang.reflect.Method.invoke(Method.java:507)
11-10 20:27:24.271: E/AndroidRuntime(12946): 	at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:839)
11-10 20:27:24.271: E/AndroidRuntime(12946): 	at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:597)
11-10 20:27:24.271: E/AndroidRuntime(12946): 	at dalvik.system.NativeStart.main(Native Method)
11-10 20:27:24.291: W/ActivityManager(61): Error in app org.wordpress.android running instrumentation ComponentInfo{com.nofatclips.crawler/android.test.InstrumentationTestRunner}:
11-10 20:27:24.291: W/ActivityManager(61):   android.content.ActivityNotFoundException
11-10 20:27:24.291: W/ActivityManager(61):   android.content.ActivityNotFoundException: No Activity found to handle Intent { act=android.intent.action.VIEW dat=data:text/html;UTF-8,%3Chead%3E%3Cscript%20type=%22text/javascript%22%3Efunction%20submitform(){document.loginform.submit();}%20%3C/script%3E%3C/head%3E%3Cbody%20onload=%22submitform()%22%3E%3Cform%20style=%22visibility:hidden;%22%20name=%22loginform%22%20id=%22loginform%22%20action=%22https://androidcrawler.wordpress.com/wp-login.php%22%20method=%22post%22%3E%3Cinput%20type=%22text%22%20name=%22log%22%20id=%22user_login%22%20value=%22androidcrawler69%22/%3E%3C/label%3E%3Cinput%20type=%22password%22%20name=%22pwd%22%20id=%22user_pass%22%20value=%22paran01d75%22%20/%3E%3C/label%3E%3Cinput%20type=%22submit%22%20name=%22wp-submit%22%20id=%22wp-submit%22%20value=%22Log%20In%22%20/%3E%3Cinput%20type=%22hidden%22%20name=%22redirect_to%22%20value=%22http://en.wordpress.com/reader/mobile/v2%22%20/%3E%3C/form%3E%3C/body%3E }
```

The problem arises when the user choose to open the blog in the browser before it's been loaded in the app (that is, while the progress bar is still shown in the title bar.)
It's been solved by ignoring the command until the login to the blog succeeded.

See: [Ticket 209](https://android.trac.wordpress.org/ticket/209) - [Changeset 419](https://android.trac.wordpress.org/changeset/419)

## Stats and Back ##

#### Bad Token Exception when dismissing stats too fast ####

Rev. 412 suffers a crash when the Stats page is opened and closed in rapid succession. To reproduce the bug:

  * Click _Stats_
  * Click the BACK key even before the throbber appears
  * Wait

```
11-10 14:28:40.664: W/dalvikvm(388): threadid=1: thread exiting with uncaught exception (group=0x40015560)
11-10 14:28:40.673: E/AndroidRuntime(388): FATAL EXCEPTION: main
11-10 14:28:40.673: E/AndroidRuntime(388): android.view.WindowManager$BadTokenException: Unable to add window -- token android.os.BinderProxy@4065a618 is not valid; is your activity running?
11-10 14:28:40.673: E/AndroidRuntime(388): 	at android.view.ViewRoot.setView(ViewRoot.java:527)
11-10 14:28:40.673: E/AndroidRuntime(388): 	at android.view.WindowManagerImpl.addView(WindowManagerImpl.java:177)
11-10 14:28:40.673: E/AndroidRuntime(388): 	at android.view.WindowManagerImpl.addView(WindowManagerImpl.java:91)
11-10 14:28:40.673: E/AndroidRuntime(388): 	at android.view.Window$LocalWindowManager.addView(Window.java:424)
11-10 14:28:40.673: E/AndroidRuntime(388): 	at android.app.Dialog.show(Dialog.java:241)
11-10 14:28:40.673: E/AndroidRuntime(388): 	at android.app.Activity.showDialog(Activity.java:2566)
11-10 14:28:40.673: E/AndroidRuntime(388): 	at android.app.Activity.showDialog(Activity.java:2524)
11-10 14:28:40.673: E/AndroidRuntime(388): 	at org.wordpress.android.ViewStats$statsUserDataTask.onPostExecute(ViewStats.java:1012)
11-10 14:28:40.673: E/AndroidRuntime(388): 	at org.wordpress.android.ViewStats$statsUserDataTask.onPostExecute(ViewStats.java:1)
11-10 14:28:40.673: E/AndroidRuntime(388): 	at android.os.AsyncTask.finish(AsyncTask.java:417)
11-10 14:28:40.673: E/AndroidRuntime(388): 	at android.os.AsyncTask.access$300(AsyncTask.java:127)
11-10 14:28:40.673: E/AndroidRuntime(388): 	at android.os.AsyncTask$InternalHandler.handleMessage(AsyncTask.java:429)
11-10 14:28:40.673: E/AndroidRuntime(388): 	at android.os.Handler.dispatchMessage(Handler.java:99)
11-10 14:28:40.673: E/AndroidRuntime(388): 	at android.os.Looper.loop(Looper.java:123)
11-10 14:28:40.673: E/AndroidRuntime(388): 	at android.app.ActivityThread.main(ActivityThread.java:3683)
11-10 14:28:40.673: E/AndroidRuntime(388): 	at java.lang.reflect.Method.invokeNative(Native Method)
11-10 14:28:40.673: E/AndroidRuntime(388): 	at java.lang.reflect.Method.invoke(Method.java:507)
11-10 14:28:40.673: E/AndroidRuntime(388): 	at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:839)
11-10 14:28:40.673: E/AndroidRuntime(388): 	at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:597)
11-10 14:28:40.673: E/AndroidRuntime(388): 	at dalvik.system.NativeStart.main(Native Method)
```

The problem arises when the user closes the ViewStats activity before anything happens on screen.
The crash was caused by the lack of a check on the state of the activity: the stats are loaded asynchronously and displayed when the task is finished. If the Stats activity gets closed before the async task finishes, the application crashes.
It was solved by checking that the ViewStats activity is not in the "finishing" state, before showing or dismissing the statistics.

See: [Ticket 208](https://android.trac.wordpress.org/ticket/208) - [Changeset 420](https://android.trac.wordpress.org/changeset/420)

## Crash On Edit Page ##

#### Cursor Error when trying to edit a page ####

All rev.s from 398 to 409 crash when trying to edit a post for the second time. To reproduce the bug, setup a new blog, then:

  * Click _Pages_
  * Click _About_
  * Click the edit button (the notepad right after the title)
  * Push the BACK key
  * Click the edit button again

```
11-10 12:07:02.163: E/AndroidRuntime(521): FATAL EXCEPTION: main
11-10 12:07:02.163: E/AndroidRuntime(521): java.lang.RuntimeException: Unable to start activity ComponentInfo{org.wordpress.android/org.wordpress.android.EditPost}: android.database.CursorIndexOutOfBoundsException: Index 0 requested, with a size of 0
11-10 12:07:02.163: E/AndroidRuntime(521): 	at android.app.ActivityThread.performLaunchActivity(ActivityThread.java:1647)
11-10 12:07:02.163: E/AndroidRuntime(521): 	at android.app.ActivityThread.handleLaunchActivity(ActivityThread.java:1663)
11-10 12:07:02.163: E/AndroidRuntime(521): 	at android.app.ActivityThread.access$1500(ActivityThread.java:117)
11-10 12:07:02.163: E/AndroidRuntime(521): 	at android.app.ActivityThread$H.handleMessage(ActivityThread.java:931)
11-10 12:07:02.163: E/AndroidRuntime(521): 	at android.os.Handler.dispatchMessage(Handler.java:99)
11-10 12:07:02.163: E/AndroidRuntime(521): 	at android.os.Looper.loop(Looper.java:123)
11-10 12:07:02.163: E/AndroidRuntime(521): 	at android.app.ActivityThread.main(ActivityThread.java:3683)
11-10 12:07:02.163: E/AndroidRuntime(521): 	at java.lang.reflect.Method.invokeNative(Native Method)
11-10 12:07:02.163: E/AndroidRuntime(521): 	at java.lang.reflect.Method.invoke(Method.java:507)
11-10 12:07:02.163: E/AndroidRuntime(521): 	at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:839)
11-10 12:07:02.163: E/AndroidRuntime(521): 	at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:597)
11-10 12:07:02.163: E/AndroidRuntime(521): 	at dalvik.system.NativeStart.main(Native Method)
11-10 12:07:02.163: E/AndroidRuntime(521): Caused by: android.database.CursorIndexOutOfBoundsException: Index 0 requested, with a size of 0
11-10 12:07:02.163: E/AndroidRuntime(521): 	at android.database.AbstractCursor.checkPosition(AbstractCursor.java:580)
11-10 12:07:02.163: E/AndroidRuntime(521): 	at android.database.AbstractWindowedCursor.checkPosition(AbstractWindowedCursor.java:214)
11-10 12:07:02.163: E/AndroidRuntime(521): 	at android.database.AbstractWindowedCursor.getString(AbstractWindowedCursor.java:41)
11-10 12:07:02.163: E/AndroidRuntime(521): 	at org.wordpress.android.WordPressDB.loadPost(WordPressDB.java:1153)
11-10 12:07:02.163: E/AndroidRuntime(521): 	at org.wordpress.android.models.Post.<init>(Post.java:85)
11-10 12:07:02.163: E/AndroidRuntime(521): 	at org.wordpress.android.EditPost.onCreate(EditPost.java:191)
11-10 12:07:02.163: E/AndroidRuntime(521): 	at android.app.Instrumentation.callActivityOnCreate(Instrumentation.java:1047)
11-10 12:07:02.163: E/AndroidRuntime(521): 	at android.app.ActivityThread.performLaunchActivity(ActivityThread.java:1611)
11-10 12:07:02.163: E/AndroidRuntime(521): 	... 11 more
11-10 12:07:02.183: W/ActivityManager(61): Error in app org.wordpress.android running instrumentation ComponentInfo{com.nofatclips.crawler/android.test.InstrumentationTestRunner}:
11-10 12:07:02.183: W/ActivityManager(61):   android.database.CursorIndexOutOfBoundsException
11-10 12:07:02.183: W/ActivityManager(61):   android.database.CursorIndexOutOfBoundsException: Index 0 requested, with a size of 0
```

The problem was solved by adding additional information in the Intent launched to perform the task. Since the same code is used both for pages and posts, metadata are required to distinguish the two cases.

See: [Ticket 207](https://android.trac.wordpress.org/ticket/207) - [Changeset 412](https://android.trac.wordpress.org/changeset/412)

## Hello World ##

#### Unable to open default "Hello World!" post ####

The rev. 394 crashes when trying to open the default post. To reproduce the bug, setup a new blog, then:

  * Click _Posts_
  * Click _Hello World!_

```
11-09 21:38:43.496: E/AndroidRuntime(870): FATAL EXCEPTION: main
11-09 21:38:43.496: E/AndroidRuntime(870): java.lang.StringIndexOutOfBoundsException
11-09 21:38:43.496: E/AndroidRuntime(870): 	at java.lang.String.substring(String.java:1651)
11-09 21:38:43.496: E/AndroidRuntime(870): 	at org.wordpress.android.util.HtmlToSpannedConverter.characters(WPHtml.java:928)
11-09 21:38:43.496: E/AndroidRuntime(870): 	at org.ccil.cowan.tagsoup.Parser.pcdata(Parser.java:994)
11-09 21:38:43.496: E/AndroidRuntime(870): 	at org.ccil.cowan.tagsoup.HTMLScanner.scan(HTMLScanner.java:522)
11-09 21:38:43.496: E/AndroidRuntime(870): 	at org.ccil.cowan.tagsoup.Parser.parse(Parser.java:449)
11-09 21:38:43.496: E/AndroidRuntime(870): 	at org.wordpress.android.util.HtmlToSpannedConverter.convert(WPHtml.java:455)
11-09 21:38:43.496: E/AndroidRuntime(870): 	at org.wordpress.android.util.WPHtml.fromHtml(WPHtml.java:154)
11-09 21:38:43.496: E/AndroidRuntime(870): 	at org.wordpress.android.util.WPHtml.fromHtml(WPHtml.java:117)
11-09 21:38:43.496: E/AndroidRuntime(870): 	at org.wordpress.android.ViewPostFragment.loadPost(ViewPostFragment.java:98)
11-09 21:38:43.496: E/AndroidRuntime(870): 	at org.wordpress.android.ViewPostFragment.onActivityCreated(ViewPostFragment.java:26)
11-09 21:38:43.496: E/AndroidRuntime(870): 	at android.support.v4.app.FragmentManagerImpl.moveToState(FragmentManager.java:760)
11-09 21:38:43.496: E/AndroidRuntime(870): 	at android.support.v4.app.FragmentManagerImpl.moveToState(FragmentManager.java:933)
11-09 21:38:43.496: E/AndroidRuntime(870): 	at android.support.v4.app.BackStackRecord.run(BackStackRecord.java:578)
11-09 21:38:43.496: E/AndroidRuntime(870): 	at android.support.v4.app.FragmentManagerImpl.execPendingActions(FragmentManager.java:1219)
11-09 21:38:43.496: E/AndroidRuntime(870): 	at android.support.v4.app.FragmentManagerImpl$1.run(FragmentManager.java:380)
11-09 21:38:43.496: E/AndroidRuntime(870): 	at android.os.Handler.handleCallback(Handler.java:587)
11-09 21:38:43.496: E/AndroidRuntime(870): 	at android.os.Handler.dispatchMessage(Handler.java:92)
11-09 21:38:43.496: E/AndroidRuntime(870): 	at android.os.Looper.loop(Looper.java:123)
11-09 21:38:43.496: E/AndroidRuntime(870): 	at android.app.ActivityThread.main(ActivityThread.java:3683)
11-09 21:38:43.496: E/AndroidRuntime(870): 	at java.lang.reflect.Method.invokeNative(Native Method)
11-09 21:38:43.496: E/AndroidRuntime(870): 	at java.lang.reflect.Method.invoke(Method.java:507)
11-09 21:38:43.496: E/AndroidRuntime(870): 	at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:839)
11-09 21:38:43.496: E/AndroidRuntime(870): 	at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:597)
11-09 21:38:43.496: E/AndroidRuntime(870): 	at dalvik.system.NativeStart.main(Native Method)
```

The bug was caused by the lack of control on the length of a substring extracted from the body of the post when an unsupported HTML tag is found. This led to the discovery of an additional bug, since the OL tag was not recognized as standard HTML.

See: [Ticket 206](https://android.trac.wordpress.org/ticket/206) - [Changeset 398](https://android.trac.wordpress.org/changeset/398)

# Test Results #

## Test Results from 29 Nov 2011 (Changeset 452 w\ AndCrawler r-104) ##


This test was performed on the latest and more stable release of Wordpress in order to evaluate the code coverage that the current version of the crawler manages to achieve.

OVERALL COVERAGE SUMMARY
| name	| class, %	| method, %	| block, %	| line, % |
|:-----|:---------|:----------|:---------|:--------|
| all classes	| 57%  (139/246)	| 54%  (519/965)	| 49%  (16587/33693)	| 48%  (3686,1/7737) |

Input and output of the test are provided in this package:

  * Parameters and outputs: https://gui2efg2junit.googlecode.com/files/wordpress2beta_20111129.zip
  * Report: http://gui2efg2junit.googlecode.com/files/wordpress2_20111129.xhtml

## Test Results from 23 Nov 2011 (Changeset 434 w\ AndCrawler r-99) ##

This test was conducted to find out if the [Ghost Spam Comment](#Ghost_Spam_Comment.md) was fixed in the latest available revision. A different crash occurence was found instead: [Stats Crash](#Stats_Crash.md). Since resuming was disabled, the session stopped.

  * Complete test result package: http://gui2efg2junit.googlecode.com/files/wordpress2_20111123.zip

The bug was already known:

https://android.trac.wordpress.org/ticket/215

It has been fixed in [Changeset 437](https://android.trac.wordpress.org/changeset/437).

## Test Results from 20 Nov 2011 (Changeset 394 w\ AndCrawler r-97) ##

This test on an older version of WP was conducted to try the new resuming feature of AndCrawler rev.97 in order to find out how many of the issues found so far we're able to detect with a single smoke test session. Settings and results are provided:

  * Complete test result package: https://gui2efg2junit.googlecode.com/files/wordpress2_20111120.zip

Crashes found by this session:

  * [Share with Wordpress](#Share_with_Wordpress.md)
  * [Edit Page](#Crash_On_Edit_Page.md)
  * [Ghost Spam Comment](#Ghost_Spam_Comment.md).

Information on the fix are found in the respective sections.

## Test Results from 14 Nov 2011 (Changeset 424 w\ AndCrawler r-91) ##

This test was mainly conducted to try the new feature of AndCrawler rev.91 but also to provide a complete log which we weren't able to save in the previous session. The crawler stopped after 17 traces (time: 218 s) due to the [Stats with Wrong Login](#Wrong_Login_and_Stats.md) issue.

  * Complete log: http://gui2efg2junit.googlecode.com/files/wordpress2_20111114_complete_log.txt

## Test Results from 14 Nov 2011 (Changeset 424 w\ AndCrawler r-90) ##

The test stopped after 31 traces (time: approx 20 min.) due to the [Stats with Wrong Login](#Wrong_Login_and_Stats.md) issue reported above. Gui Tree for the first 20 traces and log for the last one are provided:

  * Gui Tree: http://gui2efg2junit.googlecode.com/files/wordpress2_20111114.xml
  * Log: http://gui2efg2junit.googlecode.com/files/wordpress2_20111114_log2.txt

Note that the user name had already been changed in the previous crawling session. Starting with the correct login and changing it leads the current version of the crawler to crash (see [Issue 7](https://code.google.com/p/android-crawler/issues/detail?id=7)).

## Test Results from 14 Nov 2011 (Changeset 422 w\ AndCrawler r-90) ##

The test stopped after 18 traces (time: 243 s) due to the [Refresh Stats](#Refresh_Stats_Crash.md) issue reported above. Complete log is provided:

  * Log: http://gui2efg2junit.googlecode.com/files/wordpress_20111114_log.txt

A bug report was filed:

https://android.trac.wordpress.org/ticket/212

The bug has been fixed in [Changeset 423](https://android.trac.wordpress.org/changeset/423).

## Test Results from 11 Nov 2011 (Changeset 421 w\ AndCrawler r-78) ##

[![](http://gui2efg2junit.googlecode.com/files/wordpress2_20111111.png)](http://code.google.com/p/gui2efg2junit/downloads/detail?name=wordpress2_20111111.png&can=2&q=)

The test stopped after 137 traces due to the [Geotag](#Geotag_Crash.md) issue reported above. Gui Tree for the first 135 traces and the log for the remaining two are provided:

  * Gui Tree: http://gui2efg2junit.googlecode.com/files/wordpress2_20111111_2.xml
  * Log: http://gui2efg2junit.googlecode.com/files/wp2_20111111_2log.txt

A bug report was filed:

https://android.trac.wordpress.org/ticket/211

The bug has been fixed in [Changeset 422](https://android.trac.wordpress.org/changeset/422).

## Test Results from 11 Nov 2011 (Changeset 419 with AndCrawler r-78) ##

The test stopped again with the same bug as the previous session.
Gui Tree for the first 20 traces and a log of the remaining four are provided along with complete jUnit test suite (the test cases for the cases 20 to 23 have been wrote manually):

  * Gui Tree: http://gui2efg2junit.googlecode.com/files/wordpress2_20111111.xml
  * Log: http://gui2efg2junit.googlecode.com/files/wordpress2_crashlog2_20111111.txt%20.txt
  * jUnit: http://gui2efg2junit.googlecode.com/files/AndroidGuiTest.java

The jUnit suite made possible to see the bug happening and reproduce it manually. Details are listed in the [Ghost Page](#Ghost_Page.md) section.

A bug report was filed:

https://android.trac.wordpress.org/ticket/210

The bug has been fixed in [Changeset 421](https://android.trac.wordpress.org/changeset/421).

## Test Results from 11 Nov 2011 (Changeset 419 with AndCrawler r-77) ##

The test stopped due to a bug which was not possible to reproduce manually. (see [#Ghost\_Page](#Ghost_Page.md))
Complete log for the session is provided:

  * Log: http://gui2efg2junit.googlecode.com/files/wordpress2_crashlog_20111111.txt

The bug has been fixed in [Changeset 421](https://android.trac.wordpress.org/changeset/421).

## Test Results from 10 Nov 2011 (Changeset 412 with AndCrawler r-77) ##

[![](http://gui2efg2junit.googlecode.com/files/wordpress2.png)](http://code.google.com/p/gui2efg2junit/downloads/detail?name=wordpress2.png&can=2&q=)

The test stopped due to the [View in Browser](#View_in_Browser.md) issue reported above.
Gui tree for the first 80 traces and the log for the remaining 2 traces are provided:

  * Gui Tree: http://gui2efg2junit.googlecode.com/files/wordpress2_crash4.xml
  * Log: http://gui2efg2junit.googlecode.com/files/wordpress2_crash4_log.txt

A bug report was filed:

https://android.trac.wordpress.org/ticket/209

The bug has been fixed in [Changeset 419](https://android.trac.wordpress.org/changeset/419).

## Test Results from 10 Nov 2011 (Changeset 412 with AndCrawler r-76) ##

The test stopped due to the [Stats and Back](#Stats_and_Back.md) issue reported above.
Complete log is provided:

  * Log: http://gui2efg2junit.googlecode.com/files/wordpress2_crashlog_20111110_bis.txt

A bug report was filed:

https://android.trac.wordpress.org/ticket/208

The bug has been fixed in [Changeset 420](https://android.trac.wordpress.org/changeset/420).

## Test Results from 10 Nov 2011 (Changeset 398 with AndCrawler r-76) ##

The test stopped due to the [Edit Page](#Crash_On_Edit_Page.md) issue reported above.
Incomplete GUI Tree (40 traces) and a log for the last trace (#48) are provided:

  * Gui Tree: http://gui2efg2junit.googlecode.com/files/wordpress2_20111110.xml
  * Log: http://gui2efg2junit.googlecode.com/files/wordpress2_crashlog_20111110.txt

A bug report was filed:

https://android.trac.wordpress.org/ticket/207

The bug has been fixed in [Changeset 412](https://android.trac.wordpress.org/changeset/412).

## Test Results from 09 Nov 2011 (Changeset 394 with AndCrawler r-75) ##

The test stopped due to the [Hello World!](#Hello_World.md) issue reported above. Complete log is provided:

http://gui2efg2junit.googlecode.com/files/wordpress2_crashlog_20111109.txt

A bug report was filed:

https://android.trac.wordpress.org/ticket/206

The bug has been fixed in [Changeset 398](https://android.trac.wordpress.org/changeset/398).