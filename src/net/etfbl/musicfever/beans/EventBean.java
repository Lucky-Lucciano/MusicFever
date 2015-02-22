package net.etfbl.musicfever.beans;

import java.io.Serializable;
import java.security.Security;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.smtp.SMTPTransport;

import net.etfbl.musicfever.dao.EventDAO;
import net.etfbl.musicfever.dao.UserDAO;
import net.etfbl.musicfever.dto.Event;

@ManagedBean
@RequestScoped
public class EventBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Event eventSelected = new Event();
	private Event eventAdd = new Event();
	private Event eventDelete = new Event();
	private Event eventToBeApproved = new Event();
	private int userCreatorId = -1;
	
	//Samo admin ima pristup upravljanja ovim podacima
	public ArrayList<Event> getAllEvents() {
		return EventDAO.getAllEvents();
	}
	
	public String removeEvent() {
		if(EventDAO.deleteEvent(eventDelete)){
			eventDelete = new Event();
			String messageSuccess = "Event deleted succesfully!";
			System.out.println(messageSuccess);
			addMessage(messageSuccess, null);
			return "";
		} else {
			eventDelete = new Event();
			String messageFailure = "Genre couldn't be deleted!";
			System.out.println(messageFailure);
			addMessage(messageFailure, null);
			return null;
		}
	}
	
	/*public String updateEvent() {
		System.out.println("New "+ eventSelected.getName() + " id: " + eventSelected.getId());
		if(EventDAO.updateEvent(eventSelected)){
			String messageSuccess = "Event updated succesfully!";
			System.out.println(messageSuccess);
			addMessage(messageSuccess);
			return " ";
		} else {
			String messageFailure = "Event couldn't be updated!";
			System.out.println(messageFailure);
			addMessage(messageFailure);
			return null;
		}
	}*/
	
	public String addNewEvent() {
		if(EventDAO.addEvent(eventAdd, userCreatorId)){
			eventAdd = new Event();
			String messageSuccess = "Success!";
			System.out.println(messageSuccess);
			addMessage(messageSuccess, "Event added!");
			return " ";
		} else {
			eventAdd = new Event();
			String messageFailure = "Failed to add event!";
			System.out.println(messageFailure);
			addMessage(messageFailure, null);
			return null;
		}
	}
	
	public String approveEvent() {
		if(EventDAO.approveEvent(eventToBeApproved)) {
			// Send mail to every registered user (not admin)
			// Probao preko James-a SMTP, nema exception-a ali ne dolazi mail
			//sendMailToUsers();
			ArrayList<String> allRegUserEmails = UserDAO.getAllEmailsFromRegUsers();
			
			try {
				for (int i = 0; i < allRegUserEmails.size(); i++) {
					System.out.println("Sending to: " + allRegUserEmails.get(i));
					sendViaGmail("luka.trifunovic89", "ObojeniProgram", allRegUserEmails.get(i), "", "MusciFever - New Event!", 
									"You are invited to a new event: " + eventToBeApproved.getName() + " at " +  eventToBeApproved.getLocation() + " with the start at " + eventToBeApproved.getStartTime());
				}
			} catch (AddressException e) {
				e.printStackTrace();
			} catch (MessagingException e) {
				e.printStackTrace();
			}
			
			String messageSuccess = "Event approved!";
			String messageDetails = "E-mail has been sent to all registered users";
			System.out.println(messageSuccess);
			addMessage(messageSuccess, messageDetails);
			eventToBeApproved = new Event();
			return "";
		} else {
			eventToBeApproved = new Event();
			String messageFailure = "Approvemnet failed!";
			String messageDetails = "Failed to send e-mail";
			System.out.println(messageFailure);
			addMessage(messageFailure, messageDetails);
			return null;
		}
	}
	
	public void addMessage(String summary, String details) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary,  details);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
	
	public void sendMailToUsers() {
		  // Recipient's email ID needs to be mentioned.
	      String to = "luka.trifunovic89@gmail.com";

	      // Sender's email ID needs to be mentioned
	      String from = "admin@example.com";

	      // Assuming you are sending email from localhost
	      String host = "localhost";

	      // Get system properties
	      /*Properties properties = System.getProperties();

	      // Setup mail server
	      properties.setProperty("mail.smtp.host", host);

	      // Get the default Session object.
	      Session session = Session.getDefaultInstance(properties);*/
	      
	      Properties props = new Properties();
	        Session session = Session.getDefaultInstance(props, null);


	      try{
	         // Create a default MimeMessage object.
	         MimeMessage message = new MimeMessage(session);

	         // Set From: header field of the header.
	         message.setFrom(new InternetAddress(from));

	         // Set To: header field of the header.
	         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

	         // Set Subject: header field
	         message.setSubject("New Event!");

	         // Now set the actual message
	         message.setText("You are invited to a new event: " + eventToBeApproved.getName() + " at " +  eventToBeApproved.getLocation());

	         // Send message
	         Transport.send(message);
	         System.out.println("Sent message successfully....");
	      }catch (MessagingException mex) {
	         mex.printStackTrace();
	      }
	}
	
	public static void sendViaGmail(final String username, final String password, String recipientEmail, String ccEmail, String title, String message) throws AddressException, MessagingException {
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

        // Get a Properties object
        Properties props = System.getProperties();
        props.setProperty("mail.smtps.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.setProperty("mail.smtps.auth", "true");

        /*
        If set to false, the QUIT command is sent and the connection is immediately closed. If set 
        to true (the default), causes the transport to wait for the response to the QUIT command.

        ref :   http://java.sun.com/products/javamail/javadocs/com/sun/mail/smtp/package-summary.html
                http://forum.java.sun.com/thread.jspa?threadID=5205249
                smtpsend.java - demo program from javamail
        */
        props.put("mail.smtps.quitwait", "false");

        Session session = Session.getInstance(props, null);

        // -- Create a new message --
        final MimeMessage msg = new MimeMessage(session);

        // -- Set the FROM and TO fields --
        msg.setFrom(new InternetAddress(username + "@gmail.com"));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail, false));

        if (ccEmail.length() > 0) {
            msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccEmail, false));
        }

        msg.setSubject(title);
        msg.setText(message, "utf-8");
        msg.setSentDate(new Date());

        SMTPTransport t = (SMTPTransport)session.getTransport("smtps");

        t.connect("smtp.gmail.com", username, password);
        t.sendMessage(msg, msg.getAllRecipients());      
        t.close();
    }

	public Event getEventSelected() {
		return eventSelected;
	}

	public void setEventSelected(Event eventSelected) {
		this.eventSelected = eventSelected;
	}

	public Event getEventAdd() {
		return eventAdd;
	}

	public void setEventAdd(Event eventAdd) {
		this.eventAdd = eventAdd;
	}

	public Event getEventDelete() {
		return eventDelete;
	}

	public void setEventDelete(Event eventDelete) {
		this.eventDelete = eventDelete;
	}

	public Event getEventToBeApproved() {
		return eventToBeApproved;
	}

	public void setEventToBeApproved(Event eventToBeApproved) {
		this.eventToBeApproved = eventToBeApproved;
	}

	public int getUserCreatorId() {
		return userCreatorId;
	}

	public void setUserCreatorId(int userCreatorId) {
		this.userCreatorId = userCreatorId;
	}
	
	
}
