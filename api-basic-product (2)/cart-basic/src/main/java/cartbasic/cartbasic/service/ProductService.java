package cartbasic.cartbasic.service;


import cartbasic.cartbasic.config.ServiceUrlConfig;
import cartbasic.cartbasic.viewmodel.ProductGetVm;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Service
public class ProductService {
    private final WebClient webClient;
    private final ServiceUrlConfig serviceUrlConfig;

    public ProductService(WebClient webClient, ServiceUrlConfig serviceUrlConfig) {
        this.webClient = webClient;
        this.serviceUrlConfig = serviceUrlConfig;
    }

    public List<ProductGetVm> getProducts (List<Long> ids){
        final URI uri = UriComponentsBuilder
                .fromHttpUrl(serviceUrlConfig.product())
                .path("/list-featured")
                .queryParam("productIds", ids)
                .build()
                .toUri();
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(ProductGetVm.class)
                .collectList()
                .block();
    }
}
