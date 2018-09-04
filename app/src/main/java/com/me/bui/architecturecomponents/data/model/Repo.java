package com.me.bui.architecturecomponents.data.model;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mao.bui on 9/1/2018.
 */

@Entity(indices = {@Index("id"), @Index("owner_login")},
        primaryKeys = {"name", "owner_login"})
public class Repo {

    public final int id;

    @SerializedName("name")
    @NonNull
    public final String name;

    @SerializedName("full_name")
    public final String fullName;

    @SerializedName("description")
    public final String description;

    @SerializedName("stargazers_count")
    public final int stars;

    @SerializedName("owner")
    @Embedded(prefix = "owner_")
    @NonNull
    public final Owner owner;

    public String html_url;

    public Repo(int id, @NonNull String name, String fullName, String description, int stars, @NonNull Owner owner) {
        this.id = id;
        this.name = name;
        this.fullName = fullName;
        this.description = description;
        this.stars = stars;
        this.owner = owner;
    }
}
