package org.harpeng.serverstatus.androidclient;

import org.harpeng.serverstatus.client.CommonLogicSocket;
import org.harpeng.serverstatus.client.IGui;

import android.app.Activity;
import android.os.Handler;
import android.preference.PreferenceManager;

public class AndroidLogic extends CommonLogicSocket {
	private IGui mainGui;
	
	public AndroidLogic(IGui mainGui) {
		this.mainGui = mainGui;
		this.setGui(mainGui);
		this.setModel(new AndroidModel(PreferenceManager
				.getDefaultSharedPreferences(((Activity) mainGui))));
	}

	public void closeProgram() {
		((Activity) mainGui).finish();
	}
	
	public void wakeUp(Handler handler){
		new Thread(new WakeUpThread(this, handler)).start();
	}
}
