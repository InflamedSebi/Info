package main;

import protocol.POProtocol3;
import protocol.SMTProtocol;
import ress.Response;

public class MySocket {

	private POProtocol3 recieve = new POProtocol3();
	private SMTProtocol send = new SMTProtocol();

	public Response createSocket(String pop3, String smtp) {
		Response r = new Response();
		String[] split = pop3.split(":");
		String pop3Url = split[0];
		int pop3Port = Integer.parseInt(split[1]);
		Response o = recieve.connect(pop3Url, pop3Port);
		while (o.hasNext())
			r.add(o.getNext());

		split = smtp.split(":");
		String smtpUrl = split[0];
		int smtpPort = Integer.parseInt(split[1]);
		o = send.connect(smtpUrl, smtpPort);
		while (o.hasNext())
			r.add(o.getNext());
		return r;
	}

	public Response login(String id, String pass) {
		Response r = new Response();
		Response o = recieve.login(id, pass);
		while (o.hasNext())
			r.add(o.getNext());
		o = send.login(id, pass);
		while (o.hasNext())
			r.add(o.getNext());
		return r;
	}

	public Response getMailList() {
		return recieve.getMailList();
	}

	public Response getMail(int id) {
		return recieve.getMail(id);
	}

}
