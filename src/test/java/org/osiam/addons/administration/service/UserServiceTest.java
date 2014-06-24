package org.osiam.addons.administration.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.osiam.addons.administration.model.SessionData;
import org.osiam.client.OsiamConnector;
import org.osiam.client.oauth.AccessToken;
import org.osiam.client.query.Query;
import org.osiam.resources.scim.SCIMSearchResult;
import org.osiam.resources.scim.User;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    OsiamConnector connector;
    
    @Mock
    AccessToken accessToken;
    
    @Spy
    @InjectMocks
    SessionData sessionData = new SessionData();
    
    @Spy
    @InjectMocks
    UserService toTestSpy = new UserService();

    @SuppressWarnings("unchecked")
    @Test
    public void searchUser_onlyQuery() {
        final SCIMSearchResult<User> toReturn = mock(SCIMSearchResult.class);
        doReturn(toReturn).when(toTestSpy).searchUser(anyString(), anyInt(), anyLong(), anyString(), anyBoolean());

        final String query = "TestQuery";
        final SCIMSearchResult<User> result = toTestSpy.searchUser(query);

        assertSame(result, toReturn);
        verify(toTestSpy).searchUser(query, null, null, null, null);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void searchUser_withLimit() {
        final SCIMSearchResult<User> toReturn = mock(SCIMSearchResult.class);
        doReturn(toReturn).when(toTestSpy).searchUser(anyString(), anyInt(), anyLong(), anyString(), anyBoolean());

        final String query = "TestQuery";
        final Integer limit = 13;
        final Long offset = 12L;
        final SCIMSearchResult<User> result = toTestSpy.searchUser(query, limit, offset);

        assertSame(result, toReturn);
        verify(toTestSpy).searchUser(query, limit, offset, null, null);
    }

    @Test
    public void searchUser_advanced() {
        final String query = "testQuery";
        final Integer limit = 13;
        final Long offset = 12L;
        final String orderBy = "orderby";
        final Boolean asc = false; // desc

        toTestSpy.searchUser(query, limit, offset, orderBy, asc);

        ArgumentCaptor<Query> cap = ArgumentCaptor.forClass(Query.class);

        verify(connector).searchUsers(cap.capture(), same(accessToken));

        Query usedQuery = cap.getValue();
        assertEquals(query, usedQuery.getFilter());
        assertTrue(limit == usedQuery.getCount());
        assertTrue(offset == usedQuery.getStartIndex());
        assertEquals(orderBy, usedQuery.getSortBy());
        assertEquals("descending", usedQuery.getSortOrder());
    }
}
