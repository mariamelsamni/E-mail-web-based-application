package mail.com.mail_project;

import java.util.Vector;

public class MailSummary {
    Vector<String >Name;
    String subject;
    String bodySummary;
    Boolean read;
    public Vector<String > getName() {
        return Name;
    }

    public void setName(Vector<String> name) {
        Name = name;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public String getSubject() {
        return subject;
    }

    public String getBodySummary() {
        return bodySummary;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setBodySummary(String bodySummary) {
        this.bodySummary = bodySummary;
    }
}
