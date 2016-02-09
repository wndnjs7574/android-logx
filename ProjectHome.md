## Introduction ##
Android-logx is a framework that facilitates the logging of Java API calls from within the AOSP. This is particularly useful in dynamically analyzing the behavior of applications running on Android. The framework is more useful than a protocol sniffer because it has access to information about processes running on the system. For example, android-logx can listen for activity in java.net.Socket and print relevant data along with process information to the Android log stream.

## Example ##
Here is an example of a log entry when running android-logx:

`07-26 11:43:47.554 V/LogX    (  406): [Socket.startupSocket, Socket[addr=www.google.com/74.125.225.16,port=80,localport=56031], com.android.browser (406)]`

It shows activity in java.net.Socket coming from com.android.browser.
