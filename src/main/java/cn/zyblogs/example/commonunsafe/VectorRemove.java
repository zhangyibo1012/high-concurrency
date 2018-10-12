package cn.zyblogs.example.commonunsafe;

import java.util.Iterator;
import java.util.Vector;

/**
 * @Title: VectorRemove.java
 * @Package cn.zyblogs.example.commonunsafe
 * @Description: TODO 迭代器和foreach遍历的时候删除会出错 普通for ok
 * @Author ZhangYB
 * @Version V1.0
 */
public class VectorRemove {

    /**
     *  java.util.ConcurrentModificationException
     * @param v1
     */
    private static void test1(Vector<Integer> v1){
        for (Integer i : v1){
            if (i.equals(3)){
                v1.remove(i);
            }
        }
    }

    /**
     *  java.lang.ArrayIndexOutOfBoundsException: Array index out of range: 3
     * @param v1
     */
    private static void test2(Vector<Integer> v1){
        Iterator<Integer> iterator = v1.iterator();
        while (iterator.hasNext()){
            Integer next = iterator.next();
            if (next.equals(3)){
                v1.remove(3);
            }
        }
    }

    /**
     *  OK
     * @param v1
     */
    private static void test3(Vector<Integer> v1){
        for (int i = 0 ; i < v1.size(); i++){
            if (v1.get(i).equals(3)){
                v1.remove(i);
            }
        }
    }
    public static void main(String[] args) {
        Vector<Integer> vector = new Vector<>();
        vector.add(1);
        vector.add(2);
        vector.add(3);
        test3(vector);
        System.err.println("remove Ok");

    }
}
