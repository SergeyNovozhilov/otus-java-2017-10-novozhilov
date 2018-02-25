package ru.otus.messageSystem;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author tully
 */
@Slf4j
@Service
public final class MessageSystem {
    private final static Logger logger = Logger.getLogger(MessageSystem.class.getName());
    private static final int DEFAULT_STEP_TIME = 10;

    private final List<Thread> workers;
    private final Map<Address, ConcurrentLinkedQueue<Message>> messagesMap;
    private final BiMap<Address, Addressee> addresseeMap;

    public MessageSystem() {
        workers = new ArrayList<>();
        messagesMap = new HashMap<>();
        addresseeMap = HashBiMap.create();
    }

    public boolean register(Addressee addressee) {

        if (addresseeMap.values().stream().anyMatch(a -> a.equals(addressee))) {
            logger.log(Level.SEVERE, "Addressee name: " + addressee.getName() + ", address: " + addressee.getAddress().getId() + " already registered");
            return false;
        }

        addresseeMap.put(addressee.getAddress(), addressee);
        messagesMap.put(addressee.getAddress(), new ConcurrentLinkedQueue<>());
        return true;
    }

    public void sendMessage(Message message) {
        messagesMap.get(message.getTo()).add(message);
    }

    public Address lookUp(String name) {
        Addressee addressee = addresseeMap.values().stream().filter(a -> StringUtils.equalsIgnoreCase(a.getName(), name)).findAny().orElse(null);
        return addressee != null ? addressee.getAddress() : null;
    }


    @SuppressWarnings("InfiniteLoopStatement")
    public void start() {
        for (Map.Entry<Address, Addressee> entry : addresseeMap.entrySet()) {
            String name = "MS-worker-" + entry.getKey().getId();
            Thread thread = new Thread(() -> {
                while (true) {

                    ConcurrentLinkedQueue<Message> queue = messagesMap.get(entry.getKey());
                    while (!queue.isEmpty()) {
                        Message message = queue.poll();
                        message.exec(entry.getValue());
                    }
                    try {
                        Thread.sleep(MessageSystem.DEFAULT_STEP_TIME);
                    } catch (InterruptedException e) {
                        logger.log(Level.INFO, "Thread interrupted. Finishing: " + name);
                        return;
                    }
                    if (Thread.currentThread().isInterrupted()) {
                        logger.log(Level.INFO, "Finishing: " + name);
                        return;
                    }
                }
            });
            thread.setName(name);
            thread.start();
            workers.add(thread);
        }
    }

    public void dispose() {
        workers.forEach(Thread::interrupt);
    }
}
