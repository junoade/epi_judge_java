package epi.ch4_primitives;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

public class SwapBits {
    /**
     * 시간복잡도 O(1)
     *
     * @param x
     * @param i
     * @param j
     * @return
     */
    @EpiTest(testDataFile = "swap_bits.tsv")
    public static long swapBits(long x, int i, int j) {
        // 1. i 번쨰 비트와 j 번째 비트가 다른지 확인
        // 2. 다르다면, 0x1 << i 마스크와 0x1 << j 마스크에 대해 입력 x를 XOR 연산하여 비트를 flip (swap)
        if (((x >>> i) & 1) != ((x >>> j) & 1)) {
          long bit_mask = (0x1L << i) | (0x1L << j);
          x ^= bit_mask;
        }
        return x;
    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "SwapBits.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
