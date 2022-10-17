package com.pe.pcm.b2b.usermailbox;

public class RemoteMailboxModel {

    private static final long serialVersionUID = 1L;
    private boolean createParentMailbox;
    private String id;
    private String description;
    private String linkedToMailbox;
    private String mailboxType;
    private String path;

    public RemoteMailboxModel(boolean createParentMailbox, String id, String description, String linkedToMailbox, String mailboxType, String path) {
        this.createParentMailbox = createParentMailbox;
        this.id = id;
        this.description = description;
        this.linkedToMailbox = linkedToMailbox;
        this.mailboxType = mailboxType;
        this.path = path;
    }

    public boolean isCreateParentMailbox() {
        return createParentMailbox;
    }

    public RemoteMailboxModel setCreateParentMailbox(boolean createParentMailbox) {
        this.createParentMailbox = createParentMailbox;
        return this;
    }

    public String getId() {
        return id;
    }

    public RemoteMailboxModel setId(String id) {
        this.id = id;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public RemoteMailboxModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getLinkedToMailbox() {
        return linkedToMailbox;
    }

    public RemoteMailboxModel setLinkedToMailbox(String linkedToMailbox) {
        this.linkedToMailbox = linkedToMailbox;
        return this;
    }

    public String getMailboxType() {
        return mailboxType;
    }

    public RemoteMailboxModel setMailboxType(String mailboxType) {
        this.mailboxType = mailboxType;
        return this;
    }

    public String getPath() {
        return path;
    }

    public RemoteMailboxModel setPath(String path) {
        this.path = path;
        return this;
    }
}
