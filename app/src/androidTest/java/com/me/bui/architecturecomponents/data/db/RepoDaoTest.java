package com.me.bui.architecturecomponents.data.db;

import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.me.bui.architecturecomponents.data.model.Owner;
import com.me.bui.architecturecomponents.data.model.Repo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.me.bui.architecturecomponents.util.LiveDataTestUtil.getValue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by mao.bui on 9/4/2018.
 */

@RunWith(AndroidJUnit4.class)
public class RepoDaoTest {

    private GithubDb db;
    private Repo repo;

    @Before
    public void setUp() throws Exception {
        db = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                GithubDb.class).build();

        Owner owner = new Owner("foo", null, null);
        repo = new Repo(1, "foo", "foo/bar", "desc",50, owner);
    }

    @After
    public void closeDb() throws Exception {
        db.close();
    }

    @Test
    public void insertAndLoad() throws InterruptedException {
        // Insert repo
        db.repoDao().insert(repo);
        // Query repo
        final Repo loaded = getValue(db.repoDao().load("foo", "foo"));
        // Assert query result
        assertThat(loaded.owner.login, is("foo"));
        assertThat(loaded.name, is("foo"));
    }
}