package mail.com.mail_project;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.Objects;
import java.util.Vector;

public class Mail {
    private String type="";
    private String sender="";
    private String senderUsername="";
    private String subject="";
    private int importance=0;
    public String idCode="";
    public int index=0;
    private Vector<String> receivers=new Vector<String>();
    private Vector<String> receiversUsername=new Vector<String>();
    private String body="";
    private Vector<String> attachment=new Vector<String>();
    private boolean read=false;
    private String deletionDate="";
    private String date="";



    public void setDate(String date) {
        this.date = date;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public String getDeletionDate() {
        return deletionDate;
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////
    public void setDeletionDate(String deletionDate) {
        this.deletionDate = deletionDate;
    }





    public Mail() {
    }
    public Mail(Vector<String> receiver, Vector<String> receiverUsername, String type, String sender, String senderUsername, String subject, int importance, boolean read, String body, Vector<String> attachment,String deletionDate,String date) {
        this.type = type;
        this.sender = sender;
        this.senderUsername = senderUsername;
        this.subject = subject;
        this.read = read;
        this.body = body;
        this.attachment = attachment;
        this.receivers=receiver;
        this.receiversUsername=receiverUsername;
        this.deletionDate=deletionDate;
        this.date=date;
        this.importance=importance;

    }
    public String getDate() {
        return date;
    }

    public String DateCon(){
        String[] date_time = new String[3];
        date_time = this.date.split(" ");
        String[] date = new String[3];
        String[] time = new String[2];
        System.out.println(this.date);
        System.out.println("date time");
        System.out.println(date_time);
        date = date_time[0].split("-");
        time = date_time[1].split(":");
        String am_pm = date_time[2];
        String result;
        if (Objects.equals(am_pm, "PM")) {
            result = date[0] + date[1] + date[2] + Integer.toString(Integer.parseInt(time[0]) + 12) + time[1];
        }
        else {
            if(time[0].length()==1)
                result = date[0] + date[1] + date[2] +"0"+time[0] + time[1];
            else
                result = date[0] + date[1] + date[2] + time[0] + time[1];
        }
        return result;
    }
//    public void setDate() {
//        LocalDate date = LocalDate.now();
//        LocalTime time = LocalTime.now();
//        this.date= String.valueOf(date);
//        if(time.getMinute()<10)
//            this.date+=" "+ time.getHour()%12 +":0"+time.getMinute();
//        else
//            this.date+=" "+ time.getHour()%12 +":"+time.getMinute();
//        if(time.getHour()<11 )
//            this.date+=" AM";
//        else
//            this.date+=" PM";
//    }

    public void addReceiverUsername(String usrname){
        receiversUsername.addElement(usrname);
    }
    public Vector<String> getReceiversUsername() {
        return receiversUsername;
    }
    public void setReceivers(Vector<String> receivers) {
        this.receivers = receivers;
    }
    public void setReceiversUsername(Vector<String> receiversUsername) {
        this.receiversUsername = receiversUsername;
    }

    public Vector<String> getReceivers() {
        return receivers;
    }




    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }



    public void setImportance(int importance) {
        this.importance = importance;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }



    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setAttachment(Vector<String> attachment) {
        this.attachment = attachment;
    }

    public void setImporance(int imporance) {
        this.importance = imporance;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    //    private Date date=new Date();


    public String getType() {
        return type;
    }

    public String getSender() {
        return sender;
    }


    public String getSubject() {
        return subject;
    }

//    public Date getDate() {
//        return date;
//    }

    public String getBody() {
        return body;
    }

    public Vector<String> getAttachment() {
        return attachment;
    }

    public int getImportance() {
        return importance;
    }

    public boolean isRead() {
        return read;
    }

    public Mail clone(Vector<String> receiver,Vector<String> receiverUsername){
        Mail clonedMail=new Mail(receiver,receiverUsername,this.type,this.sender,this.senderUsername,this.subject,this.importance,false,this.body,this.attachment,this.deletionDate,this.date);
        return clonedMail;
    }


}
