package serverside.service;

import serverside.interfce.AuthenticationService;

import java.util.List;
import java.util.stream.Collectors;

public class AuthenticationServiceImpl implements AuthenticationService {

    private List<DataBase.User> usersList;
    DataBase db = new DataBase();
    public AuthenticationServiceImpl() {
        usersList = db.getUserList();
    }

    @Override
    public void start() {
//        System.out.println("start");
    }

    @Override
    public void stop() {
//        System.out.println("stop");
    }

    @Override
    public String getNickByLoginAndPassword(String login, String password) {
            return usersList.stream()
                .map(a  -> {
                   if (a.login.equals(login) && a.password.equals(password)){
                       return a.nick;
                   }
                   return "";
                }).collect(Collectors.joining());
    }
}
