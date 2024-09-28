package com.spring.manshubapi.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class SignalingController {

    /*
    브로드캐스트: 메시지를 여러 구독자에게 동시에 전송하는 것을 의미
    밑 코드에서 @SendTo("/topic/offer")와 같은 어노테이션은
    해당 주제(/topic/offer)를 구독하는 모든 클라이언트에게
    메시지를 전송하는 기능을 한다. 따라서 이 메시지를
    브로드캐스트한다고 표현. 즉, WebSocket으로 특정
    메시지를 서버가 받으면, 그 메시지를 해당 주제를 구독하고
    있는 모든 사용자에게 다시 보내는 방식으로 작동하는 것.
     */

    // WebSocket을 통한 offer 메시지를 처리하는 메서드
    @MessageMapping("/offer")
    @SendTo("/topic/offer") // 모든 구독자들에게 브로드캐스트
    public String handleOffer(@RequestBody String offer) {
        return offer; // 받은 offer 메시지를 다시 전송
    }

    // WebSocket을 통한 answer 메시지를 처리하는 메서드
    @MessageMapping("/answer")
    @SendTo("/topic/answer") // 모든 구독자
    public String handleAnswer(@RequestBody String answer) {
        return answer; // 받은 answer 메시지를 다시 전송
    }

    // WebSocket을 통한 ICE Candidate 메시지를 처리하는 메서드
    @MessageMapping("/ice-candidate")
    @SendTo("/topic/ice-candidate") // 모든 구독자들에게 브로드캐스트
    public String handleIceCandidate(@RequestBody String iceCandidate) {
        return iceCandidate; // 받은 ICE candidate 메시지를 다시 전송
    }



}
