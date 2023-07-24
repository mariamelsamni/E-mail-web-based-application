package mail.com.mail_project;

import java.util.Vector;

public class Contact {

    private String nickName;
    private String defaultEmail;
    private Vector<String> Emails =new Vector<String>();
    public String idCode;
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getDefaultEmail() {
        return defaultEmail;
    }

    public void setDefaultEmail(String defaultEmail) {
        this.defaultEmail = defaultEmail;
    }

    public Vector<String> getEmails() {
        return Emails;
    }

    public void setEmails(Vector<String> emails) {
        Emails = emails;
    }
}
