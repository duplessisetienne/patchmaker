package com.duplessis.etienne.patchmaker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PatchmakerApplication {

	public static String getPathOfApp() {
		String res = System.getProperty("user.dir");
		String result = res.substring(0, res.lastIndexOf("/"));
		//result = result.concat("/notifier-azure/");
		result = result.concat("/patchmaker/");
		return result;
 
	}

	public static void main(String[] args) {
		
		SpringApplication.run(PatchmakerApplication.class, args);
	}

}
