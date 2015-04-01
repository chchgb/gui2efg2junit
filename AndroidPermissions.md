# Introduction #

Android Permissions can be found here:

http://code.google.com/p/androidpermissions/

The application is too simple, being composed by a single class, but it's the only app found so far using the ExpandableListView.

# Issues #

Crawling the Expandable List View as a normal List View leads to inefficient (or ineffective, depending on the comparation strategy) exploration. See:

https://code.google.com/p/android-crawler/issues/detail?id=19

# Test Results from 06 Nov 2011 (v1.1 with AndCrawler r-70) #

First test results. Comparation was done on text.

[![](http://gui2efg2junit.googlecode.com/files/permission.png)](http://code.google.com/p/gui2efg2junit/downloads/detail?name=permission.png&can=2&q=)

  * GuiTree: http://gui2efg2junit.googlecode.com/files/permissions.xml
  * EFG: http://gui2efg2junit.googlecode.com/files/permissions_efg.xml
  * DOT: http://gui2efg2junit.googlecode.com/files/permissions.dot
  * http://gui2efg2junit.googlecode.com/files/PermissionsGuiTest.java

## Analysis ##

It's quite apparent that the crawler clicks the same option over and over, since there is no direct support for ExpandableListView. See:

https://code.google.com/p/android-crawler/issues/detail?id=19