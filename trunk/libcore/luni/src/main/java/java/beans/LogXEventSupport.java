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

package java.beans;

import java.util.ArrayList;

/**
* LogXEventSupport keeps track of the registered event listeners. This
* singleton is instantiated in com.android.internal.os.ZygoteInit to 
* preserve state across all dalvik VM instances.   
*/

public class LogXEventSupport
{
	private static LogXEventSupport mInstance = null; //singleton instance
	private ArrayList<LogXEventListener> listeners; 
	
	private LogXEventSupport()
	{
		listeners = new ArrayList<LogXEventListener>();
	}
	
	/**
	* Retrieves the current singleton instance.
	* @return LogXEventSupport instance.
	*/
	public static LogXEventSupport getInstance()
	{
		if(mInstance == null)
			mInstance = new LogXEventSupport();
			
		return mInstance;
	}	
	
	/**
	* Adds a class which implements LogXEventListener to the current
	* list of listeners.
	*/
	public void addListener(LogXEventListener listener)
	{
		listeners.add(listener);
	}
	
	/**
	* Dispatches a generic LogXEvent to all the listeners. Calls
	* the onActivity method for each registered listener.
	*/
	public void dispatchEvent(LogXEvent event)
	{
		for (LogXEventListener l : listeners)
			l.onActivity(event);
	}
	
	/**
	* Dispatches a SocketEvent to all the listeners. Calls 
	* the onSocketEvent method for each registered listener.
	*/
	public void dispatchEvent(SocketEvent event)
	{
		for(LogXEventListener l : listeners)
			l.onSocketActivity(event);
	}
	
	/**
	* Dispatches a FileEvent to all the listeners. Calls
	* the onFileActivity method for each registered listeners.
	*/
	public void dispatchEvent(FileEvent event)
	{
		for(LogXEventListener l : listeners)
			l.onFileActivity(event);
	}

}
