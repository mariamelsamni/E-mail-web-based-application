package mail.com.mail_project;

import java.util.List;
import java.util.Vector;

public class InboxState implements StateIF{
    @Override
    public String[] getSortingOptions() {
        String[] options={"date", "sender", "importance","subject","body"};
        return options;
    }

    @Override
    public String[] getFilteringOptions() {
        String[] options={"subject", "sender"};
        return options;
    }

    @Override
    public Vector<MailSummary> summarizeMails(Vector<Mail> mails) {
        Vector<MailSummary> summaries=new Vector<MailSummary>();
        mails.forEach(mail -> {
            MailSummary summary=new MailSummary();
            Vector<String> v=new Vector<>();
            v.addElement(mail.getSenderUsername());
            summary.setName(v);
            if (mail.getBody().length()>15){
                summary.setBodySummary(mail.getBody().substring(0, 15)+".....");
            }
            else{
                summary.setBodySummary(mail.getBody());
            }
            summary.setSubject(mail.getSubject());
            summary.setRead(mail.isRead());
            summaries.addElement(summary);
        });
        System.out.println("akher el state");
        return summaries;
    }
}
