package mail.com.mail_project;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

public class Proxy implements DatabaseIF{
    private boolean already_exist = false;
    private Database database=Database.getInstance();
    private JSONArray vFile;
    private Boolean validContact=false;
    private Boolean validMail=false;
    private Mediator m;
    public Proxy(Mediator m) throws IOException, ParseException {
        this.m=m;
    }
    public void clear_file(String idCode,String folder) throws IOException {
        database.clear_file(idCode,folder);
    }
    public Vector<Mail> requestFolder(String idCode, String folder) throws IOException, ParseException {
        return this.database.requestFolder(idCode,folder);
    }
    public JSONObject verify_login(JSONObject account) throws IOException, ParseException {
        vFile=this.database.get_verification_file();
        if(vFile!=null) {
            for (int i=0;i<vFile.size();i++){
                if (this.check((JSONObject) vFile.get(i), account,true)){
                    JSONObject ob=(JSONObject) vFile.get(i);
                    return get_user((JSONObject) vFile.get(i));
                }
            }
            return null;
        }
        return null;
    }
    public JSONObject get_user(JSONObject account) throws IOException, ParseException {
        return database.get_user(account);
    }
    public boolean verify_signup(JSONObject account,String id) throws IOException, ParseException { //3yza a check wogod email 3mtnn
        vFile=this.database.get_verification_file();
        if(vFile!=null) {
            for (int i=0;i<vFile.size();i++){
                if (this.check((JSONObject) vFile.get(i), account,false)){
                    return false;
                }
            }
            database.add_new_user(account,id);
            return true;
        }
        database.add_new_user(account,id);
        return true;
    }
    public void update_all_mails_in_file(String idCode,Object obj ,String folder){

            try {
                this.database.add_mail_draft_contact(obj,idCode,folder);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

    }

    public boolean add_mail(String idCode,Mail mail) throws IOException, ParseException {
        vFile = this.database.get_verification_file();
        validMail=true;
        Vector<String> receiversFolder=new Vector<String>();
        Vector<String> receiversEmail=new Vector<String>();
        Vector<String> receiversUsername=new Vector<String>();
        mail.getReceiversUsername().removeAllElements();
//        for (int i=0;i<mail.getReceivers().size();i++){
//            System.out.println(mail.getReceivers().get(i));
//        }
        mail.getReceivers().forEach(email->
        {
            Boolean curretEmail_is_valid=false;

            for (int i = 0; i < vFile.size(); i++) {
                JSONObject account = (JSONObject) vFile.get(i);
                if ((checkk(email, (String) account.get("email")))) {
                    curretEmail_is_valid = true;
                    receiversFolder.addElement((String) account.get("id"));
                    receiversEmail.addElement((String) account.get("email"));
                    receiversUsername.addElement((String) account.get("username"));
                    mail.addReceiverUsername((String) account.get("username"));
                    break;
                }
                else
                {
                    System.out.println("email "+email+" account email "+(String) account.get("email"));
                    System.out.println("felfalse");
                }
            }
            if (!curretEmail_is_valid) {
                validMail=false;
//                return ;
            }});
        if (!validMail){
            return false;
        }

        this.database.add_mail_draft_contact(mail,idCode,"sent");
        for (int i=0;i<receiversEmail.size();i++){
            this.database.add_mail_draft_contact(m.cloneMail(mail, new Vector<String>(List.of(new String[]{receiversEmail.get(i)})),new Vector<String>(List.of(new String[]{receiversUsername.get(i)}))),receiversFolder.get(i),"inbox");
        }
        return true;

    }
    public void add_draft_folder(String idCode,Mail draft,String folder) throws JsonProcessingException {
        this.database.add_mail_draft_contact(draft, idCode, folder);
    }
    public boolean checkk(String original,String compare){
        if (original.equals(compare)){
            return true;
        }
        else {
            return false;
        }
    }
    public Vector<Contact> requestContactsFolder(String idCode) throws IOException, ParseException {
        return database.requestContacts(idCode,"contacts");

    }
    public boolean check(JSONObject account,JSONObject comp_account,Boolean password){
        if (Objects.equals((String) account.get("email"), (String)comp_account.get("email"))){
            if (password){
                if (Objects.equals((String) account.get("password"), (String)comp_account.get("password"))){
                    return true;
                }
                else {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public boolean addContact (String idCode,Contact contact) throws IOException, ParseException {
        vFile = this.database.get_verification_file();
        validContact=true;

        contact.getEmails().forEach(email->
                {
                    Boolean curretEmail_is_valid=false;
                    for (int i = 0; i < vFile.size(); i++) {
                        JSONObject account = (JSONObject) vFile.get(i);
                        if ((checkk(email, (String) account.get("email")))) {
                            curretEmail_is_valid = true;
                            break;
                        }
                  }
                    if (!curretEmail_is_valid) {
                        validContact=false;
                        return ;
                    }});
        if (!validContact){
            return false;
        }
        this.database.add_mail_draft_contact(contact,idCode,"contacts");
        return true;
    }

    public boolean newFolder(String idCode,String folder) throws IOException {
        return database.newFolder(idCode, folder);
    }
    public void updateMainPage(String idCode,JSONObject obj) throws IOException {
        database.updateMainPage(idCode,obj);
    }

    public boolean isAlready_exist() {
        return already_exist;
    }
}

