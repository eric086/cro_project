package com.mk.openai.service.impl;

import com.mk.openai.config.ConfigMap;
import com.mk.openai.dto.Ai;
import com.mk.openai.entity.dao.TblCategory;
import com.mk.openai.entity.dao.TblPassage;
import com.mk.openai.entity.dao.TblRegulation;
import com.mk.openai.entity.vo.RegulationVo;
import com.mk.openai.exception.ServiceException;
import com.mk.openai.service.TblCategoryService;
import com.mk.openai.service.TblPassageService;
import com.mk.openai.service.TblRegulationService;
import com.mk.openai.service.TestService;
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
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.mk.openai.enums.ErrorCodeEnum.INTERNAL_SERVER_ERROR;

@Service
@AllArgsConstructor
@Slf4j
public class TestServiceImpl implements TestService {

    private final ConfigMap configMap;

    private RestTemplate restTemplate;

    private final String AI_SEARCH_URL = "/search";

    private final TblPassageService tblPassageService;

    private final TblCategoryService tblCategoryService;

    private final TblRegulationService tblRegulationService;

    @Override
    public List<RegulationVo> query(String query,String topK){
        Map<String, String> params = new HashMap<>();
        params.put("query", query);
        params.put("top_k", topK);
        List<Ai> listPassage = post(configMap.getAiUrl() + AI_SEARCH_URL, params);

        // 段落id
        List<Integer> passageIds = listPassage.stream().map(Ai::getId).collect(Collectors.toList());

        // 法规id
        List<Integer> regulationIds = tblPassageService.listByIds(passageIds).stream().map(TblPassage::getRegulationId).collect(Collectors.toList());

        // 法规
        List<TblRegulation> regulationList = tblRegulationService.listByIds(regulationIds);

        if (regulationList.isEmpty()) {
            return Collections.emptyList();
        }

        List<TblCategory> categoryList = tblCategoryService.list();

        Map<Integer, TblCategory> categoryMap = categoryList.stream().collect(Collectors.toMap(TblCategory::getId,
                Function.identity()));

        List<RegulationVo> regulationVoList = new ArrayList<>();

        regulationList.forEach(r -> {
            String categoryName = categoryMap.containsKey(r.getCategoryId()) ?
                    categoryMap.get(r.getCategoryId()).getName() : "";

            RegulationVo regulationVo = RegulationVo.builder()
                    .regulationId(r.getId())
                    .fileName(r.getFileName())
                    .filePath(r.getFilePath())
                    .categoryId(r.getCategoryId())
                    .categoryName(categoryName)
                    .build();
            regulationVoList.add(regulationVo);
        });

        return regulationVoList;


    }

    private <T> List<Ai> post(String url, T sendData) {
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




