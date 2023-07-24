package mail.com.mail_project;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public interface DatabaseIF {
    public boolean newFolder(String idCode,String folder) throws IOException ;
    public void clear_file(String idCode,String folder) throws IOException;
    public JSONObject get_user(JSONObject account) throws IOException, ParseException;


}
