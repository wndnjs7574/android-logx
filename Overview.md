## Introduction ##
It is assumed that you are familiar with the AOSP (http://source.android.com/). You must have a local copy of the AOSP and be able to run it in the Android emulator.

## Installation ##
This project modifies certain parts of the AOSP. These changes are distributed as patches. Your linux distribution should come with patch installed, you will need it to apply the patches. If you
are in the root of your local copy of the AOSP, _patch -p2 < path\_to\_patch\_file_ should apply the patch correctly. Any files that were added to the frameworks/ and libcore/ directories are also included as java source files. Their directory trees represent where the files should be located in the AOSP directory.

## Build ##
You will need to build the entire AOSP. You can find instructions for that here: http://source.android.com/source/building.html.

If you have already built the AOSP, you can just rebuild the relevant portions and include them in the system image. This can be done with the following commands from the root of your local copy of the AOSP (make sure you have executed build/envsetup.sh):

  * `mmm frameworks/base snod`
  * `mmm libcore/ snod`

The changes should now be included in your system.img.

## View the Log ##
Android-logx prints to the standard android.util.log stream. To view the log, you can run `adb logcat -v time LogX:* *:S`, with adb in your path. In android.util.LogX.java, you can change what gets logged. Initially, it's both java.io.File and java.net.Socket. Any changes to LogX.java requires a rebuild of frameworks/base.