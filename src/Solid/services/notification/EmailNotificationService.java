package Solid.services.notification;

public class EmailNotificationService implements NotificationService {
    @Override
    public void send(String email, String message) {
        System.out.println("Email wysłany do " + email + ": " + message);
    }
}

