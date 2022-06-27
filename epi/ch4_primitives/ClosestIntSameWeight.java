package epi.ch4_primitives;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

/**
 * <title>sec4.4 같은 무게를 가진 가장 가까운 정수 찾기 </title>
 * 음이 아닌 어떤 정수 x를 이진수로 표현했을 때 1로 세팅된 비트의 개수를 무게라고 정의하자.
 *
 */
public class ClosestIntSameWeight {
  /**
   * 내가 풀이한 시간복잡도 O(1) 풀이
   * LSB의 비트가 1일때와 0일때, swap의 기준이 되는 최하위 비트의 값이 달라짐을 활용
   * @time-complexity O(1)
   * @param x
   * @return
   */
  @EpiTest(testDataFile = "closest_int_same_weight.tsv")
  public static long closestIntSameBitCount(long x) {
    boolean isLsbEqualsOne = (x & 0x1) == 1;
    long i;
    long j;

    /* 이경우 최하위 0 비트를 기준으로 바로 하위 비트와 swap하도록 자리와 swap 하도록 */
    if(isLsbEqualsOne){
      // i = x & ~(x-1);
      // j = ~x & ~(~x-1);
      i = ~x & ~(~x-1); // 최하위 0비트의 자리만 1로
      j = i >>> 1;      // 바로 밑의 하위 비트 자리만 1로
      return x + i - j; // swap 반환
    }
    /* 일반적인 경우, 최하위 1비트의 옆과 swap하도록*/
    else{
      i = x & ~(x-1);
      j = i >>> 1;
      return x - i + j; // swap 반환
    }
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "ClosestIntSameWeight.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
