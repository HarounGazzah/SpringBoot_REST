/*
 * Copyright 2018 by Haroun GAZZAH <haroun.gazzah@esprit.tn>
 * This is an Open Source Software
 * License: http://www.gnu.org/licenses/gpl.html GPL version 3
 */

package tn.esprit.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Cette Classe sert a Lancer l'application web et alimenter la base de données.
 * Toute la configuration de cette application SpringBoot est dans le fichier
 * application.properties.
 * 
 * @author Haroun GAZZAH
 *
 */

@SpringBootApplication
@EnableTransactionManagement
public class Application {

	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);
	}

}