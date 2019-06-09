package com.xcase.aws;

import com.xcase.aws.constant.AWSConstant;
import com.xcase.aws.impl.simple.core.AWSConfigurationManager;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.http.SdkHttpClient;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.rds.RdsClient;
import software.amazon.awssdk.services.rds.RdsClientBuilder;
import software.amazon.awssdk.services.rds.model.CreateDbInstanceRequest;
import software.amazon.awssdk.services.rds.model.CreateDbInstanceResponse;
import software.amazon.awssdk.services.rds.model.DeleteDbInstanceRequest;
import software.amazon.awssdk.services.rds.model.DeleteDbInstanceResponse;
import software.amazon.awssdk.services.s3.*;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.CreateBucketResponse;
import software.amazon.awssdk.services.s3.model.DeleteBucketRequest;
import software.amazon.awssdk.services.s3.model.DeleteBucketResponse;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;
import software.amazon.awssdk.services.s3.model.S3Request;
import java.lang.invoke.MethodHandles;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AWSApplication {
    
    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    
    public static void main(String[] args) {
        try {
            LOGGER.debug("starting main()");
            String accessKey = AWSConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(AWSConstant.LOCAL_AWS_ACCESS_KEY_ID);
            LOGGER.debug("accessKey is " + accessKey);
            String secretKey = AWSConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(AWSConstant.LOCAL_AWS_SECRET_ACCESS_KEY);
            LOGGER.debug("secretKey is " + secretKey);
            SdkHttpClient apacheSdkHttpClient = ApacheHttpClient.builder().build();
            AwsCredentialsProvider awsCredentialsProvider = StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey));
            String localRegion = AWSConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(AWSConstant.LOCAL_AWS_REGION);
            LOGGER.debug("localRegion is " + localRegion);
            Region region = Region.of(localRegion);
            /* S3 operations */
            S3ClientBuilder s3ClientBuilder = S3Client.builder().httpClient(apacheSdkHttpClient).region(region).credentialsProvider(awsCredentialsProvider);
            S3Client s3Client = s3ClientBuilder.build();
            LOGGER.debug("created s3Client");
            ListBucketsResponse listBucketsResponse = s3Client.listBuckets();
            LOGGER.debug("got listBucketsResponse " + listBucketsResponse.toString());
            List<Bucket> bucketList = listBucketsResponse.buckets();
            if (bucketList != null) {
                LOGGER.debug("bucketList size is " + bucketList.size());
            } else {
                LOGGER.debug("bucketList is null");
            }
            
            String bucketName = "com.xcase.testbucket";
            CreateBucketRequest createBucketRequest = CreateBucketRequest.builder().bucket(bucketName).build();
            CreateBucketResponse createBucketResponse = s3Client.createBucket(createBucketRequest);
            listBucketsResponse = s3Client.listBuckets();
            LOGGER.debug("got listBucketsResponse " + listBucketsResponse.toString());
            bucketList = listBucketsResponse.buckets();
            if (bucketList != null) {
                LOGGER.debug("bucketList size is " + bucketList.size());
            } else {
                LOGGER.debug("bucketList is null");
            }
            
            DeleteBucketRequest deleteBucketRequest = DeleteBucketRequest.builder().bucket(bucketName).build();
            DeleteBucketResponse deleteBucketResponse = s3Client.deleteBucket(deleteBucketRequest);
            listBucketsResponse = s3Client.listBuckets();
            LOGGER.debug("got listBucketsResponse " + listBucketsResponse.toString());
            bucketList = listBucketsResponse.buckets();
            if (bucketList != null) {
                LOGGER.debug("bucketList size is " + bucketList.size());
            } else {
                LOGGER.debug("bucketList is null");
            }
            
            /* Database operations */
            RdsClientBuilder rdsClientBuilder = RdsClient.builder().httpClient(apacheSdkHttpClient).region(region).credentialsProvider(awsCredentialsProvider);
            RdsClient rdsClient = rdsClientBuilder.build();
            LOGGER.debug("created rdsClient");
            String dbInstanceIdentifier = "com-xcase-testdatabase";
            String dbName = "TestDatabase";
            CreateDbInstanceRequest createDbInstanceRequest = CreateDbInstanceRequest.builder().dbInstanceClass("db.t2.micro").engine("postgres").dbInstanceIdentifier(dbInstanceIdentifier).dbName(dbName).masterUsername("sa").masterUserPassword("Tsunami9").allocatedStorage(20).build();
            CreateDbInstanceResponse createDbInstanceResponse = rdsClient.createDBInstance(createDbInstanceRequest);
            LOGGER.debug("created dbInstance");
            DeleteDbInstanceRequest deleteDbInstanceRequest = DeleteDbInstanceRequest.builder().dbInstanceIdentifier(dbInstanceIdentifier).build();
            DeleteDbInstanceResponse deleteDbInstanceResponse = rdsClient.deleteDBInstance(deleteDbInstanceRequest);
            LOGGER.debug("deleted dbInstance");
        } catch (Exception e) {
            LOGGER.warn("exception executing AWS operations: " + e.getMessage());
        }

    }

}
