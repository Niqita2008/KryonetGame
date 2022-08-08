package me.niqitadev.core.handlers;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Client;
import me.niqitadev.core.listeners.InGameInputListener;
import me.niqitadev.core.packets.MovePacket;

public class MoveHandler implements Runnable {
    private final InGameInputListener listener;
    private final Client client;
    public boolean running;

    public MoveHandler(Client client) {
        this.client = client;
        listener = new InGameInputListener();
        Gdx.input.setInputProcessor(listener);
    }

    public synchronized void start() {
        running = true;
        new Thread(this).start();
    }

    @Override
    public void run() {
        long pastTime = System.nanoTime();
        double amountOfTicks = 30;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;

        while (running) {

            try {
                Thread.sleep((long) (60 / amountOfTicks));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            long now = System.nanoTime();
            delta += (now - pastTime) / ns;
            pastTime = now;

            for (; delta > 0; delta--) if (listener.update((float) delta, 3f)) {
                MovePacket movePacket = new MovePacket();
                movePacket.x = listener.x;
                movePacket.y = listener.y;
                client.sendTCP(movePacket);

            }
        }
    }
}