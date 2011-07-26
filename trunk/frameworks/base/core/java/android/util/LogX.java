/*  Copyright 2011 LogX Project.
*
*   Licensed under the Apache License, Version 2.0 (the "License");
*   you may not use this file except in compliance with the License.
*   You may obtain a copy of the License at
*
*       http://www.apache.org/licenses/LICENSE-2.0
*
*   Unless required by applicable law or agreed to in writing, software
*   distributed under the License is distributed on an "AS IS" BASIS,
*   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*   See the License for the specific language governing permissions and
*   limitations under the License.
*/

package android.util;

import java.beans.LogXEventListener;
import java.beans.LogXEvent;
import java.beans.SocketEvent;
import java.beans.FileEvent;

import android.os.Process;
import android.app.ActivityManager;
import android.app.ActivityManagerNative;

import dalvik.system.VMStack;
import java.lang.StackTraceElement;
import java.lang.Thread;
import java.lang.Integer;
import java.util.List;

/**
* LogX utility class.
*
* Receives LogX events and prints them to the android.util.Log stream.
* Logs can be read from adb logcat with the following command:
* logcat -v time LogX:* *:S. 
* 
* @hide 
*/

public final class LogX implements LogXEventListener
{
	private final String TAG = "LogX";

	/* Log Settings
	* TODO: Make this and any additional configuration dynamic. 
	*/
	private final boolean LOG_STACK = false; //Indicates if the stack should be printed after each log event.
	private final boolean LOG_FILE_ACTIVITY = true;
	private final boolean LOG_SOCKET_ACTIVITY = true;

	public LogX()
	{		
		log(this.toString());
	}	
	
	/**
	* Prints to android.util.log.
	* @param log the msg to print.
	*/ 
	public void log(String log)
	{
		Log.v(TAG, log);
	}
	
	/** 
	* Handles generic event activity.
	*/
	public void onActivity(LogXEvent evt)
	{
		log(evt.toString());
		
		if(LOG_STACK)
			printStack();
	}
	
	/**
	* Handler for java.net.Socket activity. Gets the process id and package name
	* and prints the socket activity to the log.
	*/
	public void onSocketActivity(SocketEvent evt)
	{
		if(!LOG_SOCKET_ACTIVITY) return;

		String procInfo = myProcName() + " (" + Integer.toString(Process.myPid()) + ")";
		log(evt.toString().replace("####", procInfo));
	
		if(LOG_STACK) 
			printStack();
	}

	public void onFileActivity(FileEvent evt)
	{
		if(!LOG_FILE_ACTIVITY) return;

		String procInfo = myProcName() + " (" + Integer.toString(Process.myPid()) + ")";
		log(evt.toString().replace("####", procInfo));

		if(LOG_STACK)
			printStack();
	}
	
	/**
	* Traces back up the VMStack of the current thread and prints
	* each StackTraceElement along the way.
	*/
	private void printStack()
	{	
		StackTraceElement[] ste = VMStack.getThreadStackTrace(Thread.currentThread());
		
		for(StackTraceElement elem : ste)
			log(elem.getClassName());		
	}
	
	/**
	* Finds the process name of the applications that triggered
	* the event. This is achieved by getting a list of the running 
	* applications on the system and comparing each applications pid 
	* to this apps pid. Once we find a match, return the process name
	* of the application.
	* @return The process name (pkg name) of the application that triggered the event. 
	*/
	private String myProcName()
	{
		//We don't have a context because we aren't started
		//as an "official" android application. Instead of using our context to
		//access the ActivityManager, get the system default AM.
		List<ActivityManager.RunningAppProcessInfo> processes = null;
		try
		{
			//is the AM ready? (There might be a isReady() method in ActivityManagerNative that checks this.)
			if(ActivityManagerNative.getDefault() != null)
				processes = ActivityManagerNative.getDefault().getRunningAppProcesses();
		}
		catch (android.os.RemoteException e) { log("Exception: " + e.toString()); }
		
		if(processes != null)
			for(ActivityManager.RunningAppProcessInfo pinfo : processes)	
			{
				if(Process.myPid() == pinfo.pid)
					return pinfo.processName;
			}
		
		return null;			
	}

	/**
	* Return a human readable string that displays the current
	* configuration of the LogX instance.
	*/
	@Override
	public String toString() 
	{
		return "LogX Instance [PrintStack: " + LOG_STACK + "]";
	}

}
