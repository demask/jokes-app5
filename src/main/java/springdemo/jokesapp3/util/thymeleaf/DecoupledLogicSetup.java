package springdemo.jokesapp3.util.thymeleaf;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DecoupledLogicSetup {
	
	private final SpringResourceTemplateResolver templateResolver;

	
	public DecoupledLogicSetup(SpringResourceTemplateResolver templateResolver) {
		
		this.templateResolver = templateResolver;
	}
	
	@PostConstruct
	public void init() {
		templateResolver.setUseDecoupledLogic(true);
		
	}
	
}
