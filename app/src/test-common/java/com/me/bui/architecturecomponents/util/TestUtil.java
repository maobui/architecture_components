package com.me.bui.architecturecomponents.util;

import com.me.bui.architecturecomponents.data.model.Owner;
import com.me.bui.architecturecomponents.data.model.Repo;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TestUtil {
    public static Repo createRepo(String owner, String name, String description) {
        return createRepo(1, owner, name, description);
    }
    public static Repo createRepo(int id, String owner, String name, String description) {
        return new Repo(id, name, owner + "/" + name,
                description,3, new Owner(owner, null, null));
    }
}