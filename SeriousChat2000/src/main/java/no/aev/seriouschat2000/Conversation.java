package no.aev.seriouschat2000;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
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
    @Id @GeneratedValue
    long id;
    
    /*@OneToMany(mappedBy = "conversation",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    List<Message> messages;*/

    @Version
    Timestamp version;
 }
