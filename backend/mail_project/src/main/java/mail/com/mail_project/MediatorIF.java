package mail.com.mail_project;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Vector;

public interface MediatorIF {
    public boolean add_mail(Mail mail) throws IOException, ParseException;
    public boolean add_draft(Mail draft) throws IOException, ParseException;

}

