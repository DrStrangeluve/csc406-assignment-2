public class TimerKnapp1 {
    private long duration;

    TimerKnapp1() {
        duration = 0;
    }
    /**
     * Starts timing by setting duration to the current nanoTime.
     */
    public void start() {
        duration = System.nanoTime(); // tracks time in seconds
    }
    /**
     * Stops timing by setting duration to the current nanoTime minus the start nanoTime.
     */
    public void stop() {
        long end_time = System.nanoTime();
        duration = end_time - duration;
    }
    /**
     * Returns the duration variable to the caller.
     * @return the saved duration.
     */
    public long getDuration() {
        return duration;
    }
}
