package org.harpeng.serverstatus.androidclient;

import org.harpeng.serverstatus.client.CommonLogicSocket;

import android.os.Handler;
import android.os.Message;

public class WakeUpThread implements Runnable {

	private CommonLogicSocket logic;
	private Handler handler;

	public WakeUpThread(CommonLogicSocket logic, Handler handler) {
		this.logic = logic;
		this.handler = handler;
	}

	public void run() {
		Message msg = new Message();
		
		if (this.logic.wakeUpServer() == false)
			msg.what = 0;
		else
			msg.what = 1;
		handler.sendMessage(msg);
	}
}
