package epi.ch4_primitives;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
/**
 * <title>sec4.2 비트 스왑</title>
 */
public class SwapBits {

    /* step11) 스스로 생각해보기, O(1)*/
    public static long my_way_swap(long x, short i, short j){
        // Shift 연산과 플래그를 활용해서,
        // i에 해당하는 값,
        // j에 해당하는 값을 얻어 오고, swap
        // short left = (short)(x & 1<<i);
        // short right = (short)(x & 1<<j);
        short left = (short)((x>>>i) & 1);
        short right = (short)((x>>>j) & 1);
        if(left != right){ // 서로 다를 때만 swap 하면 됨.
            x ^= ((long) 1 << i); // 이진법이라, swap이 XOR를 의미하게 됨.
            x ^= ((long) 1 << j);
        }
        return x;
    }


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
