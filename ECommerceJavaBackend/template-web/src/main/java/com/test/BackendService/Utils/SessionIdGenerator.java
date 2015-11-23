package com.test.BackendService.Utils;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class SessionIdGenerator {
	
	public String generateSessionId(String name)
	{		
		String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";		
		StringBuffer buffer = new StringBuffer();
		int charactersLength = characters.length();
		
		for (int i = 0; i < 20; i++) {
			double index = Math.random() * charactersLength;
			
			buffer.append(characters.charAt((int) index));
		}
		
		final String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		return buffer.substring(0,10).concat(uuid.substring(0,10));
	}

}
