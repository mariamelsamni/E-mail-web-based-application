package mail.com.mail_project;

import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.Vector;

public class CriteriaSubject implements CriteriaIF{

    @Override
    public Vector<Mail> meetCriteria(Vector<Mail> Mails, ArrayList<String> subjects) {
        Vector<Mail> mails = new Vector<>();
        for(int i=0;i<subjects.size();i++) {
            String subject=(String) subjects.get(i);
            for (Mail mail : Mails) {
                if (mail.getSubject().equalsIgnoreCase(subject)) {
                    mails.add(mail);
                }
            }
        }
        return mails;
    }


}
