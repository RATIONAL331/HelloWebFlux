package com.example.productservice.config;

import com.example.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DataSetupConfiguration implements CommandLineRunner {
	private final ProductService productService;

	@Override
	public void run(String... args) throws Exception {
		// 만약 내장 DB로 앱을 띄운다면 유용하게 사용 가능
//		ProductDto productDto1 = new ProductDto("4k-tv", 1000);
//		ProductDto productDto2 = new ProductDto("camera", 750);
//		ProductDto productDto3 = new ProductDto("iphone", 800);
//		ProductDto productDto4 = new ProductDto("headphone", 100);
//		Flux.just(productDto1, productDto2, productDto3, productDto4)
//		    .flatMap(p -> productService.insertProduct(Mono.just(p)))
//		    .subscribe(System.out::println);
	}
}
