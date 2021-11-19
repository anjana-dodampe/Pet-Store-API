package com.example.petstore;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

@Path("/v1/pets")
@Produces("application/json")
public class PetResource {
	
	List<Pet> pets = new ArrayList<Pet>();
	
	public PetResource () {
		
		Pet pet1 = new Pet();
		pet1.setPetId(1);
		pet1.setPetAge(3);
		pet1.setPetName("Boola");
		pet1.setPetType("Dog");

		Pet pet2 = new Pet();
		pet2.setPetId(2);
		pet2.setPetAge(4);
		pet2.setPetName("Sudda");
		pet2.setPetType("Cat");

		Pet pet3 = new Pet();
		pet3.setPetId(3);
		pet3.setPetAge(2);
		pet3.setPetName("Peththappu");
		pet3.setPetType("Bird");

		pets.add(pet1);
		pets.add(pet2);
		pets.add(pet3);
	}
	
	//Get pet Names
	
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "All Pets", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))) })
	@GET
	public Response getPets() {
	
		return Response.ok(pets).build();
	}
	
	
	// AddPets
	@APIResponses(value = {
			@APIResponse(responseCode = "201", description = "Add Pet", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))) })
	@POST
	public Response addPet(@RequestBody Pet pet) {
        pets.add(pet);
        return Response.created(URI.create("/v1/pets")).build();
    }
	
	//DeletePets
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Pet Deleted Successfully!", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))),
			@APIResponse(responseCode = "404", description = "No Pet found for the id.") })
	@DELETE
	@Path("{petId}")
	public Response deletePet(@PathParam("petId") int petId) {
		if (petId < 0) {
			return Response.status(Status.NOT_FOUND).build();
		}
		for (Pet pet : pets) {
			if(pet.getPetId() == petId) {
		    	pets.remove(pet);
			    return Response.noContent().build();
		    }
        }
		return Response.status(Status.NOT_FOUND).build();
	}
	
	//Update
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Update Pet", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))),
			@APIResponse(responseCode = "404", description = "No Pet found for the id") })
	
	@PUT
	@Path("{petId}")
	public Response updatePet(@PathParam("petId") int petId, @RequestBody Pet newPet) {
		if (petId < 0) {
			return Response.status(Status.NOT_FOUND).build();
		}
		for (Pet pet : pets) {
			if(pet.getPetId() == petId) {
		    	pets.remove(pet);
		    	pets.add(newPet);
			    return Response.ok(newPet).build();
		    }
        }
		return Response.status(Status.NOT_FOUND).build();
	}
	
	//Get Pet Id 
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Pet for id", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))),
			@APIResponse(responseCode = "404", description = "No Pet found for the id.") })
	@GET
	@Path("{petId}")
	public Response getPet(@PathParam("petId") int petId) {
		if (petId < 0) {
			return Response.status(Status.NOT_FOUND).build();
		}
		Pet pet = new Pet();
		pet.setPetId(petId);
		pet.setPetAge(3);
		pet.setPetName("Buula");
		pet.setPetType("Dog");

		return Response.ok(pet).build();
		
	}
}
