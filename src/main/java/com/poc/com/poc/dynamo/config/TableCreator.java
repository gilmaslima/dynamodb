package com.poc.com.poc.dynamo.config;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteTableRequest;
import com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndex;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.LocalSecondaryIndex;
import com.amazonaws.services.dynamodbv2.model.Projection;
import com.amazonaws.services.dynamodbv2.model.ProjectionType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;

/**
 * Created by gkatzioura on 6/23/16.
 */
public class TableCreator {

    private AmazonDynamoDB amazonDynamoDB;

    private static final Logger LOGGER = LoggerFactory.getLogger(TableCreator.class);

    public TableCreator(AmazonDynamoDB amazonDynamoDB) {
        this.amazonDynamoDB = amazonDynamoDB;
    }

    public void createUserTable() {

        List<KeySchemaElement> elements = new ArrayList<KeySchemaElement>();
        KeySchemaElement keySchemaElement = new KeySchemaElement()
                .withKeyType(KeyType.HASH)
                .withAttributeName("email");
        elements.add(keySchemaElement);

        List<AttributeDefinition> attributeDefinitions = new ArrayList<>();

        attributeDefinitions.add(new AttributeDefinition()
                .withAttributeName("email")
                .withAttributeType(ScalarAttributeType.S));

        CreateTableRequest createTableRequest = new CreateTableRequest()
                .withTableName("Users")
                .withKeySchema(elements)
                .withProvisionedThroughput(new ProvisionedThroughput()
                        .withReadCapacityUnits(5L)
                        .withWriteCapacityUnits(5L))
                .withAttributeDefinitions(attributeDefinitions);

        amazonDynamoDB.createTable(createTableRequest);
    }

    public void deleteUsersTable() {
        deleteTable("Users");
    }

    public void createLoginsTable() {

        List<KeySchemaElement> elements = new ArrayList<KeySchemaElement>();
        KeySchemaElement hashKey = new KeySchemaElement()
                .withKeyType(KeyType.HASH)
                .withAttributeName("email");
        
        KeySchemaElement rangeKey = new KeySchemaElement()
                .withKeyType(KeyType.RANGE)
                .withAttributeName("timestamp");
        elements.add(hashKey);
        elements.add(rangeKey);


        List<AttributeDefinition> attributeDefinitions = new ArrayList<>();

        attributeDefinitions.add(new AttributeDefinition()
                .withAttributeName("email")
                .withAttributeType(ScalarAttributeType.S));


        attributeDefinitions.add(new AttributeDefinition()
                .withAttributeName("timestamp")
                .withAttributeType(ScalarAttributeType.N));

        CreateTableRequest createTableRequest = new CreateTableRequest()
                .withTableName("Logins")
                .withKeySchema(elements)
                .withProvisionedThroughput(new ProvisionedThroughput()
                        .withReadCapacityUnits(5L)
                        .withWriteCapacityUnits(5L))
                .withAttributeDefinitions(attributeDefinitions);


        amazonDynamoDB.createTable(createTableRequest);
    }

    public void deleteLoginsTable() {
        deleteTable("Logins");
    }

    public void createSupervisor() {

        List<KeySchemaElement> elements = new ArrayList<>();
        KeySchemaElement hashKey = new KeySchemaElement()
                .withKeyType(KeyType.HASH)
                .withAttributeName("name");
        elements.add(hashKey);

        List<GlobalSecondaryIndex> globalSecondaryIndices = new ArrayList<>();

        ArrayList<KeySchemaElement> indexKeySchema = new ArrayList<>();

        indexKeySchema.add(new KeySchemaElement()
                .withAttributeName("company")
                .withKeyType(KeyType.HASH));  //Partition key
        indexKeySchema.add(new KeySchemaElement()
                .withAttributeName("factory")
                .withKeyType(KeyType.RANGE));  //Sort key


        GlobalSecondaryIndex factoryIndex = new GlobalSecondaryIndex()
                .withIndexName("FactoryIndex")
                .withProvisionedThroughput(new ProvisionedThroughput()
                        .withReadCapacityUnits((long) 10)
                        .withWriteCapacityUnits((long) 1))
                .withKeySchema(indexKeySchema)
                .withProjection(new Projection().withProjectionType(ProjectionType.ALL));
        globalSecondaryIndices.add(factoryIndex);

        List<AttributeDefinition> attributeDefinitions = new ArrayList<>();

        attributeDefinitions.add(new AttributeDefinition()
                .withAttributeName("name")
                .withAttributeType(ScalarAttributeType.S));
        attributeDefinitions.add(new AttributeDefinition()
                .withAttributeName("company")
                .withAttributeType(ScalarAttributeType.S));
        attributeDefinitions.add(new AttributeDefinition()
                .withAttributeName("factory")
                .withAttributeType(ScalarAttributeType.S));

        CreateTableRequest createTableRequest = new CreateTableRequest()
                .withTableName("Supervisors")
                .withKeySchema(elements)
                .withProvisionedThroughput(new ProvisionedThroughput()
                        .withReadCapacityUnits(5L)
                        .withWriteCapacityUnits(5L))
                .withGlobalSecondaryIndexes(factoryIndex)
                .withAttributeDefinitions(attributeDefinitions);

        amazonDynamoDB.createTable(createTableRequest);
    }

