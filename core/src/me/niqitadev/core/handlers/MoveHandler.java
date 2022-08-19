package me.niqitadev.core.handlers;

import me.niqitadev.core.Starter;
import me.niqitadev.core.listeners.InGameInputListener;
import me.niqitadev.core.packets.MovePacket;

public class MoveHandler implements Runnable {
    public final InGameInputListener listener;
    private final Starter starter;
    private boolean running;

    public void stop() {
        running = false;
        listener.reset();

    }

    public MoveHandler(Starter starter) {
        this.starter = starter;
        listener = new InGameInputListener(starter);
    }

    public synchronized void start() {
        running = true;
        new Thread(this).start();
    }

    @Override
    public synchronized void run() {

        final long optimalTime = 27000000; // ms / amount of ticks

        for (long now, updateTime, wait; running; ) {
            now = System.nanoTime();

            starter.client.sendTCP(new MovePacket(listener.a, listener.w, listener.s, listener.d));

            updateTime = System.nanoTime() - now;
            wait = (optimalTime - updateTime) / 1000000;

            if (wait < 1) continue;

            try {
                Thread.sleep(wait);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}