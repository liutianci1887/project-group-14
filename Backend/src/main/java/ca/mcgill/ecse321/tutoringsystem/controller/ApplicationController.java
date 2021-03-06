package ca.mcgill.ecse321.tutoringsystem.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.tutoringsystem.dto.ApplicationDto;
import ca.mcgill.ecse321.tutoringsystem.model.Application;
import ca.mcgill.ecse321.tutoringsystem.service.ApplicationService;

@CrossOrigin(origins = "*")
@RestController
public class ApplicationController {
	
	@Autowired
	ApplicationService applicationService;
	
	@GetMapping(value = { "/applications", "/applications/" })
	public List<ApplicationDto> getAllApplications() {
		List<ApplicationDto> applicationDtos = new ArrayList<>();
		for (Application application : applicationService.getAllApplications()) {
			applicationDtos.add(DtoConverter.toDto(application));
		}
		return applicationDtos;
	}
	
	@GetMapping(value = { "/applications/{input}", "/applications/{input}/" })
	public List<ApplicationDto> getApplicationBy(@PathVariable(name = "input") String input) throws IllegalArgumentException {
		List<ApplicationDto> applicationDtos = new ArrayList<>();
		if (input.chars().allMatch(Character::isDigit)) {
			// input is a number, get application by id
			Application application = applicationService.getApplication(Integer.parseInt(input));
			applicationDtos.add(DtoConverter.toDto(application));
			return applicationDtos;
		} else {
			for (Application application : applicationService.getApplication(input)) {
				applicationDtos.add(DtoConverter.toDto(application));
			}
			return applicationDtos;
		}
	}
	
	@PostMapping(value = { "/applications/create", "/applications/create/" })
	public ApplicationDto createApplication(@RequestParam(name = "existing") Boolean isExistingUser,
		@RequestParam(name = "name") String name, @RequestParam(name = "email") String email,
		@RequestParam(name = "courses") String courses) throws IllegalArgumentException {

		Application a = applicationService.createApplication(isExistingUser, name, email, courses);
		return DtoConverter.toDto(a);
	}
	
	@DeleteMapping(value = { "/applications/{input}", "/applications/{input}/" })
	public boolean deleteApplicationBy(@PathVariable(name = "input") String input) throws IllegalArgumentException {
		if (input.chars().allMatch(Character::isDigit)) {
			// input is a number, get application by id
			return applicationService.deleteApplication(Integer.parseInt(input));
		} else {
			return applicationService.deleteApplication(input);
		}
	}
}
