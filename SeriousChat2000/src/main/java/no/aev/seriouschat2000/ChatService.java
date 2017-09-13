package no.aev.seriouschat2000;

import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author aev
 */
@Stateless
@Path("chat")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ChatService {   
    @PersistenceContext
    EntityManager em;
    
    @GET
    public List<Message> getMessages(@QueryParam("name") long conversation) {
        List<Message> result = null;
        System.out.println("conversationid " + conversation);
        result = em.createQuery("SELECT m FROM Message m WHERE m.conversation.id = :id", 
                Message.class)
            .setParameter("id", conversation)
            .getResultList();
        
        return result != null ? result : Collections.EMPTY_LIST;
    }


    @POST
    @Path("add")
    public Response addMessage(@QueryParam("name")long converstionid, Message message) {
        Conversation c = em.find(Conversation.class, converstionid);
        if(c == null) {
            c = new Conversation();
            em.persist(c);
        }
        message.setConversation(c);
        em.persist(message);

        return Response.ok(message).build();
    }
    
    @GET
    @Path("stuff") 
    public Conversation create() {
        Conversation c = new Conversation();
        em.persist(c);
        System.out.println("Conversation id " + c.getId());
        
        for(int i = 0; i < 5; i++) {
            Message m = new Message("user", "Message: " + i);
            m.setConversation(c);
            em.persist(m);            
        }
        return em.merge(c);
    }
}
