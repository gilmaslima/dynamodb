package com.poc.com.poc.dynamo.config;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableCollection;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeTableResult;
import com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndex;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.dynamodbv2.model.Projection;
import com.amazonaws.services.dynamodbv2.model.ProjectionType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;

@Configuration
public class DynamoDbConfiguration {

	private static final Logger log = LoggerFactory.getLogger(DynamoDbConfiguration.class);

	private static final Long READ_WRITE_CAPACITY_UNIT = 5L;

	@Value("${aws.access.key}")
	private String user;

	@Value("${aws.secret.key}")
	private String password;

	@Value("${aws.region}")
	private String awsRegion;

	@Value("${aws.dynamodb.endpoint.use}")
	private boolean useEndpoint;

	@Value("${aws.dynamodb.endpoint.host}")
	private String dynamoEndpointHost;

	@Value("${aws.dynamodb.endpoint.port}")
	private Integer dynamoEndpointPort;

	private String tableName = "Pessoa";

	@Bean
	public AmazonDynamoDBClient amazonDynamoDBClient() {
		BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(user, password);
		AmazonDynamoDBClient dbClient = new AmazonDynamoDBClient(basicAWSCredentials);

		if (useEndpoint) {
			String dynamoEndpoint = "http://".concat(dynamoEndpointHost).concat(":")
					.concat(dynamoEndpointPort.toString());
			dbClient.withEndpoint(dynamoEndpoint);
		} else {
			dbClient.setRegion(Region.getRegion(Regions.fromName(awsRegion)));
		}
		configureTables(dbClient);
		return dbClient;
	}

	public void configureTables(AmazonDynamoDBClient dbClient) {
		try {
			if (existeTabela(dbClient, tableName)) {
				log.info("=========== Excluindo Tabela {}", tableName);
				dbClient.deleteTable(tableName);

			}
			log.info("=========== Criando Tabela {}{} e {}", tableName);

			createTable(dbClient, tableName);

		} catch (InterruptedException e) {
			log.error("Erro: ", e);
		}
	}

	private void createTable(AmazonDynamoDBClient dbClient, String tableName) throws InterruptedException {
		
		TableCreator tableCreator = new TableCreator(dbClient);
		tableCreator.createPessoa();
		
		
		log.info("=========== Tabelas criadas com sucesso ===========");
	}

	private boolean existeTabela(AmazonDynamoDBClient dbClient, String tableName) {
		try {
			DynamoDB dynamoDB = new DynamoDB(dbClient);

			TableCollection<ListTablesResult> tables = dynamoDB.listTables();
			Iterator<Table> iterator = tables.iterator();

			while (iterator.hasNext()) {
				Table table = iterator.next();
				// table.delete();
				if (tableName.equalsIgnoreCase(table.getTableName())) {
					DescribeTableResult describeTable = dbClient.describeTable(tableName);
					String tableStatus = describeTable.getTable().getTableStatus();
					return "ACTIVE".equals(tableStatus);
				}

			}
		} catch (Exception e) {
			log.error("Erro: ", e);
			return false;
		}

		log.info("=========== Tabela {} não encontrada =========", tableName);
		return false;
	}

	public String getTableName() {
		return tableName;
	}
}
