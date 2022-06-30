package epi.ch4_primitives;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

/**
 * <title>4.7 x^y 계산하기 </title>
 */
public class PowerXY {

    /**
     * @time-complexity O(n)
     * @param x
     * @param y
     * @return
     */
    @EpiTest(testDataFile = "power_x_y.tsv")
    public static double power(double x, int y) {
        double result = 1.0;
        long power = y;

        /* y, power 가 음수라면? */
        if(y < 0){
            power = -power;
            x = 1.0/x;
        }

        /* power를 쪼개며 곱셈의 횟수를 줄여나감 */
        while(power != 0){
            // result에 이전의 누적 곱 x를 곱해줌 (지수는 결국 1이 되기 때문에)
            // 만약 초기 power가 1이 아닌 홀수였다면 x가 하나 더 곱해짐
            if((power & 0x1) != 0){
                result *= x;
            }
            /* 그 외엔, 지수법칙의 a^(power/2) + a^(power/2) = a^power 성질을 이용 */
            x *= x;
            power >>>= 1; // 2를 나눠줌
        }
        return result;
    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "PowerXY.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
