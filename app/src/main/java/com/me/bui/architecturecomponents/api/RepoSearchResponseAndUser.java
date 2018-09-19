package com.me.bui.architecturecomponents.api;

import com.me.bui.architecturecomponents.data.model.User;

/**
 * Created by mao.bui on 9/19/2018.
 */
public class RepoSearchResponseAndUser {
    public RepoSearchResponse repoSearchResponse;
    public User user;

    public RepoSearchResponseAndUser(RepoSearchResponse repoSearchResponse, User user) {
        this.repoSearchResponse = repoSearchResponse;
        this.user = user;
    }
}
