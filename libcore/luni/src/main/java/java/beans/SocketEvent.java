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

import java.net.Socket;

/**
* An event class for activity that occurs on
* java.net.Socket.
*/

public class SocketEvent extends LogXEvent
{	
	public SocketEvent(String eventTag, Socket source)
	{
		super(eventTag, source);
	}
}
