**Serious Chat 2000**
================
## JAXB, JPA & simple JavaScript client

<br>  
###**Project & Configuration Files**
 File                      | Comment 
 ---                       | --- 
 .gitignore                | Ignore temporary files not to be included in git 
 nb-configuration.xml      | Configuration help-file for Netbeans
 pom.xml                   | Project build file
 src/main                  | All source files
    java                   | Java source files
    resources              | Various project resources
        META-INF           | Java EE configuration files
           persistence.xml | JPA database configuration file. Binds the datasource
                           || to the appserver configured concrete database.
        markdown           | Project documentation                  
             readme.md     | This file. Tagged in markdown
 webapp                    | HTML related source files
    WEB-INF                | Web-client configuration files.
        glassfish-web.xml  | Payara/Glassfish specific web-client configuration
                           || file. Sets up the security mapping between roles and groups
        web.xml            | Main Java EE web-client configuration. Specifies
                           || roles and security constraints.

###**HTML Client**
 File                      | Comment 
 ---                       | --- 
 webapp                    | HTML related source files
    index.html             | Shows all available chats and allows creating of new chats
    chat.html              | Shows the selected chat
    chat.js                | JavaScript file used in chat.html. Handles chat messages
    script.js              | JavaScript file used in index.html. Populates index.html
                           || with photos in system. Uses PictureService.java to get
                           || photos from system.
    style.css              | The CSS stylesheet used in index.html and photo.html
    worker.js              | Background thread reading new messages from server.

###**Java EE Server**
 File                     | Comment 
 ---                      | --- 
 src/main/java/no/aev/seriouschat2000 | Java source files
    ChatService.java      | REST service. Add and list Messages
    Conversation.java     | Groups a set of messages together
    Message.java          | A message from a named user bound to a Conversation
    PicService.java       | REST service. List, add and upload images.
    RestConfig.java       | Configuration class for REST services. Lists all REST classes
