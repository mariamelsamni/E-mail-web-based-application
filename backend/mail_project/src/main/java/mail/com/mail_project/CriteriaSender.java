package mail.com.mail_project;

import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.Vector;

public class CriteriaSender implements CriteriaIF{

    @Override
    public Vector<Mail> meetCriteria(Vector<Mail> Mails, ArrayList<String> senders){
        Vector<Mail> mails = new Vector<>();
        for(int i =0;i<senders.size();i++) {
            String sender=(String) senders.get(i);
            for (Mail mail : Mails) {
                if (mail.getSender().equalsIgnoreCase(sender)) {
                    mails.add(mail);
                }
            }
        }
        return mails;
    }
}
