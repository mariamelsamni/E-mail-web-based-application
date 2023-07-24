package mail.com.mail_project;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class Mediator {

    HashMap<String, User> userHashMap = new HashMap<String, User>();
    Stack<String> logOut = new Stack<String>();
    Proxy databaseProxy = new Proxy(this);
    Director director = new Director();
    UserBuilder builder = new UserBuilder();
    private int shift;

    public Mediator() throws IOException, ParseException {
    }

    public String generate_id() {
        StringBuilder id = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 8; i++) {
            int wich_interval = random.nextInt(0, 30);
            if (wich_interval > 20) {
                int ranNum = random.nextInt(49, 58);
                id.append(String.valueOf((char) ranNum));
            } else if (wich_interval > 10) {
                int ranNum = random.nextInt(65, 91);
                id.append(String.valueOf((char) ranNum));
            } else {
                int ranNum = random.nextInt(97, 123);
                id.append(String.valueOf((char) ranNum));
            }
        }
        return id.toString();


    }

    public Boolean addContact(String idCode, Contact contact) throws IOException, ParseException {
        if (databaseProxy.addContact(idCode, contact)) {
            builder.userMap.get(idCode).getContacts().addElement(contact);
            builder.userMap.get(idCode).addNickname_to_contacts(contact.getNickName(), contact.getDefaultEmail());
            return true;
        } else {
            return false;
        }
    }

    public String notify_signup(JSONObject account) throws IOException, ParseException {
        String id = generate_id();
        if (databaseProxy.verify_signup(account, id)) {

            String usedID;
            if (logOut.size() != 0) {
                usedID = logOut.pop();
            } else {
                usedID = id;
            }
            UserBuilder builder = new UserBuilder();
            director.new_user(builder, account, usedID, id);
            return id;
        }
        return "false";
    }

    public JSONObject notify_login(JSONObject account) throws IOException, ParseException {
        JSONObject userAccount = databaseProxy.verify_login(account);
        if (userAccount != null) {
            String id = (String) userAccount.get("id");
            String usedID;
            if (logOut.size() != 0)
                usedID = logOut.pop();
            else
                usedID = id;
            UserBuilder builder = new UserBuilder();
            director.existing_user(builder, userAccount, usedID, id);
            this.request_constacts_folder(id);

            return userAccount;

        }
        return null;
    }

    public boolean add_mail(String idCode, Mail mail) throws IOException, ParseException {
        UserBuilder.userMap.get(idCode).mapNicknameToEmail(mail.getReceivers());
        mail.setSender(UserBuilder.userMap.get(idCode).getUsername());
        mail.setSenderUsername(UserBuilder.userMap.get(idCode).getUsername());
        mail.setDate(getCurrentDate());
        return (databaseProxy.add_mail(idCode, mail));
    }

    public boolean add_draft(String idCode, Mail draft) throws JsonProcessingException {
        draft.setDate("");
        draft.setDate(getCurrentDate());

        databaseProxy.add_draft_folder(idCode, draft, "drafts");
        return true;
    }

    public Vector<MailSummary> request_mails_folder(String idCode, String folder) throws IOException, ParseException {
        if (UserBuilder.userMap.get(idCode).getMarked_as_read()) {
            databaseProxy.clear_file(idCode, UserBuilder.userMap.get(idCode).getSelected_folder());
            if (!UserBuilder.userMap.get(idCode).getSelected_folder().equals("contacts")) {
                UserBuilder.userMap.get(idCode).getMails().forEach(mail -> {
                    databaseProxy.update_all_mails_in_file(idCode, mail, UserBuilder.userMap.get(idCode).getSelected_folder());
                });
            } else {
                UserBuilder.userMap.get(idCode).getContacts().forEach(contact -> {
                    databaseProxy.update_all_mails_in_file(idCode, contact, UserBuilder.userMap.get(idCode).getSelected_folder());
                });
            }

        }
        UserBuilder.userMap.get(idCode).setSelected_folder(folder);
        UserBuilder.userMap.get(idCode).setMarked_as_read(false);
        Vector<Mail> mails = databaseProxy.requestFolder(idCode, folder);
        //////////////////////////////////////////////////////////////////////////////////////////////////////////
        UserBuilder.userMap.get(idCode).setMails(mails, folder);
        UserBuilder.userMap.get(idCode).setSelected_folder(folder);
        if (folder.equals("trash")) {
            Vector<Integer> indices = new Vector<>();
            for (int i = 0; i < mails.size(); i++) {
                String deletionDate = mails.elementAt(i).getDeletionDate();
                String currentTime = getCurrentDate();
                if (compareA_newThanB(currentTime, deletionDate, 30)) {
                    indices.addElement(i);
                }
            }
            if (UserBuilder.userMap.get(idCode).getSearchResult() != null)
                UserBuilder.userMap.get(idCode).getSearchResult().removeAllElements();
            moveMails(idCode, null, indices, false);
        }

        /////////////////////////////////////////////////////////////////////////////////////////////////////////
        UserBuilder.userMap.get(idCode).sort("date");
        Vector<MailSummary> summaries = UserBuilder.userMap.get(idCode).getMailSummaries();
        return summaries;
    }

    public boolean compareA_newThanB(String a, String b, int comp) {
        String[] date_timeA = new String[3];
        date_timeA = a.split(" ");
        String[] dateA = new String[3];
        String[] timeA = new String[2];
        dateA = date_timeA[0].split("-");
        timeA = date_timeA[1].split(":");
        String am_pmA = date_timeA[2];

        String[] date_timeB = new String[3];
        date_timeB = b.split(" ");
        String[] dateB = new String[3];
        String[] timeB = new String[2];
        dateB = date_timeB[0].split("-");
        timeB = date_timeB[1].split(":");
        String am_pmB = date_timeB[2];
        String resultA, resultB;
        resultA = dateA[0] + dateA[1] + dateA[2];
        resultB = dateB[0] + dateB[1] + dateB[2];


        if (Integer.parseInt(resultA) - Integer.parseInt(resultB) > comp)
            return true;
        else
            return false;
    }

    public String getCurrentDate() {
        String currentDate;
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        currentDate = String.valueOf(date);
        if (time.getMinute() < 10)
            currentDate += " " + time.getHour() % 12 + ":0" + time.getMinute();
        else
            currentDate += " " + time.getHour() % 12 + ":" + time.getMinute();
        if (time.getHour() < 11)
            currentDate += " AM";
        else
            currentDate += " PM";
        return currentDate;
    }

    public Vector<String> request_constacts_folder(String idCode) throws IOException, ParseException {
        /////////////ycheck
        if (UserBuilder.userMap.get(idCode).getMarked_as_read() & (UserBuilder.userMap.get(idCode).getSelected_folder() != null)) {
            databaseProxy.clear_file(idCode, UserBuilder.userMap.get(idCode).getSelected_folder());
            UserBuilder.userMap.get(idCode).getMails().forEach(mail -> {
                databaseProxy.update_all_mails_in_file(idCode, mail, UserBuilder.userMap.get(idCode).getSelected_folder());
            });
        }
        UserBuilder.userMap.get(idCode).setSelected_folder("contacts");
        UserBuilder.userMap.get(idCode).setMarked_as_read(false);
        Vector<Contact> contacts = databaseProxy.requestContactsFolder(idCode);
        UserBuilder.userMap.get(idCode).setContacts(contacts);
        Vector<String> contactNames = UserBuilder.userMap.get(idCode).getAllContacts();

        return contactNames;
    }

    public Mail cloneMail(Mail mail, Vector<String> receiver, Vector<String> receiverUsrname) {

        return mail.clone(receiver, receiverUsrname);
    }

    public Mail requestMailByIndex(String idCode, int index) {
        String selected = UserBuilder.userMap.get(idCode).getSelected_folder();
        if (!selected.equals("sent") & !selected.equals("trash") & !selected.equals("drafts")) {
            if (!UserBuilder.userMap.get(idCode).requestMailByIndex(index).isRead()) {
                UserBuilder.userMap.get(idCode).requestMailByIndex(index).setRead(true);
                UserBuilder.userMap.get(idCode).setMarked_as_read(true);
            }
        }
        return UserBuilder.userMap.get(idCode).requestMailByIndex(index);
    }



    public void logout(String idCode) throws IOException {
        /////3yzen ne update el main page
        this.updateMainPage(idCode);
        logOut.push(idCode);
        UserBuilder.userMap.get(idCode).reset();
    }

    public void updateMainPage(String idCode) throws IOException {
        User user = UserBuilder.userMap.get(idCode);
        JSONObject obj = new JSONObject();
        obj.put("id", idCode);
        obj.put("username", user.username);
        obj.put("email", user.getEmail());
        obj.put("password", user.getPassword());
        obj.put("photo", null);
        obj.put("background", null);
        obj.put("nicknameMap", user.getNicknameMap());
        obj.put("folders", user.getFolderNames());

        databaseProxy.updateMainPage(idCode, obj);
    }

    public Vector<MailSummary> notify_filteration(String id, JSONObject filterDetails) {
        User user = UserBuilder.userMap.get(id);
        if (UserBuilder.userMap.get(id).getSearchResult() != null)
            user.getSearchResult().removeAllElements();
        Vector<Mail> result = new Vector<Mail>();
        ArrayList<String> categories = (ArrayList) filterDetails.get("categories");
        ArrayList<String> subjects = (ArrayList) filterDetails.get("subjects");
        ArrayList<String> senders = (ArrayList) filterDetails.get("senders");


        if (categories.size() == 2) {
            CriteriaAnd and = new CriteriaAnd();
            result = and.meetCriteria(UserBuilder.userMap.get(id).getMails(), subjects, senders);
        } else if (Objects.equals((String) categories.get(0), "subjects")) {
            CriteriaSubject criteriaSubject = new CriteriaSubject();
            result = criteriaSubject.meetCriteria(UserBuilder.userMap.get(id).getMails(), subjects);
        } else {
            CriteriaSender criteriaSender = new CriteriaSender();
            result = criteriaSender.meetCriteria(UserBuilder.userMap.get(id).getMails(), senders);
        }
        user.setSearchResult(result);
        return user.getMailState().summarizeMails(result);


    }

    public Vector<String> request_a_contact(String idCode, int index) {

        return UserBuilder.userMap.get(idCode).requestContactByIndex(index).getEmails();

    }

    public boolean change_defaultEmail(String idCode, int index, String defaultEmail) throws IOException, ParseException {
        UserBuilder.userMap.get(idCode).setMarked_as_read(true);
        UserBuilder.userMap.get(idCode).requestContactByIndex(index).setDefaultEmail(defaultEmail);
        UserBuilder.userMap.get(idCode).getNicknameMap().replace(UserBuilder.userMap.get(idCode).requestContactByIndex(index).getNickName(), defaultEmail);
        return true;
    }


    public boolean notify_newFolder(String idCode, String folder) throws IOException {
        UserBuilder.userMap.get(idCode).add_folder(folder); //a7na w bn3ml logout hn3ml save mn elawll
        this.updateMainPage(idCode);
        return databaseProxy.newFolder(idCode, folder);
    }

    public boolean edit_draft(String idCode, Mail draft, int index) throws IOException {
//        builder.userMap.get(idCode).setMarked_as_read(true);
        databaseProxy.clear_file(idCode, UserBuilder.userMap.get(idCode).getSelected_folder());
        draft.setDate(getCurrentDate());
        UserBuilder.userMap.get(idCode).edit_draft(draft, index);
        UserBuilder.userMap.get(idCode).getMails().forEach(mail -> {
            databaseProxy.update_all_mails_in_file(idCode, mail, UserBuilder.userMap.get(idCode).getSelected_folder());
        });
        return true;
    }

    public boolean send_draft(String idCode, Mail draft, int index) throws IOException, ParseException {
        draft.setDate(getCurrentDate());
        if (this.add_mail(idCode, draft)) {
            UserBuilder.userMap.get(idCode).remove_mail_from_index(index);
            databaseProxy.clear_file(idCode, UserBuilder.userMap.get(idCode).getSelected_folder());
            UserBuilder.userMap.get(idCode).getMails().forEach(mail -> {
                databaseProxy.update_all_mails_in_file(idCode, mail, UserBuilder.userMap.get(idCode).getSelected_folder());
            });
            return true;
        }
        return false;
    }

    public boolean moveMails(String idCode, String toFolder, Vector<Integer> indices, Boolean move) throws IOException {
        ////////////////////////////////////////////////////////////////////////////////////////////////
        Vector<Mail> mails = new Vector<Mail>();
        shift = 0;
        indices.forEach(index -> {
            mails.addElement(UserBuilder.userMap.get(idCode).remove_mail_from_index(index - shift));
            shift += 1;
        });
        databaseProxy.clear_file(idCode, UserBuilder.userMap.get(idCode).getSelected_folder());
        UserBuilder.userMap.get(idCode).getMails().forEach(mail -> {
            databaseProxy.update_all_mails_in_file(idCode, mail, UserBuilder.userMap.get(idCode).getSelected_folder());
        });
        if (move) {
            mails.forEach(mail -> {
                try {
                    databaseProxy.add_draft_folder(idCode, mail, toFolder);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            });
        }
        UserBuilder.userMap.get(idCode).setMarked_as_read(false);

        return true;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    public boolean deleteMails(String idCode, Vector<Integer> indices, Boolean move) throws IOException {
        //7t-set el deletion date elawal b3den tnady move mails bs l folder trash
        if (UserBuilder.userMap.get(idCode).getSearchResult().size() != 0) {
            Vector<Integer> actualInices = new Vector<Integer>();
            indices.forEach(i -> {
                Mail m = UserBuilder.userMap.get(idCode).getSearchResult().elementAt(i);
                Integer actualIndx = UserBuilder.userMap.get(idCode).getMails().indexOf(m);
                actualInices.addElement(actualIndx);
                UserBuilder.userMap.get(idCode).getMails().elementAt(actualIndx).setDeletionDate(getCurrentDate());
                UserBuilder.userMap.get(idCode).getSearchResult().remove(i);
            });
            moveMails(idCode, "trash", actualInices, move);
        } else {
            indices.forEach(i -> {
                UserBuilder.userMap.get(idCode).getMails().elementAt(i).setDeletionDate(getCurrentDate());
            });
            moveMails(idCode, "trash", indices, move);

        }
        return true;

    }

    public boolean deleteContact(String idCode, int index) throws IOException {
        UserBuilder.userMap.get(idCode).removeContactByIndex(index);

//
        databaseProxy.clear_file(idCode, "contacts");
        UserBuilder.userMap.get(idCode).getContacts().forEach(contact -> {
            databaseProxy.update_all_mails_in_file(idCode, contact, "contacts");
        });
        return true;
    }

    public boolean set_mails_read(String idCode, Vector<Integer> indices, boolean read) {
        UserBuilder.userMap.get(idCode).setMarked_as_read(true);
        indices.forEach(index -> {
            UserBuilder.userMap.get(idCode).getMails().get(index).setRead(read);
        });
        return true;
    }

    public Vector<MailSummary> notifySorting(String idCode, String sortType) {
        User user = UserBuilder.userMap.get(idCode);
        user.sort(sortType);
        return user.getMailSummaries();
    }

    public Vector<MailSummary> notifySearching(String idCode, String searchWord) {
        User user = UserBuilder.userMap.get(idCode);
        if (UserBuilder.userMap.get(idCode).getSearchResult() != null)
            user.getSearchResult().removeAllElements();
        Vector<Mail> mails = user.getMails();
        Vector<Mail> result = new Vector<>();
        for (int i = 0; i < mails.size(); i++) {
            if (mails.elementAt(i).getDate().contains(searchWord)) {
                result.addElement(mails.elementAt(i));
                continue;
            } else if (mails.elementAt(i).getSender().contains(searchWord)) {
                result.addElement(mails.elementAt(i));
                continue;
            } else if (mails.elementAt(i).getSubject().contains(searchWord)) {
                result.addElement(mails.elementAt(i));
                continue;
            } else if (mails.elementAt(i).getBody().contains(searchWord)) {
                result.addElement(mails.elementAt(i));
                continue;
            }
//            else if(mails.elementAt(i).getAttachment().indexOf(searchWord,mails.elementAt(i).getAttachment().lastIndexOf("\\"))!=-1){
//                result.addElement(mails.elementAt(i));
//                continue;
//            }
            else {
                for (int j = 0; j < mails.elementAt(i).getReceivers().size(); j++) {
                    if (mails.elementAt(i).getReceivers().elementAt(j).contains(searchWord)) {
                        result.addElement(mails.elementAt(i));
                        break;
                    }
                }
            }
        }
        user.setSearchResult(result);
        return user.getMailState().summarizeMails(result);
    }

    public String[] sortOptions(String idCode) {
        return UserBuilder.userMap.get(idCode).getMailState().getSortingOptions();
    }

    public String[] requestSubject(String idCode) {
        Vector<Mail> mails = UserBuilder.userMap.get(idCode).getMails();
        String[] subjects = new String[mails.size()];
        for (int i = 0; i < mails.size(); i++) {
            subjects[i] = mails.elementAt(i).getSubject();
        }
        return subjects;
    }

    public Vector<String> filterSenders(String idCode) {
        Vector<String> result = new Vector<String>();
        if (UserBuilder.userMap.get(idCode).getSelected_folder().equals("sent"))
            return result;
        Vector<Mail> mails = UserBuilder.userMap.get(idCode).getMails();

        for (int i = 0; i < mails.size(); i++) {
            if (!result.contains(mails.elementAt(i).getSender())) {
                result.addElement(mails.elementAt(i).getSender());
            }
        }
        return result;
    }

    public Vector<String> request_folders(String idCode) {
        User user = UserBuilder.userMap.get(idCode);
        return user.getFolderNames();
    }

    public Vector<String> getMoveToFolders(@RequestParam String idCode) {
        User user = UserBuilder.userMap.get(idCode);
        Vector v = user.getFolderNames();
        for (int i = 0; i < 4; i++) {
            v.removeElementAt(0);
        }
        return v;
    }


}