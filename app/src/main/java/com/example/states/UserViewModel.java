package com.example.states;

import androidx.lifecycle.ViewModel;

public class UserViewModel extends ViewModel {
    private User user = new User("undefined", 0);

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
