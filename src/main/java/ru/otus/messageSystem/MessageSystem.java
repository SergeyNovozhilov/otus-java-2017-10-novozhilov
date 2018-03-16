package ru.otus.messageSystem;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public final class MessageSystem {
    private final static Logger logger = Logger.getLogger(MessageSystem.class.getName());
    private static final int DEFAULT_STEP_TIME = 10;
    private static final int CHECK_MAP_TIME = 100;

    private final Map<String, Thread> workers;
    private final Map<Address, ConcurrentLinkedQueue<Message>> messagesMap;
    private final BiMap<Address, Addressee> addresseeMap;

    public MessageSystem() {
        workers = new HashMap<>();
        messagesMap = new HashMap<>();
        addresseeMap = HashBiMap.create();
        logger.log(Level.INFO, "ms created: " + this.toString());
    }

    @PostConstruct
    public void init(){
        logger.log(Level.INFO, "ms started: " + this.toString());
        start();
    }

    public boolean register(Addressee addressee) {

        if (addresseeMap.values().stream().anyMatch(a -> a.getAddress().getId().equals(addressee.getAddress().getId()))) {
            logger.log(Level.SEVERE, "Addressee address: " + addressee.getAddress().getId() + " already registered");
            return false;
        }

        addresseeMap.put(addressee.getAddress(), addressee);
        messagesMap.put(addressee.getAddress(), new ConcurrentLinkedQueue<>());
        return true;
    }

    public boolean unregister(Address address) {
        dispose(getThreadName(address.getId()));
        addresseeMap.remove(address);
        messagesMap.remove(address);
        return true;
    }



    public void sendMessage(Message message) {
        logger.log(Level.INFO, "Got message");
        System.out.println("From: " + message.getFrom().getId());
        System.out.println("To: " + message.getTo().getId());
        messagesMap.get(message.getTo()).add(message);
    }

    public Address lookUp(String name) {
        Addressee addressee = addresseeMap.values().stream().filter(a -> StringUtils.equalsIgnoreCase(a.getName(), name)).findAny().orElse(null);
        return addressee != null ? addressee.getAddress() : null;
    }


    @SuppressWarnings("InfiniteLoopStatement")
    private void start() {
        Thread main = new Thread(() -> {
            while (true) {
                for (Map.Entry<Address, Addressee> entry : addresseeMap.entrySet()) {
                    String name = getThreadName(entry.getKey().getId());
                    if (!workers.containsKey(name)) {
                        Thread thread = new Thread(() -> {
                            while (true) {
                                ConcurrentLinkedQueue<Message> queue = messagesMap.get(entry.getKey());
                                while (!queue.isEmpty()) {
                                    Message message = queue.poll();
                                    System.out.println("Poll message");
                                    System.out.println("From: " + message.getFrom().getId());
                                    System.out.println("To: " + message.getTo().getId());
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
                        workers.put(name, thread);
                    }
                }
                try {
                    Thread.sleep(MessageSystem.CHECK_MAP_TIME);
                } catch (InterruptedException e) {
                    logger.log(Level.INFO, "Main Thread interrupted. Finishing ");
                    return;
                }
                if (Thread.currentThread().isInterrupted()) {
                    logger.log(Level.INFO, "Finishing");
                    return;
                }
            }
        });
        main.setName("Main worker");
        main.start();
        workers.put("Main worker", main);
    }

    public void dispose() {
        workers.values().forEach(Thread::interrupt);
    }

    public void dispose(String name) {
        Thread thread = workers.get(name);
        if (thread != null) {
            thread.interrupt();
        }
    }

    private String getThreadName(UUID id) {
        return "MS-worker-" + id;
    }
}
