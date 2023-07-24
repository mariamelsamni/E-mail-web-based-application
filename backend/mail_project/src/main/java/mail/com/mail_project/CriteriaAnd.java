package mail.com.mail_project;

import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.Vector;

public class CriteriaAnd {
    CriteriaSubject criteria=new CriteriaSubject();
    CriteriaSender otherCriteria= new CriteriaSender();

    public Vector<Mail> meetCriteria(Vector<Mail> mails, ArrayList<String> subject, ArrayList<String>  sender){
        Vector<Mail> firstCriteriaPersons = criteria.meetCriteria(mails,subject);
        return otherCriteria.meetCriteria(firstCriteriaPersons,sender);
    }
}
