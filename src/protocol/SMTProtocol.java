package protocol;

import java.util.List;
import ress.Response;

public class SMTProtocol {

	public Response connect(String url, int port) {
		// TODO Auto-generated method stub
		return new Response();
	}

	public Response login(String un, String pw) {
		// TODO Auto-generated method stub

		return new Response();
	}

	public void exit() {
		// TODO Auto-generated method stub

	}

	public List<String> readLines() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * SMTP
	 * 
	 * EHLO AUTH LOGIN aGFucw== c2Nobml0emVsbWl0a2FydG9mZmVsc2FsYXQ= MAIL FROM:<hans@example.net> RCPT TO:<fritz@example.org> DATA
	 * 
	 * From:<hans@example.net> To:<fritz@example.org> Subject: Hallo
	 * 
	 * Hallo Fritz. . QUIT
	 */

}
