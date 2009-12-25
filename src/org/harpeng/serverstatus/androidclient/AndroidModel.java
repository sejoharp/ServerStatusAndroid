package org.harpeng.serverstatus.androidclient;

import org.harpeng.serverstatus.client.IModel;

import android.content.SharedPreferences;

public class AndroidModel implements IModel {
	private SharedPreferences preferences;
	private String address;
	private String mac;
	private String port;
	private String retries;

	public SharedPreferences getPreferences() {
		return preferences;
	}

	public String getAddress() {
		return address;
	}

	public String getMAC() {
		return mac;
	}

	public String getPort() {
		return port;
	}

	public String getRetries() {
		return retries;
	}

	public AndroidModel(SharedPreferences preferences) {
		this.preferences = preferences;
		this.getValues();
	}

	private void getValues() {
		this.address = this.preferences.getString("ip", "");
		this.mac = this.preferences.getString("mac", "");
		this.port = this.preferences.getString("port", "8080");
		this.retries = this.preferences.getString("retries", "10");
	}
}
