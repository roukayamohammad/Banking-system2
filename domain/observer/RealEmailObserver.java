

package domain.observer;

import domain.entities.Account;
import domain.entities.Customer;
import domain.entities.MockDatabase;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class RealEmailObserver implements Observer {


    private String senderEmail;
    private String appPassword;


    public RealEmailObserver(String senderEmail, String appPassword) {
        this.senderEmail = senderEmail;
        this.appPassword = appPassword;
    }

    @Override
    public void update(Account account, String message) {
        Customer customer = MockDatabase.getCustomerById(account.getOwnerId());

        if (customer != null && customer.getEmail() != null) {

            System.out.println("\n📧 [Email System] Sending alert to: " + customer.getEmail());
            sendEmail(customer.getEmail(), "Damascus Bank Notification", message);
        } else {
            System.out.println(" [Email System] No email found for owner ID: " + account.getOwnerId());
        }
    }

    private void sendEmail(String recipient, String subject, String content) {

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");


        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, appPassword);
            }
        });

        try {

            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(senderEmail, "Damascus Bank System"));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            msg.setSubject(subject);
            msg.setText("Dear Customer,\n\n" + content + "\n\nThank you,\nBank Team");


            Transport.send(msg);

            System.out.println(" [Email System] Sent Successfully to: " + recipient);

        } catch (Exception e) {
            System.out.println(" [Email System] Failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}