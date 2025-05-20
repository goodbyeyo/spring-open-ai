package app.server.openai.controller;

import app.server.openai.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 달리3 이용한 이미지 생성 고려사항
 * 1. 주제 : 그림의 주제나 핵심내용이 무것인지 설명
 * 2. 시각적 요소 : 그림에 포함될 주요 시각적 요소를 설명 (배경, 주요 객체, 색상 등)
 * 3. 상세 설명 : 각 요소에 대한 상세한 설명 제공
 * 4. 순서와 구조 : 그림의 전체적인 구성이나 배열에 대한 설명 제공
 * 5. 스타일과 텍스처 : 원하는 그림의 스타일과 텍스처 설명(실사, 수채화)
 * 6. 기타 특이사항 : 그림에 추가하거나 특별히 고려해야할 점이 있다면 설명
 */
@RestController
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @GetMapping("/image")
    public String image(@RequestParam String message ) {
        return imageService.getResponse(message);
    }

}
