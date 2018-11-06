package code;

import java.io.*;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

// Refers: http://www.rgagnon.com/javadetails/java-receive-email-using-imap.html
public class TestImap {
	public static void main(String[] args) throws MessagingException, IOException {
		Folder folder = null;
	    Store store = null;
	    try {
	      Properties props = System.getProperties();
	      props.setProperty("mail.store.protocol", "imaps");

	      Session session = Session.getDefaultInstance(props, null);
	      // session.setDebug(true);
	      store = session.getStore("imaps");
	      store.connect("stbeehive.oracle.com","yue.yw.wang@oracle.com", "cde3VFR$");
	      //store.connect("stbeehive.oracle.com","c9-lab-autoidcsqa_ww@oracle.com", "Fusionapps1");
	      folder = store.getFolder("Inbox");
	      folder.open(Folder.READ_WRITE);
	      Message messages[] = folder.getMessages();
	      System.out.println("No of Messages : " + folder.getMessageCount());
	      System.out.println("No of Unread Messages : " + folder.getUnreadMessageCount());
	      System.out.println("\nPrinting individual messages\n");
	      //for (int i=0; i < messages.length; ++i) {
	      //for (int i=0; i < 3; ++i) {
	      for (int i=19910; i < 19917; ++i) {

	        Message msg = messages[i];
	        /*
	          if we don''t want to fetch messages already processed
	          if (!msg.isSet(Flags.Flag.SEEN)) {
	             String from = "unknown";
	             ...
	          }
	        */
	        String from = "unknown";
	        if (msg.getReplyTo().length >= 1) {
	          from = msg.getReplyTo()[0].toString();
	        }
	        else if (msg.getFrom().length >= 1) {
	          from = msg.getFrom()[0].toString();
	        }
	        String subject = msg.getSubject();
	        //System.out.println("Saving ... " + subject +" " + from);
	        System.out.print("\n==========================================================\n");
	        //System.out.println("MESSAGE #" + (i + 1) + ":");
            System.out.println("No# " + (i + 1));
	        System.out.println("Email Subject: " + msg.getSubject());
	        System.out.println("Sender: " + msg.getFrom()[0]);
	        System.out.println("Content: " + msg.getContent().toString());
	        
	        String content=msg.getContent().toString();
	        if (content.contains("javax.mail.internet.MimeMultipart"))
	        {
		        String filename = "c:/temp/" +  content.substring(35);
		        saveParts(msg.getContent(), filename);		        	        	
	        }
	        
	        // to delete the message
	        // msg.setFlag(Flags.Flag.DELETED, true);
	        msg.setFlag(Flags.Flag.SEEN,true);
	        // to delete the message
	        // msg.setFlag(Flags.Flag.DELETED, true);
	      }
	    }
	    finally {
	      if (folder != null) { folder.close(true); }
	      if (store != null) { store.close(); }
	    }
	}
	
	  public static void saveParts(Object content, String filename)
			  throws IOException, MessagingException
			  {
			    OutputStream out = null;
			    InputStream in = null;
			    try {
			      if (content instanceof Multipart) {
			        Multipart multi = ((Multipart)content);
			        int parts = multi.getCount();
			        for (int j=0; j < parts; ++j) {
			          MimeBodyPart part = (MimeBodyPart)multi.getBodyPart(j);
			          if (part.getContent() instanceof Multipart) {
			            // part-within-a-part, do some recursion...
			            saveParts(part.getContent(), filename);
			          }
			          else {
			            String extension = "";
			            if (part.isMimeType("text/html")) {
			              extension = "html";
			            }
			            else {
			              if (part.isMimeType("text/plain")) {
			                extension = "txt";
			              }
			              else {
			                //  Try to get the name of the attachment
			                extension = part.getDataHandler().getName();
			              }
			              filename = filename + "." + extension;
			              System.out.println("... " + filename);
			              out = new FileOutputStream(new File(filename));
			              in = part.getInputStream();
			              int k;
			              while ((k = in.read()) != -1) {
			                out.write(k);
			              }
			            }
			          }
			        }
			      }
			    }
			    finally {
			      if (in != null) { in.close(); }
			      if (out != null) { out.flush(); out.close(); }
			    }
			  }
}
