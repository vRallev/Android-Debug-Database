/*
 *
 *  *    Copyright (C) 2016 Amit Shekhar
 *  *    Copyright (C) 2011 Android Open Source Project
 *  *
 *  *    Licensed under the Apache License, Version 2.0 (the "License");
 *  *    you may not use this file except in compliance with the License.
 *  *    You may obtain a copy of the License at
 *  *
 *  *        http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *    Unless required by applicable law or agreed to in writing, software
 *  *    distributed under the License is distributed on an "AS IS" BASIS,
 *  *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *    See the License for the specific language governing permissions and
 *  *    limitations under the License.
 *
 */

package com.amitshekhar;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;

import com.amitshekhar.model.Response;
import com.amitshekhar.server.ClientServer;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class DbFindTest {

    @Test
    public void findDatabases() {
        Context context = InstrumentationRegistry.getTargetContext().getApplicationContext();

        File[] databases = new File[] {
                context.getDatabasePath("database1.db"),
                new File(context.getFilesDir(), "database2.db"),
                new File(new File(context.getFilesDir(), "folder"), "database3.db")
        };

        for (File database : databases) {
            //noinspection ResultOfMethodCallIgnored
            database.getParentFile().mkdirs();

            context.openOrCreateDatabase(database.getAbsolutePath(), 0, null).close();
        }

        ClientServer server = new ClientServer(context, 8080);
        Response response = server.getDBList();

        assertThat(response.isSuccessful).isTrue();
        assertThat(response.rows).hasSize(4);

        for (File database : databases) {
            //noinspection ResultOfMethodCallIgnored
            database.delete();
        }
    }
}
