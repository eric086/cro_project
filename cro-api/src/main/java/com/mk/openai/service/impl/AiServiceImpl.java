package com.mk.openai.service.impl;

import com.mk.openai.config.ConfigMap;
import com.mk.openai.dto.Ai;
import com.mk.openai.entity.dao.TblPassage;
import com.mk.openai.exception.ServiceException;
import com.mk.openai.service.AiService;
import com.mk.openai.service.TblPassageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import static com.mk.openai.enums.ErrorCodeEnum.INTERNAL_SERVER_ERROR;

@Service
@AllArgsConstructor
@Slf4j
public class AiServiceImpl implements AiService {

    private final ConfigMap configMap;

    private RestTemplate restTemplate;

    private final String AI_SEARCH_URL = "/search";

    private final TblPassageService tblPassageService;

    @Override
    public List<Integer> queryList(List<String> queryList){

        List<Ai> aiList = new ArrayList<>();

        queryList.forEach(q-> aiList.addAll(query(q)));

        // 段落id
        List<Integer> passageIds = aiList.stream().map(Ai::getId).collect(Collectors.toList());

        // 段落列表
        List<TblPassage> passageList = tblPassageService.listByIds(passageIds);

        if (passageList.isEmpty()) {
            return Collections.emptyList();
        }

        return passageList.stream().map(TblPassage::getRegulationId).distinct().collect(Collectors.toList());
    }

    private List<Ai> query(String query){
        Map<String, String> params = new HashMap<>();
        params.put("query", query);
        params.put("top_k", "5");
        List<Ai> listPassage = post(configMap.getAiUrl() + AI_SEARCH_URL, params);

        if (listPassage.isEmpty()) {
            return Collections.emptyList();
        }
        return listPassage;
    }

    public <T> List<Ai> post(String url, T sendData) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set(HttpHeaders.ACCEPT_CHARSET, StandardCharsets.UTF_8.toString());
            HttpEntity<T> requestEntity = new HttpEntity<>(sendData, headers);

            ResponseEntity<List<Ai>> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    new ParameterizedTypeReference<List<Ai>>() {}
            );

            if (response.getStatusCode() != HttpStatus.OK) {
                log.error(
                        "fail to call the address [{}] with {}",
                        url,
                        Objects.requireNonNull(response.getBody()));
                throw new ServiceException(
                        "get error ["
                                + response.getBody()
                                + "] by calling the address ["
                                + url
                                + "]",
                        INTERNAL_SERVER_ERROR.getCode());
            }
            return response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.error("fail to call the address [{}] with {}", url, e.getMessage());
            throw new ServiceException(
                    "get error ["
                            + e.getResponseBodyAsString()
                            + "] by calling the address ["
                            + url
                            + "]",
                    INTERNAL_SERVER_ERROR.getCode());
        }
    }
}




