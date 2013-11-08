package ress;

import java.util.ArrayList;
import java.util.List;

public class Response {

	public enum State {
		ERR_UID_POP3, ERR_PASS_POP3, ERR_UID_SMTP, ERR_PASS_SMTP, ERR_CONNECT_POP3, ERR_CONNECT_SMTP, ERR_SERVER, ERR_MAIL;
	}

	private int pointer = 0;
	private List<State> responses = new ArrayList<State>();
	private Object object;

	public Response() {
	}

	public void add(State state) {
		responses.add(state);
	}

	public boolean isValid() {
		return responses.isEmpty();
	}

	public boolean containsState(State state) {
		return responses.contains(state);
	}

	public boolean hasNext() {
		try {
			responses.get(pointer);
			return true;
		} catch (IndexOutOfBoundsException e) {
			return false;
		}
	}

	public State getNext() {
		try {
			return responses.get(pointer++);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	public void reset() {
		pointer = 0;
	}

	public Object getValidObject() {
		return object;
	}

	public void setValidObject(Object object) {
		this.object = object;
	}
}
