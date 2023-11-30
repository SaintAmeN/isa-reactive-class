package com.amen.isa.component.controller;

import com.amen.isa.component.sse.ResultCollector;
import com.amen.isa.model.response.NonPrimitiveResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple3;

@Slf4j
@RestController()
@RequiredArgsConstructor
@RequestMapping("/collect")
public class CollectController {
    private final ResultCollector<NonPrimitiveResponse> resultCollector = new ResultCollector<>();

    @GetMapping()
    public Flux<ResultCollector.CallResult<NonPrimitiveResponse>> collect() {
        return resultCollector.collect(ResultCollector.getCollectCalls()).log();
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ResultCollector.CallResult<NonPrimitiveResponse>> collectStream() {
        return resultCollector.collect(ResultCollector.getCollectCalls()).log();
    }

    @GetMapping("/zip")
    public Flux<Tuple3<
            ResultCollector.CallResult<NonPrimitiveResponse>,
            ResultCollector.CallResult<NonPrimitiveResponse>,
            ResultCollector.CallResult<NonPrimitiveResponse>>> zip() {
        return resultCollector.zipCollect().log();
    }

    @GetMapping(value = "/zip/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Tuple3<
            ResultCollector.CallResult<NonPrimitiveResponse>,
            ResultCollector.CallResult<NonPrimitiveResponse>,
            ResultCollector.CallResult<NonPrimitiveResponse>>> zipStream() {
        return resultCollector.zipCollect().log();
    }
}
