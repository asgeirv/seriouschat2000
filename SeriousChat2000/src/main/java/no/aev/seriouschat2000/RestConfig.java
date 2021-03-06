package no.aev.seriouschat2000;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

/**
 *
 * @author aev
 */
@ApplicationPath("api")
public class RestConfig extends Application
{

    @Override
    public Set<Class<?>> getClasses()
    {
        Set<Class<?>> resources = new HashSet<>();
        resources.add(MultiPartFeature.class);
        addRestResourceClasses(resources);
        return resources;
    }

    private void addRestResourceClasses(Set<Class<?>> resources)
    {
        resources.add(no.aev.seriouschat2000.ChatService.class);
        //resources.add(no.aev.seriouschat2000.PicService.class);
        resources.add(no.aev.seriouschat2000.PicService.class);
    }
}
