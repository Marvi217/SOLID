package Solid.services.notification;

public class PushNotificationService implements NotificationService {
    @Override
    public void send(String deviceToken, String message) {
        System.out.println("Push wysłany do urządzenia " + deviceToken + ": " + message);
    }
}
