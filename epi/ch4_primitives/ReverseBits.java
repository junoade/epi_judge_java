package epi.ch4_primitives;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

/**
 * <title>section 4.3 비트 뒤집기</title>
 *
 * @topic 64비트 정수 x의 이진수를 뒤집을 때 어떻게 효율적으로 뒤집을 수 있을까?
 * @problem 룩업 테이블 하나의 엔트리를 16비트 int형으로 하고 싶은데 왜 책에서는 long으로 굳이 공간을 더 쓸까?
 * @ref 노션_망각주기_비트연산정리
 * @see Parity 4.1 패리티 문제의 룩업 테이블 풀이
 */
public class ReverseBits {

    /* 룩업 테이블 메모리 공간 할당, 공간 복잡도 O(2^16) = 65536*/
    private static final int WORD_SIZE = 16;
    public static int[] lookupTable = new int[1 << WORD_SIZE]; // SHL의 성질 1 * 2^16

    /* lookup Table의 초기값 설정하기*/
    public static int reverse(int x, int n) {
        /* 처음과 끝을 swap하는 방식으로 reverse*/
        for (int i = 0, j = n; i < j; i++, j--) {
            /* swap 구현 */
            if (((x >>> i) & 1) != ((x >>> j) & 1)) { // 다를 떄만
                int bitMask = (0x1 << i) | (0x1 << j);
                x ^= bitMask;
            }
        }
        return x;
    }

    static {
        for (int i = 0; i < (1 << 16); i++) {
            lookupTable[i] = reverse(i, WORD_SIZE);
        }
    }

    /**
     * 전제) 룩업 테이블 구성이 완료 되었다.
     * 64비트 정수 x를 16비트씩 쪼개서, reverse된 비트를 담고 있는 룩업 테이블을 조회
     * 그리고 각 비트들에 대해 OR 연산을 통해 최종값 반환
     *
     * @param x
     * @return
     */
    @EpiTest(testDataFile = "reverse_bits.tsv")
    public static long reverseBits(long x) {
        // TODO - you fill in here.
        final int BIT_MASK = 0xFFFF; // long 형 정수 x를 2^15-1 범위로 추출
        long leastToMost = (long) lookupTable[(int) (x & BIT_MASK)] << (3 * WORD_SIZE);
        long secondToThird = (long) lookupTable[((int) (x >>> WORD_SIZE) & BIT_MASK)] << (2 * WORD_SIZE);
        long thirdToSecond = (long) lookupTable[((int) (x >>> (2 * WORD_SIZE)) & BIT_MASK)] << WORD_SIZE;
        long mostToLeast = lookupTable[((int) (x >>> (3 * WORD_SIZE)) & BIT_MASK)];
        return leastToMost | secondToThird | thirdToSecond | mostToLeast;

    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "ReverseBits.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
