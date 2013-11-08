package ress;

import java.util.ArrayList;
import java.util.List;

public class Mail {

	public String from = null;
	public String to = null;
	public String subject = null;
	public String date = null;
	public List<String> txt = new ArrayList<String>();

	public Mail(List<String> log) {
		boolean msgStart = false;
		for (String msg : log)
			try {
				if (msg.startsWith("+OK") || msg.equals("."))
					continue;

				if (msgStart) {
					txt.add(msg);
					continue;
				}

				if (msg.isEmpty()) {
					txt.add(msg);
					msgStart = true;
					continue;
				}

				if (msg.startsWith("To:") && to == null) {
					to = msg;
					continue;
				}
				if (msg.startsWith("From:") && from == null) {
					from = msg;
					continue;
				}
				if (msg.startsWith("Subject:") && subject == null) {
					subject = msg;
					continue;
				}
				if (msg.startsWith("Date:") && date == null) {
					date = msg;
					continue;
				}

			} catch (Exception e) {

			}
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (String msg : txt)
			sb.append(msg + "\n");
		return from + "\n" + to + "\n" + date + "\n" + subject + "\n" + sb.toString();
	}
}
