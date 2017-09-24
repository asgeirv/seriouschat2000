package no.aev.seriouschat2000;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import net.coobird.thumbnailator.Thumbnails;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

/**
 *
 * @author aev
 */
@Path("chat")
public class PicService
{

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    @GET
    @Path("upload")
    public Response getImages()
    {
        JsonArrayBuilder builder = Json.createArrayBuilder();

        try
        {
            Files.list(Paths.get("pix"))
                    .filter(Files::isRegularFile)
                    .map(java.nio.file.Path::toFile)
                    .forEach((File f) ->
                    {
                        builder.add(Json.createObjectBuilder().add("name", f.getName())
                                .add("size", f.length())
                                .add("da", format.format(new Date(f.lastModified()))));
                    });
        } catch (IOException e)
        {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }

        return Response.ok(builder.build()).build();
    }

    @GET
    @Path("{name}")
    @Produces("image/jpg")
    public Response getImage(@PathParam("name") String name, @QueryParam("width") int width)
    {
        StreamingOutput result = (OutputStream os) ->
        {
            java.nio.file.Path image = Paths.get("pix", name);
            if (width == 0)
            {

                Files.copy(image, os);
                os.flush();
            } else
            {
                Thumbnails.of(image.toFile())
                        .size(width, width)
                        .outputFormat("jpeg")
                        .toOutputStream(os);
            }
        };
        
        CacheControl cc = new CacheControl();
        cc.setMaxAge(86400);
        cc.setPrivate(true);
        
        return Response.ok(result).cacheControl(cc).build();
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
