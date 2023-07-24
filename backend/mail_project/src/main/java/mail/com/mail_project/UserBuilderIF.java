package mail.com.mail_project;

import org.json.simple.JSONObject;

import java.util.Vector;

public interface UserBuilderIF {
    public void set_username();
    public void set_password();
    public void set_photo();
    public void set_background();
    public void set_contacts();
    public void createUser(JSONObject userJ, String reusedID, String ID);
    public void setNickNameMap();
    public User getUser();

}
