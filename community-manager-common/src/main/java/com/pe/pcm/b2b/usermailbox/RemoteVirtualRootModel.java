package com.pe.pcm.b2b.usermailbox;

public class RemoteVirtualRootModel {

	private String mailboxPath;
	private String userName;


	public RemoteVirtualRootModel(String parentDirectory, String userId) {
		this.mailboxPath = parentDirectory;
		this.userName = userId;
	}

	public String getMailboxPath() {
		return mailboxPath;
	}

	public RemoteVirtualRootModel setMailboxPath(String mailboxPath) {
		this.mailboxPath = mailboxPath;
		return this;
	}

	public String getUserName() {
		return userName;
	}

	public RemoteVirtualRootModel setUserName(String userName) {
		this.userName = userName;
		return this;
	}
}
