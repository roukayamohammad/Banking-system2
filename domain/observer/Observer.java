package domain.observer;
import domain.entities.Account;

public interface Observer {

    void update(Account account, String message);
}