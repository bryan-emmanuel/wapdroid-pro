/*
 * Wapdroid - Android Location based Wifi Manager
 * Copyright (C) 2009 Bryan Emmanuel
 * 
 * This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.

 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *  
 *  Bryan Emmanuel piusvelte@gmail.com
 */

package com.piusvelte.wapdroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {
	private static final String TAG = "Wapdroid";
	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
			SharedPreferences sp = context.getSharedPreferences(context.getString(R.string.key_preferences), WapdroidService.MODE_PRIVATE);
			if (sp.getBoolean(context.getString(R.string.key_manageWifi), true)) {
				Log.v(TAG,"starting service on boot");
				ManageWakeLocks.acquire(context);
				context.startService(new Intent(context, WapdroidService.class));
			}
		}
		else if (intent.getAction().equals(WapdroidService.WAKE_SERVICE)) {
			Log.v(TAG,"waking service");
			ManageWakeLocks.acquire(context);
			context.startService(new Intent(context, WapdroidService.class));
		}
	}
}