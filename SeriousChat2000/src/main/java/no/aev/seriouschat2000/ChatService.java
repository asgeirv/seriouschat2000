package no.aev.seriouschat2000;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

/**
 *
 * @author aev
 */
@Stateless
@Path("chat")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ChatService
{

    @PersistenceContext
    EntityManager em;

    @GET
    public List<Message> getMessages(@QueryParam("name") long conversation)
    {
        List<Message> result = null;
        System.out.println("conversationid " + conversation);
        result = em.createQuery("SELECT m FROM Message m WHERE m.conversation.id = :id", Message.class)
                .setParameter("id", conversation)
                .getResultList();

        return result != null ? result : Collections.EMPTY_LIST;
    }

    @GET
    @Path("conversations")
    public List<Conversation> getConversations()
    {
        return em.createQuery("SELECT c FROM Conversation c", Conversation.class)
                .getResultList();
    }

    @POST
    @Path("add")
    public Response addMessage(@QueryParam("name") long conversationid, Message message)
    {
        Conversation c = em.find(Conversation.class, conversationid);
        if (c == null)
        {
            c = new Conversation();
            em.persist(c);
        }
        message.setConversation(c);
        em.persist(message);

        return Response.ok(message).build();
    }

    @POST
    @Path("img")
    public Response addImage(@QueryParam("name") long conversationid, @QueryParam("url") String url)
    {
        return Response.ok().build();
    }

    @GET
    @Path("newconversation")
    public Conversation create()
    {
        Conversation c = new Conversation();
        em.persist(c);
        System.out.println("Conversation id " + c.getId());

        return em.merge(c);
    }

    @POST
    @Path("upload")
    @Consumes(
            {
                MediaType.MULTIPART_FORM_DATA
            })
    public Response uploadPic(
            @FormDataParam("file") InputStream is,
            @FormDataParam("file") FormDataContentDisposition details)
    {
        try
        {
            Files.copy(is, Paths.get("pix", details.getFileName()));
        } catch (IOException e)
        {
            Logger.getLogger(PicService.class.getName()).log(Level.SEVERE, null, e);
            return Response.serverError().build();
        }

        return Response.ok().build();
    }

}
