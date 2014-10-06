package net.nfiniteloop.loqale.backend;

import java.util.List;

/**
 * Created by vaek on 10/5/14.
 */
public class Profile {
    private String UserId;
    private List<CheckIn> checkIns;
    private List<Tag> tags;
    private Friendship friends;

    public void setUserId(String userId) {
        UserId = userId;
    }

    public void setCheckIns(List<CheckIn> checkIns) {
        this.checkIns = checkIns;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public void setFriends(Friendship friends) {
        this.friends = friends;
    }

    public String getUserId() {
        return UserId;
    }

    public List<CheckIn> getCheckIns() {
        return checkIns;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public Friendship getFriends() {
        return friends;
    }
}
