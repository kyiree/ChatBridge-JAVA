package com.cn.chat.bridge.gpt.service.impl;


import com.cn.chat.bridge.admin.dto.BotConfigDto;
import com.cn.chat.bridge.admin.dto.OpenAiConfigDto;
import com.cn.chat.bridge.admin.dto.ProxyConfigDto;
import com.cn.chat.bridge.admin.service.SystemService;
import com.cn.chat.bridge.business.repository.PersonalityRepository;
import com.cn.chat.bridge.business.repository.entity.Personality;
import com.cn.chat.bridge.business.vo.PersonalityConfigStructureVo;
import com.cn.chat.bridge.business.vo.ServerConfigVo;
import com.cn.chat.bridge.common.constant.EnableEnum;
import com.cn.chat.bridge.common.constant.OperateConstant;
import com.cn.chat.bridge.common.constant.ServerConstant;
import com.cn.chat.bridge.gpt.request.OpenAiGptMessageRequest;
import com.cn.chat.bridge.gpt.request.OpenAiGptRequest;
import com.cn.chat.bridge.common.service.ICacheService;
import com.cn.chat.bridge.common.utils.AuthUtils;
import com.cn.chat.bridge.gpt.dto.WebMessageRequest;
import com.cn.chat.bridge.gpt.service.GptService;
import com.cn.chat.bridge.user.constant.PersonalityConstant;
import com.cn.chat.bridge.user.repository.UserRepository;
import com.cn.chat.bridge.user.request.UpdatePersonalityRequest;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.ProxyProvider;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@Data
public class GptServiceImpl implements GptService {

    private final UserRepository userRepository;

    private final ICacheService cacheService;

    private final RedisTemplate<String, Object> redisTemplate;

    private final WebClient.Builder webClient;

    private final PersonalityRepository personalityRepository;

    private final SystemService systemService;

    @Override
    @Async
    public void lastOperationTime(Long userId) {
        //设置当前最后操作时间
       // cacheService.addToCache4Object(OperateConstant.USER_CALL_TIME + userId, LocalDateTime.now(), 604800L);
    }

    @Override
    public PersonalityConfigStructureVo getPersonalityConfig() {
        Long currentLoginId = AuthUtils.getCurrentLoginId();

        String key = PersonalityConstant.GPT_CONFIG + currentLoginId;
        PersonalityConfigStructureVo personalityConfigStructureVo = cacheService.getFromCache4Object(key, PersonalityConfigStructureVo.class);

        if (Objects.isNull(personalityConfigStructureVo)) {
            Personality personality = personalityRepository.getByUserId(currentLoginId);
            if (Objects.nonNull(personality)) {
                personalityConfigStructureVo = PersonalityConfigStructureVo.create(personality.getModel(), personality.getTopP(), personality.getMaxTokens(),
                        personality.getTemperature(), personality.getOpenKey(), personality.getOpenAiUrl(), personality.getQuestions(), personality.getAnswer(), personality.getSpeed());
                cacheService.addToCache4Object(key, personalityConfigStructureVo, 43200L);
            }
        }

        return personalityConfigStructureVo;
    }

    @Override
    public void updatePersonalityConfig(UpdatePersonalityRequest request) {
        Long currentLoginId = AuthUtils.getCurrentLoginId();

        Personality old = personalityRepository.getByUserId(currentLoginId);
        if (Objects.nonNull(old)) {
            Personality update = Personality.create4Update(old, request);
            personalityRepository.updateById(update);
        } else {
            Personality add = Personality.create4Add(request, currentLoginId);
            personalityRepository.save(add);
        }
        cacheService.removeFromCache4Object(PersonalityConstant.GPT_CONFIG + currentLoginId);

    }

    @Override
    public Flux<String> concatenationGpt(WebMessageRequest request) {
        OpenAiConfigDto openAiConfig = systemService.getOpenAiConfig();
        ProxyConfigDto proxyConfig = systemService.getProxyConfig();


        // 创建带有代理的HttpClient
        HttpClient httpClient = Objects.equals(proxyConfig.getEnable(), EnableEnum.ENABLE) ? HttpClient.create()
                .tcpConfiguration(tcpClient ->
                        tcpClient.proxy(proxy ->
                                proxy.type(ProxyProvider.Proxy.HTTP)
                                        .host(proxyConfig.getProxyIp())
                                        .port(proxyConfig.getProxyPort())
                        )
                ) : null;
        WebClient.Builder webClientBuilder = webClient.defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + openAiConfig.fetchOpenPlusKey());

        if (Objects.nonNull(httpClient)) {
            webClientBuilder.clientConnector(new ReactorClientHttpConnector(httpClient));
        }

        return webClientBuilder.build()
                .post()
                .uri(openAiConfig.getOpenAiPlusUrl())
                .body(BodyInserters.fromValue(OpenAiGptRequest.create4Request(request.getMessages())))
                .retrieve()
                .bodyToFlux(String.class);
    }
}
