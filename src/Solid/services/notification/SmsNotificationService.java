package Solid.services.notification;

public class SmsNotificationService implements NotificationService {
    @Override
    public void send(String phoneNumber, String message) {
        System.out.println("SMS wysłany na numer " + phoneNumber + ": " + message);
    }
}
