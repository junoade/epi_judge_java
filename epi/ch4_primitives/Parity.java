package epi.ch4_primitives;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

/**
 * <title>section 4.1</title>
 */
public class Parity {

  /* 1. LSB의 이진값과 1을 먼저 비교하여, 1인지 확인. 그 다음 현재 parity 값과 XOR 연산. 입력 x를 (unsigned) SHL 해줌
  * 시간 복잡도 O(N)
  * */
  // @EpiTest(testDataFile = "parity.tsv")
  public static short parity_(long x) {
    short parity = 0;
    while(x != 0){ // 입력 x의 이진 표현에서 1로 세팅된 비트의 개수를 O(N)으로 세는 방법
      parity = (short) (parity ^ (x & 1));
      x >>>=1;
    }
    return parity;
  }

  /* 2. 하위 비트를 한번에 지워서 최선의 경우와 평균적인 경우의 성능을 향상시키는 방법
  * 시간 복잡도 O(k), k = 1로 세팅된 비트의 개수
  * */
  @EpiTest(testDataFile = "parity.tsv")
  public static short parity(long x){
    short parity = 0;
    while(x != 0){
      x = x & (x-1);
      parity ^= 1; // 1의 개수만큼 0-> 1-> 0 -> 1 반복 // 홀수개면 1 반환
    }
    return parity;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "Parity.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
