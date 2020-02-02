public class MyThread extends Thread {
    private float[] array;
    private int size;
    private int half;

    MyThread(float[] array, int size, int half) {
        this.array = array;
        this.size = size;
        this.half = half;
    }

    @Override
    public void run() {
        long a = System.currentTimeMillis();

        for (int i = 0; i < size; i++) {
            array[i] = (float)(array[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }

        System.out.println("Time of processing half " + half + " of the array (ms): " + (System.currentTimeMillis() - a));
    }
}
