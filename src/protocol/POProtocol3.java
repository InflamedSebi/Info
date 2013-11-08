package protocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import ress.Mail;
import ress.Response;
import ress.Response.State;

public class POProtocol3 {

	public POProtocol3() {

	}

	private java.net.Socket server = null;
	private BufferedReader in = null;
	private OutputStreamWriter out = null;
	private boolean prnt = false;

	public Response connect(String url, int port) {
		Response r = new Response();
		try {
			server = new Socket(url, port);
			in = new BufferedReader(new InputStreamReader(server.getInputStream()));
			out = new OutputStreamWriter(server.getOutputStream());
			if (!check(getResponse(prnt)))
				r.add(State.ERR_CONNECT_POP3);
		} catch (Exception e) {
			e.printStackTrace();
			r.add(State.ERR_SERVER);
		}
		return r;
	}

	public Response login(String un, String pw) {
		Response r = new Response();
		try {
			out.write("USER " + un + "\n");
			out.flush();
			if (!check(getResponses(prnt)))
				r.add(State.ERR_UID_POP3);
			out.write("PASS " + pw + "\n");
			out.flush();
			if (!check(getResponses(prnt)))
				r.add(State.ERR_PASS_POP3);
		} catch (Exception e) {
			r.add(State.ERR_SERVER);
		}
		return r;
	}

	public Response getMailList() {
		Response r = new Response();
		try {
			out.write("LIST" + "\n");
			out.flush();
			List<String> log = getResponses(prnt);
			if (!check(log)) {
				r.add(State.ERR_MAIL);
				return r;
			}
			TreeMap<Integer, String> tm = new TreeMap<Integer, String>();
			for (String msg : log)
				try {
					if (msg.startsWith("+OK") || msg.equals("."))
						continue;
					String[] split = msg.split(" ");
					tm.put(Integer.parseInt(split[0]), split[1]);
				} catch (Exception e) {

				}
			r.setValidObject(tm);
		} catch (Exception e) {
			r.add(State.ERR_SERVER);
		}
		return r;
	}

	// TODO
	public Response getMail(int id) {
		Response r = new Response();
		try {
			out.write("RETR" + " " + id + "\n");
			out.flush();
			List<String> log = getResponses(prnt);
			if (!check(log)) {
				r.add(State.ERR_MAIL);
				return r;
			}

			Mail mail = new Mail(log);
			r.setValidObject(mail);
		} catch (Exception e) {
			r.add(State.ERR_SERVER);
		}
		return r;
	}

	public boolean check(List<String> log) throws ConnectException, IOException {
		for (String msg : log) {
			if (msg.startsWith("-ERR")) {
				// System.out.println("Error in: " + msg);
				return false;
			}
		}
		return true;
	}

	public List<String> getResponses(boolean print) throws IOException {
		List<String> log = new ArrayList<String>();
		out.write("NOOP" + "\n");
		out.flush();
		for (;;) {
			String msg = in.readLine();
			if (print)
				System.out.println(msg);
			if (msg.equals("+OK") || msg.equals("-ERR Unrecognized command"))
				return log;
			log.add(msg);
		}

	}

	public List<String> getResponse(boolean print) throws IOException {
		List<String> log = new ArrayList<String>();
		String msg = in.readLine();
		if (print)
			System.out.println(msg);
		log.add(msg);
		return log;

	}

	/*
	 * pop3
	 * 
	 * USER PASS LIST RETR DELE QUIT
	 */

	public void exit() throws IOException {
		out.write("QUIT" + "\n");
		out.flush();
		getResponses(prnt);
		in = null;
		out = null;
		server = null;
	}

}
