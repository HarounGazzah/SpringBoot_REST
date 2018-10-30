/*
 * Copyright 2018 by Haroun <haroun.gazzah@esprit.tn>
 * This is an Open Source Software
 * License: http://www.gnu.org/licenses/gpl.html GPL version 3
 */

package tn.esprit.spring.controller.impl;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.entity.Client;
import tn.esprit.spring.service.interfaces.IClientInfoService;

/**
 * 
 * Cette classe implémente les resources REST qui permettent de gérer l'identité
 * d'un client. http://websystique.com/spring-boot/spring-boot-rest-api-example/
 * 
 * @author Haroun GAZZAH
 *
 */

@RestController
@RequestMapping("/api/identity")
public class IdentityController {

	@Autowired
	@Qualifier("clientInfoMySQLServiceImpl")
	IClientInfoService clientService;

	@GetMapping("/client/")
	public ResponseEntity<List<Client>> getAllClient() {
		List<Client> clients = clientService.getAllClients();
		return ResponseEntity.ok().body(clients);
	}

	/**
	 * Retourner le client s'il existe dans la base
	 * 
	 * @param clientId
	 * @return Client client
	 */
	// @RequestMapping(value = "/client/{clientId}", method = RequestMethod.GET,
	// produces = MediaType.APPLICATION_JSON_VALUE)

	@GetMapping("/client/{clientId}")
	public ResponseEntity<Client> getClient(@PathVariable("clientId") Long clientId) {
		Client client = clientService.getClientById(clientId);

		if (client == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(client);
	}

	/**
	 * Ajouter un client
	 * 
	 * @param client
	 */

	// @RequestMapping(value = "/client/", method = RequestMethod.POST, consumes =
	// MediaType.APPLICATION_JSON_VALUE)
	@PostMapping("/client/")
	public ResponseEntity<Void> addClient(@RequestBody Client client) {
		clientService.addClient(client);
		return ResponseEntity.ok().build();
	}

	/**
	 * Mettre a jour les informations d'un client.
	 * 
	 * @param clientId
	 * @param client
	 */
	@RequestMapping(value = "/client/{clientId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> updateClient(@PathVariable("clientId") Long clientId, @RequestBody Client client) {

		clientService.updateClientInfoById(client, clientId);
		return new ResponseEntity<>(HttpStatus.OK);

	}

	/**
	 * Supprimer un client
	 * 
	 * @param clientId
	 */
	@RequestMapping(value = "/client/{clientId}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteClient(@PathVariable("clientId") Long clientId) {
		Client client = clientService.getClientById(clientId);
		clientService.deleteClient(client);
		return ResponseEntity.ok().build();
	}

	/**
	 * @return le nombre total des clients dans la base
	 * @throws JSONException
	 */
	@RequestMapping(value = "/client/count", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> countClient() throws JSONException {

		JSONObject responseJSON = new JSONObject();
		responseJSON.put("nombre de clients", clientService.countClients());
		return new ResponseEntity<>(responseJSON.toString(), HttpStatus.OK);
	}

}
