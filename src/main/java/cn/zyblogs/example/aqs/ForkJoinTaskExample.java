package cn.zyblogs.example.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

/**
 * @Title: ForkJoinTaskExample.java
 * @Package cn.zyblogs.example.aqs
 * @Description: TODO
 * @Author ZhangYB
 * @Version V1.0
 */
@Slf4j
public class ForkJoinTaskExample {
    // 分而治之 一个任务分成多个部分 多个线程执行
        /**
         *  最大阀值 1000/200 分成5个任务
         */
        private final static int MAX_THRESHOLD = 200;

/**
 *  RecursiveTask 有返回值
 *  RecursiveAction 没有返回值 等待处理结果
 */
private static class CalcultedRecursiveTask extends RecursiveTask<Integer>{

    /**
     *  上限  下限
     */
    private final int start;
    private final int end;

    CalcultedRecursiveTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        // 判断任务是否足够小，如果足够小就直接执行任务。如果不足够小，就必须分割成两个子任务，每个子任务在调用fork方法时，又会进入compute方法，看看当前子任务是否需要继续分割成孙任务，如果不需要继续分割，则执行当前子任务并返回结果。使用join方法会等待子任务执行完并得到其结果。
        if (end - start <= MAX_THRESHOLD){
            return IntStream.rangeClosed(start, end).sum();
        }else {
            // 计算拆分
            int middle = (start + end) / 2;
            CalcultedRecursiveTask leftTask = new CalcultedRecursiveTask(start ,middle);
            CalcultedRecursiveTask rightTask = new CalcultedRecursiveTask(middle + 1 ,end);

            // 执行子任务调用fork方法并不是最佳的选择，最佳的选择是invokeAll方法。
//                 leftTask.fork();
//                 rightTask.fork();
            invokeAll(leftTask, rightTask);
            // 结果子任务结果
            return leftTask.join() + rightTask.join();
        }
    }
}
    public static void main(String[] args) {
        final ForkJoinPool forkJoinPool = new ForkJoinPool();
        // ForkJoinPool 使用submit 或 invoke 提交的区别：invoke是同步执行，调用之后需要等待任务完成，才能执行后面的代码；submit是异步执行，只有在Future调用get获取结果的时候会阻塞。
        ForkJoinTask<Integer> future = forkJoinPool.submit(new CalcultedRecursiveTask(0, 1000));
        try {
            Integer result = future.get();
            System.out.println(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
