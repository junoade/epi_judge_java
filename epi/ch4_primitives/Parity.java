package epi.ch4_primitives;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

/**
 * <title>section 4.1 패리티 계산하기</title>
 * @def 패리티: 데이터 통신에서, 송신하고자 하는 데이터의 각 비트에서 1의 개수가 짝수 인지 홀수인지 새서,
 * -> even parity 면 0, odd parity 면 1 로 나타내어, Error Detecting 하는 방법
 *
 * @topic 64비트 숫자 하나의 패리티를 계산을 어떻게 효율적으로 할 수 있을까?
 * @ref 노션_망각주기_비트연산정리
 * @ref 알고리즘 문제 해결 전략
 */
public class Parity {

  /**
   * step 1) LSB의 이진값과 0x1을 먼저 비교하여, LSB가 1인지 확인. 그 다음 현재 parity 값과 XOR 연산. 입력 x를 (unsigned) SHL 해줌
   * @time-complexity O(N)
   * @param x
   * @return parity
   */
  // @EpiTest(testDataFile = "parity.tsv")
  public static short parityMostSimple(long x) {
    short parity = 0;
    while(x != 0){ // 입력 x의 이진 표현에서 1로 세팅된 비트의 개수를 O(N)으로 세는 방법
      // x의 LSB가 1인지 검사하고
      // LSB가 1이면, result 는 1, 0 을 반복하게 된다.
      // 결과적으로,
      // 1이 홀수개 이면 result는 1
      // 1이 짝수개가 되면 result는 0
      // 이렇게 반복된다.
      parity = (short) (parity ^ (x & 1));
      x >>>=1;
    }
    return parity;
  }

  /**
   * step 2) 하위 비트를 한번에 지워, 최선-평균적 경우의 성능을 향상시키는 방법
   * <b>x & (x-1) 연산의 성질</b>\
   * 예를 들어 x = 1011 0010 이라하면, x-1 = 1011 0001 이다.
   * x & (x-1) 연산을 취하면, 1011 00 이외의 lower bit 자리는 모두 0으로 된다. (지워진다)
   * 이를 반복하면, 1의 개수 k 만큼의 시간복잡도로 문제를 해결할 수 있게 된다.
   *
   * @time-complexity O(k), k = 1로 세팅된 비트의 개수
   * @param x
   * @return parity
   */
  @EpiTest(testDataFile = "parity.tsv")
  public static short parity(long x){
    short parity = 0;
    while(x != 0){
      x = x & (x-1);
      parity ^= 1; // 1의 개수만큼 0-> 1-> 0 -> 1 반복 // 홀수개면 1 반환
    }
    return parity;
  }

  /**
   * step 3) 연산 결과를 결합법칙의 성질을 이용해, 더 적은 공간을 갖는 룩업테이블에 캐싱, 매우 큰 수에 대한 패리티도 효율적으로 구하기!
   *
   * @time-complexity O(N/L), L : 해시 테이블에 사용될 키값의 비트수
   * @param x
   * @return parity
   */
  public static short parityLookupTable(long x) {
    // 64비트를 16비트씩 나눈다고 생각해보자
    // 각 16비트의 패리티를 구하고 나서 패리티를 구하게 되어도, 똑같이 64비트의 패리티리를 구하게 된다.
    // 따라서 패리티 구하기는 결합법칙이 성립한다
    // 이를 이용해 lookup table를 구성한다
    return 0;
  }

  /* step 4) 결합 법칙 / 교환법칙 성질을 이용해서 여러 비트를 한번에 수행하도록하기 */
  public static short parity_log(long x) {
    // 64비트는 32비트, 32비트
    // 32비트는 16비트, 16비트
    // 16비트는 8비트, 8비트
    // 8비트는 4비트, 4비트
    // 4비트는 2비트, 2비트
    // 2비트는 1비트, 1비트
    // 예를 들어, 1101 0111 과 같이 8비트가 있을 때, x>>>4 는 0000 1101 이다.
    // 다시 말해 0111 과 1101 를 XOR 연산한 결과는 1010 인데, 1010의 패리티는 전체의 패리티와 같게 된다.
    // 다시 x>>>2하여 10과 10의 XOR한 결과 00을 얻고, 여기서 LSB가 패리티 값이 된다
    for (int i = 5; i > -1; i--) {
      int j = (int) Math.pow(2, i);
      x ^= x >>> j;
    }
    return (short) (x & 0x1); // x의 LSB를 반환함
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "Parity.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
