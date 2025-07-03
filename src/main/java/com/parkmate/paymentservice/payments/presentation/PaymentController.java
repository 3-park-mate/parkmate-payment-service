package com.parkmate.paymentservice.payments.presentation;

import com.parkmate.paymentservice.common.response.ApiResponse;
import com.parkmate.paymentservice.payments.application.PaymentService;
import com.parkmate.paymentservice.payments.dto.request.PaymentRequestDto;
import com.parkmate.paymentservice.payments.vo.request.PaymentRequestVo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @Operation(
            summary = "결제 정보 확인",
            description = "X-User-UUID 헤더와 결제 요청 정보를 통해 결제 처리를 수행합니다.",
            tags = {"PAYMENT-SERVICE"}
    )
    @PostMapping
    public ApiResponse<String> confirmPayment(
            @RequestHeader("X-User-UUID") String userUuid,
            @RequestBody PaymentRequestVo paymentRequestVo
    ) {
        paymentService.confirmPayment(PaymentRequestDto.of(userUuid, paymentRequestVo));
        return ApiResponse.ok(
                "결제 승인되었습니다"
        );

    }

}
