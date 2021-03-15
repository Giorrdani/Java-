package serverside.service;

import serverside.interfce.AuthenticationService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AuthenticationServiceImpl implements AuthenticationService {

    private List<User> usersList;

    public AuthenticationServiceImpl() {
        usersList = new ArrayList<>();
        usersList.add(new User("A", "A", "A"));
        usersList.add(new User("B", "B", "B"));
        usersList.add(new User("C", "C", "C"));

    }

    @Override
    public void start() {
        System.out.println("start");
    }

    @Override
    public void stop() {
        System.out.println("stop");
    }

    @Override
    public String getNickByLoginAndPassword(String login, String password) {
        /*for (User u : usersList){
            if (u.login.equals(login) && u.password.equals(password)){
                return u.nick;
            }
          }
            return null;
        }*/
        return usersList.stream()
                .map(a  -> {
                   if (a.login.equals(login) && a.password.equals(password)){
                       return a.nick;
                   }
                   return "";
                }).collect(Collectors.joining());
    }

    private class User {
        private String login;
        private String password;
        private String nick;

        public User(String login, String password, String nick){
            this.login = login;
            this.password = password;
            this.nick = nick;
        }
    }
}
