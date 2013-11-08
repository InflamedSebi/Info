package main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InvalidObjectException;
import java.util.TreeMap;
import ress.Mail;
import ress.Response;

public class Core {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {

		MySocket socket = new MySocket();
		Response r = socket.createSocket("pop3.uni-jena.de:110", "smtp.uni-jena.de:25");
		if (r.isValid())
			System.out.println("Verbindung  erfolgreich.");
		else {
			System.out.println("Verbindung fehlgeschlagen!");
			while (r.hasNext())
				System.out.println(r.getNext());
			System.exit(-1);
		}

		r = socket.login("sebastian.pammler@uni-jena.de", readString());
		if (r.isValid())
			System.out.println("Login  erfolgreich.");
		else {
			System.out.println("Login fehlgeschlagen!");
			while (r.hasNext())
				System.out.println(r.getNext());
			System.exit(-1);
		}

		int mails = 0;
		r = socket.getMailList();
		if (r.isValid()) {
			System.out.println("Postfach wurde geladen.");
			mails = ((TreeMap<Integer, String>) r.getValidObject()).size();
			System.out.println(mails + " Mails im Postfach.");
		} else {
			System.out.println("Postfach konnte nicht geladen werden!");
			while (r.hasNext())
				System.out.println(r.getNext());
			System.exit(-1);
		}
		while (true) {
			String input = readString();
			if (input.equalsIgnoreCase("exit"))
				break;

			int id = 0;
			try {
				id = Integer.parseInt(input);
				if (id > mails || id < 1)
					throw new InvalidObjectException("Wrong!");
			} catch (Exception e) {
				System.out.println("Keine Gültige Eingabe! (\"exit\" oder eine gültige Email Nummer eingeben)");
				System.out.println(mails + " Mails im Postfach.");
				continue;
			}

			r = socket.getMail(id);
			if (r.isValid()) {
				System.out.println("Email wurde geladen.");
				System.out.println(((Mail) r.getValidObject()).toString());
				System.out.println("[Ende der Email | " + mails + " Mails im Postfach]");
			} else {
				System.out.println("Email konnte nicht geladen werden!");
				while (r.hasNext())
					System.out.println(r.getNext());
				System.exit(-1);
			}
		}
	}

	public static String readString() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String input = "";
		boolean valid;
		do {
			valid = true;
			try {
				input = reader.readLine();
			} catch (Exception e) {
				System.out.println("Keine Gültige Eingabe! (Input = String)\nNeuer Versuch:");
				valid = false;
				// e.printStackTrace();
			}
		} while (valid == false);
		return input;
	}
}
