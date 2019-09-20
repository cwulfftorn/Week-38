package rest;

import DTO.PersonDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Person;
import exceptions.PersonNotFoundException;
import utils.EMF_Creator;
import facades.PersonFacade;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//Todo Remove or change relevant parts before ACTUAL use
@Path("person")
public class PersonResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
                "pu",
                "jdbc:mysql://localhost:3307/person",
                "dev",
                "ax2",
                EMF_Creator.Strategy.CREATE);
    private static final PersonFacade FACADE =  PersonFacade.getFacadeExample(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
            
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getJson() {
        return "{}";
    }
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("find/{id}")
    public String getPerson(@PathParam("id") long id) throws PersonNotFoundException {
        Person found = FACADE.getPerson((int) id);
        return GSON.toJson(new PersonDTO(found));
    }
    
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String add(String person) {
        PersonDTO p = GSON.fromJson(person,PersonDTO.class);
        Person pAdded = FACADE.addPerson(p.getfName(), p.getlName(), p.getPhone());
        return GSON.toJson(new PersonDTO(pAdded));
    }
    
    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("{id}")
    public String edit(@PathParam("id") long id,String person) throws PersonNotFoundException {
        PersonDTO p = GSON.fromJson(person,PersonDTO.class);
        Person pToEdit = new Person(p.getfName(),p.getlName(),p.getPhone());
        pToEdit.setId(id);
        Person editedPerson = FACADE.editPerson(pToEdit);
        return GSON.toJson(new PersonDTO(editedPerson));
    }
    
    @DELETE
    @Produces({MediaType.APPLICATION_JSON})
    @Path("{id}")
    public String delete(@PathParam("id") long id) throws PersonNotFoundException {
        FACADE.deletePerson((int) id);
        return "{\"status\":\"deleted\"}";
    }
    
    @GET
    @Path("all")
    @Produces({MediaType.APPLICATION_JSON})
    public String allPersons() {
        PersonDTO persons = new PersonDTO((Person) FACADE.getAllPersons());
        //return GSON.toJson(persons.getPersonDTOs());
        return GSON.toJson(persons);
    }
    
    
    @Path("count")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getRenameMeCount() {
        long count = FACADE.getPersonCount();
        System.out.println("--------------->"+count);
        return "{\"count\":"+count+"}";  //Done manually so no need for a DTO
    }

 
}