    public void deleteSupervisorsTable() {
        deleteTable("Supervisors");
    }

    public void createCompany() {

        List<KeySchemaElement> elements = new ArrayList<>();
        KeySchemaElement hashKey = new KeySchemaElement()
                .withKeyType(KeyType.HASH)
                .withAttributeName("name");
        KeySchemaElement rangeKey = new KeySchemaElement()
                .withKeyType(KeyType.RANGE)
                .withAttributeName("subsidiary");

        elements.add(hashKey);
        elements.add(rangeKey);

        List<LocalSecondaryIndex> localSecondaryIndices = new ArrayList<>();

        ArrayList<KeySchemaElement> indexKeySchema = new ArrayList<>();

        indexKeySchema.add(new KeySchemaElement()
                .withAttributeName("name")
                .withKeyType(KeyType.HASH));
        indexKeySchema.add(new KeySchemaElement()
                .withAttributeName("ceo")
                .withKeyType(KeyType.RANGE));

        LocalSecondaryIndex ceoIndex = new LocalSecondaryIndex()
                .withIndexName("CeoIndex")
                .withKeySchema(indexKeySchema)
                .withProjection(new Projection().withProjectionType(ProjectionType.ALL));
        localSecondaryIndices.add(ceoIndex);

        List<AttributeDefinition> attributeDefinitions = new ArrayList<>();

        attributeDefinitions.add(new AttributeDefinition()
                .withAttributeName("name")
                .withAttributeType(ScalarAttributeType.S));
        attributeDefinitions.add(new AttributeDefinition()
                .withAttributeName("subsidiary")
                .withAttributeType(ScalarAttributeType.S));
        attributeDefinitions.add(new AttributeDefinition()
                .withAttributeName("ceo")
                .withAttributeType(ScalarAttributeType.S));

        CreateTableRequest createTableRequest = new CreateTableRequest()
                .withTableName("Companies")
                .withKeySchema(elements)
                .withProvisionedThroughput(new ProvisionedThroughput()
                        .withReadCapacityUnits(5L)
                        .withWriteCapacityUnits(5L))
                .withLocalSecondaryIndexes(localSecondaryIndices)
                .withAttributeDefinitions(attributeDefinitions);

        amazonDynamoDB.createTable(createTableRequest);
    }
    public void deleteCompaniesTable() {
        deleteTable("Companies");
    }

    private void deleteTable(String tableName) {

        try {
            DeleteTableRequest deleteTableRequest = new DeleteTableRequest();
            deleteTableRequest.setTableName(tableName);
            amazonDynamoDB.deleteTable(deleteTableRequest);
        } catch (Exception e) {
            LOGGER.error("Table does not exist");
        }
    }

	public void createPessoa() {

		List<KeySchemaElement> elements = new ArrayList<KeySchemaElement>();
        KeySchemaElement keySchemaElement = new KeySchemaElement()
                .withKeyType(KeyType.HASH)
                .withAttributeName("id");
        elements.add(keySchemaElement);


        
        List<GlobalSecondaryIndex> globalSecondaryIndices = new ArrayList<>();

        ArrayList<KeySchemaElement> indexKeySchema = new ArrayList<>();

        indexKeySchema.add(new KeySchemaElement()
                .withAttributeName("idade")
                .withKeyType(KeyType.HASH));  //Partition key
        indexKeySchema.add(new KeySchemaElement()
                .withAttributeName("dataDeInclusao")
                .withKeyType(KeyType.RANGE));  //Sort key


        GlobalSecondaryIndex idadeIndex = new GlobalSecondaryIndex()
                .withIndexName("idadeIndex")
                .withProvisionedThroughput(new ProvisionedThroughput()
                        .withReadCapacityUnits((long) 10)
                        .withWriteCapacityUnits((long) 1))
                .withKeySchema(indexKeySchema)
                .withProjection(new Projection().withProjectionType(ProjectionType.ALL));
        globalSecondaryIndices.add(idadeIndex);

        
        
        List<AttributeDefinition> attributeDefinitions = new ArrayList<>();

        attributeDefinitions.add(new AttributeDefinition()
                .withAttributeName("id")
                .withAttributeType(ScalarAttributeType.S));
        
        attributeDefinitions.add(new AttributeDefinition()
                .withAttributeName("idade")
                .withAttributeType(ScalarAttributeType.N));
	
        attributeDefinitions.add(new AttributeDefinition()
                .withAttributeName("dataDeInclusao")
                .withAttributeType(ScalarAttributeType.N));
	
        
        CreateTableRequest createTableRequest = new CreateTableRequest()
                .withTableName("Pessoa")
                .withKeySchema(elements)
                .withProvisionedThroughput(new ProvisionedThroughput()
                        .withReadCapacityUnits(5L)
                        .withWriteCapacityUnits(5L))
                .withGlobalSecondaryIndexes(idadeIndex)
                .withAttributeDefinitions(attributeDefinitions);

        amazonDynamoDB.createTable(createTableRequest);

		/*
		 @DynamoDBHashKey(attributeName = "id")
	private String id;

	@DynamoDBAttribute(attributeName="nome")
	private String nome;

	@DynamoDBAttribute(attributeName="idade")
	private Integer idade;

	@DynamoDBAttribute(attributeName="dataDeInclusao")

		 */
		
	}

}
