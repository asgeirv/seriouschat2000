package no.aev.seriouschat2000;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author aev
 */
@Data @NoArgsConstructor
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Conversation implements Serializable {
    @Id
    String id;
    
    @XmlTransient
    @OneToMany(mappedBy = "conversation",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    List<Message> messages;

    @Version
    Timestamp version;

    public Conversation(String id) {
        this.id = id;
    }
}
