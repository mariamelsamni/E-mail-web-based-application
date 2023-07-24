package mail.com.mail_project;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.HashMap;
import java.util.Vector;

public class UserBuilder implements UserBuilderIF{
    private User user;
    private JSONObject userJ;
    public static final HashMap<String,User> userMap=new HashMap<String,User>();

    public void createUser(JSONObject userJ,String reusedID, String ID){
        this.user=(User) userMap.get(reusedID);
        userMap.remove(reusedID);
        userMap.put(ID,this.user);
        if (this.user==null){
            this.user=new User();
            System.out.println("dakhal fel if");
            userMap.put(ID,this.user);
        }
        System.out.println("added to the map with id");
        System.out.println(ID);
        this.user.setId(ID);
        this.userJ=userJ;
    }
    public void setEmail(){this.user.setEmail((String)this.userJ.get("email"));}
    public void setNickNameMap(){
        this.user.setNicknameMap((HashMap<String,String>)this.userJ.get("nicknameMap"));
    }
    @Override
    public void set_username() {
        this.user.setUsername((String)this.userJ.get("username"));
    }

    @Override
    public void set_password() {
        this.user.setPassword((String)this.userJ.get("password"));
    }

    @Override
    public void set_photo() {
        this.user.setPhoto((String)this.userJ.get("photo"));
    }

    @Override
    public void set_background() {
        this.user.setBackground((String)this.userJ.get("background"));
    }

    public void setFoldersName(){
        this.user.setFolderNames((JSONArray) this.userJ.get("folders"));
    }

    @Override
    public void set_contacts() {
        JSONArray contacts =(JSONArray) userJ.get("contacts");
        if(contacts!=null) {
            contacts.forEach( contact->{
                this.user.addContacts((Contact) contact);
                this.user.addNickname_to_contacts(((Contact) contact).getNickName(),((Contact) contact).getDefaultEmail());
            });

        }
    }
    @Override
    public User getUser() {
        return this.user;
    }

}
