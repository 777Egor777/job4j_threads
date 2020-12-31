package ru.job4j.multithreads;

/**
 * @author Geraskin Egor
 * @version 1.0
 * @since 31.12.2020
 */
public final class MasterSlaveBarrier {
    private boolean canMaster = true;
    private boolean canSlave = false;

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
        while (!canSlave) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public synchronized void doneMaster() {
        canMaster = false;
        canSlave = true;
        this.notifyAll();
    }

    public synchronized void doneSlave() {
        canSlave = false;
        canMaster = true;
        this.notifyAll();
    }
}
