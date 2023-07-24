package mail.com.mail_project;

import org.json.simple.JSONArray;

import java.util.*;

public class User {
//    private static User instance;
    private String id;
    private StateIF mailState;
    protected String username;
    private String email;
    private String password;
    private String photo;
    private String background;
//    private Boolean fromDraftToSent=false;
    public Vector<Mail> getSearchResult() {
      return searchResult;
    }

    public void setSearchResult(Vector<Mail> searchResult) {
        this.searchResult = searchResult;
    }

    //    private Boolean fromDraftToSent=false;
    private Vector<Mail> searchResult=new Vector<Mail>();
    public StateIF getMailState() {
        return mailState;
    }

    public String getSelected_folder() {
        return selected_folder;
    }

    public void setSelected_folder(String selected_folder) {
        this.selected_folder = selected_folder;
    }

    public HashMap<String, String> getNicknameMap() {
        return nicknameMap;
    }

    private String selected_folder="";
    private Boolean folder_is_selected=false;
    private Boolean marked_as_read=false;
    private Vector<Contact> contacts=new Vector<Contact>();
    private Vector<Mail> mails=new Vector<Mail>();

    private PriorityQueue<Mail> priority=new PriorityQueue<Mail>(1000,new importanceComparator());

    private Vector<MailSummary> mailSummaries=new Vector<MailSummary>();
    private Vector<String > contactNames=new Vector<String>();

    private String sort_criteria="date";
    private HashMap<String,String> nicknameMap=new HashMap<String,String>();

    public Boolean getFolder_is_selected() {
        return folder_is_selected;
    }

    public void setFolder_is_selected(Boolean folder_is_selected) {
        this.folder_is_selected = folder_is_selected;
    }

    public Boolean getMarked_as_read() {
        return marked_as_read;
    }

    public void setMarked_as_read(Boolean marked_as_read) {
        this.marked_as_read = marked_as_read;
    }

    public void edit_draft(Mail draft,int index){
//        mails.removeElementAt(index);
        mails.remove(index);
        mails.addElement(draft);
    }
    public Mail remove_mail_from_index(int index){
        Mail mail=mails.elementAt(index);
        mails.removeElementAt(index);
        return mail;

    }

    Vector <String> folderNames=new Vector<String>(List.of(new String[]{"inbox", "sent", "trash", "drafts"}));

    public void add_folder(String folder){
        this.folderNames.addElement(folder);
    }
    public void setFolderNames(JSONArray array){
        this.folderNames.removeAllElements();
        if (array!=null){
            array.forEach(name->{
                folderNames.addElement((String) name);
            });
        }

    }

    public void setNicknameMap(HashMap<String, String> nicknameMap) {
        this.nicknameMap = nicknameMap;
    }

    public Vector<Mail> getMails() {
        return mails;
    }
    public void sort_mails(){

    }

    public void setMails(Vector<Mail> mails,String type) {
        this.mails = mails;
        if (type.equals("drafts") || type.equals("sent")){
            this.mailState=new SentDraftsState();
        }
        else if (type.equals("inbox")){
            this.mailState=new InboxState();
        }
        else {
            this.mailState=new TrashNewFoldersState();
        }

    }
    public Vector<String> getAllContacts(){
        this.contactNames.clear();
        this.contacts.forEach(contact ->{
                this.contactNames.addElement(contact.getNickName());
                System.out.println(contact.getNickName());
        });
        return this.contactNames;
    }
    public Mail requestMailByIndex(int index){
        if (searchResult==null || searchResult.size()==0){
            return mails.get(index);
        }
        else{
            return searchResult.get(index);
        }

    }
    public Contact requestContactByIndex(int index){
        return contacts.get(index);
    }
    public Vector<MailSummary> getMailSummaries() {
        Vector<MailSummary> summaries=mailState.summarizeMails(mails);
        return summaries;
    }

    public User() {

    }

