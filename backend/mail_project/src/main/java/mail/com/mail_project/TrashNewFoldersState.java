package mail.com.mail_project;

import java.util.List;
import java.util.Vector;

public class TrashNewFoldersState implements StateIF{
    @Override
    public String[] getSortingOptions() {
        String[] options={"date","subject","body","sender","receiver"};
        return null;
    }

    @Override
    public String[] getFilteringOptions() {
        String[] options={"subject", "sender"};//elmafrod w receiver
        return options;
    }

    @Override
    public Vector<MailSummary> summarizeMails(Vector<Mail> mails) {

        Vector<MailSummary> summaries=new Vector<MailSummary>();
        mails.forEach(mail -> {
            MailSummary summary=new MailSummary();
            summary.setName(new Vector<String>(List.of(new String[]{mail.getSenderUsername()})));

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
