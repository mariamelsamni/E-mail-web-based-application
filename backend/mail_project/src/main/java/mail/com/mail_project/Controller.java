package mail.com.mail_project;
//import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/mail")

public class Controller {
    Mediator mediator=new Mediator();
    public Controller() throws IOException, ParseException {
    }

    //tested gerges
    @PostMapping("/signup")
    @ResponseBody
    public Vector<String> signup(@RequestBody JSONObject account) throws IOException, ParseException {
        Vector<String> v=new Vector<String>();
        v.addElement(mediator.notify_signup(account));
        if (v.get(0).equals("false")){
            return v;
        }
        v.addElement((String) account.get("username"));
        return v;

    }
    //tested gerges
    @PostMapping("/login")
    @ResponseBody
    public Vector<String> login(@RequestBody JSONObject account) throws IOException, ParseException {
        Vector<String> v=new Vector<String>();
        if(mediator.notify_login(account)==null){
            v.addElement("false");
            return v;
        }
        JSONObject obj=mediator.notify_login(account);
        System.out.println(obj);
        v.addElement((String) obj.get("id"));
        v.addElement((String) obj.get("username"));
        return v;
    }
//    @GetMapping("/logout")
//    @ResponseBody
//    public boolean logout(@RequestParam String idCode) throws IOException, ParseException {
//        mediator.logout(idCode);
//        return true;
////        return mediator.notify_login(account);
//    }
    //tested samni
    @PostMapping("/mail")
    @ResponseBody
    public boolean new_mail(@RequestBody Mail mail ) throws IOException, ParseException {
        String idCode=mail.idCode;
        System.out.println("in controller");
        System.out.println(mail.getReceivers().size());
        System.out.println("id code mn gowwa el mail");
        System.out.println(idCode);
        return mediator.add_mail(idCode,mail);
        // return true;
    }
    @GetMapping("/requestFolders")
    public Vector<String> request_folders(@RequestParam String idCode ){

        return mediator.request_folders(idCode);
    }
    //tested samni mn 3'er sort defaul
    @GetMapping("/requestMailsFolder")
    public Vector<MailSummary> request_mails_folder(@RequestParam String idCode, @RequestParam String folder ) throws IOException, ParseException {
//        System.out.println(f.get("password"));
        Vector<MailSummary> summaries=mediator.request_mails_folder(idCode,folder);
        return summaries;
    }

//    @GetMapping("/requestContactsFolder")
//    public Vector<Mail> request_contacts_folder(@RequestParam String idCode, @RequestParam String folder ) throws IOException, ParseException {
////        Vector<Mail> mails=mediator.request_mails_folder(idCode,folder);
//        return null;
//    }

//    C:\Users\Samni\Downloads\mail_project_new\mail_project\database\accounts\20011878\drafts.json
    //tested elsamni
    @PostMapping("/draftt")
    @ResponseBody
    public boolean new_draft(@RequestBody Mail draft) throws IOException, ParseException {
        String idCode=draft.idCode;
        return mediator.add_draft(idCode,draft);
    }

    //tested elsamni
    @PostMapping("/addContact")
    @ResponseBody
    public boolean add_contact(@RequestBody Contact contact) throws IOException, ParseException {
        String idCode=contact.idCode;
        contact.getEmails().addElement(contact.getDefaultEmail());
        return mediator.addContact(idCode,contact);
    }
    @GetMapping("/defaultEmail")
    public boolean change_defaultEmail(@RequestParam String idCode,@RequestParam int index,@RequestParam String defaultEmail ) throws IOException, ParseException {
        return mediator.change_defaultEmail(idCode,index,defaultEmail);
    }
    @PostMapping("/editDraft")
    @ResponseBody
    public boolean edit_draft(@RequestBody Mail draft) throws IOException, ParseException {
        String idCode=draft.idCode;
        int index=draft.index;
        return mediator.edit_draft(idCode,draft,index);
    }
    @PostMapping("/sendDraft")
    @ResponseBody
    public boolean send_draft(@RequestBody Mail draft) throws IOException, ParseException {
        String idCode=draft.idCode;
        int index=draft.index;
        return mediator.send_draft(idCode,draft,index);
    }



//    @GetMapping("/defaultMail")
//    @ResponseBody
//    public boolean add_contact(@RequestBody Contact contact) throws IOException, ParseException {
//        String idCode=contact.idCode;
//        return mediator.addContact(idCode,contact);
//    }

    @GetMapping("/requestContactsFolder")
    public Vector<String > request_contacts_folder(@RequestParam String idCode) throws IOException, ParseException {
//        Vector<String> vector=new Vector<>();
//        vector.addElement("rgreg");
//        return vector;
        return mediator.request_constacts_folder(idCode) ;
    }
    @GetMapping("/requestContact")
    public Vector<String> request_a_contact(@RequestParam String idCode, @RequestParam int index) throws IOException, ParseException {

        return mediator.request_a_contact(idCode,index) ;
    }

