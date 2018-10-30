/*
 * Copyright 2018 by Haroun GAZZAH <haroun.gazzah@esprit.tn>
 * This is an Open Source Software
 * License: http://www.gnu.org/licenses/gpl.html GPL version 3
 */

package tn.esprit.spring.service.impl;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.entity.Client;
import tn.esprit.spring.entity.Project;
import tn.esprit.spring.repository.ClientRepository;
import tn.esprit.spring.repository.ProjectRepository;
import tn.esprit.spring.service.interfaces.IClientInfoService;

/**
 * Cette classe permet de gérer les informations client.
 * 
 * @author Haroun GAZZAH
 * 
 */
@Component
public class ClientInfoMySQLServiceImpl implements IClientInfoService {

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private ProjectRepository projectRepository;

	/**
	 * Ajouter un projet et l'affecter a un client
	 * 
	 * @param projet
	 * @param clientId
	 */
	@Override
	public void addProjectAndAssignToClient(Project project, Long clientId) {

		project.setClient(new Client(clientId));
		projectRepository.save(project);
	}

	/**
	 * Trouver un projet en ayant son Id
	 */
	@Override
	public Project getProjectById(Long projectId) {
		return projectRepository.findOne(projectId);
	}

	/**
	 * Supprimer tous les projets
	 */
	@Override
	public void deleteAllProjects() {
		projectRepository.deleteAll();
	}

	/**
	 * Récupérer tous les projets
	 */
	@Override
	public List<Project> getAllProjects() {
		return (List<Project>) projectRepository.findAll();
	}

	@Override
	public List<Client> getAllClients() {
		return (List<Client>) clientRepository.findAll();
	}

	/**
	 * Récupérer les données client en ayant son Id
	 * 
	 * @param clientId l'identifiant du client
	 * @return Client client
	 */
	@Override
	public Client getClientById(Long clientId) {
		return clientRepository.findOne(clientId);
	}

	/**
	 * Ajouter un client dans la base
	 */
	@Override
	public void addClient(Client client) {
		clientRepository.save(client);
	}

	/**
	 * Mettre a jour les informations client
	 */
	@Override
	public void updateClientInfoById(Client client, Long clientId) {
		clientRepository.updateClientInfoById(client.getFirstName(), client.getLastName(), client.getEmail(), clientId);
	}

	@Override
	@Transactional
	public void updateProject(Long projectId, Project project) {

		Project projectManagedEntity = projectRepository.findOne(projectId);
		if(projectManagedEntity == null){
			throw new NoResultException();
		}
		projectManagedEntity.setDetails(project.getDetails());
		projectManagedEntity.setEndDate(project.getEndDate());
		projectManagedEntity.setStartDate(project.getStartDate());
		projectManagedEntity.setTitle(project.getTitle());

	}

	/**
	 * Supprimer un client
	 */
	@Override
	public void deleteClient(Client client) {
		clientRepository.delete(client);
	}

	/**
	 * Calculer le nombre total des clients
	 */
	@Override
	public Long countClients() {
		return clientRepository.count();
	}
}
