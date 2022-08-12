package me.niqitadev.core.handlers;

import me.niqitadev.core.Starter;
import me.niqitadev.core.listeners.InGameInputListener;
import me.niqitadev.core.packets.MovePacket;

public class MoveHandler implements Runnable {
    public final InGameInputListener listener;
    private final Starter starter;
    public boolean running;

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

        long now, updateTime, wait;

        final long optimalTime = 55000000; // ms / amount of ticks

        while (running) {
            now = System.nanoTime();

            if (listener.update(30)) {
                MovePacket movePacket = new MovePacket();
                movePacket.x = listener.x;
                movePacket.y = listener.y;
                starter.client.sendTCP(movePacket);

            }

            updateTime = System.nanoTime() - now;
            wait = (optimalTime - updateTime) / 1000000;

            try {
                Thread.sleep(wait);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}