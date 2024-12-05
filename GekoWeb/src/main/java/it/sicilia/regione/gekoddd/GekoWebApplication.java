/**
 * GekoWebApplication è la classe di ingresso della applicazione Web.
 * Estende SpringBootServletInitializer
 * ed è annotata con @SpringBootApplication
 */

package it.sicilia.regione.gekoddd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;


@SpringBootApplication
public class GekoWebApplication extends SpringBootServletInitializer{
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application){
		return application.sources(GekoWebApplication.class);
		//return builder.sources(Application.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(GekoWebApplication.class, args);
	}
	
}
