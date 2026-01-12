package Solid.services.notification;

import java.util.ArrayList;
import java.util.List;

public class NotificationDispatcher {
    private final List<NotificationService> channels = new ArrayList<>();

    public void addChannel(NotificationService channel) {
        channels.add(channel);
    }

    public void dispatch(String recipient, String message) {
        for (NotificationService channel : channels) {
            channel.send(recipient, message);
        }
    }
}