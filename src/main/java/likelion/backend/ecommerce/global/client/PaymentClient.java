package likelion.backend.ecommerce.global.client;

import org.springframework.stereotype.Component;

@Component
public class PaymentClient {

    private final String PG_API_URL = "https://api.tosspayments.com/v1/payments/confirm"; //실제 PG(Toss, kakaopay .. etc) 넣기

    public boolean verifyPaymentWithPG(String token, Integer amount) {
        try {
            // 1. 결제사 서버가 요구하는 형식으로 헤더와 바디(예시로 (토큰,금액)을 제공 구성)
            // 2. HTTP 요청을 전송(POST).
            // 3. response 받기
            // ResponseEntity<String> response = restTemplate.postForEntity(PG_API_URL, request, String.class);

            // 4. 결제사 서버의 응답 코드가 200(성공)이면 true 반환
            // if (response.getStatusCode().is2xxSuccessful()) return true;

            return true; //지금은 Mock 방식이라 가상으로 성공 가정
        } catch (Exception e) {
            return false;
        }
    }
}