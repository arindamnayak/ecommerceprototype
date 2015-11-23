package com.test;



import org.parse4j.Parse;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableAutoConfiguration
@ComponentScan
@EnableScheduling
public class Application extends SpringBootServletInitializer implements
		CommandLineRunner {

	public static void main(String[] args) throws Exception {
		
		new SpringApplicationBuilder(Application.class).showBanner(false).run(
				args);
	}

	@Override
	protected final SpringApplicationBuilder configure(
			final SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

	@Override
	public void run(String... args) throws Exception {		
		
		System.out.println("Initilaizing Parse");
		//ClintKey
		//Parse.initialize("qG2zJIcWHHBXSHoy8XJV0TLRLpTkdrbg1rHtOUFX","VG5mKd6Kd4EC9ZqXtSPg2Ts0I7Dty2mrHixpjNVv");
		
		//RestAPI Key
		Parse.initialize("qG2zJIcWHHBXSHoy8XJV0TLRLpTkdrbg1rHtOUFX","SabKsC05oGonE9Zd0UYENXWDS2bogpNYA1dpOex8");		
		
		//MasterKey
		//Parse.initialize("qG2zJIcWHHBXSHoy8XJV0TLRLpTkdrbg1rHtOUFX","f0Qy91cm1wLqTxQpfoQDw9UTTDNQ3x990YbXfV2k");
	
		
	}
	
/*	 public void onCreate() {
		 	//Parse.initialize(this,"qG2zJIcWHHBXSHoy8XJV0TLRLpTkdrbg1rHtOUFX","VG5mKd6Kd4EC9ZqXtSPg2Ts0I7Dty2mrHixpjNVv");
	    }
*/
}
