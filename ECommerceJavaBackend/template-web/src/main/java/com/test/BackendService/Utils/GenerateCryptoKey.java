package com.test.BackendService.Utils;

import java.security.Key;
import java.util.Base64;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;

import com.test.Enums.CryptoCode;
import com.test.Enums.Status;
import com.test.persistence.entities.CryptoData;
import com.test.service.CryptoService;

/**
 * Description: This class generates key to be used to encrypt / decrypt string.
 * 
 * @author AsishS
 *
 */
@Configuration
@EnableAutoConfiguration
public class GenerateCryptoKey implements CommandLineRunner {

	@Autowired
	CryptoService cryptoService;

	private static final byte[] keyValue = new byte[] { 'T', '#', 'l', '$',
			'n', '%', 'i', '^', '&', 's', '*', '(', 'r', ')', 't', '+' };

	@Autowired
	public static void main(String... args) throws Exception {
		SpringApplication.run(GenerateCryptoKey.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Key key = generateKey();

		if (key != null) {
			CryptoData cryptoData = cryptoService
					.findByCode(CryptoCode.ECommerce.toString());

			if (cryptoData == null) {
				cryptoData = new CryptoData();

				String encodedKey = Base64.getEncoder().encodeToString(
						key.getEncoded());
				cryptoData.setSecretKey(encodedKey);
				cryptoData.setCode(CryptoCode.ECommerce.toString());
				cryptoData.setStatus(Status.ACTIVE.toString());
				cryptoService.save(cryptoData);
			}
		}
	}

	private static Key generateKey() throws Exception {
		Key key = new SecretKeySpec(keyValue, "AES");
		return key;
	}

}
