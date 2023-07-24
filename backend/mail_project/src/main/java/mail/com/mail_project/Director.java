package mail.com.mail_project;
import org.json.simple.JSONObject;

import java.util.Vector;

public class Director {
    private UserBuilder builder;
    public void Director(){

    }
    public void new_user(UserBuilder builder,JSONObject user,String reusedID,String ID){
        this.builder=builder;
        builder.createUser(user,reusedID,ID);
        builder.set_username();
        builder.set_password();

    }
    public void existing_user(UserBuilder builder, JSONObject user, String reusedID, String ID){
        this.builder=builder;
        builder.createUser(user,reusedID,ID);
        builder.set_username();
        builder.set_password();
        builder.set_contacts();
        builder.set_background();
        builder.set_photo();
        builder.setFoldersName();
        builder.setNickNameMap();
        builder.setEmail();
    }

}
