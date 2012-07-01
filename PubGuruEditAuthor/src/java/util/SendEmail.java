package util;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import uit.pubguru.constant.PubGuruConst;

public class SendEmail {

	public static void send(String smtpServer, String to, String from,
			String psw, String subject, String body) throws Exception {
		Properties props = System.getProperties();
		props.put("mail.smtp.host", smtpServer);
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.starttls.enable", "true");
		final String login = from;
		final String pwd = psw;
		Authenticator pa = null;
		if (login != null && pwd != null) {
			props.put("mail.smtp.auth", "true");
			pa = new Authenticator() {
				public PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(login, pwd);
				}
			};
		}// else: no authentication
		Session session = Session.getInstance(props, pa);
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(from));
		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to,
				false));

		msg.setSubject(subject);
		msg.setContent(body, "text/html");
		msg.setHeader("X-Mailer", "LOTONtechEmail");
		msg.setSentDate(new Date());
		msg.saveChanges();
		Transport.send(msg);
		System.out.println("Message sent OK.");

	}

	/* Tao pt static */
	public static void sendMail(String to, String subject, String body) {
		try {
			String smtpServer = PubGuruConst.SMTP_SERVER;			
			String from = PubGuruConst.EMAIL_FROM;
			String password = PubGuruConst.EMAIL_PASS;
			send(smtpServer, to, from, password, subject, body);
			System.out.println("Finish!");
		} catch (Exception ex) {
			System.out.println("not Connected internet.....");
		}
	}

	public static void main(String[] args) {
		{
			try {
				sendMail("thaonx2890@zing.vn", "aThnhasssdasd", "<a href='https://www.google.com.vn/'>asdasdasd<a>");
			} catch (Exception ex) {
				System.out.println("not Connected internet...........");
			}

		}

	}

}
