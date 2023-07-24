package mail.com.mail_project;

import java.util.Vector;

public interface StateIF {
    public String[] getSortingOptions();
    public String[] getFilteringOptions();
    public Vector<MailSummary> summarizeMails(Vector<Mail> mail);

}
