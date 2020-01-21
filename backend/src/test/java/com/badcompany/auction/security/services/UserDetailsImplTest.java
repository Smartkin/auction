package com.badcompany.auction.security.services;

import com.badcompany.auction.entities.Image;
import com.badcompany.auction.entities.User;
import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.Objects;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDetailsImplTest {

    @MockBean
    Collection <GrantedAuthority>authorities;
    Objects o;

    UserDetailsImpl UserDetails = new UserDetailsImpl(1L, "Test", "TestName", "TestSurName", "Test@test.ru", "testpassword", authorities);
    UserDetailsImpl UserTest = new UserDetailsImpl(1L, "Test", "TestName", "TestSurName", "Test@test.ru", "testpassword", authorities);
    User user = new User("Test", "Test", "Test", "test", "test@test.com", new Image());
    @Test
    public void build()
    {
     assertNotNull(UserDetails.build(user));
    }

    @Test
    public void getId()
    {
        assertEquals(java.util.Optional.of(1L), java.util.Optional.of(UserDetails.getId()));
    }

    @Test
    public void getUsername() {
        assertEquals("Test", UserDetails.getUsername());
    }

    @Test
    public void getName() {
        assertEquals("TestName", UserDetails.getName());
    }

    @Test
    public void getSurname() {
        assertEquals("TestSurName", UserDetails.getSurname());
    }

    @Test
    public void getPassword() {
        assertEquals("testpassword", UserDetails.getPassword());
    }

    @Test
    public void getEmail() {
        assertEquals("Test@test.ru", UserDetails.getEmail());
    }

    @Test
    public void isAccountNonExpired() {
        assertTrue(UserDetails.isAccountNonExpired());
    }

    @Test
    public void isAccountNonLocked() {
        assertTrue(UserDetails.isAccountNonLocked());

    }

    @Test
    public void isCredentialsNonExpired() {
        assertTrue(UserDetails.isCredentialsNonExpired());
    }

    @Test
    public void isEnabled() {
        assertTrue(UserDetails.isEnabled());
    }

    @Test
    public void testEquals()
    {
        assertTrue(UserDetails.equals(UserDetails));
        assertFalse(UserDetails.equals(o));
        assertTrue(UserDetails.equals(UserTest));
    }

    @Test
    public void testCollections()
    {
        assertNull(UserDetails.getAuthorities());
    }
}