import java.util.Arrays;

public class HowMuchTimeItTakes {

    static final int SIZE = 10000000;
    static final int H = SIZE / 2;
    static float[] arr = new float[SIZE];

    private static void withoutSplit(float[] array) {
        long a = System.currentTimeMillis();

        for (int i = 0; i < SIZE; i++) {
            array[i] = (float)(array[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }

        System.out.println("Time of processing whole array without split (ms): " + (System.currentTimeMillis() - a));
    }

    private static void withSplit(float[] array) throws InterruptedException {
        long a = System.currentTimeMillis();
        int totalTime = 0;
        float[] a1 = new float[H];
        float[] a2 = new float[H];

        // Разбиваем массив
        System.arraycopy(array, 0, a1, 0, H);
        System.arraycopy(array, H, a2, 0, H);
        System.out.println("Time of splitting array into two arrays (ms): " + (System.currentTimeMillis() - a));
        totalTime += (int) (System.currentTimeMillis() - a);
        a = System.currentTimeMillis();

        // Многопоточность
        MyThread t1 = new MyThread(a1, H, 1);
        MyThread t2 = new MyThread(a2, H, 2);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        totalTime += (int) (System.currentTimeMillis() - a);
        a = System.currentTimeMillis();

        // Склеиваем массивы
        System.arraycopy(a1, 0, array, 0, H);
        System.arraycopy(a2, 0, array, H, H);
        System.out.println("Time of combining two arrays into one (ms): " + (System.currentTimeMillis() - a));
        totalTime += (int) (System.currentTimeMillis() - a);

        // Сколько всего времени затрачено
        System.out.println("Total time of processing with split (ms): " + totalTime);
    }

    public static void main(String[] args) {
        // Заполняем единицами
        Arrays.fill(arr, 1);

        withoutSplit(arr);

        try {
            withSplit(arr);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
