# E-mail-web-application
Designed object-oriented model was used with some design pattern to create mail service web application with the basic functionalities using angular for frontend and java with spring boot framework for backend.

#### **note**:
  *it's not a networked app* the aim of this project is to design object-oriented model and to use different design patterns for better design


#### the basic functionalities include:
- Composing and Drafts. 
- Default folders:
  - Inbox (default, priority)
  - Sent
  - Trash: auto delete emails after 30 days
  - Drafts
- Any additional folder that user can create and move messages to it.
- User Folders (Adding, Deleting).
- Filters according to:
  - subject or sender.
  - Subject only in sent folder.
  - both categories.
  - multiple elements in the same category
- Searching:
  - Date
  - Sender
  - Receivers
  - Subject
  - Body
  - Attachments
- Sorting:
  - Date
  - Sender
  - Receivers
  - Importance
  - Subject
  - Body
  - Attachments
- Attachment:
  - Sending multiple attachments.
  - Any type of attachments
  - View
  - delete
- contacts:
  - adding, deleting
  - a contact can have multiple emails with one set default
- selecting multiple emails to
  - move
  - mark as read
  - delete
 ## UML Class Diagram:
 ![mail](https://user-images.githubusercontent.com/96488115/218330960-6af9d26b-60f1-4b50-9aff-5593bedd37a2.png)

## Design Patterns Used:
##### 1. Proxy design pattern:
  used to control access database class, allowing to 
perform something either before or after the request gets through to it (e.g., 
verifying password or making sure that the receiver’s email is an actual existing 
email).
##### 2. Singleton design pattern:
  used to make sure that the database class has only one instance to 
  provide a global access point to this instance.
##### 3. Filter design pattern:
  used simply to implement filter feat.
##### 4. Builder design pattern:
  used to build an object of type user with a different 
  number of attributes.
##### 5. State design pattern:
  Was implemented to handle the different states of the user’s 
  opened folder. Since the behavior of each folder is different from the other. For 
  example, you can’t sort by sender in the “sent” folder while you can do that in the 
  “inbox” folder. Moreover, the list of summarized that appears when you request a 
  certain folder should contain the receiver’s username in case of “sent” folder and the 
  sender’s username in case of “inbox” folder.
##### 6. Mediator design pattern:
  Mediator DP to reduce dependencies between objects and restricts 
  direct communications between these objects and forces them to collaborate only 
  via a mediator object.
##### 7. Flyweight design pattern:
  this design pattern to avoid destroying unused objects
  (Objects of type users who have logged out) which is hard, also leaving them will 
  waste a lot of storage, so we used this design pattern to reuse already existing objects 
  instead of creating new ones.
##### 8. Prototype Design Pattern
  this DP allows making clone of mail objects so that each can be edited before saving them to database (e.g. message with multiple receivers should appear to each    receiver with only one receiver which is his name). 
  
  
  
  ## List of steps required to run our code: 
- Open any IDE for java (IntelliJ, Eclipse, etc.).
- Open backend folder from file>>open folder.
- If you don’t have JDK 19, Download it or the IDE will ask you to if you want to download.
- Run the main class
- Download Node.js
- From the terminal, install the Angular CLI globally with this command
 ```
npm install -g @angular/cli.
```
- Open VS Code or any id that can open angular.
- Open frontend folder from file>>open folder.
- Open terminal from terminal>>new terminal then write 
```
ng serve –open.
```

## screenshots of UI:
![image](https://user-images.githubusercontent.com/96488115/218702281-c0a051fb-7b32-4123-8a0a-0517836290be.png)

![image](https://user-images.githubusercontent.com/96488115/218702131-29f0478b-00ca-453e-aa67-7215c9fb2380.png)

![image](https://user-images.githubusercontent.com/96488115/218702787-cac895b6-d965-4321-96a0-205a61abc1c9.png)
