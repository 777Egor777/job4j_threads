package ru.job4j.multithreads;

/**
 * @author Geraskin Egor
 * @version 1.0
 * @since 31.12.2020
 */
public final class MasterSlaveBarrier {
    private boolean canMaster = true;

    public synchronized void tryMaster() {
        while (!canMaster) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public synchronized void trySlave() {
        while (canMaster) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public synchronized void doneMaster() {
        canMaster = false;
        this.notifyAll();
    }

    public synchronized void doneSlave() {
        canMaster = true;
        this.notifyAll();
    }
}
