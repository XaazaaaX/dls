package de.dlsa.api.responses;

public class MemberCountResponse {
    private long allMembers;
    private long activeMembers;
    private long passiveMembers;
    private long dlsRequiredMembers;

    public long getAllMembers() {
        return allMembers;
    }

    public MemberCountResponse setAllMembers(long allMembers) {
        this.allMembers = allMembers;
        return this;
    }

    public long getActiveMembers() {
        return activeMembers;
    }

    public MemberCountResponse setActiveMembers(long activeMembers) {
        this.activeMembers = activeMembers;
        return this;
    }

    public long getPassiveMembers() {
        return passiveMembers;
    }

    public MemberCountResponse setPassiveMembers(long passiveMembers) {
        this.passiveMembers = passiveMembers;
        return this;
    }

    public long getDlsRequiredMembers() {
        return dlsRequiredMembers;
    }

    public MemberCountResponse setDlsRequiredMembers(long dlsRequiredMembers) {
        this.dlsRequiredMembers = dlsRequiredMembers;
        return this;
    }
}
