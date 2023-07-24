package mail.com.mail_project;

import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.Vector;

public interface CriteriaIF {
    public Vector<Mail> meetCriteria(Vector<Mail> mails, ArrayList<String> subject);
}