    public User(String username, String password, String email, String photo, String background, Vector<Contact> contacts,String id) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.photo = photo;
        this.background = background;
        this.contacts = contacts;
        this.id=id;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public Vector<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(Vector<Contact> contacts) {
        nicknameMap=new HashMap<String,String>();
        this.contacts = contacts;
        contacts.forEach(contact -> {
            nicknameMap.put(contact.getNickName(),contact.getDefaultEmail());
            System.out.println(contact.getDefaultEmail());
            System.out.println(contact.getNickName());
        });

    }
    public void addContacts(Contact contact) {
        this.contacts.addElement(contact);
    }
    public void setId(String id) {
        this.id = id;
    }
    public void addNickname_to_contacts(String nickname,String email){
        if (nicknameMap==null)
            nicknameMap=new HashMap<String,String>();
        nicknameMap.put(nickname,email);
    }
    public void mapNicknameToEmail(Vector<String> nicknames){
        for (int i=0;i<nicknames.size();i++){
            String mappedEmail=nicknameMap.get(nicknames.get(i));
            if (mappedEmail!=null){
                nicknames.set(i,mappedEmail);
            }
        }


    }
    public static class importanceComparator implements Comparator< Mail> {
        public int compare(Mail m1, Mail m2) {
            if (m1.getImportance() < m2.getImportance())
                return 1;
            else if (m1.getImportance() > m2.getImportance())
                return -1;
            return 0;
        }
    }

    public Vector<String> getFolderNames() {
        return folderNames;
    }
    public void removeContactByIndex(int index){
        nicknameMap.remove(contacts.get(index).getNickName());
        contacts.removeElementAt(index);

    }
    public void sort(String field){

        this.mails.sort( new Comparator<Mail>() {
            @Override
            public int compare(Mail m1, Mail m2) {
                if(field.equals("sender")) {
                    return m1.getSender().compareTo(m2.getSender());
                } if(field.equals("subject")) {
                    return m1.getSubject().compareTo(m2.getSubject());
                } else if(field.equals("body")) {
                    System.out.println(m1.getBody().compareTo(m2.getBody()));
                    return m1.getBody().compareTo(m2.getBody());
                }
                else if(field.equals("receiver")) {
                    return m1.getReceiversUsername().elementAt(0).compareTo(m2.getReceiversUsername().elementAt(0));
                }
                else if(field.equals("date")) {
                    return m2.DateCon().compareTo(m1.DateCon());
                }
                else if(field.equals("importance")){
                    return Integer.toString(m2.getImportance()).compareTo(Integer.toString(m1.getImportance()));
                }
                    return 0;
            }
        });
    }
    public void sortPriotity(){
        if(Objects.requireNonNull(this.searchResult).size()!=0){
            System.out.println("search");
            for(int i=0;i<this.searchResult.size();i++){
                System.out.println("i1");
                this.priority.add(this.searchResult.elementAt(i));
            }
            this.searchResult.removeAllElements();
            for(int i=0;i<this.priority.size();i++){
                System.out.println("i2");
                this.searchResult.addElement(this.priority.poll());
            }
        }
        else{
            int sizem=this.mails.size();
            int sizep=this.priority.size();
            System.out.println("3ady");
            for(int i=0;i<sizem;i++){
                System.out.println("i3");
                this.priority.add(this.mails.elementAt(i));
            }
            this.mails.removeAllElements();
            for(int i=0;i<sizep;i++){
                System.out.println("4");
                this.mails.addElement(this.priority.poll());
            }
        }
    }
    public void settMails(Vector<Mail> v){
        this.mails=v;
    }

    public void setMailState(StateIF mailState) {
        this.mailState = mailState;
    }

    public void setPriority(PriorityQueue<Mail> priority) {
        this.priority = priority;
    }

    public void setMailSummaries(Vector<MailSummary> mailSummaries) {
        this.mailSummaries = mailSummaries;
    }

    public void setContactNames(Vector<String> contactNames) {
        this.contactNames = contactNames;
    }

    public void setSort_criteria(String sort_criteria) {
        this.sort_criteria = sort_criteria;
    }

    public void reset(){
        this.setMarked_as_read(false);
        this.setSelected_folder(null);
        this.setContacts(null);
        this.setUsername(null);
        this.setSearchResult(null);
        this.setEmail(null);
        this.setId(null);
        this.setBackground(null);
        this.setFolderNames(null);
        this.settMails(null);
//        this.setNicknameMap(null);
        this.nicknameMap.clear();
        this.setPhoto(null);
        this.setContactNames(null);
        this.setMailState(null);
        this.setPriority(null);
        this.setSort_criteria(null);
        this.setMailSummaries(null);

    }

}
