package com.tokbox.brewtest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.util.Base64;

public class Util {

	public static boolean isP2PSession(String sessionId) {
		sessionId = sessionId.substring(2);
		
		List<String> parts = new ArrayList<String>(Arrays.asList(sessionId.split("-")));		
		String decodedPart = new String(Base64.decode(parts.get(parts.size() - 1), Base64.DEFAULT));
		
		parts = new ArrayList<String>(Arrays.asList(decodedPart.split("~")));
		if (parts.size() == 0)
			return false;
		else
			return "P".equals(parts.get(parts.size() - 1));
	}
	
}
