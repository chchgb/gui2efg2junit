# Introduction #

Tippy Tipper can be found here:

http://code.google.com/p/tippytipper/

# Issues #

Nothing to report.

# Test Results from 05 Nov 2011 (v1.2 with AndCrawler r-68) #

First test results. Comparation was done on buttons and dialogs.

[![](http://gui2efg2junit.googlecode.com/files/tippytipper.png)](http://code.google.com/p/gui2efg2junit/downloads/detail?name=tippytipper.png&can=2&q=)

OVERALL COVERAGE SUMMARY
| name| 	class, %| 	method, %| 	block, %| 	line, %|
|:----|:---------|:----------|:---------|:--------|
| all classes| 	78%  (42/54)| 	77%  (184/238)| 	75%  (3205/4286)| 	75%  (754,5/1009)|


  * GuiTree: http://gui2efg2junit.googlecode.com/files/tippytipper.xml
  * EFG: http://gui2efg2junit.googlecode.com/files/tippytipper_efg.xml
  * DOT: http://gui2efg2junit.googlecode.com/files/tippytipper.dot
  * jUnit: http://gui2efg2junit.googlecode.com/files/TippytipperGuiTest.java
  * Coverage: http://gui2efg2junit.googlecode.com/files/tippytipper_coverage.zip

## Analysis ##

Main issue with this app is the lack of support for the Seek Bar (crawler issue: https://code.google.com/p/android-crawler/issues/detail?id=14 ) used in the Settings. The coverage also shows that almost none of the constants in the R.java file is ever read, but nothing points to the evidence that they actually should.

Some of the methods not exercised refer to alternate method to operate on the same widget (i.e. via trackball)