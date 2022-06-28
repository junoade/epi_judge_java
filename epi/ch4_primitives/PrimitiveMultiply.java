package epi.ch4_primitives;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

/**
 * <title>4.5 곱셈과 덧셈 없이 곱셈 연산 계산하기 </title>
 *
 * @topic 덧셈 연산과 곱셈 연산 없이, 대인 연산자, 비트 연산자, 비교 연산자 만으로 곱셈 연산을 구현해보자
 * @problem 저전력 기기의 프로세서에는 곱셈 연산기가 없는 경우가 종종 있다.
 * @ref 노션_망각주기_비트연산정리
 */
public class PrimitiveMultiply {

    /**
     * 나의 풀이
     * - 덧셈 연산을 y times 반복하는 방법을 고려하여,
     * - 덧셈 연산을 직접 구현한다.
     *
     * @param x
     * @param y
     * @return
     * @time-complexity O(N ^ 2)
     */
    @EpiTest(testDataFile = "primitive_multiply.tsv")
    public static long multiply(long x, long y) {
        long sum = 0L;
        /* x의 값 만큼 반복, x의 k번째 비트가 1로 세팅되어 있다면, 2^k * y 를 더함; SHL 이용 테크닉 */
        while(x != 0){
          /* x의 끝자리 (1번째) 부터 비교 */
          if((x & 1) != 0){
            sum = add(sum, y);
          }

          x >>>= 1; // x의 값 만큼 순회하기 위해
          y <<= 1; // y를 반복횟수 n 만큼 SHR하여, 2^n * y를 만족시킴
        }
        return sum;
    }

    /* O(N) 연산 과정 k=0x1 부터 SHL하며, carry 를 조사후, 더 해나감 */
    public static long add(long a, long b) {
        long sum = 0L, carryin = 0L, k = 0x1;
        /* tempA와 tempB는 각 이진 표현의 길이를 count하기 위한 역할 */
        long aLength = a, bLength = b;

        while(aLength != 0 || bLength != 0){
          /* 현재 k비트 자리에서 음이 아닌 정수 a, b에 대해 carry가 발생하는지 조사 */
          long aCarry = a & k, bCarry = b & k;

          /* 이전 자리에서 발생한 carry_in까지 고려해준다 */
          long carryOut = ( aCarry & bCarry ) | (aCarry & carryin) | (bCarry & carryin);
          sum |= (aCarry ^ bCarry ^ carryin);
          carryin = carryOut << 1;
          k <<= 1;
          aLength >>>= 1;
          bLength >>>= 1;

        }
        return sum | carryin;
    }


    /* O(1) 로 연산 과정 시도 */
    public static long add_tried_O1(long a, long b) {
        long sum = 0L, carry = 0L;
        carry = (a & b) << 1;
        sum = (a ^ b) | carry;
        return sum;

    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "PrimitiveMultiply.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