    @GetMapping("/subjects")
    @ResponseBody
    public String[] request_subject(@RequestParam String idCode){
        return mediator.requestSubject(idCode);
    }
    @GetMapping("/filter/senders")
    @ResponseBody
    public Vector<String> filterContacts(@RequestParam String idCode){
        return mediator.filterSenders(idCode);
    }
    @PostMapping("/filter")
    @ResponseBody
    public Vector<MailSummary> filter(@RequestParam String idCode,@RequestBody JSONObject filterDetails){
        System.out.println("debugging filter");
        System.out.println(filterDetails);
        System.out.println(idCode);
        return mediator.notify_filteration(idCode,filterDetails);
        //return null;
    }
    @GetMapping("/requestSort")
    @ResponseBody
    public String[] requestSortOptions(@RequestParam String idCode){
        return mediator.sortOptions(idCode);
    }
    @GetMapping("/sort")
    @ResponseBody
    public Vector<MailSummary> sort(@RequestParam String idCode ,@RequestParam String sortType){
        return mediator.notifySorting(idCode , sortType);

    }

    @GetMapping("/search")
    @ResponseBody
    public Vector<MailSummary> search(@RequestParam String idCode ,@RequestParam String searchWord){
        return mediator.notifySearching(idCode,searchWord);
    }

    @PostMapping("/photo")
    @ResponseBody
    public String updatePhoto(@RequestBody String idCode,String path){
        return null;
    }

    @PostMapping("/background")
    @ResponseBody
    public String updateBachGround(@RequestBody String idCode,String path){
        return null;
    }

//    @PostMapping("/inbox")
//    @ResponseBody
//    public Vector<MailSummary> inbox(@RequestBody String idCode){
//        return null;
//    }

    @PostMapping("/important")
    @ResponseBody
    public Vector<MailSummary> getImporant(@RequestBody String idCode){
        return null;
    }

//    @PostMapping("/sent")
//    @ResponseBody
//    public Vector<MailSummary> sent(@RequestBody String idCode){
//        return null;
//    }

//    @PostMapping("/draft")
//    @ResponseBody
//    public Vector<MailSummary> draft(@RequestBody String idCode){
//        return null;
//    }
    @GetMapping("/selectMail")
    public Mail selectMail(@RequestParam String idCode,@RequestParam int messageID){
        Vector<Integer> v=new Vector<Integer>();
        v.addElement(messageID);
        mediator.set_mails_read(idCode,v,true);
        return mediator.requestMailByIndex(idCode,messageID);
    }
//    @GetMapping("/newFolder")
//    public boolean newFolder(@RequestParam String idCode,@RequestParam String folder,@RequestParam Vector<Mail> messages) throws IOException {
//        //elfolder f khatwaa w en ana a move dy f khatwa tnyaaa
//        return mediator.notify_newFolder(idCode, folder,messages);
//    }
    @GetMapping("/newFolder")
    public boolean newFolder(@RequestParam String idCode,@RequestParam String folder) throws IOException {
    //elfolder f khatwaa w en ana a move dy f khatwa tnyaaa
    return mediator.notify_newFolder(idCode,folder);
    }
    @PostMapping("/move")
    @ResponseBody
    /////////////////////////////////////////////////////////////////////
    public boolean moveMails(@RequestBody JSONObject obj) throws IOException {
        Vector<Integer> indices=new Vector<>();
        String idCode=(String) obj.get("idCode");
        String toFolder=(String) obj.get("toFolder");
        for (Object i : (ArrayList) obj.get("indices")) {
            indices.addElement((Integer) i);
        }
        System.out.println(idCode);
        mediator.moveMails(idCode, toFolder, indices,true);
        return true;
    }
    @PostMapping("/delete")
    @ResponseBody
    ////////////////////////////////////////////////////////////////////////////
    public boolean delete(@RequestBody JSONObject obj) throws IOException {
        Vector<Integer> indices=new Vector<>();
        String idCode=(String) obj.get("idCode");
        for (Object i : (ArrayList) obj.get("indices")) {
            indices.addElement((Integer) i);
        }
        mediator.deleteMails(idCode,indices,true);
        return true;
    }
    @PostMapping("/attachment")
    public ResponseEntity<String> handleAttachments(@RequestParam("attachment")MultipartFile[] attachments) throws IOException {
        for (MultipartFile attachment: attachments){
            attachment.transferTo(new File("C:\\Users\\Samni\\Downloads\\mail_project_last\\mail_project\\database\\accounts\\attachments"+attachment.getOriginalFilename()));


        }

        return ResponseEntity.ok("attachment received");
    }
    @PostMapping("/setMailsRead")
    public boolean set_mails_read(@RequestBody JSONObject obj) throws IOException {
        Vector<Integer> indices = new Vector<>();
        String idCode = (String) obj.get("idCode");
        for (Object i : (ArrayList) obj.get("indices")) {
            indices.addElement((Integer) i);
        }
        boolean read=(boolean) obj.get("read");
        mediator.set_mails_read(idCode,indices,read);
        return true;
    }
    @GetMapping("/deleteContact")
    ////////////////////////////////////////////////////////////////////////////
    public boolean deleteContact(@RequestParam String idCode,@RequestParam int index) throws IOException {
        mediator.deleteContact(idCode,index);
        return true;
    }
    @GetMapping("/moveToFolders")
    public Vector<String> getMoveToFolders(@RequestParam String idCode){
        return mediator.getMoveToFolders(idCode);
    }
    @GetMapping("/openAttach")
    public void open_attachment(@RequestParam String path) throws IOException {

        ProcessBuilder builder = new ProcessBuilder();
        builder.command("cmd.exe", "/c", path);
        Process process = builder.start();


	}












}
