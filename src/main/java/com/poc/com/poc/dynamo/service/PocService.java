package com.poc.com.poc.dynamo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.poc.com.poc.dynamo.dao.PessoaRepository;
import com.poc.com.poc.dynamo.entity.PessoaEntity;

@Component
public class PocService implements ApplicationListener<ApplicationReadyEvent> {

	@Autowired
	private PessoaRepository repository;

	private static final Logger log = LoggerFactory.getLogger(PocService.class);

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		try {
			repository.save(new PessoaEntity("Joao", 20));
			Thread.sleep(2000);
			repository.save(new PessoaEntity("Maria", 20));
			Thread.sleep(2000);
			PessoaEntity ultimo = new PessoaEntity("Pedro", 20);
			repository.save(ultimo);

			repository.listaTodasPessoasPelaIdadeScan(20).forEach(p -> {
				log.info("Scan {}", p);
			});

			repository.listaTodasPessoasPelaIdadeQuery(20).forEach(p -> {
				log.info("Query {}", p);
			});

			PessoaEntity entity = repository.retornaUltimoRegistroQuery();
			log.info("Registros iguais {}", Boolean.toString(ultimo.equals(entity)));

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}

}
