package mail.com.mail_project;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Vector;
import org.json.simple.parser.JSONParser;
public class Database implements DatabaseIF{
    private String path= Paths.get("").toAbsolutePath().toString();
    private static Database instance;

    public static Database getInstance (){
        if (Database.instance==null){
            //elmafrod net2aked en mafessh 7ad tny by3mel object f thread
            if (Database.instance==null){
                Database.instance= new Database();
            }
        }
        return Database.instance;
    }
    public JSONArray get_verification_file() throws IOException, ParseException {
        JSONArray json = new JSONArray();
        JSONParser jsonP = new JSONParser();
        Path of = Path.of("");
        Path path = of.toAbsolutePath();
        String p=path.normalize().toString()+"\\database\\users.json";
        FileReader reader = new FileReader(p);
        Object obj = jsonP.parse(reader);
        json = (JSONArray) obj;

        return json;
    }
    public void add_new_user(JSONObject account,String id){
        account.put("id",id);
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(".//database/users.json" , "rw");
            long pos = randomAccessFile.length();
            while (randomAccessFile.length() > 0) {
                pos--;
                randomAccessFile.seek(pos);
                if (randomAccessFile.readByte() == ']') {
                    randomAccessFile.seek(pos);
                    break;
                }
            }
            if (pos!=1){
                randomAccessFile.writeBytes(",");}
            randomAccessFile.writeBytes(account.toJSONString());
            randomAccessFile.writeBytes("]");

            Path of = Path.of("");
            Path path = of.toAbsolutePath();
            String p=path.normalize().toString()+"\\database\\accounts\\"+id;

            File f=new File(p);
            f.mkdir();
            makefiles(p,account);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void clear_file(String idCode,String folder) throws IOException {

        Path of = Path.of("");
        Path rootPath = of.toAbsolutePath();
        String path=rootPath.normalize().toString()+"\\database\\accounts\\"+idCode+"\\"+folder+".json";
        FileWriter fileWriter = new FileWriter(path, false);
        fileWriter.write("[]");
        fileWriter.close();
    }
    public JSONObject get_user(JSONObject account) throws IOException, ParseException {
        JSONObject userMainPage=new JSONObject();
        Path of = Path.of("");
        Path path = of.toAbsolutePath();
        String id=(String) account.get("id");
        String p=path.normalize().toString()+"\\database\\accounts\\"+id+"\\main page.json";
        FileReader reader = new FileReader(p);
        JSONParser jsonP = new JSONParser();
        userMainPage=(JSONObject) jsonP.parse(reader);
        return userMainPage;

    }
    public void makefiles(String path,JSONObject account) throws IOException {
        String[] filesName={"main page","inbox","sent","drafts","trash","important","contacts"};
        for(int i=0;i<7;i++){
            File file=new File(path+"\\"+filesName[i]+".json");
            file.createNewFile();

            if(filesName[i].equals("main page")){
                JSONArray array=new JSONArray();
                array.add("inbox");
                array.add("sent");
                array.add("trash");
                array.add("drafts");
                JSONObject obj=new JSONObject();
                obj.put("id",account.get("id"));
                obj.put("username",account.get("username"));
                obj.put("email",account.get("email"));
                obj.put("password",account.get("password"));
                obj.put("photo",null);
                obj.put("background",null);
                obj.put("nicknameMap",null);
                obj.put("folders",array);
                ObjectMapper mapper = new ObjectMapper();
                FileWriter fileWriter = new FileWriter(path+"\\"+filesName[i]+".json", false);
                fileWriter.write(obj.toString());
                fileWriter.close();
            }
            else{
                FileWriter fileWriter = new FileWriter(path+"\\"+filesName[i]+".json", false);
                fileWriter.write("[]");
                fileWriter.close();
            }
        }

    }

    public void updateMainPage(String idCode,JSONObject obj) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Path of = Path.of("");
        Path rootPath = of.toAbsolutePath();
        String path=rootPath.normalize().toString()+"\\database\\accounts\\"+idCode+"\\main page.json";
        FileWriter fileWriter = new FileWriter(path, false);
        fileWriter.write(obj.toString());
        fileWriter.close();
    }
    public Vector<Mail> requestFolder(String idCode, String folder) throws IOException, ParseException {
        //7ft7 folder
        JSONParser jsonP = new JSONParser();
        Path of = Path.of("");
        Path rootPath = of.toAbsolutePath();
        String path=rootPath.normalize().toString()+"\\database\\accounts\\"+idCode+"\\"+folder+".json";
        FileReader reader = new FileReader(path);
        Vector <Mail> mailList= new Vector<Mail>();
        JSONArray list = (JSONArray) jsonP.parse(reader);
        list.forEach(currentMail -> {
                    mailList.addElement( parse_mail((JSONObject) currentMail));

                }
                );

        return mailList;
    }
    public Vector<Contact> requestContacts(String idCode, String folder) throws IOException, ParseException {
        //7ft7 folder
        JSONParser jsonP = new JSONParser();
        Path of = Path.of("");
        Path rootPath = of.toAbsolutePath();
        String path=rootPath.normalize().toString()+"\\database\\accounts\\"+idCode+"\\"+folder+".json";
        FileReader reader = new FileReader(path);Vector <Contact> List= new Vector<Contact>();
        JSONArray list = (JSONArray) jsonP.parse(reader);

        list.forEach(current -> {
                      List.addElement( parse_contact((JSONObject) current));
                }
        );
        return List;
    }
    public Contact parse_contact(JSONObject jsonM) {
        Contact contact=new Contact();
        contact.setDefaultEmail((String) jsonM.get("defaultEmail"));
        contact.setNickName((String) jsonM.get("nickName"));
        JSONArray emails =(JSONArray) jsonM.get("emails");
        if(emails!=null) {
            Vector<String> temp = new Vector<String>();
            for (int i = 0; i < emails.size(); i++) {
                temp.addElement((String) emails.get(i));
            }
            contact.setEmails(temp);
        }
        return contact;
    }
    public Mail parse_mail(JSONObject jsonM){
        Mail mail=new Mail();

//        mail.setAttachment((String) jsonM.get("attachment"));
        mail.setBody((String) jsonM.get("body"));
        Long l=(Long) jsonM.get("importance");
//        l= (Long) jsonM.get("importance");
        mail.setImporance(l.intValue());
        mail.setRead((boolean) jsonM.get("read"));
        mail.setSenderUsername((String) jsonM.get("senderUsername"));
        JSONArray attachs= (JSONArray) jsonM.get("attachment");
        JSONArray recvrs =(JSONArray) jsonM.get("receivers");
        JSONArray recvrsusrname =(JSONArray) jsonM.get("receiversUsername");
        if (attachs!=null){
            Vector<String> temp3= new Vector<String>();
            for (int i=0;i<attachs.size();i++){
                temp3.addElement((String) attachs.get(i));
            }
            mail.setAttachment(temp3);
        }
        if(recvrs!=null) {
            Vector<String> temp1 = new Vector<String>();
            Vector<String> temp2 = new Vector<String>();

            for (int i = 0; i < recvrsusrname.size(); i++) {
                temp1.addElement((String) recvrs.get(i));
                temp2.addElement((String) recvrsusrname.get(i));

            }
            mail.setReceiversUsername(temp2);
            mail.setReceivers(temp1);

        }
        mail.setSender((String) jsonM.get("sender"));
        mail.setSubject((String) jsonM.get("subject"));
        mail.setType((String) jsonM.get("type"));
        mail.setDate((String) jsonM.get("date"));
        mail.setDeletionDate((String) jsonM.get("deletionDate"));
        return mail;

    }


