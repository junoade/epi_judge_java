package epi.ch4_primitives;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

/**
 * <title>4.6 x/y 계산하기</title>
 *
 * @topic 양의 정수 x와 y를 나눈 몫을, 덧셈, 뺼셈 그리고 시프트 연산만을 이용해 구현해보자
 * @idea (x - y) <= x 일 때 뺼셈을 계속 반복하여 수행한다.
 * @problem 단순히 y를 계속 빼면 필요한 계산량이 많다. 나누어 떨어지는 가장 큰 값 y로 바꾸어서 x와 뺄셈을 하고 반복을 한다면?
 * @ref 노션_망각주기_비트연산정리
 */
public class PrimitiveDivide {
    @EpiTest(testDataFile = "primitive_divide.tsv")
    public static int divide(int x, int y) {
        // TODO - you fill in here.
        int k = 32;
        int quotient = 0;
        /* overflow 방지 */
        long y_max = y << k; // y* 2^32
        while (x >= y) {
            /* y*2^k <= x 를 만족하는 k를 계산 */
            while(y_max > x){
                y_max >>>= 1;
                --k;
            }
            /* 뺼셈 수행, 몫에 값 추가 */
            quotient += 1 << k;
            x -= y_max;
        }

        return quotient;
    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "PrimitiveDivide.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
