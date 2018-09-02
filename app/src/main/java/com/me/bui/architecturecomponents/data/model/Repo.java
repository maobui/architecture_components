package com.me.bui.architecturecomponents.data.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mao.bui on 9/1/2018.
 */
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
    @NonNull
    public final Owner owner;

    public Repo(int id, @NonNull String name, String fullName, String description, int stars, @NonNull Owner owner) {
        this.id = id;
        this.name = name;
        this.fullName = fullName;
        this.description = description;
        this.stars = stars;
        this.owner = owner;
    }
}