    public void add_mail_draft_contact(Object obj,String account,String file) throws JsonProcessingException {

        Path of = Path.of("");
        Path rootPath = of.toAbsolutePath();
        String path=rootPath.normalize().toString()+"\\database\\accounts\\"+account+"\\"+file+".json";
        RandomAccessFile randomAccessFile = null;
        ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(obj);
        try {
            randomAccessFile = new RandomAccessFile(path, "rw");

            long pos = randomAccessFile.length();

            while (randomAccessFile.length() > 0) {
                pos--;
                randomAccessFile.seek(pos);
                if (randomAccessFile.readByte() == ']') {
                    randomAccessFile.seek(pos);
                    break;
                }
            }
            if (pos!=1){
                randomAccessFile.writeBytes(",");}
            randomAccessFile.writeBytes(jsonData+"]");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

public boolean newFolder(String idCode,String folder) throws IOException {
    //create new file

    Path of = Path.of("");
    Path rootPath = of.toAbsolutePath();
    String path=rootPath.normalize().toString()+"\\database\\accounts\\"+idCode+"\\"+folder+".json";
    File file=new File(path);
    //File file=new File(directory);
    file.createNewFile();


    FileWriter fileWriter = new FileWriter(file, false);
    fileWriter.write("[]");
    fileWriter.close();
    return true;


}

}


