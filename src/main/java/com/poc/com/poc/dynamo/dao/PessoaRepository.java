package com.poc.com.poc.dynamo.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.poc.com.poc.dynamo.entity.PessoaEntity;

@Service
public class PessoaRepository {

	private DynamoDBMapper mapper;

	@Autowired
	private AmazonDynamoDBClient dbClient;

	private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

	@PostConstruct
	public void init() {

		DynamoDBMapperConfig.Builder builder = new DynamoDBMapperConfig.Builder();
		DynamoDBMapperConfig dbMapperConfig = builder.build();
		mapper = new DynamoDBMapper(dbClient, dbMapperConfig);

	}

	public void removeTable() {
		dbClient.deleteTable("Pessoa");
	}

	public PessoaEntity save(PessoaEntity entity) {
		mapper.save(entity);
		return entity;
	}

	public List<PessoaEntity> listaTodasPessoasPelaIdadeScan(Integer idade) {
		Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
		expressionAttributeValues.put(":idade", new AttributeValue().withN(idade.toString()));

		DynamoDBScanExpression expression = new DynamoDBScanExpression().withConsistentRead(Boolean.FALSE)
				.withFilterExpression(":idade = idade").withExpressionAttributeValues(expressionAttributeValues);

		return mapper.scan(PessoaEntity.class, expression);

	}

	public List<PessoaEntity> listaTodasPessoasPelaIdadeQuery(Integer idade) {

		Map<String, String> expressionAttributesNames = new HashMap<>();
		expressionAttributesNames.put("#idade", "idade");
		// expressionAttributesNames.put("#nome","nome");

		Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
		expressionAttributeValues.put(":idade", new AttributeValue().withN(idade.toString()));
		// expressionAttributeValues.put(":nome",new
		// AttributeValue().withN("Pedro"));

		DynamoDBQueryExpression<PessoaEntity> expression = new DynamoDBQueryExpression<PessoaEntity>()
				.withIndexName("idadeIndex").withKeyConditionExpression("#idade = :idade")
				.withExpressionAttributeNames(expressionAttributesNames)
				.withExpressionAttributeValues(expressionAttributeValues).withConsistentRead(false);

		return mapper.query(PessoaEntity.class, expression);

	}

	public PessoaEntity retornaUltimoRegistroQuery() {

		Map<String, String> expressionAttributesNames = new HashMap<>();
		expressionAttributesNames.put("#dataDeInclusao", "dataDeInclusao");

		Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
		expressionAttributeValues.put(":dataDeInclusao",
				new AttributeValue().withN(Long.toString(new Date().getTime())));
		
		//Condition condition = new Condition();
		//condition.withComparisonOperator(ComparisonOperator.LT)
	    // .withAttributeValueList(new AttributeValue().withN(Long.toString(new Date().getTime())));
		
		
		DynamoDBQueryExpression<PessoaEntity> expression = new DynamoDBQueryExpression<PessoaEntity>()
				.withIndexName("dataIndex").withKeyConditionExpression("#dataDeInclusao = :dataDeInclusao")
				.withExpressionAttributeNames(expressionAttributesNames)
				.withExpressionAttributeValues(expressionAttributeValues).withConsistentRead(false);

		return mapper.query(PessoaEntity.class, expression).get(0);

	}

}
